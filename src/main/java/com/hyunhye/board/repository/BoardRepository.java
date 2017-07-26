package com.hyunhye.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.SearchCriteria;

@Repository
public interface BoardRepository {

	public List<BoardModel> boardListAll();

	public List<CategoryModel> categoryListAll();

	public void boardRegist(BoardModel boardModel);

	public BoardModel boardSelect(int boardNo);

	public BoardModel boardModify(BoardModel boardModel);

	public void boardDelete(int boardNo);

	public void addAttach(FileModel model);

	public List<FileModel> getAttach(int boardNo);

	public void deleteAttach(int fileNo);

	public List<BoardModel> listCriteria(SearchCriteria cri);

	public int countPaging(SearchCriteria cri);

	public void increaseViewCount(int boardNo);

	public int checkUser(int boardNo);

	public List<BoardModel> selectMyQuestions(SearchCriteria cri, int userNo);
}