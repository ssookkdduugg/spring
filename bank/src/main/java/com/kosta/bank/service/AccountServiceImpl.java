package com.kosta.bank.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.bank.dao.AccountDao;
import com.kosta.bank.dto.Account;

@Service //service를붙이면 servlet-context.xml에 있는 bean id="accountServie"인 bean 안만들어도됨
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDao accountDao;
	
	
	public AccountDao getAccountDao() {
		return accountDao;
	}


	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}



	@Override
	public void makeAccount(Account acc)throws Exception {
		if(acc.getBalance()<0) {
			throw new Exception("입금 금액을 다시 확인해주세요.");
		} else {
			accountDao.insertAccount(acc);
		}
	}


	@Override
	public Account accountInfo(String id)throws Exception {
		Account acc = accountDao.selectAccount(id);
		if(acc == null)throw new Exception("계좌번호 오류");
		return acc;
	}

	
	@Override //입금
	public void deposit(String id, Integer money) throws Exception {
		Account acc = accountInfo(id);
		acc.deposit(money);
		Map<String, Object> param = new HashMap<>();
		param.put("id", acc.getId());
		param.put("balance", acc.getBalance());
		 accountDao.updateAccountBalance(acc);	
	}

	
	@Override //출금
	public void withdraw(String id, Integer money) throws Exception {
		Account acc = accountInfo(id);
		acc.withdraw(money);
		Map<String, Object> param = new HashMap<>();
		param.put("id", acc.getId());
		param.put("balance", acc.getBalance());
		accountDao.updateAccountBalance(acc);	
	}



	@Override
	public List<Account> allAccountList() throws Exception {
		return accountDao.selectAccountList();
	}



	



	
	
	



	

}
