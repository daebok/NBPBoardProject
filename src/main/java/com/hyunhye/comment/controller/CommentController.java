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

import com.hyunhye.comment.model.CommentModel;
import com.hyunhye.comment.service.CommentService;
import com.hyunhye.security.UserSession;

@Controller
@RequestMapping("comment")
public class CommentController {
	Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	CommentService service;

	/** 답변 **/
	/* 1. 답변 달기 */
	/*@ResponseBody
	@RequestMapping(value = "regist", method = RequestMethod.POST)
	public ResponseEntity<CommentModel> commentRegist(@ModelAttribute CommentModel commentModel) {
		service.commentRegist(commentModel);
		ResponseEntity<CommentModel> entity = new ResponseEntity<CommentModel>(service.commentLastSelect(),
			HttpStatus.OK);
		return entity;
	}*/

	@RequestMapping(value = "regist", method = RequestMethod.POST)
	public String commentRegist(@ModelAttribute CommentModel commentModel, Model model) {
		service.commentRegist(commentModel);

		/* 세션에 저장된 사용자 정보 */
		model.addAttribute("user", UserSession.currentUserInfo());

		model.addAttribute("comment", service.commentLastSelect());

		return "board/answer-form";
	}

	/* 2. 답변 리스트 */
	@RequestMapping("list")
	public String commentList(@RequestParam("boardNo") int boardNo, Model model) {
		model.addAttribute("user", UserSession.currentUserInfo());
		model.addAttribute("comment", service.commentListAll(boardNo));

		return "board/answer";
	}

	/* 3. 답변 수정 */
	@RequestMapping("modify")
	public ResponseEntity<String> commentUpdate(@ModelAttribute CommentModel commentModel) {
		service.commentUpdate(commentModel);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/* 4. 답변 가져오기 */
	@ResponseBody
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public HashMap<String, Object> commentSelect(@ModelAttribute CommentModel model) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("commentContent", service.commentSelect(model).getCommentContent());

		return hashmap;
	}

	/* 5. 답변 삭제 */
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public ResponseEntity<String> answerDelete(@RequestParam("commentNo") int commentNo) {
		service.answerDelete(commentNo);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/** 댓글 **/
	/* 1. 댓글 가져오기 */
	@ResponseBody
	@RequestMapping(value = "comment/select", method = RequestMethod.GET)
	public HashMap<String, Object> commentCommentSelect(@ModelAttribute CommentModel model) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("commentCommentContent", service.commentCommentSelect(model));

		return hashmap;
	}

	/* 댓글 삭제 */
	@RequestMapping(value = "comment/delete", method = RequestMethod.GET)
	public ResponseEntity<String> commentDelete(@RequestParam("commentNo") int commentNo) {
		service.commentDelete(commentNo);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/** 답변 좋아요 **/
	/* 1. 답변 좋아요 */
	@RequestMapping(value = "like", method = RequestMethod.GET)
	public ResponseEntity<String> commentLike(@ModelAttribute CommentModel model) {
		service.commentLike(model);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/* 2. 답변 좋아요 취소*/
	@RequestMapping(value = "hate", method = RequestMethod.GET)
	public ResponseEntity<String> commenHate(@ModelAttribute CommentModel model) {
		service.commenHate(model);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
