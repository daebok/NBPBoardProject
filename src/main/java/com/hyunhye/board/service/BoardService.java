package com.hyunhye.board.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.SearchCriteria;

public interface BoardService {
	public List<BoardModel> listAll(Model model) throws Exception;

	public void delete(int boardId) throws Exception;

	public void regist(HttpSession session, BoardModel boardModel) throws Exception;

	public BoardModel read(int boardId) throws Exception;

	public BoardModel modify(HttpSession session, BoardModel dto) throws Exception;

	public List<CategoryModel> categoryListAll() throws Exception;

	public List<FileModel> getAttach(int boardId) throws Exception;

	public List<BoardModel> listCriteria(SearchCriteria cri) throws Exception;

	public int listCountCriteria(SearchCriteria cri) throws Exception;

	public void increaseViewCount(int boardId) throws Exception;

}
