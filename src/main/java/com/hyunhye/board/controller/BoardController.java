package com.hyunhye.board.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.hyunhye.admin.model.NoticeModel;
import com.hyunhye.admin.service.AdminService;
import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.BookMarkModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.PageMaker;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.service.BoardService;
import com.hyunhye.comment.service.CommentService;
import com.hyunhye.user.model.UserModelDetails;

@RequestMapping("board")
@Controller
public class BoardController {
	Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private BoardService boardService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private AdminService adminService;

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

		model.addAttribute("notice", adminService.noticeListAll());

		return "board/list";
	}

	/* 게시글 작성하기  */
	@RequestMapping(value = "question/ask", method = RequestMethod.POST)
	public String boardRegist(@ModelAttribute BoardModel model, @RequestParam("files") MultipartFile[] file)
		throws Exception {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		boardService.boardRegist(user.getUserNo(), model, file);
		return "redirect:/board/list";
	}

	@ResponseBody
	@RequestMapping(value = "badWordsCheck", method = RequestMethod.POST)
	public ResponseEntity<List<String>> badWordsCheck(@ModelAttribute BoardModel model) {
		ResponseEntity<List<String>> entity = new ResponseEntity<List<String>>(boardService.badWordsCheck(model),
			HttpStatus.OK);

		return entity;
	}

	/* 게시글 상세 보기 */
	@RequestMapping("answer")
	public String boardSelect(@RequestParam("boardNo") int boardNo, @ModelAttribute("cri") SearchCriteria cri,
		Model model, HttpServletRequest request, HttpServletResponse response) {
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
		model.addAttribute("answerCount", commentService.answerCount(boardNo));
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
	public String boardModify(@ModelAttribute BoardModel model, @RequestParam("files") MultipartFile[] file)
		throws IOException, Exception {
		boardService.boardModify(model, file);

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
	@RequestMapping("myquestions")
	public String myQuestions(@ModelAttribute("cri") SearchCriteria cri, Model model) {

		model.addAttribute("list", boardService.selectMyQuestions(cri));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.countMyQuestionsPaging(cri));

		model.addAttribute("categoryList", boardService.categoryListAll());
		model.addAttribute("pageMaker", pageMaker);

		return "user/myquestions";
	}

	/* 즐겨찾기 추가 */
	@RequestMapping(value = "bookmark", method = RequestMethod.GET)
	public ResponseEntity<String> boardBookMark(@ModelAttribute BoardModel model) {
		ResponseEntity<String> entity = null;
		boardService.boardBookMark(model);
		entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

		return entity;
	}

	/* 즐겨찾기 해제 */
	@RequestMapping(value = "bookmark/uncheck", method = RequestMethod.GET)
	public ResponseEntity<String> boardBookMarkUnCheck(@ModelAttribute BoardModel model) {
		ResponseEntity<String> entity = null;
		boardService.boardBookMarkUnCheck(model);
		entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

		return entity;
	}

	/* 즐겨찾기 목록 보기 */
	@RequestMapping("myfavorite")
	public String myFavorite(@ModelAttribute("cri") SearchCriteria cri, Model model) {
		model.addAttribute("list", boardService.myFavorite(cri));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.countMyFavoritePaging(cri));

		model.addAttribute("categoryList", boardService.categoryListAll());
		model.addAttribute("pageMaker", pageMaker);

		return "user/myfavorite";
	}

	/* 즐겨찾기 상세 보기 */
	@RequestMapping("myfavorite/memo")
	public String boardMemo(@RequestParam("boardNo") int boardNo, @ModelAttribute("cri") SearchCriteria cri,
		Model model, HttpServletRequest request, HttpServletResponse response, Principal principal) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		model.addAttribute("user", user);
		model.addAttribute("model", boardService.boardSelect(boardNo));
		model.addAttribute("attach", boardService.getAttach(boardNo));
		model.addAttribute("answerCount", commentService.answerCount(boardNo));
		model.addAttribute("comment", commentService.commentListAll(boardNo));
		model.addAttribute("memo", boardService.memoSelect(boardNo));
		return "user/memo";
	}

	/* 즐겨찾기 메모 작성 */
	@ResponseBody
	@RequestMapping(value = "memo", method = RequestMethod.POST)
	public ResponseEntity<String> commentRegist(@ModelAttribute BookMarkModel bookMarkModel) {
		ResponseEntity<String> entity = null;

		boardService.bookMarkMemoRegist(bookMarkModel);
		entity = new ResponseEntity<String>(HttpStatus.OK);
		return entity;
	}

	/* 내정보 상세 보기 */
	@RequestMapping("myinfo")
	public String boardMemo(Model model) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user", user);
		return "user/myinfo";
	}

	@RequestMapping("notice")
	public String noticeSelect(NoticeModel noticeModel, Model model) {
		model.addAttribute("model", adminService.noticeSelect(noticeModel));
		return "admin/noticeView";
	}

}
