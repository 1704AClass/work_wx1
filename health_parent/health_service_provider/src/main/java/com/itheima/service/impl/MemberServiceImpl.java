package com.itheima.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.MemberMapper;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper mapper;
	
	
	@Override
	public Member findByTelephone(String telephone) {
		return mapper.findByTelephone(telephone);
	}

	@Override
	public void add(Member member) {
		mapper.add(member);
	}

}
