package com.itheima.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;

import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Reference
	private OrderService service;
	@Autowired
	private JedisPool jedisPool;
	
	@RequestMapping("/submit")
	public Result submitOrder(@RequestBody Map map) {
		String telephone=(String) map.get("telephone");
		String codeInRedis=jedisPool.getResource().get(telephone+RedisMessageConstant.SENDTYPE_ORDER);
		String validateCode=(String) map.get("validateCode");
		if(codeInRedis==null || !codeInRedis.equals(validateCode)) {
			return new Result(false, MessageConstant.VALIDATECODE_ERROR);
		}
		Result result=null;
		try {
			map.put("orderType",com.itheima.pojo.Order.ORDERTYPE_WEIXIN);
			result=service.order(map);
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		if(result.isFlag()) {
			String orderDate=(String) map.get("orderDate");
			try {
				SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone, orderDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
