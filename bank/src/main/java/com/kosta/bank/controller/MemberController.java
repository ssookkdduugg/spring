package com.kosta.bank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosta.bank.dto.Member;
import com.kosta.bank.service.MemberService;


@Controller
public class MemberController {
	
	// servlet-context.xml에 등록해둔 빈객체를 주입받는다 (빈의 id와 주입받아 사용하는 변수의 이름을 맞춰주는 것이 권장된다)
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private HttpSession session;
	
	
	
	// 로그인
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@RequestParam("id") String id, @RequestParam("password") String password, Model model) {
		try {
			Member member = memberService.login(id, password);
			member.setPassword("");
			session.setAttribute("member", member);
			return "makeaccount";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("err", e.getMessage());
			return "error";
		}
	}

	// 회원가입
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "join";
	}
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute Member member, Model model) {
		try {
			memberService.join(member);
			return "login";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("err", e.getMessage());
			return "error";
		}
	}
	
	// 로그아웃
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout() {
		session.removeAttribute("member");
		return "login";
	}

}