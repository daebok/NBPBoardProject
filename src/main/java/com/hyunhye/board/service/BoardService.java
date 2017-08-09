package com.hyunhye.board.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.BookMark;
import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.Home;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.repository.BoardRepository;
import com.hyunhye.board.repository.BookMarkRepository;
import com.hyunhye.board.repository.CategoryRepository;
import com.hyunhye.board.repository.FileRepository;
import com.hyunhye.utils.BadWordFilteringUtils;
import com.hyunhye.utils.UserSessionUtils;

@Service
public class BoardService {
	Logger logger = LoggerFactory.getLogger(BoardService.class);

	@Autowired
	public BoardRepository boardRepository;

	@Autowired
	public CategoryRepository categoryRepository;

	@Autowired
	private BookMarkRepository bookmarkRepository;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private FileService uploadService;

	/** 게시글  Top10 리스트 **/
	public List<Board> boardTop10SelectList(Home homeModel) {
		return boardRepository.boardTop10SelectList(homeModel);
	}

	/** 게시글 **/
	/* 1. 게시글 작성하기 */
	/* 파일을 동시에 저장하기 위해 트랜잭션 사용 */
	@Transactional
	public void boardInsert(int userNo, Board boardModel, MultipartFile[] files) throws Exception {
		boardModel.setUserNo(userNo);

		/* 제거된 태그를 boardContentSummary에 담는다. */
		String summary = createSummary(boardModel.getBoardContent());
		boardModel.setBoardContentSummary(summary);

		/* 1) 작성 된 게시글 저장 */
		boardRepository.boardInsert(boardModel);

		/* 2) 업로드 된 파일 저장 */
		uploadService.fileRegist(boardModel, files);
	}

	/* HTML 태그 제거 */
	public String createSummary(String originalContent) {
		/* escape 문자 처리 */
		String boardSummary = HtmlUtils.htmlUnescape(originalContent);

		/* HTML 태그 제거  */
		boardSummary = boardSummary.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");

		/* 일부 문자열만 저장 되도록 */
		if (boardSummary.length() > 300) {
			boardSummary = boardSummary.substring(0, 300);
		}

		return boardSummary;
	}

	/* 비속어 체크 */
	public List<String> badWordsCheck(Board model) {
		String boardSummary = HtmlUtils.htmlUnescape(model.getBoardContent());
		boardSummary += model.getBoardTitle();
		List<String> badWords = BadWordFilteringUtils.badWordFilteringContainsStream(boardSummary);
		return badWords;
	}

	/* 2. 해당 게시글 상세 보기 */
	public Board boardSelectOne(int boardNo) {
		Board boardModel = new Board();
		boardModel.setBoardNo(boardNo);
		boardModel.setUserNo(UserSessionUtils.currentUserNo());
		return boardRepository.boardSelectOne(boardModel);
	}

	/* 조회수 */
	public void setViewCookies(int boardNo) {
		Board boardModel = new Board();
		boardModel.setBoardNo(boardNo);
		boardModel.setUserNo(UserSessionUtils.currentUserNo());
		int check = boardRepository.boardViewSelect(boardModel);

		/* 조회 한 적 없으면 증가 */
		if (check <= 0) {
			boardRepository.increaseViewCount(boardNo);
			boardRepository.baordViewInsert(boardModel);
		}
	}

	/* 등록 된 첨부파일 가져오기  */
	public List<FileModel> fileSelect(int boardNo) {
		return fileRepository.fileSelect(boardNo);
	}

	/* 3. 게시글 수정하기*/
	/* 파일을 동시에 저장하기 위해 트랜잭션 사용*/
	@Transactional
	public void boardUpdate(Board boardModel, MultipartFile[] files) throws IOException, Exception {
		boardModel.setUserNo(UserSessionUtils.currentUserNo());

		/* 제거된 태그를 boardContentSummary에 담는다. */
		String summary = createSummary(boardModel.getBoardContent());
		boardModel.setBoardContentSummary(summary);

		/* 1) 업로드 된 파일 중에서 삭제버튼 누른 파일 삭제하기 */
		uploadService.fileDelete(boardModel);

		/* 2) 새로 업로드 된 파일 저장 */
		uploadService.fileRegist(boardModel, files);

		/* 3) 기존 게시글 수정하기 */
		boardRepository.boardUpdate(boardModel);
	}

	/* 4. 게시글 삭제하기 */
	@Transactional
	public void boardDelete(int boardNo) {

		/* 1) 해당 게시물에 첨부된 파일 서버에서 삭제 */
		uploadService.fileDeletFromDatabase(boardNo);

		/* 2) 기존 게시물 삭제하기 */
		boardRepository.boardDelete(boardNo);
	}

	/* 5. 게시글 리스트 (페이징) */
	public List<Board> boardSelectList(SearchCriteria cri, int tab) {
		cri.setOption(1);
		cri.setTab(tab);

		return boardRepository.boardSelectList(cri);
	}

	/* 게시글 개수 구하기 */
	public int boardSelectListCount(SearchCriteria cri) {
		cri.setOption(1);

		return boardRepository.boardSelectListCount(cri);
	}

	/* 조회수 */
	public void increaseViewCount(int boardNo) {
		boardRepository.increaseViewCount(boardNo);
	}

	/* 게시글 작성자 가져오기 */
	public int checkUser(int boardNo) {
		return boardRepository.checkUser(boardNo);
	}

	/** 내 질문 모아 보기  **/
	/* 1. 내 질문 모아 보기 (전체) */
	public List<Board> myQuestionsSelectList(SearchCriteria cri) {
		cri.setOption(2);
		cri.setUserNo(UserSessionUtils.currentUserNo());


		return boardRepository.boardSelectList(cri);
	}

	/* 내 질문 모아 보기 (전체) -> 게시물 전체 개수 구하기 */
	public int myQuestionsSelectListCount(SearchCriteria cri) {
		cri.setOption(2);
		cri.setUserNo(UserSessionUtils.currentUserNo());
		;

		return boardRepository.boardSelectListCount(cri);
	}

	/* 2. 내 질문 모아 보기 (답변한 것만) */
	public List<Board> myQuestionsAnsweredSelectList(SearchCriteria cri) {
		cri.setOption(3);
		cri.setUserNo(UserSessionUtils.currentUserNo());

		return boardRepository.myQuestionsAnsweredSelectList(cri);
	}

	/* 내 질문 모아 보기 (답변한 것만) -> 게시물 전체 개수 구하기 */
	public int myQuestionsAnsweredSelectListCount(SearchCriteria cri) {
		cri.setUserNo(UserSessionUtils.currentUserNo());

		return boardRepository.myQuestionsAnsweredSelectListCount(cri);
	}

	/** 즐겨찾기 **/
	/* 즐겨찾기 리스트 */
	public List<Board> myFavoriteSelectList(SearchCriteria cri) {
		cri.setOption(4);
		cri.setUserNo(UserSessionUtils.currentUserNo());
		return boardRepository.boardSelectList(cri);

	}

	/* 즐겨 찾기 리스트 전체 개수 구하기 */
	public int myFavoriteSelectListCount(Criteria cri) {
		cri.setUserNo(UserSessionUtils.currentUserNo());
		return boardRepository.myFavoriteSelectListCount(cri);
	}

	/* 즐겨찾기 저장하기 */
	public void bookmarkInsert(Board model) {
		model.setUserNo(UserSessionUtils.currentUserNo());
		bookmarkRepository.bookmarkInsert(model);
	}

	/* 즐겨찾기 메모 저장 하기  */
	public void bookmarkMemoUpdate(BookMark bookMarkModel) {
		bookMarkModel.setUserNo(UserSessionUtils.currentUserNo());
		bookmarkRepository.bookmarkMemoUpdate(bookMarkModel);
	}

	/* 즐겨찾기 메모 불러오기  */
	public BookMark bookmarkMemoSelect(int boardNo) {
		BookMark bookMarkModel = new BookMark();
		bookMarkModel.setUserNo(UserSessionUtils.currentUserNo());
		bookMarkModel.setBoardNo(boardNo);

		return bookmarkRepository.bookmarkMemoSelect(bookMarkModel);
	}

	/* 즐겨찾기 해제  */
	public void bookmarkDelete(Board model) {
		model.setUserNo(UserSessionUtils.currentUserNo());
		bookmarkRepository.bookmarkDelete(model);
	}

}
