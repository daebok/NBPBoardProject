package com.hyunhye.comment.model;

import lombok.Data;

@Data
public class CommentModel {
	private int commentId;
	private int UID;
	private int BID;
	private String content;
	private String date;
	
}
