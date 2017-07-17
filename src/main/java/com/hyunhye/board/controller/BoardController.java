package com.hyunhye.board.controller;

import java.util.List;

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

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.PageMaker;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.service.BoardServiceImpl;
import com.hyunhye.comment.service.CommentServiceImpl;

@Controller
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	public BoardServiceImpl service;

	@Autowired
	public CommentServiceImpl commentService;

	@RequestMapping(value = {"/", "home"})
	public String home(Model model) {
		service.listAll(model);

		return "home";
	}

	@RequestMapping(value = "question")
	public String readCategory(Model model) { // get category
		List<CategoryModel> list = service.categoryListAll();
		model.addAttribute("list", list);
		return "/board/question";
	}

	@RequestMapping("list")
	public String list(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		model.addAttribute("list", service.listCriteria(cri));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listCountCriteria(cri));

		model.addAttribute("pageMaker", pageMaker);

		return "board/list";
	}

	@RequestMapping(value = "question/ask", method = RequestMethod.POST)
	public String write(@ModelAttribute BoardModel model, HttpSession session) throws Exception {
		service.regist(session, model);
		return "redirect:/list.do";
	}

	@RequestMapping("answer")
	public String read(@RequestParam("id") int boardId, @ModelAttribute("cri") Criteria cri, Model model)
		throws Exception {
		model.addAttribute("model", service.read(boardId));
		model.addAttribute("attach", service.getAttach(boardId));
		model.addAttribute("comment", commentService.listComment(boardId));
		return "board/answer";
	}

	@RequestMapping("modify")
	public String update(@RequestParam int id, Model model) {
		model.addAttribute("model", service.read(id));
		model.addAttribute("list", service.categoryListAll()); // category
		return "board/modify";
	}

	@RequestMapping(value = "/question/modify", method = RequestMethod.POST)
	public String modify(HttpSession session, @ModelAttribute BoardModel model) {
		service.modify(session, model);

		return "forward:/answer?id=" + model.getBID();
	}

	@RequestMapping("delete")
	public String delete(@RequestParam int id, BoardModel model) throws Exception {
		service.delete(id, model);
		return "redirect:list";
	}
}
