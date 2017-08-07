package com.hyunhye.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.FileModel;

@Repository
public interface FileRepository {

	/** 해당 게시글 파일 리스트 가져오기 **/
	public List<FileModel> fileSelect(int boardNo);

	/** 파일 추가하기 **/
	public void fileInsert(FileModel fileModel);

	/** 파일 삭제하기 **/
	public void fileDelete(String fileDelete);

}