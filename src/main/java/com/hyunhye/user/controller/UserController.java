package com.hyunhye.user.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.service.UserServiceImpl;

@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public UserServiceImpl service;

	@RequestMapping(value = "/signup.do")
	public String signup() {
		return "user/signup";
	}

	@RequestMapping(value = "/login.do")
	public String login() {
		return "userlogin";
	}

	@RequestMapping("/insert")
	public String write(@ModelAttribute UserModel dto) {
		service.regist(dto);
		return "redirect:/home.do";
	}

	@RequestMapping(value = "/logincheck.do", method = RequestMethod.POST)
	public ModelAndView loginCheck(@ModelAttribute UserModel dto, HttpSession session) {
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
		return mv;
	}

	@RequestMapping("logout.do")
	public ModelAndView logout(HttpSession session) {
		service.logout(session);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/login");
		mv.addObject("msg", "logout");
		return mv;
	}
}
