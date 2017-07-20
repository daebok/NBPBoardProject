package com.hyunhye.board.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("file")
public class FileModel {
	/* 파일 번호 */
	private int fileId;
	/* 게시판 번호 */
	private int boardId;
	/* 서버에 저장되는 파일 경로 */
	private String fileName;
	/* 원본 파일 이름 */
	private String originName;
	/* 확장자 */
	private String extension;
}
