package com.kosta.bank.dao;

import java.util.List;

import java.util.Map;

import com.kosta.bank.dto.Account;

public interface AccountDao {
	//계좌개설
	void insertAccount(Account acc)throws Exception;
	
	//계좌조회
	Account selectAccount(String id)throws Exception;
	
	//입금,출금
	void updateAccountBalance(Account acc)throws Exception;
	
	//전체계좌조회
	List<Account> selectAccountList()throws Exception;
	
}
