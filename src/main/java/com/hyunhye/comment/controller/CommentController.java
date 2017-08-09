package com.hyunhye.comment.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.hyunhye.comment.model.Comment;
import com.hyunhye.comment.service.CommentService;
import com.hyunhye.utils.UserSessionUtils;

@Controller
@RequestMapping("comment")
public class CommentController {
	Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	CommentService service;

	/**
	 * 답변 달기
	 * @param commentModel
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "regist", method = RequestMethod.POST)
	public String answerInsert(@ModelAttribute Comment commentModel, Model model) {
		service.answerInsert(commentModel);

		/* 세션에 저장된 사용자 정보 */
		model.addAttribute("user", UserSessionUtils.currentUserInfo());

		model.addAttribute("comment", service.commentLastSelect());

		return "board/answer-form";
	}

	/**
	 * 답변 리스트
	 * 조회수 순, 좋아요 순으로 볼 수 있도록 tab
	 * @param boardNo
	 * @param tab
	 * @param model
	 * @return
	 */
	@RequestMapping("list/tab")
	public String answerTabSelectListAll(@RequestParam("boardNo") int boardNo, @RequestParam("tab") int tab,
		Model model) {
		model.addAttribute("user", UserSessionUtils.currentUserInfo());
		model.addAttribute("comment", service.answerTabSelectListAll(boardNo, tab));

		return "board/answer-tab";
	}

	/**
	 * 답변 수정
	 * @param commentModel
	 * @return
	 */
	@RequestMapping("modify")
	public ResponseEntity<String> answerUpdate(@ModelAttribute Comment commentModel) {
		service.answerUpdate(commentModel);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 답변 수정시, 해당 답변 가져오기
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public HashMap<String, Object> commentSelectOne(@ModelAttribute Comment model) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("commentContent", service.commentSelectOne(model).getCommentContent());

		return hashmap;
	}

	/**
	 * 답변 삭제하기
	 * @param commentNo
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public ResponseEntity<String> answerDelete(@RequestParam("commentNo") int commentNo) {
		service.answerDelete(commentNo);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 답변의 댓글 리스트 가져오기
	 * @param commentModel
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "comment/select", method = RequestMethod.GET)
	public String answerCommentSelect(@ModelAttribute Comment commentModel, Model model) {
		model.addAttribute("commentComment", service.answerCommentSelect(commentModel));
		return "board/comment-form";
	}

	/**
	 * 답변의 댓글 삭제하기
	 * @param commentNo
	 * @return
	 */
	@RequestMapping(value = "comment/delete", method = RequestMethod.GET)
	public ResponseEntity<String> commentDelete(@RequestParam("commentNo") int commentNo) {
		service.commentDelete(commentNo);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 답변 좋아요 누르기
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "like", method = RequestMethod.GET)
	public ResponseEntity<String> answerLikeInsert(@ModelAttribute Comment model) {
		service.answerLikeInsert(model);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 답변 좋아요 해제
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "hate", method = RequestMethod.GET)
	public ResponseEntity<String> answerLikeDelete(@ModelAttribute Comment model) {
		service.answerLikeDelete(model);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
