package com.hyunhye.comment.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("comment")
public class CommentModel {
	/* 답변 번호 */
	private int commentId;
	/* 사용자 번호 */
	private int userId;
	/* 게시판 번호 */
	private int boardId;
	/* 답변 내용 */
	private String content;
	/* 답변 작성 날짜 */
	private String date;
	/* 사용자 이름 */
	private String name;
}
