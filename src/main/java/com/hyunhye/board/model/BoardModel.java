package com.hyunhye.board.model;

import lombok.Data;

@Data
public class BoardModel {
	private int boardId;
	private int userId;
	private int categoryId;
	private String item;
	private String name;
	private String title;
	private String content;
	private String date;
	private String[] files;
	private int[] filesId;
	private int viewCount;
}
