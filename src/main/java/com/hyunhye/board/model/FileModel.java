package com.hyunhye.board.model;

import lombok.Data;

@Data
public class FileModel {
	private int fileId;
	private int boardId;
	private String fileName;
	private String originName;
	private String extension;
}
