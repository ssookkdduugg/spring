package com.kosta.bank.service;

import java.util.List;
import java.util.Map;

import com.kosta.bank.dto.Account;

public interface AccountService {
	//계좌개설
	void makeAccount(Account acc)throws Exception;
	//계좌조회
	Account accountInfo(String id) throws Exception;
	//입금
	void deposit(String id,Integer money)throws Exception;
	//출금
	void withdraw(String id,Integer money)throws Exception;
	//전체 계좌 조회 
	List<Account> allAccountList()throws Exception;
}
