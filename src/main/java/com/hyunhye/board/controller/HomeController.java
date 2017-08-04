package com.hyunhye.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyunhye.board.model.HomeModel;
import com.hyunhye.board.service.BoardService;
import com.hyunhye.board.service.HomeService;

@Controller
public class HomeController {

	@Autowired
	public BoardService boardService;

	@Autowired
	public HomeService homeService;

	/* 게시글 목록을 가지고 홈 화면으로 이동 */
	@RequestMapping("board")
	public String home(Model model) {
		model.addAttribute("categoryList", boardService.categoryListAll());
		return "home";
	}

	/* 최신 순 */
	@RequestMapping("board/home")
	public String homeFavorites(@ModelAttribute HomeModel homeModel, Model model) {
		model.addAttribute("model", homeService.selectBoardList(homeModel));
		return "home_top";
	}
}