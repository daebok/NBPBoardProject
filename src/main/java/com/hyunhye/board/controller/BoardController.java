package com.hyunhye.board.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hyunhye.admin.model.NoticeModel;
import com.hyunhye.admin.service.AdminService;
import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.BookMarkModel;
import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.PageMaker;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.service.BoardService;
import com.hyunhye.board.service.UploadService;
import com.hyunhye.comment.service.CommentService;
import com.hyunhye.security.UserSession;

@RequestMapping("board")
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private UploadService uploadService;

	/** 게시글 **/
	/* 질문하기 페이지 이동  */
	@RequestMapping("ask")
	public String question(Model model) {
		/* 카테고리 불러오기 */
		model.addAttribute("list", boardService.categoryListAll());
		return "/board/ask";
	}

	/* 리스트 목록 보기 (페이징) */
	@RequestMapping("list")
	public String listCriteria(@ModelAttribute("cri") SearchCriteria cri, Model model) {
		/* 한 페이지에 보여줄 게시글 */
		model.addAttribute("list", boardService.selectListAll(cri));

		/* 페이징 계산하기 */
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.countListAllPaging(cri));

		/* 카테고리 리스트 */
		model.addAttribute("categoryList", boardService.categoryListAll());

		/* 페이징에 사용될 변수 */
		model.addAttribute("pageMaker", pageMaker);

		/* 공지사항 */
		model.addAttribute("notice", adminService.noticeListAll());

		return "board/list";
	}

	/* 게시글 작성하기  */
	@RequestMapping(value = "question/ask", method = RequestMethod.POST)
	public String boardRegist(@ModelAttribute BoardModel model, @RequestParam("files") MultipartFile[] file)
		throws Exception {
		boardService.boardRegist(UserSession.currentUserNo(), model, file);
		return "redirect:/board/list";
	}

	/* 비속어 체크 */
	@ResponseBody
	@RequestMapping(value = "badWordsCheck", method = RequestMethod.POST)
	public ResponseEntity<List<String>> badWordsCheck(@ModelAttribute BoardModel model) {
		/* 게시글에 포함 된 비속어 리스트 */
		ResponseEntity<List<String>> entity = new ResponseEntity<List<String>>(boardService.badWordsCheck(model),
			HttpStatus.OK);

		return entity;
	}

	/* 게시글 상세 보기 */
	@RequestMapping("question")
	public String boardSelect(@RequestParam("boardNo") int boardNo,
		@RequestParam(value = "section", defaultValue = "1") int section,
		@ModelAttribute("cri") SearchCriteria cri,
		Model model, HttpServletRequest request, HttpServletResponse response) {

		/* 조회수 */
		boardService.setViewCookies(boardNo, request, response);

		/* 세션에 저장된 사용자 정보 */
		model.addAttribute("user", UserSession.currentUserInfo());

		/* 해당 게시글 */
		model.addAttribute("model", boardService.boardSelect(boardNo));

		/* 해당 게시글에 포함된 첨부 파일 */
		model.addAttribute("attach", boardService.getAttach(boardNo));

		/* 해당 게시글의 답변 수 */
		model.addAttribute("answerCount", commentService.answerCount(boardNo));

		/* 해당 게시글의 답변 목록*/
		model.addAttribute("comment", commentService.commentListAll(boardNo));

		model.addAttribute("section", section);

		return "board/question";
	}

	/* 파일 다운로드 */
	@ResponseBody
	@RequestMapping("downloadFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
		return uploadService.fileDownload(fileName);
	}

	/* 게시글 수정화면으로 이동 */
	@RequestMapping("modify")
	public String modify(@RequestParam("boardNo") int boardNo, Model model) {
		/* 해당 게시글 */
		model.addAttribute("model", boardService.boardSelect(boardNo));

		/* 카테고리 리스트 */
		model.addAttribute("list", boardService.categoryListAll());

		/* 첨부된 파일 */
		model.addAttribute("attach", boardService.getAttach(boardNo));
		return "board/modify";
	}

	/* 게시글 수정 등록 */
	@RequestMapping(value = "question/modify", method = RequestMethod.POST)
	public String boardModify(@ModelAttribute BoardModel model, @RequestParam("files") MultipartFile[] file)
		throws IOException, Exception {
		/* 게시글 수정하기 */
		boardService.boardModify(model, file);

		return "redirect:/board/answer?boardNo=" + model.getBoardNo();
	}

	/* 게시글 삭제 */
	@RequestMapping("delete")
	public String boardDelete(@RequestParam("boardNo") int boardNo) {
		/* 게시글 삭제하기 */
		boardService.boardDelete(boardNo);
		return "redirect:/board/list";
	}

	/** 내 질문 모아 보기 **/
	/* 1. 전체 리스트 */
	@RequestMapping("myquestions")
	public String myQuestions(@ModelAttribute("cri") Criteria cri, Model model) {
		/* 내 질문 전체 리스트 */
		model.addAttribute("list", boardService.selectMyQuestions(cri));

		/* 페이징 계산하기 */
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.countMyQuestionsPaging(cri));

		/* 전체리스트임을 알려주는 변수 */
		model.addAttribute("check", 0);

		/* 카테고리 리스트 */
		model.addAttribute("categoryList", boardService.categoryListAll());

		/* 페이징 정보 */
		model.addAttribute("pageMaker", pageMaker);

		return "user/myquestions";
	}

	/* 2. 답변 달린 것만 */
	@RequestMapping("myquestions/answered")
	public String myQuestionsAnswered(@ModelAttribute("cri") Criteria cri, Model model) {
		/* 답변 달린 내 질문 리스트 */
		model.addAttribute("list", boardService.selectMyQuestionsAnswered(cri));

		/* 페이징 계산하기 */
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.countMyQuestionsAnsweredPaging(cri));

		/* 답변 달린 것만임을 알려주는 변수 */
		model.addAttribute("check", 1);


		/* 카테고리 리스트 */
		model.addAttribute("categoryList", boardService.categoryListAll());

		/* 페이징 정보 */
		model.addAttribute("pageMaker", pageMaker);

		return "user/myquestions";
	}

	/* 2. 내 답변 */
	@RequestMapping("myanswers")
	public String myAnswers(@ModelAttribute("cri") Criteria cri, Model model) {
		/* 답변 달린 내 질문 리스트 */
		model.addAttribute("list", commentService.selectMyAnswers(cri));

		/* 페이징 계산하기 */
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(commentService.countMyAnswersPaging(cri));

		/* 답변 달린 것만임을 알려주는 변수 */
		model.addAttribute("check", 1);

		/* 카테고리 리스트 */
		model.addAttribute("categoryList", boardService.categoryListAll());

		/* 페이징 정보 */
		model.addAttribute("pageMaker", pageMaker);

		return "user/myanswers";
	}

	/** 즐겨찾기 **/
	/* 즐겨찾기 추가 */
	@RequestMapping(value = "bookmark", method = RequestMethod.GET)
	public ResponseEntity<String> boardBookMark(@ModelAttribute BoardModel model) {
		boardService.boardBookMark(model);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/* 즐겨찾기 해제 */
	@RequestMapping(value = "bookmark/uncheck", method = RequestMethod.GET)
	public ResponseEntity<String> boardBookMarkUnCheck(@ModelAttribute BoardModel model) {
		boardService.boardBookMarkUnCheck(model);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/* 즐겨찾기 목록 보기 */
	@RequestMapping("myfavorite")
	public String myFavorite(@ModelAttribute("cri") Criteria cri, Model model) {
		/* 즐겨찾기 전체 목록 */
		model.addAttribute("list", boardService.myFavorite(cri));

		/* 페이징 계산하기 */
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.countMyFavoritePaging(cri));


		/* 카테고리 리스트 */
		model.addAttribute("categoryList", boardService.categoryListAll());

		/* 페이징 정보 */
		model.addAttribute("pageMaker", pageMaker);

		return "user/myfavorite";
	}

	/* 즐겨찾기 상세 보기 */
	@RequestMapping("myfavorite/memo")
	public String boardMemo(@RequestParam("boardNo") int boardNo, @ModelAttribute("cri") SearchCriteria cri,
		Model model, HttpServletRequest request, HttpServletResponse response, Principal principal) {

		/* 현재 사용자 정보 */
		model.addAttribute("user", UserSession.currentUserInfo());

		/* 게시글 상세 정보 */
		model.addAttribute("model", boardService.boardSelect(boardNo));

		/* 첨부파일 */
		model.addAttribute("attach", boardService.getAttach(boardNo));

		/* 게시글 답변 개수 */
		model.addAttribute("answerCount", commentService.answerCount(boardNo));

		/* 게시글 답변 리스트 */
		model.addAttribute("comment", commentService.commentListAll(boardNo));

		/* 즐겨찾기 된 해당 게시글의 메모 */
		model.addAttribute("memo", boardService.memoSelect(boardNo));
		return "user/memo";
	}

	/* 즐겨찾기 메모 작성 */
	@RequestMapping(value = "memo", method = RequestMethod.POST)
	public ResponseEntity<String> commentRegist(@ModelAttribute BookMarkModel bookMarkModel) {
		boardService.bookMarkMemoRegist(bookMarkModel);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/** 내 정보 보기 **/
	/* 내정보 상세 보기 */
	@RequestMapping("myinfo")
	public String boardMemo(Model model) {
		model.addAttribute("user", UserSession.currentUserInfo());
		return "user/myinfo";
	}

	/** 공지사항  **/
	/* 공지사항 보기 */
	@RequestMapping("notice")
	public String noticeSelect(NoticeModel noticeModel, Model model) {
		model.addAttribute("model", adminService.noticeSelect(noticeModel));
		return "admin/noticeView";
	}

}
