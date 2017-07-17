package com.hyunhye.comment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyunhye.comment.model.CommentModel;
import com.hyunhye.comment.service.CommentServiceImpl;

@Controller
public class CommentController {
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	CommentServiceImpl service;

	@ResponseBody
	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	public ResponseEntity<List<CommentModel>> register(@ModelAttribute CommentModel model) {
		logger.info("comment register");
		logger.info("model.getBID(): " + model.getBID());
		ResponseEntity<List<CommentModel>> entity = null;
		try {
			service.addComment(model);
			entity = new ResponseEntity<>(service.listComment(model.getBID()), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@ResponseBody
	@RequestMapping(value = "/comment/list", method = RequestMethod.GET)
	public ResponseEntity<List<CommentModel>> list(@RequestParam("boardId") int boardId) {
		ResponseEntity<List<CommentModel>> entity = null;
		try {
			entity = new ResponseEntity<>(service.listComment(boardId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@RequestMapping(value = "/comment/update/{commentId}", method = RequestMethod.GET)
	public ResponseEntity<String> update(@PathVariable("id") int commentId,
		@RequestBody CommentModel model) {
		ResponseEntity<String> entity = null;
		try {
			model.setCommentId(commentId);
			service.modifyComment(model);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@RequestMapping(value = "/comment/delete/{commentId}", method = RequestMethod.GET)
	public ResponseEntity<String> delete(@PathVariable("id") int commentId) {
		ResponseEntity<String> entity = null;
		try {
			service.removeComment(commentId);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

}
