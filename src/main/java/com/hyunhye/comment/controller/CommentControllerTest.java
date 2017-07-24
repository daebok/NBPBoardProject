package com.hyunhye.comment.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyunhye.comment.model.CommentModel;
import com.hyunhye.comment.service.CommentService;

public class CommentControllerTest {

	@Autowired
	CommentService service;

	@Test
	public void test() throws Exception {
		CommentModel model = new CommentModel();
		model.setCommentNo(79);

		CommentModel model2 = service.commentSelect(model);
		System.out.println(model2.getCommentContent());

	}

}
