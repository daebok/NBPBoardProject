package com.hyunhye.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hyunhye.board.dto.QuestionDto;
import com.hyunhye.board.serviceImpl.QuestionServiceImpl;

@Controller
public class QuestionController {

	/*
	 * @Inject private QuestionService service;
	 */

	@Autowired
	public QuestionServiceImpl service;

	@RequestMapping(value = "/home.do")
	public String home(Model model) {
		service.listAll(model);

		return "home";
	}

	@RequestMapping("/question/ask")
	public String write(HttpServletRequest request, Model model, QuestionDto dto) {
		model.addAttribute("request", request);
		service.regist(model, dto);

		return "redirect:/home.do";
	}

	@RequestMapping("/answer.do")
	public String read(HttpServletRequest request, Model model, QuestionDto dto) {
		model.addAttribute("request", request);
		service.read(model, dto);

		return "answer";
	}

	// 01. 게시글 목록
	@RequestMapping("search.do")
	public ModelAndView list(Model model, @RequestParam(defaultValue = "TITLE") String searchOption,
			@RequestParam(defaultValue = "") String keyword) throws Exception {
		
		service.listAll(model, searchOption, keyword);
		// 레코드의 갯수
		int count = service.countArticle(searchOption, keyword);

		System.out.println(searchOption + keyword + "");

		// 데이터를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count); // 레코드의 갯수
		map.put("searchOption", searchOption); // 검색옵션
		map.put("keyword", keyword); // 검색키워드
		
		// ModelAndView - 모델과 뷰
		ModelAndView mv = new ModelAndView();
		mv.addObject("map", map); // 맵에 저장된 데이터를 mav에 저장
		mv.setViewName("search"); // 뷰를 list.jsp로 설정
		return mv; // list.jsp로 List가 전달된다.
	}

}
