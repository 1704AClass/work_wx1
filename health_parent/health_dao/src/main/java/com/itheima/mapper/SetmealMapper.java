package com.itheima.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

public interface SetmealMapper {

	void add(Setmeal setmeal);

	void setSetmealAndCheckGroup(Map<String, Integer> map);

	Page<Setmeal> selectByCondition(String queryString);

	@Select("select * from t_setmeal")
	List<Setmeal> findAll();

	@Select("select * from t_setmeal where id=#{id}")
	Setmeal findById(int id);
	
}
