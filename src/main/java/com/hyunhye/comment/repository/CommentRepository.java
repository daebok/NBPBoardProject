package com.hyunhye.comment.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.PageCriteria;
import com.hyunhye.comment.model.Comment;

@Repository
public interface CommentRepository {

	/**
	 * @param board 게시글 번호
	 * @return {@link Board}에 해당하는 {@link Comment} 개수
	 */
	public Comment selectAnswerCountOfBoard(int boardNo);

	/**
	 * @param comment 답변 번호
	 * @return {@link Comment} 전체 리스트
	 */
	public List<Comment> selectAllAnswerList(Comment comment);

	/**
	 * {@link Comment} 작성하기
	 * @param comment 추가하는 답변 정보
	 */
	public void insertAnswer(Comment comment);

	/**
	 * {@link Comment} 수정하기
	 * @param comment 수정하는 답변 정보
	 */
	public void updateAnswer(Comment comment);

	/**
	 * {@link Comment} 삭제하기
	 * @param comment 삭제하는 답변 번호
	 */
	public void deleteAnswer(Comment comment);

	/**
	 * @param comment 답변 번호
	 * @return 해당하는 {@link Comment} 답변 상세보기
	 */
	public Comment selectAnswerDetail(Comment comment);

	/**
	 * @param comment 답변 번호
	 * @return {@link Comment}에 해당하는 댓글 리스트 (List<Comment>)
	 */
	public List<Comment> selectAnswerComment(Comment comment);

	/**
	 * {@link Comment} 좋아요 추가
	 * @param comment 답변 번호
	 */
	public void insertAnswerLike(Comment comment);

	/**
	 * {@link Comment} 좋아요 해제
	 * @param comment 답변 번호
	 */
	public void deleteAnswerLike(Comment comment);

	/**
	 * @param criteria 페이징 정보
	 * @return 내가 작성한 {@link Comment} 리스트 (List<Comment>)
	 */
	public List<Comment> selectMyAnswersList(PageCriteria criteria);

	/**
	 * @param criteria 페이징 정보
	 * @return 내가 작성한 {@link Comment} 리스트 개수 (int)
	 */
	public int selectMyAnswersCount(PageCriteria criteria);

	/**
	 * @param criteria 페이징 정보
	 * @return 내가 좋아요 한  리스트 (List<Comment>)
	 */
	public List<Comment> answersLikedSelectList(PageCriteria criteria);

	/**
	 * @param criteria 페이징 정보
	 * @return 내가 좋아요 한 {@link Comment} 개수 (int)
	 */
	public int selectMyLikedAnswersCount(PageCriteria criteria);

	/**
	 * {@link Comment}의 작성자 확인
	 * @param commentNo 답변 번호
	 * @return {@link Comment}가 존재하면, 작성자 번호 / 없으면 0
	 */
	public int checkUser(int commentNo);
}
