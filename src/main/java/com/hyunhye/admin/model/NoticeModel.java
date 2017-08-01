package com.hyunhye.admin.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("notice")
public class NoticeModel {
	private int noticeNo;
	private int userNo;
	private String noticeTitle;
	private String noticeContent;
}