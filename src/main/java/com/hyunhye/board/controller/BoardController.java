package com.hyunhye.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.service.BoardPager;
import com.hyunhye.board.service.BoardServiceImpl;

@Controller
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	public BoardServiceImpl service;

	@RequestMapping(value = {"/", "/home.do"})
	public String home(Model model) {
		service.listAll(model);

		return "home";
	}

	@RequestMapping(value = "/question.do")
	public String question(Model model) {
		List<CategoryModel> list = service.categoryListAll();
		model.addAttribute("list", list);
		return "/board/question";
	}

	// Paging
	@RequestMapping("list.do")
	public ModelAndView list(@RequestParam(defaultValue = "TITLE") String searchOption,
		@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int curPage)
		throws Exception {

		// questions count
		int count = service.countArticle(searchOption, keyword);

		BoardPager boardPager = new BoardPager(count, curPage);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();

		List<BoardModel> list = service.listAll(start, end, searchOption, keyword);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("count", count);
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("boardPager", boardPager);

		ModelAndView mv = new ModelAndView();
		mv.addObject("map", map);
		mv.setViewName("board/list");

		return mv;
	}


	@RequestMapping(value = "/question/ask", method = RequestMethod.POST)
	public String write(@ModelAttribute BoardModel model, HttpSession session) throws Exception {
		logger.info("model: " + model.getFiles());
		service.regist(session, model);
		return "redirect:/list.do";
	}

	@RequestMapping("/answer.do")
	public String read(@RequestParam int id, Model model) {
		model.addAttribute("model", service.read(id));
		return "board/answer";
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
		mv.setViewName("board/search");
		return mv;
	}

	@RequestMapping("modify.do")
	public String modify(@RequestParam int id, Model model) {
		model.addAttribute("model", service.read(id));
		model.addAttribute("list", service.categoryListAll()); // category
		return "board/modify";
	}

	@RequestMapping(value = "/question/modify", method = RequestMethod.POST)
	public String modify(HttpSession session, @ModelAttribute BoardModel model) {
		service.modify(session, model);

		return "forward:/answer.do?id=" + model.getBID();
	}

	@RequestMapping("delete.do")
	public String delete(@RequestParam int id, BoardModel model) throws Exception {
		service.delete(id, model);
		return "redirect:list.do";
	}
}
