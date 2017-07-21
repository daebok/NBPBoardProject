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
	public String home(Model model) throws Exception {
		model.addAttribute("model", boardService.boardListAll());
		model.addAttribute("categoryList", boardService.categoryListAll());
		return "home";
	}
}