package com.itheima.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.qiniu.util.Json;

import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Reference
	private MemberService service;
	@Autowired
	private JedisPool jedisPool;
	
	@RequestMapping("/login")
	public Result login(HttpServletResponse response,@RequestBody Map map) {
		String telephone=(String) map.get("telephone");
		String validateCode=(String) map.get("validateCode");
		String codeInRedis=jedisPool.getResource().get(telephone+RedisMessageConstant.SENDTYPE_LOGIN);
		if(codeInRedis==null || !codeInRedis.equals(validateCode)) {
			return new Result(false,MessageConstant.VALIDATECODE_ERROR);
		}else {
			Member member=service.findByTelephone(telephone);
			if(member==null) {
				member = new Member();
				member.setPhoneNumber(telephone);
				member.setRegTime(new Date());
				service.add(member);
				}
			//登录成功
			//写入cookie，跟踪用户
			Cookie cookie = new Cookie("login_member_telephone",telephone);
			cookie.setPath("/");
			cookie.setMaxAge(60*60*24*30);
			response.addCookie(cookie);
			String json = JSON.toJSON(member).toString();
			jedisPool.getResource().setex(telephone,60*30, json);
			return new Result(true, MessageConstant.LOGIN_SUCCESS);
		}
		
	}
}
