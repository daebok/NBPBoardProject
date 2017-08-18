package com.hyunhye.comment.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.Criteria;
import com.hyunhye.comment.model.Comment;

@Repository
public interface CommentRepository {

	/** 답변 전체 개수 **/
	public Comment answerCount(int boardNo);

	/** 답변 탭 리스트 **/
	public List<Comment> answerTabSelectListAll(Comment commentModel);

	/** 답변 작성하기 **/
	public void answerInsert(Comment commentModel);

	/** 답변 수정하기 **/
	public void answerUpdate(Comment commentModel);

	/** 답변 삭제하기 **/
	public void answerDelete(int boardNo);

	/** 댓글 개수 **/
	public Comment commentCount();

	/** 해당 댓글 가져오기 **/
	public Comment commentSelectOne(Comment commentModel);

	/** 답변에 딸린 댓글 개수 **/
	public List<Comment> answerCommentSelect(Comment commentModel);

	/** 댓글 삭제하기 **/
	public void commentDelete(int commentNo);

	/** 답변 좋아요 **/
	public void answerLikeInsert(Comment model);

	/** 답변 좋아요 취소 **/
	public void answerLikeDelete(Comment model);

	/** 내가 작성한 답변 리스트 **/
	public List<Comment> myAnswersSelect(Criteria cri);

	/** 내가 작성한 답변 개수 **/
	public int myAnswersListCount(Criteria cri);

	/** 내가 좋아요한 답변 리스트 **/
	public List<Comment> answersLikedSelectList(Criteria cri);

	public int checkUser(int commentNo);

	public int answersLikedSelectListCount(Criteria cri);

	public int isExistedComment(int commentNo);

}
