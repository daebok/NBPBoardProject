package com.hyunhye.comment.service;

import java.util.List;

import com.hyunhye.comment.model.CommentModel;

public interface CommentService {
	public void addComment(CommentModel model) throws Exception;

	public List<CommentModel> listComment(int boardId) throws Exception;

	public void modifyComment(CommentModel model) throws Exception;

	public void removeComment(int boardId) throws Exception;
}
