package com.hyunhye.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.BookMark;
import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.Home;
import com.hyunhye.board.model.SearchCriteria;

@Repository
public interface BoardRepository {
	public List<Board> selectBoardList(Home homeModel);

	public void boardInsert(Board boardModel);

	public Board boardSelectOne(Board boardModel);

	public Board boardUpdate(Board boardModel);

	public void boardDelete(int boardNo);

	public void addFile(FileModel model);

	public List<FileModel> getFile(int boardNo);

	public void deleteFile(String fileDelete);

	public List<Board> boardSelectList(SearchCriteria cri);

	public int boardSelectListCount(SearchCriteria cri);

	public void increaseViewCount(int boardNo);

	public int checkUser(int boardNo);

	public List<Board> myQuestionsSelectList(SearchCriteria cri);

	public int countMyQuestionsPaging(SearchCriteria cri);

	public void bookmarkInsert(Board model);

	public int countMyFavoritePaging(Criteria cri);

	public List<Board> myFavoriteSelectList(Criteria cri);

	public void bookmarkMemoUpdate(BookMark bookMarkModel);

	public BookMark bookmarkMemoSelect(BookMark bookMarkModel);

	public void bookmarkDelete(Board model);

	public List<FileModel> fileSelect(int boardNo);

	public List<Board> myQuestionsAnsweredSelectList(SearchCriteria cri);

	public int countMyQuestionsAnsweredPaging(SearchCriteria cri);

	public int boardViewSelect(Board boardModel);

	public void baordViewInsert(Board boardModel);
}