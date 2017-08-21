package com.hyunhye.comment.controller;

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

import com.hyunhye.board.service.BoardService;
import com.hyunhye.comment.model.Comment;
import com.hyunhye.comment.service.CommentService;
import com.hyunhye.utils.UserSessionUtils;

/**
 * 게시글의 답변과 댓글 Controller
 * @author NAVER
 *
 */
@Controller
@RequestMapping("comment")
public class CommentController {

	@Autowired
	BoardService boardService;

	@Autowired
	CommentService commentService;

	/**
	 * {@link Comment} 추가하기
	 * @param comment 답변 정보
	 * @return {@link Comment} 리스트 페이지
	 */
	@RequestMapping(value = "regist", method = RequestMethod.POST)
	public String insertAnswer(@ModelAttribute Comment comment, Model model) {
		commentService.insertAnswer(comment);

		model.addAttribute("user", UserSessionUtils.currentUserInfo());
		model.addAttribute("writer", boardService.checkUser(comment.getBoardNo()));
		model.addAttribute("answerCount", commentService.selectAnswerCountOfBoard(comment.getBoardNo()));
		model.addAttribute("answer", commentService.selectAllAnswerList(comment, 1));

		return "board/answer";
	}

	/**
	 * {@link Comment} 리스트 가져오기
	 * @param comment 게시글 번호
	 * @param tab 탭 번호 (최신순(1), 좋아요 순(2))
	 * @return {@link Comment}  리스트 페이지
	 */
	@RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
	public String selectAllAnswerList(@ModelAttribute Comment comment, @RequestParam("tab") int tab,
		Model model) {
		model.addAttribute("user", UserSessionUtils.currentUserInfo());
		model.addAttribute("answer", commentService.selectAllAnswerList(comment, tab));

		return "board/answer-tab";
	}

	/**
	 * {@link Comment} 수정
	 * @param comment 답변 번호
	 */
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public ResponseEntity<String> updateAnswer(@ModelAttribute Comment comment) {
		commentService.updateAnswer(comment);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * @param comment
	 * @return {@link Comment} 수정시, 해당 답변의 상세 내용
	 */
	@ResponseBody
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public ResponseEntity<Comment> selectAnswerDetail(@ModelAttribute Comment comment) {
		return new ResponseEntity<Comment>(commentService.selectAnswerDetail(comment), HttpStatus.OK);
	}

	/**
	 * {@link Comment} 삭제
	 * @param comment 답변 번호
	 */
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public ResponseEntity<String> deleteAnswer(@ModelAttribute Comment comment) {
		commentService.deleteAnswer(comment);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * {@link Comment} 댓글 리스트 가져오기
	 * @param comment 답변 번호
	 * @param model
	 * @return 댓글 리스트 페이지
	 */
	@RequestMapping(value = "comment/select", method = RequestMethod.GET)
	public String selectAnswerComment(@ModelAttribute Comment comment, Model model) {
		model.addAttribute("comment", commentService.selectAnswerComment(comment));
		return "board/comment-form";
	}

	/**
	 * {@link Comment} 좋아요 추가
	 * @param comment 답변 번호
	 */
	@RequestMapping(value = "like", method = RequestMethod.GET)
	public ResponseEntity<String> insertAnswerLike(@ModelAttribute Comment comment) {
		commentService.insertAnswerLike(comment);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * {@link Comment} 좋아요 해제
	 * @param comment 답변 번호
	 */
	@RequestMapping(value = "hate", method = RequestMethod.GET)
	public ResponseEntity<String> deleteAnswerLike(@ModelAttribute Comment comment) {
		commentService.deleteAnswerLike(comment);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
