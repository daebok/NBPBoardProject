package com.hyunhye.comment.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

/**
 * 답변 및 댓글 정보
 * @author NAVER
 *
 */
@Data
@Alias("comment")
public class Comment {
	/** 답변 번호 */
	private int commentNo;
	/** 답변 내용 */
	private String commentContent;
	/** 답변 작성 날짜 */
	private Date commentDate;
	/** 답변 작성 날짜 */
	private int commentParentNo;
	/** 답변 개수 */
	private int commentCount;
	/** 답변 좋아요 개수 */
	private int commentLikeCount;
	/** 댓글 여부 */
	private int commentCommentCount;
	/** 게시판 번호 */
	private int boardNo;
	/** 사용자 번호 */
	private int userNo;
	/** 좋아요 했는지 표시 */
	private int commentLike;
	/** 탭 번호 */
	private int tab;
}
