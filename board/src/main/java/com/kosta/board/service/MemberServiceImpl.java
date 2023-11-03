package com.kosta.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.board.dao.MemberDao;
import com.kosta.board.dto.Member;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao memberDao;
	
	
	

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public Member login(String id, String password) throws Exception {
		Member member =	memberDao.selectMember(id);
		if(member==null)throw new Exception("아이디 오류");
		if(!member.getPassword().equals(password))throw new Exception("비밀번호 오류");
			return member;
	}

	@Override
	public void join(Member member) throws Exception {
		Member smember = memberDao.selectMember(member.getId());
		if(smember!=null)throw new Exception("아이디 중복");
		memberDao.insertMember(smember);
		
	}

	@Override
	public Member userInfo(String id) throws Exception {
		
		return memberDao.selectMember(id);
	}

}
