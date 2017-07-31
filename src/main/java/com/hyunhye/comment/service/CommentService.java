package com.hyunhye.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hyunhye.comment.model.CommentModel;
import com.hyunhye.comment.repository.CommentRepository;
import com.hyunhye.user.model.UserModelDetails;

@Service
public class CommentService {

	@Autowired
	CommentRepository repository;

	/* 답변 등록 */
	public void commentRegist(CommentModel commentModel) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commentModel.setUserNo(user.getUserNo());

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
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		commentModel.setUserNo(user.getUserNo());
		commentModel.setBoardNo(boardNo);

		return repository.commentListAll(commentModel);
	}

	/* 답변 수정 */
	public void commentUpdate(CommentModel commentModel) {
		repository.commentUpdate(commentModel);
	}

	/* 답변 삭제 */
	public int commentDelete(int commentNo) {
		/* 답변에 달린 댓글의 개수 확인 */
		int countChildrenComment = answerHasComment(commentNo);
		if (countChildrenComment <= 0) {
			repository.commentDelete(commentNo);
		} else {
			repository.commentUpdateNull(commentNo);
		}
		return countChildrenComment;
	}

	public CommentModel commentCount() {
		return repository.commentCount();
	}

	public CommentModel answerCount(int boardNo) {
		return repository.answerCount(boardNo);
	}

	public CommentModel commentSelect(CommentModel commentModel) {
		return repository.commentSelect(commentModel);
	}

	public CommentModel commentLastSelect() {
		return repository.commentLastSelect();
	}

	public List<CommentModel> commentCommentSelect(CommentModel model) {
		return repository.commentCommentSelect(model);
	}

	public void commentLike(CommentModel model) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.setUserNo(user.getUserNo());
		repository.commentLike(model);
	}

	public Integer answerHasComment(int commentNo) {
		return repository.answerHasComment(commentNo);
	}

	public void commenHate(CommentModel model) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.setUserNo(user.getUserNo());
		repository.commenHate(model);
	}

}
