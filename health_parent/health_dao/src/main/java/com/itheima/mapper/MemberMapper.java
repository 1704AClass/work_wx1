package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.pojo.Member;

public interface MemberMapper {

	    public Page<Member> selectByCondition(String queryString);
	    public void deleteById(Integer id);
	    public Member findById(Integer id);
	    public void edit(Member member);
	    public Integer findMemberCountBeforeDate(String date);
	    public Integer findMemberCountByDate(String date);
	    public Integer findMemberCountAfterDate(String date);
	    public Integer findMemberTotalCount();
	    Member findByTelephone(String telephone);
		void add(Member member);


}
