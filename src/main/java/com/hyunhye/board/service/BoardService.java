package com.hyunhye.board.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.SearchCriteria;

public interface BoardService {
	public List<BoardModel> listAll(Model model);

	public void delete(int boardId);

	public void regist(HttpSession session, BoardModel boardModel);

	public BoardModel read(int boardId);

	public BoardModel modify(HttpSession session, BoardModel dto);

	public List<CategoryModel> categoryListAll();

	public List<FileModel> getAttach(int boardId);

	public List<BoardModel> listCriteria(SearchCriteria cri) throws Exception;

	public int listCountCriteria(SearchCriteria cri) throws Exception;

}
