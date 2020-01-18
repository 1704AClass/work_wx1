package com.itheima.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.mapper.CheckItemMapper;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;



@Service
public class CheckItemServiceImpl implements CheckItemService{
	
	@Autowired
	private CheckItemMapper	checkItemMapper;

	@Override
	public void add(CheckItem checkItem) {
		// TODO Auto-generated method stub
		checkItemMapper.add(checkItem);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		System.out.println(id);
		checkItemMapper.deleteAssocication(id);
		checkItemMapper.dels(id);
	}

	@Override
	public void edit(CheckItem checkItem) {
		// TODO Auto-generated method stub
		checkItemMapper.edit(checkItem);
	}

	@Override
	public CheckItem findById(Integer id) {
		// TODO Auto-generated method stub
		return checkItemMapper.findById(id);
	}

	@Override
	public List<CheckItem> findAll() {
		// TODO Auto-generated method stub
		return checkItemMapper.findAll();
	}

	@Override
	public PageResult pageQuery(QueryPageBean queryPageBean) {
		// TODO Auto-generated method stub
		//当前页
		Integer currentPage = queryPageBean.getCurrentPage();
		//总条数
		Integer pageSize = queryPageBean.getPageSize();
		//模糊
		String queryString = queryPageBean.getQueryString();
		//拼接字符串
		PageHelper.startPage(currentPage,pageSize);
		//page对象的数据
		Page<CheckItem> page = checkItemMapper.pageQuery(queryString);
		long total = page.getTotal();
		List<CheckItem> rows = page.getResult();
		return new PageResult(total, rows);
	}

	@Override
	public void dels(Integer[] ids) {
		for (Integer id : ids) {
			checkItemMapper.deleteAssocication(id);
			checkItemMapper.dels(id);
		}
	}
}
