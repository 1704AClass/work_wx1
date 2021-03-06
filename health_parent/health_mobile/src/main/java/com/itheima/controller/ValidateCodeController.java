package com.itheima.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;

import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

	@Autowired
	private JedisPool jedisPool;

	//体检时预约时发送手机验证码
	@RequestMapping("/sendOrder")
	public Result sendOrder(String telephone) {
		Integer code = ValidateCodeUtils.generateValidateCode(4);
		try {
			SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
		}
		System.out.println("发送的验证码为："+code);
		//将生成的验证码缓存到redis
		jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_ORDER,5*60,code.toString());
		return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
	}
	@RequestMapping("/send4Login")
	public Result send4Login(String telephone) {
		Integer code = ValidateCodeUtils.generateValidateCode(6);
		try {
			SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
		} catch (Exception e) {
			return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
		}
		System.out.println("您的验证码为"+code);
		jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_LOGIN,5*60,code.toString());
		return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
	}
	
}