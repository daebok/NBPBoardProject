package com.hyunhye.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.comment.model.CommentModel;
import com.hyunhye.comment.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	CommentRepository repository;

	/* 답변 등록 */
	public void commentRegist(CommentModel commentModel) throws Exception {
		repository.commentRegist(commentModel);
	}

	/* 답변 리스트 */
	public List<CommentModel> commentListAll(int boardNo) throws Exception {
		return repository.commentListAll(boardNo);
	}

	/* 답변 수정 */
	public void commentUpdate(CommentModel commentModel) throws Exception {
		repository.commentUpdate(commentModel);
	}

	/* 답변 삭제 */
	public void commentDelete(int commentNo) throws Exception {
		repository.commentDelete(commentNo);
	}
}
