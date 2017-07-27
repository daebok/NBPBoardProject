package com.hyunhye.board.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.PageMaker;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.service.BoardService;
import com.hyunhye.comment.service.CommentService;
import com.hyunhye.user.model.UserModelDetails;

@RequestMapping("board")
@Controller
public class BoardController {

	@Autowired
	public BoardService boardService;

	@Autowired
	public CommentService commentService;

	/* 질문하기 페이지 이동  */
	@RequestMapping("question")
	public String question(Model model) {
		List<CategoryModel> list = boardService.categoryListAll();
		model.addAttribute("list", list);
		return "/board/question";
	}

	/* 리스트 목록 보기 (페이징) */
	@RequestMapping("list")
	public String listCriteria(@ModelAttribute("cri") SearchCriteria cri, Model model) {
		model.addAttribute("list", boardService.listCriteria(cri));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.listCountCriteria(cri));

		model.addAttribute("categoryList", boardService.categoryListAll());
		model.addAttribute("pageMaker", pageMaker);

		return "board/list";
	}

	/* 게시글 작성하기  */
	@RequestMapping(value = "question/ask", method = RequestMethod.POST)
	public String boardRegist(@ModelAttribute BoardModel model, Principal principal) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boardService.boardRegist(user.getUserNo(), model);
		return "redirect:/board/list";
	}

	/* 게시글 상세 보기 */
	@RequestMapping("answer")
	public String boardSelect(@RequestParam("boardNo") int boardNo, @ModelAttribute("cri") SearchCriteria cri,
		Model model, HttpServletRequest request, HttpServletResponse response, Principal principal) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		/* 조회수 */
		Cookie viewCount = WebUtils.getCookie(request, boardNo + "&" + user.getUserNo());
		int cookieMaxAge = 60 * 5; // 쿠키가 5 분 동안만 유지 할 수 있도록 한다.
		if (viewCount == null) { // 해당 쿠키을 가지고 있으면...
			boardService.increaseViewCount(boardNo);
			Cookie cookie = new Cookie(boardNo + "&" + user.getUserNo(), "view");
			cookie.setMaxAge(cookieMaxAge);
			response.addCookie(cookie);
		}

		model.addAttribute("user", user);
		model.addAttribute("model", boardService.boardSelect(boardNo));
		model.addAttribute("attach", boardService.getAttach(boardNo));
		model.addAttribute("comment", commentService.commentListAll(boardNo));
		return "board/answer";
	}

	/* 게시글 수정화면으로 이동 */
	@RequestMapping("modify")
	public String modify(@RequestParam("boardNo") int boardNo, Model model) {
		model.addAttribute("model", boardService.boardSelect(boardNo));
		model.addAttribute("list", boardService.categoryListAll());
		model.addAttribute("attach", boardService.getAttach(boardNo));
		return "board/modify";
	}

	/* 게시글 수정 등록 */
	@RequestMapping(value = "question/modify", method = RequestMethod.POST)
	public String boardModify(@ModelAttribute BoardModel model) {
		boardService.boardModify(model);

		return "redirect:/board/answer?boardNo=" + model.getBoardNo();
	}

	/* 게시글 삭제 */
	@RequestMapping("delete")
	public String boardDelete(@RequestParam("boardNo") int boardNo, RedirectAttributes rttr) {
		boardService.boardDelete(boardNo);

		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/board/list";
	}

	/* 내 질문들 보기 */
	@RequestMapping("myQuestions")
	public String myQuestions(@ModelAttribute("cri") SearchCriteria cri, Model model) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		model.addAttribute("list", boardService.selectMyQuestions(cri, user.getUserNo()));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.listCountCriteria(cri));

		model.addAttribute("categoryList", boardService.categoryListAll());
		model.addAttribute("pageMaker", pageMaker);

		return "user/myQuestions";

	}
}
