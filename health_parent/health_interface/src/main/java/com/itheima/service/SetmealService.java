package com.itheima.service;

import java.util.List;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;

public interface SetmealService {

	public void add(Setmeal setmeal,Integer[] checkgroupIds);

	public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

	public List<Setmeal> findAll();

	public Setmeal findById(int id);
}
