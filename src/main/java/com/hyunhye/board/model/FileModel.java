package com.hyunhye.board.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("file")
public class FileModel {
	private int fileId;
	private int boardId;
	private String fileName;
	private String originName;
	private String extension;
}
