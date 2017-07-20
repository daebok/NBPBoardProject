package com.hyunhye.comment.service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = simpleDateFormat.format(date);

		commentModel.setDate(currentTime);
		repository.commentRegist(commentModel);
	}

	/* 답변 리스트 */
	public List<CommentModel> commentListAll(int boardId) throws Exception {
		return repository.commentListAll(boardId);
	}

	/* 답변 수정 */
	public void commentUpdate(CommentModel commentModel) throws Exception {
		repository.commentUpdate(commentModel);
	}

	/* 답변 삭제 */
	public void commentDelete(int commentId) throws Exception {
		repository.commentDelete(commentId);
	}
}
