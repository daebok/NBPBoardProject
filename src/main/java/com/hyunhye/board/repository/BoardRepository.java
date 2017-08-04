package com.hyunhye.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.BookMarkModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.HomeModel;
import com.hyunhye.board.model.SearchCriteria;

@Repository
public interface BoardRepository {
	public List<BoardModel> selectBoardList(HomeModel homeModel);

	public List<CategoryModel> categoryListAll();

	public void boardInsert(BoardModel boardModel);

	public BoardModel boardSelectOne(BoardModel boardModel);

	public BoardModel boardUpdate(BoardModel boardModel);

	public void boardDelete(int boardNo);

	public void addFile(FileModel model);

	public List<FileModel> getFile(int boardNo);

	public void deleteFile(String fileDelete);

	public List<BoardModel> boardSelectList(SearchCriteria cri);

	public int countListAllPaging(SearchCriteria cri);

	public void increaseViewCount(int boardNo);

	public int checkUser(int boardNo);

	public List<BoardModel> selectMyQuestions(SearchCriteria cri);

	public int countMyQuestionsPaging(SearchCriteria cri);

	public void bookmarkInsert(BoardModel model);

	public int countMyFavoritePaging(Criteria cri);

	public List<BoardModel> selectMyFavorite(Criteria cri);

	public void bookmarkMemoUpdate(BookMarkModel bookMarkModel);

	public BookMarkModel bookmarkMemoSelect(BookMarkModel bookMarkModel);

	public void bookmarkDelete(BoardModel model);

	public List<FileModel> fileSelect(int boardNo);

	public List<BoardModel> selectMyQuestionsAnswered(SearchCriteria cri);

	public int countMyQuestionsAnsweredPaging(SearchCriteria cri);
}