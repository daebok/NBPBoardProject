package com.hyunhye.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyunhye.board.model.Home;
import com.hyunhye.board.service.BoardService;
import com.hyunhye.board.service.CategoryService;

@Controller
public class HomeController {

	@Autowired
	public BoardService boardService;

	@Autowired
	private CategoryService categoryService;

	/**
	 * 게시글 목록을 가지고 홈 화면으로 이동
	 * @param model
	 * @return
	 */
	@RequestMapping("board")
	public String home(Model model) {
		model.addAttribute("categoryList", categoryService.categorySelectList());
		return "home";
	}

	/**
	 * 최신 순, 조회 순, 답변 순
	 * @param homeModel
	 * @param model
	 * @return
	 */
	@RequestMapping("board/home")
	public String boardTop10SelectList(@ModelAttribute Home homeModel, Model model) {
		model.addAttribute("model", boardService.boardTop10SelectList(homeModel));
		return "home-top";
	}

}