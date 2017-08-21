package com.hyunhye.notice.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;
/**
 * 공지사항 정보
 * @author NAVER
 *
 */
@Data
@Alias("notice")
public class Notice {
	/** 공지사항 번호 **/
	private int noticeNo;
	/** 공지사항 작성자 번호 **/
	private int userNo;
	/** 공지사항 제목 **/
	private String noticeTitle;
	/** 공지사항 내용 **/
	private String noticeContent;
}
