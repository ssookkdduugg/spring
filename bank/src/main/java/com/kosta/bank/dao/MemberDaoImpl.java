package com.kosta.bank.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.kosta.bank.dto.Member;

@Repository
public class MemberDaoImpl implements MemberDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessoin;
	
	

	public void setSqlSessoin(SqlSessionTemplate sqlSessoin) {
		this.sqlSessoin = sqlSessoin;
	}

	@Override
	public Member selectMember(String id) throws Exception {
		return sqlSessoin.selectOne("mapper.member.selectMember",id);
	}

	@Override
	public void insertMember(Member member) throws Exception {
		sqlSessoin.insert("mapper.member.insertMember",member);
	}

}
