package com.hyunhye.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.BookMarkModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.SearchCriteria;

@Repository
public interface BoardRepository {

	public List<BoardModel> boardListViews();

	public List<CategoryModel> categoryListAll();

	public void boardRegist(BoardModel boardModel);

	public BoardModel boardSelect(BoardModel boardModel);

	public BoardModel boardModify(BoardModel boardModel);

	public void boardDelete(int boardNo);

	public void addFile(FileModel model);

	public List<FileModel> getFile(int boardNo);

	public void deleteFile(String fileDelete);

	public List<BoardModel> selectListAll(SearchCriteria cri);

	public int countListAllPaging(SearchCriteria cri);

	public void increaseViewCount(int boardNo);

	public int checkUser(int boardNo);

	public List<BoardModel> selectMyQuestions(Criteria cri);

	public int countMyQuestionsPaging(Criteria cri);

	public void boardBookMark(BoardModel model);

	public int countMyFavoritePaging(Criteria cri);

	public List<BoardModel> selectMyFavorite(Criteria cri);

	public void bookMarkMemoRegist(BookMarkModel bookMarkModel);

	public BookMarkModel memoSelect(BookMarkModel bookMarkModel);

	public void boardBookMarkUnCheck(BoardModel model);

	public List<BoardModel> boardListTopAnswers();

	public List<BoardModel> boardListNewest();

	public List<FileModel> fileSelect(int boardNo);

	public List<BoardModel> selectMyQuestionsAnswered(Criteria cri);

	public int countMyQuestionsAnsweredPaging(Criteria cri);
}