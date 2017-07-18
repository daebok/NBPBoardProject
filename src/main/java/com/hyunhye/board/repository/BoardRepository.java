package com.hyunhye.board.repository;

import java.util.List;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.SearchCriteria;

public interface BoardRepository {
	public List<BoardModel> listAll() throws Exception;

	public void regist(BoardModel model) throws Exception;

	public void delete(int boardId) throws Exception;

	public BoardModel read(int boardId) throws Exception;

	public List<CategoryModel> categoryListAll() throws Exception;

	public void addAttach(FileModel model) throws Exception;

	public List<FileModel> getAttach(int boardId) throws Exception;

	public List<BoardModel> listCriteria(SearchCriteria cri) throws Exception;

	public int countPaging(SearchCriteria cri) throws Exception;

	public void increaseViewCount(int boardId) throws Exception;

	public BoardModel modify(BoardModel model) throws Exception;

	public void deleteAttach(int boardId) throws Exception;

}
