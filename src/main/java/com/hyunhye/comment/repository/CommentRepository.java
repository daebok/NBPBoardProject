package com.hyunhye.comment.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.comment.model.CommentModel;

@Repository
public interface CommentRepository {

	public List<CommentModel> commentListAll(int boardId);

	public void commentRegist(CommentModel commentModel);

	public void commentUpdate(CommentModel commentModel);

	public void commentDelete(int commentId);

}
