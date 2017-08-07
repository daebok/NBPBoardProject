package com.hyunhye.board.repository;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.BookMark;

@Repository
public interface BookMarkRepository {

	/** 즐겨찾기 추가 **/
	public void bookmarkInsert(Board model);

	/** 즐겨찾기 메모 수정 **/
	public void bookmarkMemoUpdate(BookMark bookMarkModel);

	/** 즐겨찾기 메모 상세보기 **/
	public BookMark bookmarkMemoSelect(BookMark bookMarkModel);

	/** 즐겨찾기 삭제 **/
	public void bookmarkDelete(Board model);

}