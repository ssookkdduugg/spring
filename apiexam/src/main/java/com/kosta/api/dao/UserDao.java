package com.kosta.api.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.api.dto.UserInfo;

@Repository
public class UserDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public void insertUser(UserInfo userInfo)throws Exception{
		sqlSession.insert("mapper.user.insertUser",userInfo);
	}
	
	public void insertUserByKakao(UserInfo userInfo) throws Exception {
		sqlSession.insert("mapper.user.insertUserByKakao", userInfo);
	}

	public void insertUserByNaver(UserInfo userInfo) throws Exception {
		sqlSession.insert("mapper.user.insertUserByNaver", userInfo);
	}

	public UserInfo selectUser(String id) throws Exception {
		return sqlSession.selectOne("mapper.user.selectUser", id);
	}
}
