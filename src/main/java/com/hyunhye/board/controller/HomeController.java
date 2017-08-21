package com.hyunhye.board.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hyunhye.board.model.Home;
import com.hyunhye.board.service.BoardService;
import com.hyunhye.board.service.CategoryService;
import com.hyunhye.utils.UriUtils;

@Controller
public class HomeController {

	@Autowired
	public BoardService boardService;

	@Autowired
	private CategoryService categoryService;

	/**
	 * 홈 화면으로 이동
	 * @param request
	 * @param model
	 * @return 홈 화면 페이지
	 */
	@RequestMapping("board")
	public String home(HttpServletRequest request, Model model) {
		UriUtils.getUri(request);
		model.addAttribute("categoryList", categoryService.selectAllCategoryList());
		return "home";
	}

	/**
	 * 최신 순, 조회 순, 답변 순으로 게시글 목록 중에 10개만 보여줌
	 * @param home 탭 번호
	 * @param model
	 * @return tab에 해당하는 홈 화면 게시글 리스트
	 */
	@RequestMapping(value = "board/home", method = RequestMethod.GET)
	public String boardTop10SelectList(@ModelAttribute Home home, Model model) {
		model.addAttribute("model", boardService.boardTop10SelectList(home));
		return "home-top";
	}

	/**
	 * 홈 화면 업데이트.
	 * 기존에 저장한 홈화면 게시판 리스트 캐시 삭제
	 * @return 홈 화면 페이지
	 */
	@RequestMapping("/board/home/update")
	public String boardTop10Update() {
		boardService.boardTop10Update();
		return "home";
	}

}