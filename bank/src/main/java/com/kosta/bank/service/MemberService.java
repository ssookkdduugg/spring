package com.kosta.bank.service;

import com.kosta.bank.dto.Member;

public interface MemberService {
	Member login(String id,String password)throws Exception;
	void join(Member member)throws Exception;

}
