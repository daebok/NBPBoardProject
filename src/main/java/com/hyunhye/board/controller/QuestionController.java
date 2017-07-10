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

	// 01. �Խñ� ���
	@RequestMapping("search.do")
	public ModelAndView list(Model model, @RequestParam(defaultValue = "TITLE") String searchOption,
			@RequestParam(defaultValue = "") String keyword) throws Exception {
		
		service.listAll(model, searchOption, keyword);
		// ���ڵ��� ����
		int count = service.countArticle(searchOption, keyword);

		System.out.println(searchOption + keyword + "");

		// �����͸� �ʿ� ����
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count); // ���ڵ��� ����
		map.put("searchOption", searchOption); // �˻��ɼ�
		map.put("keyword", keyword); // �˻�Ű����
		
		// ModelAndView - �𵨰� ��
		ModelAndView mv = new ModelAndView();
		mv.addObject("map", map); // �ʿ� ����� �����͸� mav�� ����
		mv.setViewName("search"); // �並 list.jsp�� ����
		return mv; // list.jsp�� List�� ���޵ȴ�.
	}

}
