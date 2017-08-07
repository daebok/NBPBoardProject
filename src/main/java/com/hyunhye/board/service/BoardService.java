package com.hyunhye.board.service;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.BookMark;
import com.hyunhye.board.model.Category;
import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.repository.BoardRepository;
import com.hyunhye.board.repository.CategoryRepository;
import com.hyunhye.common.BadWordFilteringUtils;
import com.hyunhye.security.UserSessionUtils;

@Service
public class BoardService {
	Logger logger = LoggerFactory.getLogger(BoardService.class);
	public static final Pattern SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", Pattern.DOTALL);
	public static final Pattern STYLE = Pattern.compile("<style[^>]*>.*</style>", Pattern.DOTALL);

	@Autowired
	public BoardRepository boardRepository;

	@Autowired
	public CategoryRepository categoryRepository;

	@Autowired
	private UploadService uploadService;

	/** 게시글 **/
	/* 1. 게시글 작성하기 */
	/* 파일을 동시에 저장하기 위해 트랜잭션 사용 */
	@Transactional
	public void boardInsert(int userNo, Board boardModel, MultipartFile[] files) throws Exception {
		boardModel.setUserNo(userNo);

		/* style, script 태그 제거 */
		Matcher matcher;
		matcher = SCRIPTS.matcher(boardModel.getBoardContent());
		boardModel.setBoardContent(matcher.replaceAll(""));

		matcher = STYLE.matcher(boardModel.getBoardContent());
		boardModel.setBoardContent(matcher.replaceAll(""));

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
		String boardSummary = originalContent
			.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		boardSummary = HtmlUtils.htmlUnescape(boardSummary);
		return boardSummary;

	}

	/* 비속어 체크 */
	public List<String> badWordsCheck(Board model) {
		String boardSummary = createSummary(model.getBoardContent());
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
	public List<FileModel> getAttach(int boardNo) {
		return boardRepository.getFile(boardNo);
	}

	/* 3. 게시글 수정하기*/
	/* 파일을 동시에 저장하기 위해 트랜잭션 사용*/
	@Transactional
	public void boardUpdate(Board boardModel, MultipartFile[] files) throws IOException, Exception {
		boardModel.setUserNo(UserSessionUtils.currentUserNo());

		/* style, script 태그, 제거 */
		Matcher matcher;
		matcher = SCRIPTS.matcher(boardModel.getBoardContent());
		boardModel.setBoardContent(matcher.replaceAll(""));

		matcher = STYLE.matcher(boardModel.getBoardContent());
		boardModel.setBoardContent(matcher.replaceAll(""));

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
	public List<Board> boardSelectList(SearchCriteria cri) {
		cri.setOption(1);
		return boardRepository.boardSelectList(cri);
	}

	/* 게시글 개수 구하기 */
	public int boardSelectListCount(SearchCriteria cri) {
		return boardRepository.boardSelectListCount(cri);
	}

	/* 조회수 */
	public void increaseViewCount(int boardNo) {
		boardRepository.increaseViewCount(boardNo);
	}

	/* 게시글 작성자 가져오기 */
	/* 인터셉터에서 사용 */
	public int checkUser(int boardNo) {
		return boardRepository.checkUser(boardNo);
	}

	/* 3. 카테고리 목록 가져오기 */
	public List<Category> categoryListAll() {
		List<Category> category = categoryRepository.categorySelectList();

		List<Category> list = category.stream()
			.parallel()
			.filter(s -> s.getCategoryEnabled() != 0)
			.collect(Collectors.toList());

		return list;
	}

	/** 내 질문 모아 보기  **/
	/* 1. 내 질문 모아 보기 (전체) */
	public List<Board> myQuestionsSelectList(SearchCriteria cri) {
		cri.setOption(2);
		cri.setUserNo(UserSessionUtils.currentUserNo());
		return boardRepository.boardSelectList(cri);
	}

	/* 내 질문 모아 보기 (전체) -> 게시물 전체 개수 구하기 */
	public int countMyQuestionsPaging(SearchCriteria cri) {
		cri.setUserNo(UserSessionUtils.currentUserNo());
		return boardRepository.countMyQuestionsPaging(cri);
	}

	/* 2. 내 질문 모아 보기 (답변한 것만) */
	public List<Board> myQuestionsAnsweredSelectList(SearchCriteria cri) {
		cri.setOption(3);
		cri.setUserNo(UserSessionUtils.currentUserNo());
		return boardRepository.myQuestionsAnsweredSelectList(cri);
	}

	/* 내 질문 모아 보기 (답변한 것만) -> 게시물 전체 개수 구하기 */
	public int countMyQuestionsAnsweredPaging(SearchCriteria cri) {
		cri.setUserNo(UserSessionUtils.currentUserNo());
		return boardRepository.countMyQuestionsAnsweredPaging(cri);
	}

	/** 즐겨찾기 **/
	/* 즐겨찾기 리스트 */
	public List<Board> myFavoriteSelectList(SearchCriteria cri) {
		cri.setOption(4);
		cri.setUserNo(UserSessionUtils.currentUserNo());
		return boardRepository.boardSelectList(cri);

	}

	/* 즐겨찾기 저장하기 */
	public void bookmarkInsert(Board model) {
		model.setUserNo(UserSessionUtils.currentUserNo());
		boardRepository.bookmarkInsert(model);
	}

	/* 즐겨 찾기 리스트 전체 개수 구하기 */
	public int countMyFavoritePaging(Criteria cri) {
		cri.setUserNo(UserSessionUtils.currentUserNo());
		return boardRepository.countMyFavoritePaging(cri);
	}

	/* 즐겨찾기 메모 저장 하기  */
	public void bookmarkMemoUpdate(BookMark bookMarkModel) {
		bookMarkModel.setUserNo(UserSessionUtils.currentUserNo());
		boardRepository.bookmarkMemoUpdate(bookMarkModel);
	}

	/* 즐겨찾기 메모 불러오기  */
	public BookMark bookmarkMemoSelect(int boardNo) {
		BookMark bookMarkModel = new BookMark();
		bookMarkModel.setUserNo(UserSessionUtils.currentUserNo());
		bookMarkModel.setBoardNo(boardNo);

		return boardRepository.bookmarkMemoSelect(bookMarkModel);
	}

	/* 즐겨찾기 해제  */
	public void bookmarkDelete(Board model) {
		model.setUserNo(UserSessionUtils.currentUserNo());
		boardRepository.bookmarkDelete(model);
	}
}
