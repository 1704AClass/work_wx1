package com.itheima.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.mapper.SetmealMapper;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;

import redis.clients.jedis.JedisPool;

@Service
public class SetmealServiceImpl implements SetmealService {

	@Autowired
	private SetmealMapper mapper;
	@Autowired
	private JedisPool jedisPool;

	//新增套餐
	@Override
	public void add(Setmeal setmeal, Integer[] checkgroupIds) {
		mapper.add(setmeal);
		if(checkgroupIds!=null && checkgroupIds.length>0) {
			setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
		}
		savePic2Redis(setmeal.getImg());
	}
	private void savePic2Redis(String img) {
		jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,img);
	}
	//绑定套餐和检查组的多对多关系
	public void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds){
		for (Integer checkgroupId : checkgroupIds) {
			HashMap<String,Integer> map = new HashMap<>();
			map.put("Setmeal_id",id);
			map.put("checkgroup_Id", checkgroupId);
			mapper.setSetmealAndCheckGroup(map);
		}
	}
	@Override
	public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
		PageHelper.startPage(currentPage, pageSize);
		Page<Setmeal> page=mapper.selectByCondition(queryString);
		PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
		return pageResult;
	}
	@Override
	public List<Setmeal> findAll() {
		// TODO Auto-generated method stub
		return mapper.findAll();
	}
	@Override
	public Setmeal findById(int id) {
		return mapper.findById(id);
	}
	
	
}
