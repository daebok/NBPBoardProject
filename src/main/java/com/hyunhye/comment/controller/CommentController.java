package com.hyunhye.comment.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyunhye.comment.model.CommentModel;
import com.hyunhye.comment.service.CommentService;

@Controller
@RequestMapping("comment")
public class CommentController {
	Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	CommentService service;

	/* 답변 달기 */
	@ResponseBody
	@RequestMapping(value = "regist", method = RequestMethod.POST)
	public ResponseEntity<CommentModel> commentRegist(@ModelAttribute CommentModel commentModel) {
		ResponseEntity<CommentModel> entity = null;
		service.commentRegist(commentModel);
		entity = new ResponseEntity<CommentModel>(service.commentLastSelect(), HttpStatus.OK);

		return entity;
	}

	/* 답변 리스트 */
	@ResponseBody
	@RequestMapping("list")
	public ResponseEntity<List<CommentModel>> commentList(@RequestParam("boardNo") int boardNo) {
		ResponseEntity<List<CommentModel>> entity = null;
		entity = new ResponseEntity<List<CommentModel>>(service.commentListAll(boardNo),
			HttpStatus.OK);

		return entity;
	}

	/* 답변 수정 */
	@RequestMapping("modify")
	public ResponseEntity<String> commentUpdate(@ModelAttribute CommentModel commentModel) {
		ResponseEntity<String> entity = null;

		service.commentUpdate(commentModel);
		entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

		return entity;
	}

	/* 답변 가져오기 */
	@ResponseBody
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public HashMap<String, Object> commentSelect(@ModelAttribute CommentModel model) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("commentContent", service.commentSelect(model).getCommentContent());

		return hashmap;
	}

	/* 댓글 가져오기 */
	@ResponseBody
	@RequestMapping(value = "comment/select", method = RequestMethod.GET)
	public HashMap<String, Object> commentCommentSelect(@ModelAttribute CommentModel model) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("commentCommentContent", service.commentCommentSelect(model));

		return hashmap;
	}

	/* 답변 삭제 */
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public ResponseEntity<String> answerDelete(@RequestParam("commentNo") int commentNo) {
		ResponseEntity<String> entity = null;
		service.answerDelete(commentNo);
		entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

		return entity;
	}

	@RequestMapping(value = "comment/delete", method = RequestMethod.GET)
	public ResponseEntity<String> commentDelete(@RequestParam("commentNo") int commentNo) {
		ResponseEntity<String> entity = null;
		service.commentDelete(commentNo);
		entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

		return entity;
	}

	/* 답변 좋아요 */
	@RequestMapping(value = "like", method = RequestMethod.GET)
	public ResponseEntity<String> commentLike(@ModelAttribute CommentModel model) {
		ResponseEntity<String> entity = null;
		service.commentLike(model);
		entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

		return entity;
	}

	/* 답변 좋아요 취소*/
	@RequestMapping(value = "hate", method = RequestMethod.GET)
	public ResponseEntity<String> commenHate(@ModelAttribute CommentModel model) {
		ResponseEntity<String> entity = null;
		service.commenHate(model);
		entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

		return entity;
	}



}
