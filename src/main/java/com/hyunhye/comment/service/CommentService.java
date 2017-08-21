package com.hyunhye.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.PageCriteria;
import com.hyunhye.comment.model.Comment;
import com.hyunhye.comment.repository.CommentRepository;
import com.hyunhye.utils.UserSessionUtils;

@Service
public class CommentService {

	@Autowired
	CommentRepository repository;

	/**
	 * @param comment 사용자 번호
	 * @param tab 탭 번호 (최신순(1), 좋아요 순(2))
	 * @return {@link Comment} 전체 리스트
	 */
	public List<Comment> selectAllAnswerList(Comment comment, int tab) {
		comment.setUserNo(UserSessionUtils.currentUserNo());
		comment.setTab(tab);

		return repository.selectAllAnswerList(comment);
	}

	/**
	 * {@link Comment} 작성하기.
	 * 답변일 경우, {@link Comment#setCommentParentNo(int)} = 0.
	 * 댓글일 경우, {@link Comment#setCommentParentNo(int)} = 해당 답변 번호.
	 * @param comment
	 */
	public void insertAnswer(Comment comment) {
		comment.setUserNo(UserSessionUtils.currentUserNo());

		if (comment.getCommentNo() != 0) {
			comment.setCommentParentNo(comment.getCommentNo());
		} else {
			comment.setCommentParentNo(0);
		}
		repository.insertAnswer(comment);
	}

	/**
	 * {@link Comment} 수정하기
	 * @param comment 수정하는 {@link Comment} 정보
	 */
	public void updateAnswer(Comment comment) {
		repository.updateAnswer(comment);
	}

	/**
	 * {@link Comment} 삭제
	 * @param comment 삭제하는 {@link Comment} 정보
	 */
	public void deleteAnswer(Comment comment) {
		repository.deleteAnswer(comment);
	}

	/**
	 * @param board 게시글 번호
	 * @return {@link Board}에 해당하는 {@link Comment} 개수
	 */
	public Comment selectAnswerCountOfBoard(int boardNo) {
		return repository.selectAnswerCountOfBoard(boardNo);
	}

	/**
	 * @param comment 답변 번호
	 * @return {@link Comment} 상세 보기
	 */
	public Comment selectAnswerDetail(Comment comment) {
		return repository.selectAnswerDetail(comment);
	}

	/**
	 * @param comment 답변 번호
	 * @return {@link Comment} 댓글 리스트 (List<Comment>)
	 */
	public List<Comment> selectAnswerComment(Comment comment) {
		return repository.selectAnswerComment(comment);
	}

	/**
	 * {@link Comment} 좋아요 추가
	 * @param comment 답변 번호
	 */
	public void insertAnswerLike(Comment comment) {
		comment.setUserNo(UserSessionUtils.currentUserNo());
		repository.insertAnswerLike(comment);
	}

	/**
	 * {@link Comment} 좋아요 해제
	 * @param comment 답변 번호
	 */
	public void deleteAnswerLike(Comment comment) {
		comment.setUserNo(UserSessionUtils.currentUserNo());
		repository.deleteAnswerLike(comment);
	}

	/**
	 * option = 1
	 * @param criteria 페이징 정보
	 * @return 내가 작성한 {@link Comment} 리스트 (List<Comment>)
	 */
	public List<Comment> selectMyAnswersList(PageCriteria criteria) {
		criteria.setOption(1);
		criteria.setUserNo(UserSessionUtils.currentUserNo());
		return repository.selectMyAnswersList(criteria);
	}

	/**
	 * @param criteria 페이징 정보
	 * @return 내가 작성한 {@link Comment} 리스트 개수 (int)
	 */
	public int selectMyAnswersCount(PageCriteria criteria) {
		criteria.setUserNo(UserSessionUtils.currentUserNo());
		return repository.selectMyAnswersCount(criteria);
	}

	/**
	 * @param criteria 페이징 정보
	 * @return 내가 좋아요한 {@link Comment} 리스트 (List<Comment>)
	 */
	public List<Comment> selectMyLikedAnswersList(PageCriteria criteria) {
		criteria.setOption(2);
		criteria.setUserNo(UserSessionUtils.currentUserNo());
		return repository.selectMyAnswersList(criteria);
	}

	/**
	 * @param criteria 페이징 정보
	 * @return 내가 좋아요 한  {@link Comment} 리스트 개수 (int)
	 */
	public int selectMyLikedAnswersCount(PageCriteria criteria) {
		criteria.setUserNo(UserSessionUtils.currentUserNo());
		return repository.selectMyLikedAnswersCount(criteria);
	}

	/**
	 * {@link Comment}의 작성자 확인
	 * @param commentNo 답변 번호
	 * @return {@link Comment}가 존재하면, 작성자 번호 / 없으면 0 (int)
	 */
	public int checkUser(int commentNo) {
		return repository.checkUser(commentNo);
	}

}
