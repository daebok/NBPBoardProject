package com.hyunhye.comment.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.comment.model.CommentModel;
import com.hyunhye.comment.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	CommentRepository repository;

	public void commentRegist(CommentModel commentModel) throws Exception {

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = simpleDateFormat.format(date);

		commentModel.setDate(currentTime);
		repository.commentRegist(commentModel);
	}

	public List<CommentModel> commentListAll(int boardId) throws Exception {
		return repository.commentListAll(boardId);
	}

	public void commentUpdate(CommentModel commentModel) throws Exception {
		repository.commentUpdate(commentModel);
	}

	public void commentDelete(int commentId) throws Exception {
		repository.commentDelete(commentId);
	}
}
