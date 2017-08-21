package com.hyunhye.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.BoardFile;
import com.hyunhye.board.model.BookMark;
import com.hyunhye.board.model.Home;
import com.hyunhye.board.model.PageCriteria;
import com.hyunhye.board.model.SearchCriteria;

/**
 * 게시판의 게시글 Repository
 * @author NAVER
 *
 */
@Repository
public interface BoardRepository {
	/**
	 * 홈 화면에 보여줄 {@link Board} 리스트 가져오기
	 * @param home
	 * @return {@link Board} 리스트 10개 (최신순, 조회순, 답변순) (List<Board>)
	 */
	public List<Board> boardTop10SelectList(Home home);

	/**
	 * {@link Board} 작성하기
	 * @param board 새로 작성한 {@link Board} 항목
	 */
	public void insertBoard(Board board);

	/**
	 * @param board 게시글 번호
	 * @return {@link Board} 상세보기
	 */
	public Board selectBoardDetail(Board board);

	/**
	 * {@link Board} 수정하기
	 * @param board 수정한 {@link Board} 항목
	 */
	public void updateBoardDetail(Board board);

	/**
	 * {@link Board} 삭제하기
	 * @param board {@link Board} 번호
	 */
	public void deleteBoardDetail(Board board);

	/**
	 * @param criteria 검색 및 페이징 조건
	 * @return {@link Board} 리스트 (List<Board>)
	 */
	public List<Board> selectBoardList(SearchCriteria criteria);

	/**
	 * @param criteria 검색 및 페이징 조건
	 * @return {@link Board} 전체 개수 (int)
	 */
	public int selectBoardCount(SearchCriteria criteria);

	/**
	 * @param criteria 검색 및 페이징
	 * @return 내가 작성한 {@link Board} 리스트 (List<Board>)
	 */
	public List<Board> myQuestionsSelectList(SearchCriteria criteria);

	/**
	 * @param criteria 검색 및 페이징
	 * @return 내가 작성한 {@link Board} 리스트 중에서 {@link Comment} 달린 리스트 게시글  개수 (int)
	 */
	public int selectAnsweredMyBoardCount(SearchCriteria criteria);

	/**
	 * @param criteria 검색 및 페이징
	 * @return {@link BookMark} 개수 (int)
	 */
	public int selectMyBookMarkCount(PageCriteria criteria);

	/**
	 * {@link Board} 조회수 증가
	 * @param board 게시글 번호
	 */
	public void baordViewInsert(Board board);

	/**
	 * 내가 작성한 {@link Board} 개수 가져오기
	 * @param userNo 사용자 번호
	 * @return 내가 작성한 {@link Board} 개수 (int)
	 */
	public int selectMyBoardCount(int userNo);

	/**
	 * 해당 게시글이 존재하면, 해당 게시글의 사용자 번호 반환.
	 * 그렇지 않으면, 0 반환
	 * @param boardNo 게시글 번호
	 * @return {@link Board} 작성자 사용자 번호
	 */
	public int checkUser(int boardNo);

	/**
	 * @param board 게시판 번호
	 * @return {@link BoardFile} 리스트 (List<BoardFile>)
	 */
	public List<BoardFile> selectFileListByBoardId(Board board);

	/**
	 * {@link BoardFile} 추가하기
	 * @param file 파일 정보
	 */
	public void insertFile(BoardFile file);

	/**
	 * {@link BoardFile} 삭제
	 * @param fileName 삭제하려는 파일 이름
	 */
	public void deleteFile(String fileName);

}