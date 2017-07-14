package com.hyunhye.board.service;



import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;

public interface BoardService {
	public void listAll(Model model);

	public int countArticle(String searchOption, String keyword) throws Exception;

	public void listAll(Model model, String searchOption, String keyword) throws Exception;

	public void delete(int bid, BoardModel dto);

	public void regist(HttpSession session, BoardModel boardModel);

	public BoardModel read(int id);

	public BoardModel modify(HttpSession session, BoardModel dto);

	public List<BoardModel> listAll(int start, int end, String searchOption, String keyword);

	public List<CategoryModel> categoryListAll();
}
