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
		return repository.commentListAll(boardNo);
	}

	/* 답변 수정 */
	public void commentUpdate(CommentModel commentModel) {
		repository.commentUpdate(commentModel);
	}

	/* 답변 삭제 */
	public void commentDelete(int boardNo) {
		repository.commentDelete(boardNo);
	}

	public CommentModel commentCount() {
		return repository.commentCount();
	}

	public CommentModel commentSelect(CommentModel commentModel) {
		return repository.commentSelect(commentModel);
	}

	public CommentModel commentLastSelect() {
		return repository.commentLastSelect();
	}

	public List<CommentModel> commentCommentSelect(CommentModel model) {
		// TODO Auto-generated method stub
		return repository.commentCommentSelect(model);
	}

}
