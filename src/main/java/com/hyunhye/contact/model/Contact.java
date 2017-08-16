package com.hyunhye.contact.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("contact")
public class Contact {
	/** 문의사항 번호 **/
	private int contactNo;
	/** 사용자 번호 **/
	private int userNo;
	/**  사용자 이름 **/
	private String userId;
	/** 문의사항 제목 **/
	private String contactTitle;
	/** 문의사항 내용 **/
	private String contactContent;
	/** 문의사항 비밀번호 **/
	private String contactPassword;
	/** 문의사항 날짜 **/
	private Date contactDate;
	/** 문의사항 답변 개수 **/
	private int contactCommentCount;
}
