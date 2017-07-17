package com.hyunhye.comment.model;

import lombok.Data;

@Data
public class CommentModel {
	private int commentId;
	private int userId;
	private int boardId;
	private String content;
	private String date;
}
