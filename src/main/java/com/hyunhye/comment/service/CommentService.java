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

	public void addComment(CommentModel model) throws Exception {
		// TODO Auto-generated method stub

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = simpleDateFormat.format(date);

		model.setDate(currentTime);
		repository.create(model);
	}

	public List<CommentModel> listComment(int boardId) throws Exception {
		// TODO Auto-generated method stub
		return repository.list(boardId);
	}

	public void modifyComment(CommentModel model) throws Exception {
		// TODO Auto-generated method stub
		repository.update(model);
	}

	public void removeComment(int boardId) throws Exception {
		// TODO Auto-generated method stub
		repository.delete(boardId);
	}
}
