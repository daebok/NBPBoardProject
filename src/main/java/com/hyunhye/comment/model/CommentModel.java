package com.hyunhye.comment.model;

import java.util.Date;

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
	private Date commentDate;
	/* 답변 작성 날짜 */
	private int commentParentNo;
	/* 답변 개수 */
	private int commentCount;
	/* 답변 좋아요 개수 */
	private int commentLikeCount;
	/* 답변 존재 여부 */
	private int commentEnabled;
	/* 댓글 여부 */
	private int commentCommentCount;
	/* 게시판 번호 */
	private int boardNo;
	/* 사용자 번호 */
	private int userNo;
	/* 사용자 이름 */
	private String userName;
	/* 사용자 아이디 */
	private String userId;
}
