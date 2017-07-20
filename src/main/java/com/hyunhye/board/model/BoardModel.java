package com.hyunhye.board.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("board")
public class BoardModel {
	/* 게시판 번호 */
	private int boardId;
	/* 게시글 제목 */
	private String title;
	/* 게시글 내용 */
	private String content;
	/* 게시글 작성 날짜 */
	private String date;
	/* 게시글 첨부 파일 */
	private String[] files;
	/* 게시글 첨부 파일 번호 */
	private int[] filesId;
	/* 조회수 */
	private int viewCount;
	/* 카테고리 번호 */
	private int categoryId;
	/* 카테고리 목록이름 */
	private String item;
	/* 사용자 번호 */
	private int userId;
	/* 사용자 이름 */
	private String name;
}
