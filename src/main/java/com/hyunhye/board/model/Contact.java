package com.hyunhye.board.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("contact")
public class Contact {
	private int contactNo;
	private int userNo;
	private String contactTitle;
	private String contactContent;
	private String contactPassword;
}
