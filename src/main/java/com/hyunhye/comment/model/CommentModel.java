package com.hyunhye.comment.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("comment")
public class CommentModel {
	private int commentId;
	private int userId;
	private int boardId;
	private String content;
	private String name;
	private String date;
}
