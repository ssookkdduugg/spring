package com.kosta.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.api.dto.ElecCharge;
import com.kosta.api.dto.PageInfo;
import com.kosta.api.service.ElecChargeService;

@Controller
public class ElecChargeController {
	
	@Autowired
	private ElecChargeService elecChargeService;
	
	@RequestMapping(value={"/eleccharge/{page}","/eleccharge"}, method=RequestMethod.GET)	
	public ModelAndView elecChargeList(@PathVariable(required = false)Integer page) {
		PageInfo pageInfo = new PageInfo();
		if(page==null) {
			pageInfo.setCurPage(1);
		}else {
			pageInfo.setCurPage(page);
		}
		ModelAndView mav = new ModelAndView();
		try {	
		List<ElecCharge> elecCharge = elecChargeService.elecChargeList(pageInfo);
		mav.addObject("elecCharge",elecCharge);
		mav.addObject("pageInfo",pageInfo);
		mav.setViewName("elect_charge");
		} catch (Exception e) {
			e.printStackTrace();
			mav.setViewName("error");
		}
		return mav;
	}

}
