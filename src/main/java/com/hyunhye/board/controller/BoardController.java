package com.hyunhye.board.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.PageMaker;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.service.BoardService;
import com.hyunhye.comment.service.CommentService;

@Controller
@RequestMapping("board")
public class BoardController {

	@Autowired
	public BoardService boardService;

	@Autowired
	public CommentService commentService;

	@RequestMapping(value = {"", "/"})
	public String home(Model model) throws Exception {
		model.addAttribute("model", boardService.boardListAll(model));
		model.addAttribute("categoryList", boardService.categoryListAll());
		return "home";
	}

	@RequestMapping("question")
	public String question(Model model) throws Exception {
		List<CategoryModel> list = boardService.categoryListAll();
		model.addAttribute("list", list);
		return "/board/question";
	}

	@RequestMapping("list")
	public String listCountCriteria(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		model.addAttribute("list", boardService.listCriteria(cri));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.listCountCriteria(cri));

		model.addAttribute("categoryList", boardService.categoryListAll());
		model.addAttribute("pageMaker", pageMaker);

		return "board/list";
	}

	@RequestMapping(value = "question/ask", method = RequestMethod.POST)
	public String boardRegist(@ModelAttribute BoardModel model, HttpSession session) throws Exception {
		boardService.boardRegist(session, model);
		return "redirect:/board/list";
	}

	@RequestMapping("answer")
	public String boardSelect(@RequestParam("boardId") int boardId, @ModelAttribute("cri") Criteria cri,
		Model model, HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		Cookie viewCount = WebUtils.getCookie(request, "viewCount" + boardId);
		if (viewCount == null) {
			boardService.increaseViewCount(boardId);
			Cookie cookie = new Cookie("viewCount" + boardId, Integer.toString(boardId));
			response.addCookie(cookie);
		}

		model.addAttribute("model", boardService.boardSelect(boardId));
		model.addAttribute("attach", boardService.getAttach(boardId));
		model.addAttribute("comment", commentService.commentListAll(boardId));
		return "board/answer";
	}

	@RequestMapping("modify")
	public String modify(@RequestParam("boardId") int boardId, Model model) throws Exception {
		model.addAttribute("model", boardService.boardSelect(boardId));
		model.addAttribute("list", boardService.categoryListAll());
		model.addAttribute("attach", boardService.getAttach(boardId));
		return "board/modify";
	}

	@RequestMapping(value = "/question/modify", method = RequestMethod.POST)
	public String boardModify(HttpSession session, @ModelAttribute BoardModel model)
		throws Exception {
		boardService.boardModify(session, model);

		return "redirect:/board/answer?boardId=" + model.getBoardId();
	}

	@RequestMapping("delete")
	public String boardDelete(@RequestParam("boardId") int boardId, RedirectAttributes rttr) throws Exception {
		boardService.boardDelete(boardId);

		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/board/list";
	}
}
