package com.hyunhye.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.BookMark;
import com.hyunhye.board.model.PageCriteria;
import com.hyunhye.board.model.PageMaker;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.service.BoardService;
import com.hyunhye.board.service.CategoryService;
import com.hyunhye.board.service.FileService;
import com.hyunhye.comment.service.CommentService;
import com.hyunhye.notice.service.NoticeService;
import com.hyunhye.utils.UriUtils;
import com.hyunhye.utils.UserSessionUtils;

/**
 * 게시판의 게시글에 관한 Controller
 * @author NAVER
 *
 */
@RequestMapping("board")
@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private NoticeService adminService;

	@Autowired
	private FileService uploadService;

	@Autowired
	private CategoryService categoryService;

	/**
	 * {@link Board} 작성하기 페이지 이동
	 * @return 게시물 작성 페이지
	 */
	@RequestMapping("ask")
	public String goBoardRegistPage(Model model) {
		/* 카테고리 목록  불러오기 */
		model.addAttribute("list", categoryService.selectAllCategoryList());
		return "/board/board-regist";
	}

	/**
	 * 전체 {@link Board} 리스트 목록 보기
	 * @param criteria 검색과 페이징 정보
	 * @param tab 최신순, 조회순, 답변순
	 * @return 게시판 목록 페이지
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String selectAllBoardList(@ModelAttribute("criteria") SearchCriteria criteria,
		@RequestParam(defaultValue = "1") int tab, HttpServletRequest request, Model model) {

		UriUtils.getUri(request);

		model.addAttribute("list", boardService.selectAllBoardList(criteria, tab));

		int boardCount = boardService.selectBoardCount(criteria);
		model.addAttribute("boardCount", boardCount);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(boardCount);

		model.addAttribute("categoryList", categoryService.selectAllCategoryList());
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("notice", adminService.selectAllNoticeList());

		return "board/board-list";
	}

	/**
	 * {@link Board} 작성하기
	 * @param board 게시글 정보
	 * @param file 파일 정보
	 * @return 게시글 리스트 페이지 Redirect
	 * @throws Exception
	 */
	@RequestMapping(value = "question/ask", method = RequestMethod.POST)
	public String insertBoard(@ModelAttribute Board board, @RequestParam("files") MultipartFile[] file)
		throws Exception {
		boardService.insertBoard(UserSessionUtils.currentUserNo(), board, file);
		return "redirect:/board/list";
	}


	/**
	 * {@link Board} 비속어 체크
	 * @param board 게시글 정보
	 * @return 게시물에 포함된 비속어 리스트
	 */
	@ResponseBody
	@RequestMapping(value = "badWordsCheck", method = RequestMethod.POST)
	public ResponseEntity<List<String>> checkBadWords(@ModelAttribute Board board) {
		return new ResponseEntity<List<String>>(boardService.checkBadWords(board),
			HttpStatus.OK);
	}

	/**
	 * 처음 게시물 상세보기를 했을 경우에만 조회 수 증가.
	 * 해당 게시물에 대한 답변 개수 가져옴.
	 * @param board 게시글 번호
	 * @return {@link Board} 상세 보기
	 */
	@RequestMapping(value = "question", method = RequestMethod.GET)
	public String selectBoardDetail(@ModelAttribute("criteria") SearchCriteria criteria, @ModelAttribute Board board,
		Model model, HttpServletRequest request) {
		model.addAttribute("user", UserSessionUtils.currentUserInfo());
		model.addAttribute("board", boardService.selectBoardDetail(board));
		model.addAttribute("answerCount", commentService.selectAnswerCountOfBoard(board.getBoardNo()));
		model.addAttribute("uri", request.getSession().getAttribute("uri"));
		boardService.increaseViewCount(board);

		return "board/board-view";
	}

	/**
	 * {@link Board}에 포함된 파일 다운로드
	 * @param fileName 다운로드 할 파일명
	 * @return 다운로드 용 파일 (이미지일 경우, mimeType으로 반환)
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("downloadFile")
	public ResponseEntity<byte[]> downloadFile(String fileName) throws Exception {
		return uploadService.downloadFile(fileName);
	}

	/**
	 * {@link Board} 수정 화면으로 이동
	 * @param board 해당 게시물의 내용
	 * @return 게시물 수정하기 화면으로 이동
	 */
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String goBoardUpdatePage(@ModelAttribute Board board, Model model) {
		model.addAttribute("board", boardService.selectBoardDetail(board));
		model.addAttribute("list", categoryService.selectAllCategoryList());

		return "board/board-modify";
	}

	/**
	 * {@link Board} 수정 하기
	 * @param board 게시글 정보
	 * @param file 파일 정보
	 * @return 게시글 상세보기 페이지로 Redirect
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping(value = "question/modify", method = RequestMethod.POST)
	public String updateBoardDetail(@ModelAttribute Board board, @RequestParam("files") MultipartFile[] file)
		throws IOException, Exception {
		boardService.updateBoardDetail(board, file);
		return "redirect:/board/question?boardNo=" + board.getBoardNo();
	}

	/**
	 * {@link Board} 삭제
	 * @param board 게시글 번호
	 * @return 게시물 리스트 Redirect
	 */
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String deleteBoardDetail(@ModelAttribute Board board) {
		boardService.deleteBoardDetail(board);
		return "redirect:/board/list";
	}

	/**
	 * 내 질문 모아보기 전체 리스트.
	 * [check = 0]: 내 질문 모아보기(전체) / [check = 1]: 내 질문 모아보기 (답변달린 것만)
	 * @param criteria  검색 조건 및 페이징 정보
	 * @return 내 질문모아 보기 리스트 페이지
	 */
	@RequestMapping(value = "myquestions", method = RequestMethod.GET)
	public String selectAllMyBoardList(@ModelAttribute("criteria") SearchCriteria criteria,
		HttpServletRequest request, Model model) {
		UriUtils.getUri(request);

		model.addAttribute("list", boardService.selectAllMyBoardList(criteria));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(boardService.selectAllMyBoardCount(criteria));

		model.addAttribute("check", 0);
		model.addAttribute("categoryList", categoryService.selectAllCategoryList());
		model.addAttribute("pageMaker", pageMaker);

		return "user/questions/myquestions-list";
	}

	/**
	 * 내 질문 모아보기 전체 리스트 (답변 달린 것만).
	 * [check = 0]: 내 질문 모아보기(전체) / [check = 1]: 내 질문 모아보기 (답변달린 것만)
	 * @param criteria 검색 조건 및 페이징 정보
	 * @return 내 질문 보기 리스트 페이지
	 */
	@RequestMapping(value = "myquestions/answered", method = RequestMethod.GET)
	public String selectAnsweredMyBoardList(@ModelAttribute("criteria") SearchCriteria criteria,
		HttpServletRequest request, Model model) {
		UriUtils.getUri(request);

		model.addAttribute("list", boardService.selectAnsweredMyBoardList(criteria));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(boardService.selectAnsweredMyBoardCount(criteria));

		model.addAttribute("check", 1);
		model.addAttribute("categoryList", categoryService.selectAllCategoryList());
		model.addAttribute("pageMaker", pageMaker);

		return "user/questions/myquestions-list";
	}

	/**
	 * 내 답변 모아보기 전체 리스트.
	 * [like = 0]: 내 답변 리스트 / [like = 1]: 내가 좋아요한 답변 리스트
	 * @param criteria 페이징 정보
	 * @return 내 답변 모아보기 페이지
	 */
	@RequestMapping(value = "myanswers", method = RequestMethod.GET)
	public String myAnswersSelectList(@ModelAttribute("criteria") PageCriteria criteria, HttpServletRequest request,
		Model model) {

		UriUtils.getUri(request);

		model.addAttribute("list", commentService.selectMyAnswersList(criteria));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(commentService.selectMyAnswersCount(criteria));

		model.addAttribute("check", 1);
		model.addAttribute("like", 0);
		model.addAttribute("pageMaker", pageMaker);

		return "user/questions/myanswers-list";
	}

	/**
	 * 내가 좋아요한 답변 리스트.
	 * [like = 0]: 내 답변 리스트 / [like = 1]: 내가 좋아요한 답변 리스트
	 * @param criteria  페이징 정보
	 * @return 내가 좋아요한 답변 리스트 페이지
	 */

	@RequestMapping(value = "answers/liked", method = RequestMethod.GET)
	public String selectMyLikedAnswersList(@ModelAttribute("criteria") PageCriteria criteria,
		HttpServletRequest request,
		Model model) {

		UriUtils.getUri(request);

		model.addAttribute("list", commentService.selectMyLikedAnswersList(criteria));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(commentService.selectMyLikedAnswersCount(criteria));

		model.addAttribute("check", 1);
		model.addAttribute("like", 1);
		model.addAttribute("pageMaker", pageMaker);

		return "user/questions/myanswers-list";
	}

	/**
	 * {@link BookMark} 추가하기
	 * @param board 해당 게시물 번호
	 */
	@RequestMapping(value = "bookmark", method = RequestMethod.GET)
	public ResponseEntity<String> insertBookMark(@ModelAttribute Board board) {
		boardService.insertBookMark(board);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * {@link BookMark} 해제하기
	 * @param board 해당 게시물 번호
	 */
	@RequestMapping(value = "bookmark/uncheck", method = RequestMethod.GET)
	public ResponseEntity<String> deleteBookMark(@ModelAttribute Board board) {
		boardService.deleteBookMark(board);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * {@link BookMark} 리스트 보기
	 * @param criteria 검색 조건 및 페이징 정보
	 * @return 즐겨찾기 리스트 페이지
	 */
	@RequestMapping(value = "myfavorite", method = RequestMethod.GET)
	public String selectMyBookMarkList(@ModelAttribute("criteria") SearchCriteria criteria, Model model) {

		model.addAttribute("list", boardService.selectMyBookMarkList(criteria));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(boardService.selectMyBookMarkCount(criteria));

		model.addAttribute("pageMaker", pageMaker);

		return "user/favorite/myfavorite-list";
	}

	/**
	 * {@link BookMark} 상세보기
	 * @param board 해당 게시글 번호
	 * @return 즐겨찾기 상세보기 페이지 이동
	 */
	@RequestMapping(value = "myfavorite/memo", method = RequestMethod.GET)
	public String selectBookMarkDetail(@ModelAttribute("criteria") SearchCriteria criteria,
		@ModelAttribute Board board, Model model) {

		model.addAttribute("user", UserSessionUtils.currentUserInfo());
		model.addAttribute("board", boardService.selectBoardDetail(board));
		model.addAttribute("memo", boardService.selectBookMarkMemo(board));
		model.addAttribute("answerCount", commentService.selectAnswerCountOfBoard(board.getBoardNo()));

		return "user/favorite/myfavorite-view";
	}

	/**
	 * {@link BookMark} 메모 작성하기
	 * @param bookMark 즐겨찾기 정보
	 */
	@RequestMapping(value = "memo", method = RequestMethod.POST)
	public ResponseEntity<String> updateBookMarkMemo(@ModelAttribute BookMark bookMark) {
		boardService.updateBookMarkMemo(bookMark);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
