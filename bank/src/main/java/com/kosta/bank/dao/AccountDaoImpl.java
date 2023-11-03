package com.kosta.bank.dao;

import java.util.List;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.kosta.bank.dto.Account;

@Repository
public class AccountDaoImpl implements AccountDao {
	
	//context-mapper.xml의 bean id="sqlSessoin"가져다씀.
	//필드 private SqlSessionTemplate sqlSession;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	

	public SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	
	//계좌 개설
	@Override
	public void insertAccount(Account acc) throws Exception {
		sqlSession.insert("mapper.account.insertAccount",acc);
	}

	//계좌 조회
	@Override
	public Account selectAccount(String id) throws Exception {
		return sqlSession.selectOne("mapper.account.selectAccount",id);
	}

	//전체계좌조회
	@Override
	public List<Account> selectAccountList() throws Exception {
		return sqlSession.selectList("mapper.account.selectAccountList");
	}

	//입금,출금
	@Override
	public void updateAccountBalance(Account acc) throws Exception {
		sqlSession.update("mapper.account.updateBalance",acc);
		
	}

	

}
