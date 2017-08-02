package com.hyunhye.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.comment.model.CommentModel;
import com.hyunhye.comment.repository.CommentRepository;
import com.hyunhye.security.UserSession;

@Service
public class CommentService {

	@Autowired
	CommentRepository repository;

	/* 답변 등록 */
	public void commentRegist(CommentModel commentModel) {
		commentModel.setUserNo(UserSession.getUserNo());

		if (commentModel.getCommentNo() != 0) {
			commentModel.setCommentParentNo(commentModel.getCommentNo());
		} else {
			commentModel.setCommentParentNo(0);
		}
		repository.commentRegist(commentModel);
	}

	/* 답변 리스트 */
	public List<CommentModel> commentListAll(int boardNo) {
		CommentModel commentModel = new CommentModel();
		commentModel.setUserNo(UserSession.getUserNo());
		commentModel.setBoardNo(boardNo);

		return repository.commentListAll(commentModel);
	}

	/* 답변 수정 */
	public void commentUpdate(CommentModel commentModel) {
		repository.commentUpdate(commentModel);
	}

	/* 답변 삭제 */
	public void answerDelete(int commentNo) {
		repository.answerDelete(commentNo);
	}

	/* 답변 삭제 */
	public void commentDelete(int commentNo) {
		repository.commentDelete(commentNo);
	}

	/* 답변 개수 구하기 */
	public CommentModel answerCount(int boardNo) {
		return repository.answerCount(boardNo);
	}

	/* 답변 가져오기 */
	public CommentModel commentSelect(CommentModel commentModel) {
		return repository.commentSelect(commentModel);
	}

	/* 마지막에 달른 답변 가져오기 */
	public CommentModel commentLastSelect() {
		return repository.commentLastSelect();
	}

	/* 댓글 리스트 */
	public List<CommentModel> commentCommentSelect(CommentModel model) {
		return repository.commentCommentSelect(model);
	}

	/* 댓글 개수 구하기 */
	public CommentModel commentCount() {
		return repository.commentCount();
	}

	/* 답변에 댓글 여부 확인 */
	public Integer answerHasComment(int commentNo) {
		return repository.answerHasComment(commentNo);
	}

	/* 답변 좋아요 */
	public void commentLike(CommentModel model) {
		model.setUserNo(UserSession.getUserNo());
		repository.commentLike(model);
	}

	/* 답변 좋아요 취소 */
	public void commenHate(CommentModel model) {
		model.setUserNo(UserSession.getUserNo());
		repository.commenHate(model);
	}

}
