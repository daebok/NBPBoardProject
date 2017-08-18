package com.hyunhye.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.board.model.Criteria;
import com.hyunhye.comment.model.Comment;
import com.hyunhye.comment.repository.CommentRepository;
import com.hyunhye.utils.UserSessionUtils;

@Service
public class CommentService {

	@Autowired
	CommentRepository repository;

	/* 답변 전체 리스트 */
	public List<Comment> answerTabSelectListAll(int boardNo, int tab) {
		Comment commentModel = new Comment();
		commentModel.setUserNo(UserSessionUtils.currentUserNo());
		commentModel.setBoardNo(boardNo);
		commentModel.setTab(tab);

		return repository.answerTabSelectListAll(commentModel);
	}

	/* 답변 등록 */
	public void answerInsert(Comment commentModel) {
		commentModel.setUserNo(UserSessionUtils.currentUserNo());

		if (commentModel.getCommentNo() != 0) {
			commentModel.setCommentParentNo(commentModel.getCommentNo());
		} else {
			commentModel.setCommentParentNo(0);
		}
		repository.answerInsert(commentModel);
	}

	/* 답변 수정 */
	public void answerUpdate(Comment commentModel) {
		repository.answerUpdate(commentModel);
	}

	/* 답변 삭제 */
	public void answerDelete(int commentNo) {
		repository.answerDelete(commentNo);
	}

	/* 댓글 삭제 */
	public void commentDelete(int commentNo) {
		repository.commentDelete(commentNo);
	}

	/* 답변 개수 구하기 */
	public Comment answerCount(int boardNo) {
		return repository.answerCount(boardNo);
	}

	/* 답변 가져오기 */
	public Comment commentSelectOne(Comment commentModel) {
		return repository.commentSelectOne(commentModel);
	}

	/* 댓글 리스트 */
	public List<Comment> answerCommentSelect(Comment model) {
		return repository.answerCommentSelect(model);
	}

	/* 댓글 개수 구하기 */
	public Comment commentCount() {
		return repository.commentCount();
	}

	/* 답변 좋아요 */
	public void answerLikeInsert(Comment model) {
		model.setUserNo(UserSessionUtils.currentUserNo());
		repository.answerLikeInsert(model);
	}

	/* 답변 좋아요 취소 */
	public void answerLikeDelete(Comment model) {
		model.setUserNo(UserSessionUtils.currentUserNo());
		repository.answerLikeDelete(model);
	}

	/* 내가 작성한 답변 리스트 */
	public List<Comment> myAnswersSelect(Criteria cri) {
		cri.setOption(1);
		cri.setUserNo(UserSessionUtils.currentUserNo());
		return repository.myAnswersSelect(cri);
	}

	/* 내가 작성한 답변 개수 */
	public int myAnswersListCount(Criteria cri) {
		cri.setUserNo(UserSessionUtils.currentUserNo());
		return repository.myAnswersListCount(cri);
	}

	/* 내가 좋아요한 답변 리스트 */
	public List<Comment> answersLikedSelectList(Criteria cri) {
		cri.setOption(2);
		cri.setUserNo(UserSessionUtils.currentUserNo());
		return repository.myAnswersSelect(cri);
	}

	public int answersLikedSelectListCount(Criteria cri) {
		cri.setUserNo(UserSessionUtils.currentUserNo());
		return repository.answersLikedSelectListCount(cri);
	}

	/* 답변 작성자 번호 가져오기 */
	public int checkUser(int commentNo) {
		return repository.checkUser(commentNo);
	}

	public int isExistedComment(int commentNo) {
		return repository.isExistedComment(commentNo);
	}

}
