package com.hyunhye.user.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.service.UserServiceImpl;

@Controller
public class UserController {

	@Autowired
	public UserServiceImpl service;

	@RequestMapping(value = "/signup")
	public String signup() {
		return "user/signup";
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "user/login";
	}

	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	public String write(@ModelAttribute UserModel model) {
		service.regist(model);
		return "redirect:/login";
	}

	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	public ModelAndView loginCheck(@ModelAttribute UserModel model, HttpSession session) {
		boolean result = service.loginCheck(session, model);
		ModelAndView mv = new ModelAndView();
		if (result) {
			mv.setViewName("forward:/home");
			mv.addObject("msg", "success");
		} else {
			mv.setViewName("user/login");
			mv.addObject("msg", "failure");
		}
		return mv;
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		service.logout(session);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/login");
		mv.addObject("msg", "logout");
		return mv;
	}

	@RequestMapping(value = "/duplicationId", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody int duplicationId(@RequestBody String id) {
		return service.select(id);
	}
}
