package com.kosta.board.service;

import com.kosta.board.dto.Member;

public interface MemberService {
	Member login(String id,String password)throws Exception;
	void join(Member member)throws Exception;
	Member userInfo(String id)throws Exception;

}
