package com.hyunhye.board.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

/**
 * 즐겨찾기 정보
 * @author NAVER
 *
 */
@Data
@Alias("bookmark")
public class BookMark {
	/** 즐겨찾기 번호 **/
	private int bookmarkNo;
	/** 사용자 번호 **/
	private int userNo;
	/** 게시판 번호 **/
	private int boardNo;
	/** 즐겨찾기 메모 **/
	private String bookmarkMemo;
}
