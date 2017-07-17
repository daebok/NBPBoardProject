package com.hyunhye.board.repository;

import java.util.List;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.SearchCriteria;

public interface BoardRepository {
	public List<BoardModel> listAll();

	public void regist(BoardModel model);

	public void delete(int boardId);

	public BoardModel read(int boardId);

	public List<CategoryModel> categoryListAll();

	public void addAttach(FileModel model);

	public List<FileModel> getAttach(int boardId);

	public List<BoardModel> listCriteria(SearchCriteria cri) throws Exception;

	public int countPaging(SearchCriteria cri) throws Exception;

}
