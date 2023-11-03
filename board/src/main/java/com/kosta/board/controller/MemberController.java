package com.kosta.board.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.board.dto.Member;
import com.kosta.board.service.MemberService;

@Controller
public class MemberController {
   
   @Autowired
   private MemberService memberService;
   
   @Autowired
   private HttpSession session;
   

   // 로그인
   @RequestMapping(value = "/login", method = RequestMethod.GET)
   public String login() {
      return "login";
   }
   @RequestMapping(value = "/login", method = RequestMethod.POST)
   public String login(@RequestParam("id") String id, @RequestParam("password") String password, Model model) {
      try {
         Member member = memberService.login(id, password);
         member.setPassword("");
         session.setAttribute("user", member);
         return "main";
         
      } catch (Exception e) {
         e.printStackTrace();
         model.addAttribute("err", e.getMessage());
         return "error";
      }
      
   }
   
   
   // 로그아웃
   @RequestMapping(value = "/logout", method = RequestMethod.GET)
   public String logout() {
      session.removeAttribute("user");
      return "login";
   }
   
   // 회원가입
   @RequestMapping(value = "/join", method = RequestMethod.GET)
   public String join() {
      return "join";
   }
   @RequestMapping(value = "/join", method = RequestMethod.POST)
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
   
   //idcheck
   @RequestMapping(value = "/idcheck", method = RequestMethod.POST)
   @ResponseBody //비동기라서 view없이 data자체를 리턴한다.
   public String idCheck(@RequestParam("id") String id) {
      
      try {
         Member member = memberService.userInfo(id);
         if(member==null) return "notexist";
         return "exist";
         
      } catch (Exception e) {
         e.printStackTrace();
         return "exist";
      }
   }
   
   
   
   
}