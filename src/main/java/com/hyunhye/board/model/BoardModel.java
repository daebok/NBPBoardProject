package com.hyunhye.board.model;

import lombok.Data;

@Data
public class BoardModel {
	private int BID;
	private int UID;
	private int CID;
	private String ITEM;
	private String NAME;
	private String TITLE;
	private String CATEGORY;
	private String CONTENT;
	private String DATE;
}
