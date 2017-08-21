package com.hyunhye.board.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.BookMark;
import com.hyunhye.board.model.Home;
import com.hyunhye.board.model.PageCriteria;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.repository.BoardRepository;
import com.hyunhye.board.repository.BookMarkRepository;
import com.hyunhye.board.repository.CategoryRepository;
import com.hyunhye.utils.BadWordFilteringUtils;
import com.hyunhye.utils.UserSessionUtils;

/**
 * 게시판의 게시글 Service
 * @author NAVER
 *
 */
@Service
public class BoardService {

	@Autowired
	public BoardRepository boardRepository;

	@Autowired
	public CategoryRepository categoryRepository;

	@Autowired
	private BookMarkRepository bookmarkRepository;

	@Autowired
	private FileService fileService;

	/**
	 * @param home 탭 번호
	 * @return 게시글  Top10 리스트 (List<Board>)
	 */
	@Cacheable("top-list")
	public List<Board> boardTop10SelectList(Home home) {
		return boardRepository.boardTop10SelectList(home);
	}

	/**
	 * 홈화면 업데이트.
	 * top-list 캐시 삭제.
	 */
	@CacheEvict(value = "top-list", allEntries = true)
	public void boardTop10Update() {
		return;
	}

	/**
	 * {@link Board} 작성하기
	 * @param userNo 현재 로그인 한 사용자 번호
	 * @param board 게시글 정보
	 * @param files 파일 정보
	 * @throws Exception
	 */
	public void insertBoard(int userNo, Board board, MultipartFile[] files) throws Exception {
		board.setUserNo(userNo);

		String summary = createSummary(board.getBoardContent());
		board.setBoardContentSummary(summary);

		boardRepository.insertBoard(board);
		fileService.insertFile(board, files);
	}

	/**
	 * {@link Board#getBoardContent()}의 태그를 제거하여, 일부만 저장
	 * @param originalContent
	 * @return {@link Board} BoardContent 요약
	 */
	public String createSummary(String originalContent) {
		originalContent = originalContent.replaceAll("<(/)?([a-zA-Z]*)([0-9]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");

		if (originalContent.length() > 300) {
			originalContent = originalContent.substring(0, 300);
		}

		return originalContent;
	}

	/**
	 * {@link Board}의 비속어 체크
	 * @param board 게시글 제목 및 내용
	 * @return {@link Board}에 포함된 비속어 리스트
	 */
	public List<String> checkBadWords(Board board) {
		String boardSummary = HtmlUtils.htmlUnescape(board.getBoardContent());
		boardSummary += board.getBoardTitle();
		List<String> badWords = BadWordFilteringUtils.badWordFilteringContainsStream(boardSummary);
		return badWords;
	}

	/**
	 * @param board 게시글 번호
	 * @return {@link Board} 상세보기
	 */
	public Board selectBoardDetail(Board board) {
		board.setUserNo(UserSessionUtils.currentUserNo());
		return boardRepository.selectBoardDetail(board);
	}

	/**
	 * {@link Board} 조회수 증가.
	 * 현재 사용자가 한번 방문한 적이 있으면, 증가하지 않는다.
	 * @param board 게시글 번호
	 */
	public void increaseViewCount(Board board) {
		board.setUserNo(UserSessionUtils.currentUserNo());
		boardRepository.baordViewInsert(board);
	}

	/**
	 * {@link Board} 수정하기.
	 * 수정 된 파일의 삭제 및 추가도 함께 진행한다.
	 * @param board 게시글 정보
	 * @param files 파일 정보
	 * @throws IOException
	 * @throws Exception
	 */
	public void updateBoardDetail(Board board, MultipartFile[] files) throws IOException, Exception {
		board.setUserNo(UserSessionUtils.currentUserNo());

		String summary = createSummary(board.getBoardContent());
		board.setBoardContentSummary(summary);

		fileService.deleteFile(board);
		fileService.insertFile(board, files);
		boardRepository.updateBoardDetail(board);
	}

	/**
	 * {@link Board} 삭제하기.
	 * 게시글에 해당하는 파일도 함께 삭제.
	 * @param board {@link Board} 번호
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void deleteBoardDetail(Board board) throws FileNotFoundException, IOException {

		fileService.deleteFileFromDatabase(board);
		boardRepository.deleteBoardDetail(board);
	}

	/**
	 * option = 1
	 * @param criteria 검색 및 페이징 조건
	 * @param tab 최신순(1), 조회순(2), 답변순(3)
	 * @return {@link Board} 리스트 (List<Board>)
	 */
	public List<Board> selectAllBoardList(SearchCriteria criteria, int tab) {
		criteria.setOption(1);
		criteria.setTab(tab);
		criteria = isSearchTypeNull(criteria);
		criteria = dateCheck(criteria);
		return boardRepository.selectBoardList(criteria);
	}

	/**
	 * @param criteria 검색 및 페이징 조건
	 * @return {@link Board} 전체 개수 (int)
	 */
	public int selectBoardCount(SearchCriteria criteria) {
		criteria.setOption(1);
		criteria = isSearchTypeNull(criteria);
		criteria = dateCheck(criteria);
		return boardRepository.selectBoardCount(criteria);
	}

	/**
	 * {@link SearchCriteria}의 categoryType과 searchType의 null 체크
	 * @param criteria 검색 조건
	 */
	public SearchCriteria isSearchTypeNull(SearchCriteria criteria) {
		if (StringUtils.isBlank(criteria.getCategoryType())) {
			criteria.setCategoryType("all");
		}
		if (StringUtils.isBlank(criteria.getSearchType())) {
			criteria.setSearchType(null);
		}
		return criteria;
	}

	/**
	 * {@link SearchCriteria}의 date 체크
	 * @param criteria
	 */
	public SearchCriteria dateCheck(SearchCriteria criteria) {
		LocalDate theDate = LocalDate.now();
		String today = theDate.toString();

		String from = criteria.getFromDate();
		String to = criteria.getToDate();

		if (StringUtils.isBlank(from) && TextUtils.isEmpty(to)) {
			criteria.setFromDate(null);
			criteria.setToDate(null);
		} else if (StringUtils.isBlank(from)) {
			criteria.setFromDate(to);
			criteria.setToDate(today);
		} else if (StringUtils.isBlank(to)) {
			criteria.setToDate(today);
		} else if (from.compareTo(criteria.getToDate()) > 0) {
			String tmpDate = from;
			criteria.setFromDate(to);
			criteria.setToDate(tmpDate);
		} else if (criteria.getToDate().compareTo(today) > 0) {
			criteria.setToDate(today);
		} else if (from.compareTo(today) > 0) {
			criteria.setFromDate(today);
		}

		return criteria;
	}


	/**
	 * option = 2
	 * @param criteria 검색 및 페이징 조건
	 * @return 내가 작성한 {@link Board} 리스트 (List<Board>)
	 */
	public List<Board> selectAllMyBoardList(SearchCriteria criteria) {
		criteria.setOption(2);
		criteria.setUserNo(UserSessionUtils.currentUserNo());
		criteria = isSearchTypeNull(criteria);
		criteria = dateCheck(criteria);
		return boardRepository.selectBoardList(criteria);
	}

	/**
	 * @param criteria 검색 및 페이징 조건
	 * @return 내가 작성한 {@link Board} 리스트 개수
	 */
	public int selectAllMyBoardCount(SearchCriteria criteria) {
		criteria.setOption(2);
		criteria.setUserNo(UserSessionUtils.currentUserNo());
		criteria = isSearchTypeNull(criteria);
		criteria = dateCheck(criteria);
		return boardRepository.selectBoardCount(criteria);
	}

	/**
	 * option = 3
	 * @param criteria 검색 및 페이징 조건
	 * @return 내가 작성한 {@link Board} 중에서 {@link Comment} 리스트 (List<Board>)
	 */
	public List<Board> selectAnsweredMyBoardList(SearchCriteria criteria) {
		criteria.setOption(3);
		criteria.setUserNo(UserSessionUtils.currentUserNo());
		criteria = isSearchTypeNull(criteria);
		criteria = dateCheck(criteria);
		return boardRepository.selectBoardList(criteria);
	}

	/**
	 * @param criteria 검색 및 페이징 조건
	 * @return 내가 작성한 {@link Board} 중에서 {@link Comment}  리스트 개수
	 */
	public int selectAnsweredMyBoardCount(SearchCriteria criteria) {
		criteria.setUserNo(UserSessionUtils.currentUserNo());
		criteria = isSearchTypeNull(criteria);
		criteria = dateCheck(criteria);
		return boardRepository.selectAnsweredMyBoardCount(criteria);
	}

	/**
	 * option = 4
	 * @param criteria 검색 및 페이징 조건
	 * @return {@link BookMark} 전체 리스트
	 */
	public List<Board> selectMyBookMarkList(SearchCriteria criteria) {
		criteria.setOption(4);
		criteria.setUserNo(UserSessionUtils.currentUserNo());
		return boardRepository.selectBoardList(criteria);

	}

	/**
	 * @param criteria 검색 및 페이징 조건
	 * @return {@link BookMark} 전체 리스트 개수 (int)
	 */
	public int selectMyBookMarkCount(PageCriteria criteria) {
		criteria.setUserNo(UserSessionUtils.currentUserNo());
		return boardRepository.selectMyBookMarkCount(criteria);
	}

	/**
	 * {@link BookMark} 추가하기
	 * @param board 게시글 번호
	 */
	public void insertBookMark(Board board) {
		board.setUserNo(UserSessionUtils.currentUserNo());
		bookmarkRepository.insertBookMark(board);
	}

	/**
	 * {@link BookMark} 메모 추가하기
	 * @param bookMark 즐겨찾기 정보
	 */
	public void updateBookMarkMemo(BookMark bookMark) {
		bookMark.setUserNo(UserSessionUtils.currentUserNo());
		bookmarkRepository.updateBookMarkMemo(bookMark);
	}

	/**
	 * @param board 게시글 번호
	 * @return {@link BookMark} 상세 보기
	 */
	public BookMark selectBookMarkMemo(Board board) {
		board.setUserNo(UserSessionUtils.currentUserNo());
		return bookmarkRepository.selectBookMarkMemo(board);
	}

	/**
	 * {@link BookMark} 해제
	 * @param board 게시글 번호
	 */
	public void deleteBookMark(Board board) {
		board.setUserNo(UserSessionUtils.currentUserNo());
		bookmarkRepository.deleteBookMark(board);
	}

	/**
	 * @param userNo 해당 사용자 번호
	 * @return 내가 작성한 {@link Board} 리스트 개수
	 */
	public int selectMyBoardCount(int userNo) {
		return boardRepository.selectMyBoardCount(userNo);
	}

	/**
	 * @param boardNo 게시글 번호
	 * @return 해당 게시글이 존재하면, 해당 게시글의 사용자 번호. 그렇지 않으면, 0.
	 */
	public int checkUser(int boardNo) {
		return boardRepository.checkUser(boardNo);
	}

}
