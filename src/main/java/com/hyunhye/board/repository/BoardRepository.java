package com.hyunhye.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.Home;
import com.hyunhye.board.model.SearchCriteria;

@Repository
public interface BoardRepository {
	/** 홈 화면 **/
	public List<Board> boardTop10SelectList(Home homeModel);

	/** 게시굴 작성하기 **/
	public void boardInsert(Board boardModel);

	/** 게시굴 상세보기  **/
	public Board boardSelectOne(Board boardModel);

	/** 게시굴 수정하기 **/
	public Board boardUpdate(Board boardModel);

	/** 게시굴 삭제하기 **/
	public void boardDelete(int boardNo);

	/** 게시굴 전체 리스트 **/
	public List<Board> boardSelectList(SearchCriteria cri);

	/** 게시굴 전체 개수 **/
	public int boardSelectListCount(SearchCriteria cri);

	/** 게시굴 작성자 확인 **/
	public int checkUser(int boardNo);

	/** 내 질문 전체 리스트  **/
	public List<Board> myQuestionsSelectList(SearchCriteria cri);

	/** 내 질문 답변한 것만 리스트 **/
	public List<Board> myQuestionsAnsweredSelectList(SearchCriteria cri);

	/** 내 질문 답변한 것만 개수 **/
	public int myQuestionsAnsweredSelectListCount(SearchCriteria cri);

	/** 즐겨찾기 전체 개수 **/
	public List<Board> myFavoriteSelectList(Criteria cri);

	/** 즐겨찾기 전체 리스트 **/
	public int myFavoriteSelectListCount(Criteria cri);

	/** 게시굴 조회수  **/
	public void increaseViewCount(int boardNo);

	/** 조회 확인 **/
	public int boardViewSelect(Board boardModel);

	/** 조회수 추가 **/
	public void baordViewInsert(Board boardModel);
}