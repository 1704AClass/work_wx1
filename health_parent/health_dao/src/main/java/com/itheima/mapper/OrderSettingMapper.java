package com.itheima.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.OrderSetting;

public interface OrderSettingMapper {

	long findCountByOrderDate(Date orderDate);

	void editNumberByOrderDate(OrderSetting orderSetting);

	void add(OrderSetting orderSetting);

	List<OrderSetting> getOrderSettingByMonth(Map map);

}
