package com.kosta.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.api.dto.AnimalClinic;
import com.kosta.api.dto.PageInfo;
import com.kosta.api.service.SeoulApiService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SeoulApiController {
	
	@Autowired
	private SeoulApiService seoulApiService;
	
	// 리스트
	@RequestMapping(value = {"/clinic/{page}", "/clinic"}, method = RequestMethod.GET)
	public ModelAndView animalClinicList(@PathVariable(required=false) Integer page) {
	// <org.springframework-version>4.3.3.RELEASE</org.springframework-version> 스프링 버전을 바꾸어서 @PathVariable(required=false) false로 지정 가능, 단 값이 없을 수 있다면 int가 아닌 Integer로 매개변수를 선언해야함
		
		if(page==null) page=1;
		ModelAndView mav = new ModelAndView();
		
		try {
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCurPage(page);
			List<AnimalClinic> acList = seoulApiService.animalClinicList(pageInfo);
			mav.addObject("acList", acList);
			mav.addObject("pageInfo", pageInfo);
			mav.setViewName("animalclinic");
			
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", "서울시 동물병원 허가 정보 조회 실패");
			mav.setViewName("error");
		}
		return mav;
	}

	
	
	
	
}
