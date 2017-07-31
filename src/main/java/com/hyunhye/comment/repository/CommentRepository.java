package com.hyunhye.comment.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.comment.model.CommentModel;

@Repository
public interface CommentRepository {

	public List<CommentModel> commentListAll(CommentModel commentModel);

	public void commentRegist(CommentModel commentModel);

	public void commentUpdate(CommentModel commentModel);

	public void answerDelete(int boardNo);

	public CommentModel commentLastSelect();

	public CommentModel commentCount();

	public CommentModel answerCount(int boardNo);

	public CommentModel commentSelect(CommentModel commentModel);

	public List<CommentModel> commentCommentSelect(CommentModel commentModel);

	public void commentLike(CommentModel model);

	public Integer answerHasComment(int commentNo);

	public void commentUpdateNull(int commentNo);

	public void commenHate(CommentModel model);

	public void commentDelete(int commentNo);

}
