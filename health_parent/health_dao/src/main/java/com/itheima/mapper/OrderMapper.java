package com.itheima.mapper;

import java.util.List;
import java.util.Map;

import com.itheima.pojo.Order;

public interface OrderMapper {

     Integer findOrderCountByDate(String date);
     Integer findOrderCountAfterDate(String date);
     Integer findVisitsCountByDate(String date);
     Integer findVisitsCountAfterDate(String date);
     List<Map> findHotSetmeal();
     List<Order> findByCondition(Order order);

 	void add(Order order);

 	Map findByIdDetail(Integer id);

}
