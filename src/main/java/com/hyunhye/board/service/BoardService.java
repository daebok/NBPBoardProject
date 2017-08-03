package com.hyunhye.board.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.BookMarkModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.repository.BoardRepository;
import com.hyunhye.common.Filtering;
import com.hyunhye.security.UserSession;

@Service
public class BoardService {
	Logger logger = LoggerFactory.getLogger(BoardService.class);

	@Autowired
	public BoardRepository repository;

	@Autowired
	private UploadService uploadService;

	@Resource(name = "uploadPath")
	private String uploadPath;

	/** 게시글  Top10 리스트 **/
	/* 조회순*/
	public List<BoardModel> boardListViews() {
		return repository.boardListViews();
	}

	/* 답변순 */
	public List<BoardModel> boardListTopAnswers() {
		return repository.boardListTopAnswers();
	}

	/* 최신순 */
	public List<BoardModel> boardListNewest() {
		return repository.boardListNewest();
	}

	/** 게시글 **/
	/* 1. 게시글 작성하기 */
	/* 파일을 동시에 저장하기 위해 트랜잭션 사용 */
	@Transactional
	public void boardRegist(int userNo, BoardModel boardModel, MultipartFile[] files) throws Exception {
		boardModel.setUserNo(userNo);

		/* 제거된 태그를 boardContentSummary에 담는다. */
		String summary = createSummary(boardModel.getBoardContent());
		boardModel.setBoardContentSummary(summary);

		/* 1) 작성 된 게시글 저장 */
		repository.boardRegist(boardModel);

		/* 2) 업로드 된 파일 저장 */
		uploadService.fileRegist(boardModel, files);
	}

	/* HTML 태그 제거 */
	public String createSummary(String originalContent) {
		String boardSummary = originalContent
			.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		/* 300글자가 넘어가면 자르기
		if (boardSummary.length() > 300) {
			boardSummary = boardSummary.substring(0, 300);
		}
		 제거된 태그를 boardContentSummary에 담는다. */
		return boardSummary;

	}

	/* 비속어 체크 */
	public List<String> badWordsCheck(BoardModel model) {
		String boardSummary = model.getBoardContent()
			.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		boardSummary += model.getBoardTitle();
		List<String> badWords = Filtering.badWordFilteringContainsStream(boardSummary);
		return badWords;
	}

	/* 2. 해당 게시글 상세 보기 */
	public BoardModel boardSelect(int boardNo) {
		BoardModel boardModel = new BoardModel();
		boardModel.setBoardNo(boardNo);
		boardModel.setUserNo(UserSession.currentUserNo());
		return repository.boardSelect(boardModel);
	}

	/* 조회수 */
	public void setViewCookies(int boardNo, HttpServletRequest request, HttpServletResponse response) {
		Cookie viewCount = WebUtils.getCookie(request, boardNo + "&" + UserSession.currentUserNo());
		int cookieMaxAge = 60 * 5; // 쿠키가 5 분 동안만 유지 할 수 있도록 한다.
		if (viewCount == null) { // 해당 쿠키을 가지고 있으면...
			increaseViewCount(boardNo);
			Cookie cookie = new Cookie(boardNo + "&" + UserSession.currentUserNo(), "view");
			cookie.setMaxAge(cookieMaxAge);
			response.addCookie(cookie);
		}
	}

	/* 등록 된 첨부파일 가져오기  */
	public List<FileModel> getAttach(int boardNo) {
		return repository.getFile(boardNo);
	}

	/* 3. 게시글 수정하기*/
	/* 파일을 동시에 저장하기 위해 트랜잭션 사용*/
	@Transactional
	public void boardModify(BoardModel boardModel, MultipartFile[] files) throws IOException, Exception {
		boardModel.setUserNo(UserSession.currentUserNo());

		/* 제거된 태그를 boardContentSummary에 담는다. */
		String summary = createSummary(boardModel.getBoardContent());
		boardModel.setBoardContentSummary(summary);

		/* 1) 업로드 된 파일 중에서 삭제버튼 누른 파일 삭제하기 */
		uploadService.fileDelete(boardModel);

		/* 2) 새로 업로드 된 파일 저장 */
		uploadService.fileRegist(boardModel, files);

		/* 3) 기존 게시글 수정하기 */
		repository.boardModify(boardModel);
	}

	/* 4. 게시글 삭제하기 */
	@Transactional
	public void boardDelete(int boardNo) {

		/* 1) 해당 게시물에 첨부된 파일 서버에서 삭제 */
		uploadService.fileDeletFromDatabase(boardNo);

		/* 2) 기존 게시물 삭제하기 */
		repository.boardDelete(boardNo);
	}

	/* 5. 게시글 리스트 (페이징) */
	public List<BoardModel> selectListAll(SearchCriteria cri) {
		/* 검색 타입 null 체크 */
		cri = searchTypecheck(cri);

		/* 검색 키워드 <> 체크 */
		if (cri.getKeyword() != null) {
			cri = keywordCheck(cri);
		}
		return repository.selectListAll(cri);
	}

	/* 검색 시, null 체크 */
	public SearchCriteria searchTypecheck(SearchCriteria cri) {
		if (cri.getCategoryType() == null || cri.getCategoryType().equals("null")) {
			cri.setCategoryType("");
		}
		if (cri.getSearchType() == null || cri.getSearchType().equals("null")) {
			cri.setSearchType("");
		}
		return cri;
	}

	/* <> 검색 처리 */
	public SearchCriteria keywordCheck(SearchCriteria cri) {
		String modifiedKeyword = cri.getKeyword().replaceAll("<", "&lt;");
		modifiedKeyword = modifiedKeyword.replaceAll(">", "&gt;");
		cri.setModifiedKeyword(modifiedKeyword);

		return cri;
	}

	/* 게시글 개수 구하기 */
	public int countListAllPaging(SearchCriteria cri) {
		return repository.countListAllPaging(cri);
	}

	/* 조회수 */
	public void increaseViewCount(int boardNo) {
		repository.increaseViewCount(boardNo);
	}

	/* 게시글 작성자 가져오기 */
	/* 인터셉터에서 사용 */
	public int checkUser(int boardNo) {
		return repository.checkUser(boardNo);
	}

	/* 3. 카테고리 목록 가져오기 */
	public List<CategoryModel> categoryListAll() {
		return repository.categoryListAll();
	}

	/** 내 질문 모아 보기  **/
	/* 1. 내 질문 모아 보기 (전체) */
	public List<BoardModel> selectMyQuestions(SearchCriteria cri) {
		/* 검색 타입 null 체크 */
		cri = searchTypecheck(cri);

		/* 검색 키워드 <> 체크 */
		if (cri.getKeyword() != null) {
			cri = keywordCheck(cri);
		}

		cri.setUserNo(UserSession.currentUserNo());
		return repository.selectMyQuestions(cri);
	}

	/* 내 질문 모아 보기 (전체) -> 게시물 전체 개수 구하기 */
	public int countMyQuestionsPaging(SearchCriteria cri) {
		cri.setUserNo(UserSession.currentUserNo());
		return repository.countMyQuestionsPaging(cri);
	}

	/* 2. 내 질문 모아 보기 (답변한 것만) */
	public List<BoardModel> selectMyQuestionsAnswered(SearchCriteria cri) {
		cri.setUserNo(UserSession.currentUserNo());
		return repository.selectMyQuestionsAnswered(cri);
	}

	/* 내 질문 모아 보기 (답변한 것만) -> 게시물 전체 개수 구하기 */
	public int countMyQuestionsAnsweredPaging(SearchCriteria cri) {
		cri.setUserNo(UserSession.currentUserNo());
		return repository.countMyQuestionsAnsweredPaging(cri);
	}

	/** 즐겨찾기 **/
	/* 즐겨찾기 저장하기 */
	public void boardBookMark(BoardModel model) {
		model.setUserNo(UserSession.currentUserNo());
		repository.boardBookMark(model);
	}

	/* 즐겨찾기 리스트 */
	public List<BoardModel> myFavorite(Criteria cri) {
		cri.setUserNo(UserSession.currentUserNo());
		return repository.selectMyFavorite(cri);

	}

	/* 즐겨 찾기 리스트 전체 개수 구하기 */
	public int countMyFavoritePaging(Criteria cri) {
		cri.setUserNo(UserSession.currentUserNo());
		return repository.countMyFavoritePaging(cri);
	}

	/* 즐겨찾기 메모 저장 하기  */
	public void bookMarkMemoRegist(BookMarkModel bookMarkModel) {
		bookMarkModel.setUserNo(UserSession.currentUserNo());
		repository.bookMarkMemoRegist(bookMarkModel);
	}

	/* 즐겨찾기 메모 불러오기  */
	public BookMarkModel memoSelect(int boardNo) {
		BookMarkModel bookMarkModel = new BookMarkModel();
		bookMarkModel.setUserNo(UserSession.currentUserNo());
		bookMarkModel.setBoardNo(boardNo);

		return repository.memoSelect(bookMarkModel);
	}

	/* 즐겨찾기 해제  */
	public void boardBookMarkUnCheck(BoardModel model) {
		model.setUserNo(UserSession.currentUserNo());
		repository.boardBookMarkUnCheck(model);
	}
}
