package com.hyunhye.board.repository;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.BookMark;

/**
 * 즐겨 찾기에 관련된 Repository
 * @author NAVER
 *
 */
@Repository
public interface BookMarkRepository {

	/**
	 * {@link BookMark} 추가하기
	 * @param board 게시글 번호
	 */
	public void insertBookMark(Board board);

	/**
	 * {@link BookMark} 메모 수정
	 * @param bookMark 게시글 즐겨찾기 항목
	 */
	public void updateBookMarkMemo(BookMark bookMark);

	/**
	 * {@link BookMark} 상세보기
	 * @param board 게시글 번호
	 * @return {@link BookMark} 항목
	 */
	public BookMark selectBookMarkMemo(Board board);

	/**
	 * {@link BookMark} 삭제하기
	 * @param board 게시글 번호
	 */
	public void deleteBookMark(Board board);

}