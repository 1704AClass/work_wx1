package com.itheima.service;

import java.util.List;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;



public interface CheckItemService {

	void add(CheckItem checkItem);

	void delete(Integer id);

	void edit(CheckItem checkItem);

	CheckItem findById(Integer id);

	List<CheckItem> findAll();

	PageResult pageQuery(QueryPageBean queryPageBean);

	void dels(Integer[] ids);

}
