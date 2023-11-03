package com.kosta.board.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.board.dto.Member;

@Repository
public class MemberDaoImpl implements MemberDao{
	
	@Autowired
	private SqlSessionTemplate sqlSessoin;
	
	@Override
	public Member selectMember(String id) throws Exception {
		return sqlSessoin.selectOne("mapper.member.selectMember",id);
	}

	@Override
	public void insertMember(Member member) throws Exception {
		sqlSessoin.insert("mapper.member.insertMember",member);
	}

}
