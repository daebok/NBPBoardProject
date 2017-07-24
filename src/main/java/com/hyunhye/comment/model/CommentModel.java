package com.hyunhye.comment.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("comment")
public class CommentModel {
	/* 답변 번호 */
	private int commentNo;
	/* 답변 내용 */
	private String commentContent;
	/* 답변 작성 날짜 */
	private String commentDate;
	/* 게시판 번호 */
	private int boardNo;
	/* 사용자 번호 */
	private int userNo;
	/* 사용자 이름 */
	private String userName;
	/* 사용자 아이디 */
	private String userId;
}
