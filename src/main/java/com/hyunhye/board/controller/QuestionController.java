package com.hyunhye.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hyunhye.board.dto.QuestionDto;
import com.hyunhye.board.serviceImpl.QuestionServiceImpl;

@Controller
public class QuestionController {

	@Autowired
	public QuestionServiceImpl service;

	@RequestMapping(value = {"/", "/home.do"})
	public String home(Model model) {
		service.listAll(model);

		return "home";
	}

	@RequestMapping("/question/ask")
	public String write(HttpSession session, HttpServletRequest request, Model model, QuestionDto dto) {
		model.addAttribute("request", request);
		service.regist(session, model, dto);

		return "redirect:/home.do";
	}

	@RequestMapping("/answer.do")
	public ModelAndView read(HttpServletRequest request, Model model, QuestionDto dto) {
		model.addAttribute("request", request);
		service.read(model, dto);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("answer");
		mv.addObject("dto", service.read(model, dto));
		return mv;
	}

	@RequestMapping("search.do")
	public ModelAndView list(Model model, @RequestParam(defaultValue = "TITLE") String searchOption,
			@RequestParam(defaultValue = "") String keyword) throws Exception {

		service.listAll(model, searchOption, keyword);
		int count = service.countArticle(searchOption, keyword);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count); 
		map.put("searchOption", searchOption); 
		map.put("keyword", keyword); 

		ModelAndView mv = new ModelAndView();
		mv.addObject("map", map);  
		mv.setViewName("search"); 
		return mv; 
	}

	@RequestMapping("modify.do")
	public ModelAndView modify(HttpServletRequest request, Model model, QuestionDto dto) {
		model.addAttribute("request", request);
		service.read(model, dto);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("modify");
		mv.addObject("dto", service.read(model, dto));
		return mv;
	}
	
	@RequestMapping("/question/modify")
	public String modify(HttpSession session, HttpServletRequest request, Model model, QuestionDto dto) {
		model.addAttribute("request", request);
		service.modify(session, model, dto);

		return "forward:/answer.do?id="+dto.getBID();
	}
	
    @RequestMapping("delete.do")
    public String delete(@RequestParam int bid) throws Exception{
        service.delete(bid);
        return "redirect:home.do";
    }
}
