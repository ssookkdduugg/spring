package com.kosta.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.api.dto.UserInfo;
import com.kosta.api.service.NaverLoginService;

@Controller
public class NaverLoginController {
   
   @Autowired
   private NaverLoginService naverLoginService;
   
   @Autowired
   private HttpSession session;
   
   // 로그인 버튼이 있는 프론트를 요청
	/*
	 * @RequestMapping(value="/naver", method=RequestMethod.GET) public String
	 * naver() { return "naverlogin"; }
	 */

/*
   @RequestMapping(value="/naverlogin", method=RequestMethod.GET)
   public String naverLogin() {
      return "callback";
   }
*/
   
   // naverlogin.jsp에서 호출되는 콜백함수로, callback.jsp를 사용하지 않는다
   @RequestMapping(value="/naverlogin", method=RequestMethod.GET)
   public ModelAndView naverLogin(String code, String state) {
      
      System.out.println("-----컨트롤러 메서드 naverLogin 호출");
      System.out.println("code: " + code + ", state: " + state);

      ModelAndView mav = new ModelAndView();
      
      try {
         UserInfo userInfo = naverLoginService.naverLogin(code, state);
         session.setAttribute("userInfo", userInfo);
         mav.addObject("userInfo", userInfo);
         mav.setViewName("naveruserInfo");
         
      } catch (Exception e) {
         e.printStackTrace();
         mav.addObject("err", "네이버 로그인 실패");
         mav.setViewName("error");
      }
      return mav;
   }
   
   
   

}