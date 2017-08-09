package com.hyunhye.contact.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("contactComment")
public class ContactComment {
	/** 문의사항 댓글 번호 **/
	private int contactCommentNo;
	/** 문의사항 번호 **/
	private int contactNo;
	/** 사용자 번호 **/
	private int userNo;
	/** 문의사항 댓글 내용 **/
	private String contactCommentContent;
	/** 문의사항 댓글 날짜 **/
	private Date contactCommentDate;

}
