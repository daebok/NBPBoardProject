package com.hyunhye.board.repository;

import java.util.List;
import java.util.Map;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;

public interface BoardRepository {
	public List<BoardModel> listAll();

	public void regist(BoardModel model);

	public List<BoardModel> listAll(String searchOption, String keyword) throws Exception;

	public int countArticle(String searchOption, String keyword);

	public void delete(BoardModel model);

	public void insertFile(Map<String, Object> map);

	public BoardModel read(int id);

	public List<BoardModel> listAll(int start, int end, String searchOption, String keyword);

	public List<CategoryModel> categoryListAll();
}
