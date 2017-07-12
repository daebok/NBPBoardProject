package com.hyunhye.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hyunhye.board.dto.UserDto;
import com.hyunhye.board.serviceImpl.UserServiceImpl;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	public UserServiceImpl service;

	@RequestMapping("/insert")
	public String write(@ModelAttribute  UserDto dto) {
		 service.regist(dto);
		 return "redirect:/home.do";
		 

		/*String result = "0";

		logger.info("User Join AjaxData");
		
		model.addAttribute("request", request); 
		
		int resultValue = 0;
		resultValue = service.regist(model, dto);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/home.do");

		if (resultValue == 1) { 
			result = "1";
		}
		mv.addObject("result", result);
		return mv;*/

	}

	@RequestMapping(value = "/logincheck.do", method = RequestMethod.POST)
	public ModelAndView loginCheck(@ModelAttribute UserDto dto, HttpSession session ) {
		boolean result = service.loginCheck(session, dto);
		ModelAndView mv = new ModelAndView();
		if (result) {
			System.out.println("true");
			mv.setViewName("forward:/home.do");
			mv.addObject("msg", "success");
		} else {
			System.out.println("false");
			mv.setViewName("login");
			mv.addObject("msg", "failure");
		}
		System.out.println("session: " + session.getAttribute("NAME"));
		return mv;
	}

	@RequestMapping("logout.do")
	public ModelAndView logout(HttpSession session) {
		service.logout(session);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		mv.addObject("msg", "logout");
		return mv;
	}
}
