package com.hyunhye.board.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.BookMarkModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.repository.BoardRepository;
import com.hyunhye.common.Filtering;
import com.hyunhye.common.UploadFileUtils;
import com.hyunhye.user.model.UserModelDetails;

@Service
public class BoardService {
	Logger logger = LoggerFactory.getLogger(BoardService.class);

	@Autowired
	public BoardRepository repository;

	@Resource(name = "uploadPath")
	private String uploadPath;

	/* 게시글 리스트 */
	public List<BoardModel> boardListAll() {
		return repository.boardListAll();
	}

	public List<BoardModel> boardListTopAnswers() {
		return repository.boardListTopAnswers();
	}

	public List<BoardModel> boardListNewest() {
		return repository.boardListNewest();
	}

	/*
	 * 게시글 작성하기
	 * 파일을 동시에 저장하기 위해 트랜잭션 사용
	 */
	@Transactional
	public void boardRegist(int userNo, BoardModel boardModel, MultipartFile[] files) throws Exception {
		boardModel.setUserNo(userNo);

		/* HTML 태그 제거 */
		String boardSummary = boardModel.getBoardContent()
			.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		if (boardSummary.length() > 300) {
			boardSummary = boardSummary.substring(0, 300);
		}
		boardModel.setBoardContentSummary(boardSummary);

		repository.boardRegist(boardModel);

		/* 삭제된 첨부파일 번호 가져오기 */
		int[] filesNo = boardModel.getBoardFilesNo();
		int index2 = 0;

		/* 파일 업로드 */
		String homePath = System.getProperty("user.home").replaceAll("\\\\", "/");
		for (int index = 0; index < files.length; index++) {
			if (filesNo != null && index <= filesNo.length && (index - 1) == filesNo[index2]) {
				index2++;
				continue;
			}
			MultipartFile file = files[index];

			String fileOriginalName = file.getOriginalFilename();
			long fileSize = file.getSize();
			String fileContentType = fileOriginalName.substring(fileOriginalName.lastIndexOf(".") + 1);

			/* 파일을 첨부하지 않았을 때 */
			if (fileContentType.equals("")) {
				continue;
			}

			/* 1. 서버에 업로드 */
			String fileName = UploadFileUtils.uploadFile(homePath + uploadPath, fileOriginalName, fileContentType,
				file.getBytes());
			FileModel fileModel = new FileModel();
			fileModel.setFileName(fileName);
			fileModel.setFileOriginName(fileOriginalName);
			fileModel.setFileExtension(fileContentType);
			fileModel.setFileSize(fileSize);

			/* 2. 데이터베이스에 저장 */
			repository.addFile(fileModel);

		}
	}

	/* 해당 게시글 상세 보기 */
	public BoardModel boardSelect(int boardNo) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		BoardModel boardModel = new BoardModel();
		boardModel.setBoardNo(boardNo);
		boardModel.setUserNo(user.getUserNo());
		return repository.boardSelect(boardModel);
	}

	/* 첨부파일 등록 */
	public List<FileModel> getAttach(int boardNo) {
		return repository.getFile(boardNo);
	}

	/*
	 * 게시글 수정하기
	 * 파일을 동시에 저장하기 위해 트랜잭션 사용
	 */
	@Transactional
	public BoardModel boardModify(BoardModel boardModel, MultipartFile[] files) throws IOException, Exception {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userNo = user.getUserNo();

		boardModel.setUserNo(userNo);

		/* 삭제된 첨부파일  가져오기 */
		String[] filesDelete = boardModel.getBoardFilesDelete();
		if (filesDelete != null) {
			String fileDelete;
			for (int i = 0; i < filesDelete.length; i++) {
				fileDelete = filesDelete[i];

				/* 1. 서버에서 삭제 */
				String homePath = System.getProperty("user.home").replaceAll("\\\\", "/");

				new File(homePath + uploadPath + fileDelete.replace('/', File.separatorChar)).delete();

				/* 2. 데이버베이스에서 삭제*/
				repository.deleteFile(fileDelete);
			}
		}

		/* 삭제된 첨부파일 번호 가져오기 */
		int[] filesNo = boardModel.getBoardFilesNo();
		int index2 = 0;

		/* 파일 업로드 */
		String homePath = System.getProperty("user.home").replaceAll("\\\\", "/");
		for (int index = 0; index < files.length; index++) {
			if (filesNo != null && index <= filesNo.length && (index - 1) == filesNo[index2]) {
				index2++;
				continue;
			}
			MultipartFile file = files[index];
			String fileOriginalName = file.getOriginalFilename();
			String fileContentType = fileOriginalName.substring(fileOriginalName.lastIndexOf(".") + 1);
			long fileSize = file.getSize();

			/* 파일을 첨부하지 않았을 때 */
			if (fileContentType.equals("")) {
				continue;
			}

			String fileName = UploadFileUtils.uploadFile(homePath + uploadPath, fileOriginalName, fileContentType,
				file.getBytes());
			FileModel fileModel = new FileModel();
			fileModel.setFileName(fileName);
			fileModel.setFileOriginName(fileOriginalName);
			fileModel.setFileExtension(fileContentType);
			fileModel.setFileSize(fileSize);

			repository.addFile(fileModel);
		}

		return repository.boardModify(boardModel);
	}

	/* 게시글 삭제하기 */
	@Transactional
	public void boardDelete(int boardNo) {

		/* 삭제된 첨부파일  가져오기 */
		List<FileModel> filesDelete = repository.fileSelect(boardNo);
		if (filesDelete != null) {
			FileModel fileDelete;
			for (int i = 0; i < filesDelete.size(); i++) {
				fileDelete = filesDelete.get(i);
				String fileName = fileDelete.getFileName();

				/* 서버에서 삭제 */
				String homePath = System.getProperty("user.home").replaceAll("\\\\", "/");
				new File(homePath + uploadPath + fileName.replace('/', File.separatorChar)).delete();
			}
		}

		repository.boardDelete(boardNo);
	}

	/* 카테고리 목록 가져오기 */
	public List<CategoryModel> categoryListAll() {
		return repository.categoryListAll();
	}

	/* 게시글 리스트 (페이징) */
	public List<BoardModel> listCriteria(SearchCriteria cri) {
		if (cri.getCategoryType() == null || cri.getCategoryType().equals("null")) {
			cri.setCategoryType("");
		}
		if (cri.getSearchType() == null || cri.getSearchType().equals("null")) {
			cri.setSearchType("");
		}
		return repository.listCriteria(cri);
	}

	/* 게시글 개수 구하기 */
	public int listCountCriteria(SearchCriteria cri) {
		return repository.countPaging(cri);
	}

	/* 조회수 */
	public void increaseViewCount(int boardNo) {
		repository.increaseViewCount(boardNo);
	}

	/* 게시글 작성자 가져오기 */
	public int checkUser(int boardNo) {
		logger.info("checkUser-test");
		return repository.checkUser(boardNo);
	}

	public List<BoardModel> selectMyQuestions(Criteria cri) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		cri.setUserNo(user.getUserNo());
		return repository.selectMyQuestions(cri);
	}

	public int countMyQuestionsPaging(Criteria cri) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		cri.setUserNo(user.getUserNo());
		return repository.countMyQuestionsPaging(cri);
	}

	/* 즐겨찾기 */
	public void boardBookMark(BoardModel model) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.setUserNo(user.getUserNo());
		repository.boardBookMark(model);
	}

	public List<BoardModel> myFavorite(Criteria cri) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		cri.setUserNo(user.getUserNo());
		return repository.selectMyFavorite(cri);

	}

	public int countMyFavoritePaging(Criteria cri) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		cri.setUserNo(user.getUserNo());
		return repository.countMyFavoritePaging(cri);
	}

	public void bookMarkMemoRegist(BookMarkModel bookMarkModel) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		bookMarkModel.setUserNo(user.getUserNo());
		repository.bookMarkMemoRegist(bookMarkModel);
	}

	public BookMarkModel memoSelect(int boardNo) {
		BookMarkModel bookMarkModel = new BookMarkModel();
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		bookMarkModel.setUserNo(user.getUserNo());
		bookMarkModel.setBoardNo(boardNo);

		return repository.memoSelect(bookMarkModel);
	}

	public void boardBookMarkUnCheck(BoardModel model) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.setUserNo(user.getUserNo());
		repository.boardBookMarkUnCheck(model);
	}

	public List<String> badWordsCheck(BoardModel model) {
		String boardSummary = model.getBoardContent()
			.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		boardSummary += model.getBoardTitle();
		List<String> badWords = Filtering.badWordFilteringContainsStream(boardSummary);
		return badWords;
	}

	public List<BoardModel> selectMyQuestionsAnswered(Criteria cri) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		cri.setUserNo(user.getUserNo());
		return repository.selectMyQuestionsAnswered(cri);
	}

	public int countMyQuestionsAnsweredPaging(Criteria cri) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		cri.setUserNo(user.getUserNo());
		return repository.countMyQuestionsAnsweredPaging(cri);
	}

}
