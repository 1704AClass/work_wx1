package com.itheima.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;

@Service
public class OrderSettingServiceImpl implements OrderSettingService {
	@Autowired
	private OrderSettingMapper orderSettingMapper;

	//批量添加
	@Override
	public void add(List<OrderSetting> list) {
		// TODO Auto-generated method stub
		if(list != null && list.size() > 0){
			for (OrderSetting orderSetting : list) {
				//检查此数据（日期）是否存在
				long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
				if(count > 0){
					//已经存在，执行更新操作
					orderSettingMapper.editNumberByOrderDate(orderSetting);
				}else{
					//不存在，执行添加操作
					orderSettingMapper.add(orderSetting);
				}
			}
		}
	}

	//根据日期查询预约设置数据
	@Override
	public List<Map> getOrderSettingByMonth(String date) {
		String dateBegin = date + "‐1";//2019‐3‐1
		String dateEnd = date + "‐31";//2019‐3‐31
		Map map = new HashMap();
		map.put("dateBegin",dateBegin);
		map.put("dateEnd",dateEnd);
		List<OrderSetting> list = orderSettingMapper.getOrderSettingByMonth(map);
		List<Map> data = new ArrayList<>();
		for (OrderSetting orderSetting : list) {
		Map orderSettingMap = new HashMap();
		//获得日期（几号）
		orderSettingMap.put("date",orderSetting.getOrderDate().getDate());
		//可预约人数
		orderSettingMap.put("number",orderSetting.getNumber());
		//已预约人数
		orderSettingMap.put("reservations",orderSetting.getReservations());//
		
		data.add(orderSettingMap);
		}
		return data;
	}

	//根据日期修改可预约人数
	@Override
	public void editNumberByDate(OrderSetting orderSetting) {
		// TODO Auto-generated method stub
		long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
				if(count > 0){
					//当前日期已经进行了预约设置，需要进行修改操作
					orderSettingMapper.editNumberByOrderDate(orderSetting);
				}else{
					//当前日期没有进行预约设置，进行添加操作
					orderSettingMapper.add(orderSetting);
				}
	}
}