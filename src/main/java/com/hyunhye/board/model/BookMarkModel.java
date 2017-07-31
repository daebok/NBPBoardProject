package com.hyunhye.board.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("bookmark")
public class BookMarkModel {
	private int bookmarkNo;
	private int userNo;
	private int boardNo;
	private String bookmarkMemo;
}
