package com.hyunhye.comment.controller;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyunhye.comment.model.CommentModel;
import com.hyunhye.comment.service.CommentService;
import com.hyunhye.test.AbstractTestCaseRunWithSpring;

public class CommentControllerTest extends AbstractTestCaseRunWithSpring {
	Logger log = LoggerFactory.getLogger(CommentControllerTest.class);

	@Autowired
	CommentService service;

	@Override
	@Test
	public void test() {
		CommentModel model = new CommentModel();
		model.setBoardNo(44);
		model.setCommentContent("haha");

		log.info(model.getCommentNo() + "");

	}

}
