package com.kosta.bank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.bank.dto.Account;
import com.kosta.bank.service.AccountService;

@Controller
public class AccountController {
   
   // servlet-context.xml에 등록해둔 빈을 주입받는다 (빈의 id와 주입받아 사용하는 변수의 이름을 맞춰주는 것이 권장된다)
   @Autowired
   private AccountService accountService;
   
   
   // 핸들러 메서드
   // 0. 메인
   @RequestMapping(value="/", method=RequestMethod.GET)
   public String main() {
      return "main"; // 응답할 jsp파일명으로, servlet-context.xml에 지정된 prefix와 suffix가 붙어서 뷰리졸버를 통해 페이지가 응답된다.
   }

   // 1. 계좌 개설
   @RequestMapping(value="/makeaccount", method=RequestMethod.GET)
   public String makeAccount() {
      return "makeaccount";
   }   
   @RequestMapping(value="/makeaccount", method=RequestMethod.POST)
   public String makeAccount(@ModelAttribute Account acc, Model model) {
     //ModelAttribute는 (input태그의 사용자 입력값 가져옴)
	   //Model 뷰로 보낼때 뷰에 전달할 데이터 담을 공간 
	   //jsp파일 input태그의name이랑 dto필드명이랑 같아야하고 modelattribute는 싸서 전체적으로 가져온다
	   //servlet때는 하나하나씩 가져왔어서 불편 
	   
	   //대신 하나씩 가져오고싶을때는 @RequestParam("input태그의 name명") 
	   
	   //request.setattribute를해서 account 객체 담았었다면,
	   //model에 담아서 jsp에 뿌려줘야한다. model.addattribute
	   try {
         accountService.makeAccount(acc);
         Account sacc = accountService.accountInfo(acc.getId());
         model.addAttribute("acc", sacc);
         return "accountinfo";
         
      } catch (Exception e) {
         e.printStackTrace();
         model.addAttribute("err", e.getMessage());
         return "error";
      }
   }
   
   
   // 2. 입금
   @RequestMapping(value="/deposit", method=RequestMethod.GET)
   public String deposit() {
      return "deposit";
   }
   
   @RequestMapping(value="/deposit", method=RequestMethod.POST)
   public ModelAndView deposit(@RequestParam("id") String id, @RequestParam("money") Integer money, Model model) {
	// (1)HttpServlet Request객체에서 getParameter("money")해서 뽑으면 String이므로 Integer로 파싱하는 단계가 필요하지만 @RequestParam("money")를 통해 뽑으면 바로 Integer로 사용가능
	
	// (2)ModelAndView : 모델과 뷰를 분리하지 않고 함께 전달할 수 있다.
	   //mav 뷰에 뿌려줄 데이터랑, jsp이름을 한번에 담아서 한번에 뿌려준다. return mav
	   ModelAndView mav = new ModelAndView();
	   try {
		   accountService.deposit(id, money);
		   Account sacc = accountService.accountInfo(id);
		   mav.addObject("acc",sacc);
		   mav.setViewName("accountinfo");
	   }catch (Exception e) {
		   e.printStackTrace();
		   mav.addObject("err",e.getMessage());
		   mav.setViewName("error");
	}
	   return mav;
   }
   
   
   
   //출금
   @RequestMapping(value="/withdraw", method=RequestMethod.GET)
   public String withdraw() {
      return "withdraw";
   }
   
   @RequestMapping(value="/withdraw", method=RequestMethod.POST)
   public ModelAndView withdraw(@RequestParam("id") String id, @RequestParam("money") Integer money, Model model) {
      ModelAndView mav = new ModelAndView();
      try {
		accountService.withdraw(id, money);
		Account sacc = accountService.accountInfo(id);
		mav.addObject("acc",sacc);
		mav.setViewName("accountinfo");
	} catch (Exception e) {
		e.printStackTrace();
		mav.addObject("err",e.getMessage());
		mav.setViewName("error");
	}
	   return mav;
   }
   
   
      
   
   // 3. 계좌 조회 , Model model말고 String id로 수정해야함.
   @RequestMapping(value="/accountinfo", method=RequestMethod.GET)
   public String accountinfo() {
      return "accountinfoform";
   }
   
   //accountinfo input type의 name이랑 @RequestParam String id 이름이랑 같아야함.
   @RequestMapping(value="/accountinfo", method=RequestMethod.POST)
   public String accountinfo(@RequestParam("id") String id, Model model) {
      try {
         Account sacc = accountService.accountInfo(id);
         model.addAttribute("acc", sacc);
         return "accountinfo";
         
      } catch (Exception e) {
         e.printStackTrace();
         model.addAttribute("err", "계좌조회 실패");
         return "error";
      }
   }
   
   
   // 4. 전체 계좌 조회
   @RequestMapping(value="/allaccountinfo", method=RequestMethod.GET)
   public String allaccountinfo(Model model) {
	   try {
		 List<Account> accs = accountService.allAccountList();
		model.addAttribute("accs",accs);
		return "allaccountinfo";
	} catch (Exception e) {
		model.addAttribute("err","전체계좌 조회 실패");
		return "error";
	}
	   
	   
   }
   
  
   
}