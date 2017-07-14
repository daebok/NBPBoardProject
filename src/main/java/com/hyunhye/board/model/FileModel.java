package com.hyunhye.board.model;

import lombok.Data;

@Data
public class FileModel {
	private int FID;
	private int BID;
	private String FILENAME;
	private String ORIGINNAME;
	private String EXTENSION;
	private String FILESIZE;

}
