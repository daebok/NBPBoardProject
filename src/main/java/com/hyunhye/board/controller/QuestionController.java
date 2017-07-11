package com.hyunhye.board.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.hyunhye.board.serviceImpl.BoardPager;
import com.hyunhye.board.serviceImpl.QuestionServiceImpl;

@Controller
public class QuestionController {

	@Autowired
	public QuestionServiceImpl service;

	@RequestMapping(value = { "/", "/home.do" })
	public String home(Model model) {
		service.listAll(model);

		return "home";
	}

	// Paging
	@RequestMapping("list.do")
	// @RequestParam(defaultValue="") ==> �⺻�� �Ҵ� : ������������ 1�� �ʱ�ȭ
	public ModelAndView list(@RequestParam(defaultValue = "TITLE") String searchOption,
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int curPage, QuestionDto dto)
			throws Exception {

		// ���ڵ��� ���� ���
		int count = service.countArticle(searchOption, keyword);
		
		System.out.println("count: "+count);

		// ������ ������ ���� ó��
		BoardPager boardPager = new BoardPager(count, curPage);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();

		List<QuestionDto> list = service.listAll(start, end, searchOption, keyword, dto);

		Iterator<QuestionDto> it = list.iterator();
		System.out.println("list.size(): "+ list.size());
		while(it.hasNext())
			System.out.println(it.next().getTITLE());
		
		// �����͸� �ʿ� ����
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list); // list
		map.put("count", count); // ���ڵ��� ����
		map.put("searchOption", searchOption); // �˻��ɼ�
		map.put("keyword", keyword); // �˻�Ű����
		map.put("boardPager", boardPager);

		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map); // �ʿ� ����� �����͸� mav�� ����
		mav.setViewName("list"); // �並 list.jsp�� ����

		return mav; // list.jsp�� List�� ���޵ȴ�.
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

		return "forward:/answer.do?id=" + dto.getBID();
	}

	@RequestMapping("delete.do")
	public String delete(@RequestParam int id, QuestionDto dto) throws Exception {
		service.delete(id, dto);
		return "redirect:home.do";
	}
}
