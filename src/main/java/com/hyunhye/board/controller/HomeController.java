package com.hyunhye.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyunhye.board.service.BoardService;

@Controller
public class HomeController {
	@Autowired
	public BoardService boardService;
	/* 게시글 목록을 가지고 홈 화면으로 이동 */
	@RequestMapping("board")
	public String home(Model model) {
		model.addAttribute("categoryList", boardService.categoryListAll());
		return "home";
	}

	/* 조회수가 가장 많은 순 */
	@RequestMapping("board/views")
	public String homeViews(Model model) {
		model.addAttribute("model", boardService.boardListAll());
		return "home_views";
	}

	/* 즐겨찾기를 가장 많이 한 순 */
	@RequestMapping("board/favorites")
	public String homeFavorites(Model model) {
		return "home_views";
	}

	/* 답변 수가 가장 많은 순 */
	@RequestMapping("board/answers")
	public String homeAnswers(Model model) {
		model.addAttribute("model", boardService.boardListTopAnswers());
		return "home_views";
	}
}