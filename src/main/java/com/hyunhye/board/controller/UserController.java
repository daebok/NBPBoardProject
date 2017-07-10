package com.hyunhye.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hyunhye.board.dto.UserDto;
import com.hyunhye.board.serviceImpl.UserServiceImpl;

@Controller
public class UserController {

	@Autowired
	public UserServiceImpl service;
	
	@RequestMapping("/insert")
	public String write(HttpServletRequest request, Model model, UserDto dto) {
		model.addAttribute("request", request);
		service.regist(model, dto);

		return "redirect:/home.do";
	}
	
	@RequestMapping(value="/logincheck.do", method=RequestMethod.POST)
	public ModelAndView loginCheck(HttpServletRequest request, Model model,UserDto dto){
		System.out.println(request.getAttribute("ID"));
		System.out.println(model.containsAttribute("ID"));
		
		model.addAttribute("request", request);
		HttpSession session = request.getSession(true);
		
		boolean result = service.loginCheck(session, model, dto);
		ModelAndView mv = new ModelAndView();
		if(result){
			System.out.println("true");
			mv.setViewName("forward:/home.do");
			mv.addObject("msg","success");
		} else{
			System.out.println("false");
			mv.setViewName("login");
			mv.addObject("msg","failure");
		}
		System.out.println("session: "+session.getAttribute("NAME"));
		return mv;
	}
	
    @RequestMapping("logout.do")
    public ModelAndView logout(HttpSession session){
        service.logout(session);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        mv.addObject("msg", "logout");
        return mv;
    }

	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model, UserDto dto) {
		model.addAttribute("request", request);
		service.delete(model, dto);
		return "redirect:/home.do";
	}
}
