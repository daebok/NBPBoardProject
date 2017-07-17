package com.hyunhye.comment.repository;

import java.util.List;

import com.hyunhye.comment.model.CommentModel;

public interface CommentRepository {
	public List<CommentModel> list(int boardId) throws Exception;

	public void create(CommentModel model) throws Exception;

	public void update(CommentModel model) throws Exception;

	public void delete(int boardId) throws Exception;
}
