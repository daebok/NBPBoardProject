package com.hyunhye.comment.controller;

import java.util.HashMap;
import java.util.List;

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
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentService service;

	/* 답변 달기 */
	@ResponseBody
	@RequestMapping(value = "regist", method = RequestMethod.GET)
	public ResponseEntity<CommentModel> commentRegist(@ModelAttribute CommentModel commentModel) {
		ResponseEntity<CommentModel> entity = null;
		try {
			service.commentRegist(commentModel);
			entity = new ResponseEntity<CommentModel>(service.commentLastSelect(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<CommentModel>(HttpStatus.BAD_REQUEST);
		}

		return entity;
	}


	/* 답변 리스트 */
	@ResponseBody
	@RequestMapping("list")
	public ResponseEntity<List<CommentModel>> commentList(@RequestParam("boardNo") int boardNo) {
		ResponseEntity<List<CommentModel>> entity = null;
		try {
			entity = new ResponseEntity<List<CommentModel>>(service.commentListAll(boardNo),
				HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<CommentModel>>(HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	/* 답변 수정 */
	@RequestMapping("modify")
	public ResponseEntity<String> commentUpdate(@ModelAttribute CommentModel commentModel) {
		ResponseEntity<String> entity = null;
		try {
			service.commentUpdate(commentModel);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	/* 답변 가져오기 */
	@ResponseBody
	@RequestMapping("select")
	public HashMap<String, Object> commentSelect(@ModelAttribute CommentModel model) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("commentContent", service.commentSelect(model).getCommentContent());

		return hashmap;
	}

	/* 답변 삭제 */
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public ResponseEntity<String> commentDelete(@RequestParam("commentNo") int commentNo) {
		ResponseEntity<String> entity = null;
		try {
			service.commentDelete(commentNo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

}
