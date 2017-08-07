package com.hyunhye.board.controller;

import java.io.IOException;
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

import com.hyunhye.admin.model.Notice;
import com.hyunhye.admin.service.AdminService;
import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.BookMark;
import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.PageMaker;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.service.BoardService;
import com.hyunhye.board.service.UploadService;
import com.hyunhye.comment.service.CommentService;
import com.hyunhye.security.UserSessionUtils;

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

	/**
	 * 게시물 작성하기 페이지 이동
	 * @param model
	 * @return
	 */
	@RequestMapping("ask")
	public String goAskPage(Model model) {
		/* 카테고리 목록  불러오기 */
		model.addAttribute("list", boardService.categoryListAll());
		return "/board/ask";
	}

	/**
	 * 전체 게시물 리스트 목록 보기
	 * @param cri
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String boardSelectList(@ModelAttribute("cri") SearchCriteria cri, Model model) {
		/* 한 페이지에 보여줄 게시글 */
		model.addAttribute("list", boardService.boardSelectList(cri));

		/* 페이징 계산하기 */
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.boardSelectListCount(cri));

		/* 카테고리 리스트 */
		model.addAttribute("categoryList", boardService.categoryListAll());

		/* 페이징에 사용될 변수 */
		model.addAttribute("pageMaker", pageMaker);

		/* 공지사항 */
		model.addAttribute("notice", adminService.noticeListAll());

		return "board/list";
	}

	/**
	 * 게시글 작성하기
	 * @param model
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "question/ask", method = RequestMethod.POST)
	public String boardInsert(@ModelAttribute Board model, @RequestParam("files") MultipartFile[] file)
		throws Exception {
		boardService.boardInsert(UserSessionUtils.currentUserNo(), model, file);
		return "redirect:/board/list";
	}

	/**
	 * 비속어 체크
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "badWordsCheck", method = RequestMethod.POST)
	public ResponseEntity<List<String>> badWordsCheck(@ModelAttribute Board model) {
		/* 게시글에 포함 된 비속어 리스트 */
		ResponseEntity<List<String>> entity = new ResponseEntity<List<String>>(boardService.badWordsCheck(model),
			HttpStatus.OK);

		return entity;
	}

	/**
	 * 게시글 상세 보기
	 * @param boardNo
	 * @param section
	 * @param cri
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("question")
	public String boardSelectOne(@RequestParam("boardNo") int boardNo,
		@RequestParam(value = "section", defaultValue = "1") int section,
		@ModelAttribute("cri") SearchCriteria cri,
		Model model, HttpServletRequest request, HttpServletResponse response) {

		/* 조회수 */
		boardService.setViewCookies(boardNo);

		/* 세션에 저장된 사용자 정보 */
		model.addAttribute("user", UserSessionUtils.currentUserInfo());

		/* 해당 게시글 */
		model.addAttribute("model", boardService.boardSelectOne(boardNo));

		/* 해당 게시글에 포함된 첨부 파일 */
		model.addAttribute("attach", boardService.fileSelect(boardNo));

		/* 해당 게시글의 답변 수 */
		model.addAttribute("answerCount", commentService.answerCount(boardNo));

		/* 해당 게시글의 답변 목록*/
		model.addAttribute("comment", commentService.answerListAllSelect(boardNo));

		model.addAttribute("section", section);

		return "board/question";
	}

	/**
	 * 파일 다운로드
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("downloadFile")
	public ResponseEntity<byte[]> fileDownload(String fileName) throws Exception {
		return uploadService.fileDownload(fileName);
	}

	/**
	 * 게시글 수정화면으로 이동
	 * @param boardNo
	 * @param model
	 * @return
	 */
	@RequestMapping("modify")
	public String goBoardUpdatePage(@RequestParam("boardNo") int boardNo, Model model) {
		/* 해당 게시글 */
		model.addAttribute("model", boardService.boardSelectOne(boardNo));

		/* 카테고리 리스트 */
		model.addAttribute("list", boardService.categoryListAll());

		/* 첨부된 파일 */
		model.addAttribute("attach", boardService.fileSelect(boardNo));
		return "board/modify";
	}

	/**
	 * 게시글 수정하기
	 * @param model
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping(value = "question/modify", method = RequestMethod.POST)
	public String boardUpdate(@ModelAttribute Board model, @RequestParam("files") MultipartFile[] file)
		throws IOException, Exception {
		/* 게시글 수정하기 */
		boardService.boardUpdate(model, file);

		return "redirect:/board/question?boardNo=" + model.getBoardNo();
	}

	/**
	 * 게시글 삭제
	 * @param boardNo
	 * @return
	 */
	@RequestMapping("delete")
	public String boardDelete(@RequestParam("boardNo") int boardNo) {
		/* 게시글 삭제하기 */
		boardService.boardDelete(boardNo);
		return "redirect:/board/list";
	}

	/**
	 * 내 질문 모아보기 전체 리스트
	 * @param cri
	 * @param model
	 * @return
	 */
	@RequestMapping("myquestions")
	public String myQuestionsSelectList(@ModelAttribute("cri") SearchCriteria cri, Model model) {
		/* 내 질문 전체 리스트 */
		model.addAttribute("list", boardService.myQuestionsSelectList(cri));

		/* 페이징 계산하기 */
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.myQuestionsSelectListCount(cri));

		/* 전체리스트임을 알려주는 변수 */
		model.addAttribute("check", 0);

		/* 카테고리 리스트 */
		model.addAttribute("categoryList", boardService.categoryListAll());

		/* 페이징 정보 */
		model.addAttribute("pageMaker", pageMaker);

		return "user/myquestions";
	}

	/**
	 * 내 질문 모아보기 전체 리스트 (답변 달린 것만)
	 * @param cri
	 * @param model
	 * @return
	 */
	@RequestMapping("myquestions/answered")
	public String myQuestionsAnsweredSelectList(@ModelAttribute("cri") SearchCriteria cri, Model model) {
		/* 답변 달린 내 질문 리스트 */
		model.addAttribute("list", boardService.myQuestionsAnsweredSelectList(cri));

		/* 페이징 계산하기 */
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.myQuestionsAnsweredSelectListCount(cri));

		/* 답변 달린 것만임을 알려주는 변수 */
		model.addAttribute("check", 1);

		/* 카테고리 리스트 */
		model.addAttribute("categoryList", boardService.categoryListAll());

		/* 페이징 정보 */
		model.addAttribute("pageMaker", pageMaker);

		return "user/myquestions";
	}

	/**
	 *  내 답변 모아보기 전체 리스트
	 * @param cri
	 * @param model
	 * @return
	 */
	@RequestMapping("myanswers")
	public String myAnswersSelectList(@ModelAttribute("cri") Criteria cri, Model model) {
		/* 답변 달린 내 질문 리스트 */
		model.addAttribute("list", commentService.myAnswersSelect(cri));

		/* 페이징 계산하기 */
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(commentService.myAnswersListCount(cri));

		/* 답변 달린 것만임을 알려주는 변수 */
		model.addAttribute("check", 1);

		/* 답변 달린 것만임을 알려주는 변수 */
		model.addAttribute("like", 0);

		/* 페이징 정보 */
		model.addAttribute("pageMaker", pageMaker);

		return "user/myanswers";
	}

	/**
	 * 내가 좋아요한 답변 리스트
	 * @param cri
	 * @param model
	 * @return
	 */
	@RequestMapping("answers/liked")
	public String answersLikedSelectList(@ModelAttribute("cri") Criteria cri, Model model) {
		/* 내가 좋아요한 답변 리스트 */
		model.addAttribute("list", commentService.answersLikedSelectList(cri));

		/* 페이징 계산하기 */
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(commentService.myAnswersListCount(cri));

		/* 답변 달린 것만임을 알려주는 변수 */
		model.addAttribute("check", 1);

		/* 답변 달린 것만임을 알려주는 변수 */
		model.addAttribute("like", 1);

		/* 페이징 정보 */
		model.addAttribute("pageMaker", pageMaker);

		return "user/myanswers";
	}

	/**
	 * 즐겨찾기 추가하기
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "bookmark", method = RequestMethod.GET)
	public ResponseEntity<String> bookmarkInsert(@ModelAttribute Board model) {
		boardService.bookmarkInsert(model);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 즐겨찾기 해제하기
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "bookmark/uncheck", method = RequestMethod.GET)
	public ResponseEntity<String> bookmarkDelete(@ModelAttribute Board model) {
		boardService.bookmarkDelete(model);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 즐겨찾기 리스트 보기
	 * @param cri
	 * @param model
	 * @return
	 */
	@RequestMapping("myfavorite")
	public String myFavoriteSelectList(@ModelAttribute("cri") SearchCriteria cri, Model model) {
		/* 즐겨찾기 전체 목록 */
		model.addAttribute("list", boardService.myFavoriteSelectList(cri));

		/* 페이징 계산하기 */
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.myFavoriteSelectListCount(cri));

		/* 카테고리 리스트 */
		model.addAttribute("categoryList", boardService.categoryListAll());

		/* 페이징 정보 */
		model.addAttribute("pageMaker", pageMaker);

		return "user/myfavorite";
	}

	/**
	 * 즐겨찾기 상세 보기
	 * @param boardNo
	 * @param cri
	 * @param model
	 * @return
	 */
	@RequestMapping("myfavorite/memo")
	public String bookmarkSelectOne(@RequestParam("boardNo") int boardNo, @ModelAttribute("cri") SearchCriteria cri,
		Model model) {

		/* 현재 사용자 정보 */
		model.addAttribute("user", UserSessionUtils.currentUserInfo());

		/* 게시글 상세 정보 */
		model.addAttribute("model", boardService.boardSelectOne(boardNo));

		/* 첨부파일 */
		model.addAttribute("attach", boardService.fileSelect(boardNo));

		/* 게시글 답변 개수 */
		model.addAttribute("answerCount", commentService.answerCount(boardNo));

		/* 게시글 답변 리스트 */
		model.addAttribute("comment", commentService.answerListAllSelect(boardNo));

		/* 즐겨찾기 된 해당 게시글의 메모 */
		model.addAttribute("memo", boardService.bookmarkMemoSelect(boardNo));
		return "user/memo";
	}

	/**
	 * 즐겨찾기 메모 작성하기
	 * @param bookMarkModel
	 * @return
	 */
	@RequestMapping(value = "memo", method = RequestMethod.POST)
	public ResponseEntity<String> bookmarkMemoUpdate(@ModelAttribute BookMark bookMarkModel) {
		boardService.bookmarkMemoUpdate(bookMarkModel);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 공지사항 상세보기
	 * @param noticeModel
	 * @param model
	 * @return
	 */
	@RequestMapping("notice")
	public String noticeSelectOne(Notice noticeModel, Model model) {
		model.addAttribute("model", adminService.noticeSelectOne(noticeModel));
		return "admin/noticeView";
	}

	/**
	 * contactUs 페이지로 이동
	 * @return
	 */
	@RequestMapping("contactUs")
	public String goContactUsPage() {
		return "board/contact-us";
	}

}
