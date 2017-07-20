package com.hyunhye.board.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	public BoardRepository repository;


	/* 게시글 리스트 */
	public List<BoardModel> boardListAll(Model model) throws Exception {
		return repository.boardListAll();
	}

	/*
	 * 게시글 작성하기
	 * 파일을 동시에 저장하기 위해 트랜잭션 사용
	 */
	@Transactional
	public void boardRegist(HttpSession session, BoardModel boardModel) throws Exception {
		int userId = (Integer)session.getAttribute("userId");
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = simpleDateFormat.format(date);

		boardModel.setDate(currentTime);
		boardModel.setUserId(userId);

		repository.boardRegist(boardModel);

		/* 업로드 된 첨부파일 가져오기 */
		String[] files = boardModel.getFiles();
		if (files == null) {
			return;
		}

		String fileName = null;
		for (int i = 1; i < files.length; i++) {
			fileName = files[i];
			FileModel fileModel = new FileModel();
			fileModel.setFileName(fileName.substring(fileName.indexOf("=") + 1));
			fileModel.setOriginName(fileName.substring(fileName.lastIndexOf("_") + 1));
			fileModel.setExtension(fileName.substring(fileName.lastIndexOf(".") + 1));

			repository.addAttach(fileModel);
		}
	}

	/* 해당 게시글 상세 보기 */
	public BoardModel boardSelect(int boardId) throws Exception {
		return repository.boardSelect(boardId);
	}

	/* 첨부파일 등록 */
	public List<FileModel> getAttach(int boardId) throws Exception {
		return repository.getAttach(boardId);
	}

	/*
	 * 게시글 수정하기
	 * 파일을 동시에 저장하기 위해 트랜잭션 사용
	 */
	@Transactional
	public BoardModel boardModify(HttpSession session, BoardModel boardModel) throws Exception {
		int userId = (Integer)session.getAttribute("userId");

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);

		boardModel.setDate(currentTime);
		boardModel.setUserId(userId);

		/* 삭제된 첨부파일 번호 가져오기 */
		int[] filesId = boardModel.getFilesId();
		if (filesId != null) {
			int fileId = 0;
			for (int i = 0; i < filesId.length; i++) {
				fileId = filesId[i];
				repository.deleteAttach(fileId);
			}
		}

		/* 추가된 첨부파일 가져오기 */
		String[] files = boardModel.getFiles();
		if (files != null) {
			String fileName = null;
			for (int i = 1; i < files.length; i++) {
				fileName = files[i];
				FileModel fileModel = new FileModel();
				fileModel.setFileName(fileName.substring(fileName.indexOf("=") + 1));
				fileModel.setOriginName(fileName.substring(fileName.lastIndexOf("_") + 1));
				fileModel.setExtension(fileName.substring(fileName.lastIndexOf(".") + 1));

				repository.addAttach(fileModel);
			}
		}
		return repository.boardModify(boardModel);
	}

	/* 게시글 삭제하기 */
	public void boardDelete(int boardId) throws Exception {
		repository.boardDelete(boardId);
	}

	/* 카테고리 목록 가져오기 */
	public List<CategoryModel> categoryListAll() throws Exception {
		return repository.categoryListAll();
	}

	/* 게시글 리스트 (페이징) */
	public List<BoardModel> listCriteria(SearchCriteria cri) throws Exception {
		if (cri.getCategoryType() == null) {
			cri.setCategoryType("");
		}
		if (cri.getSearchType() == null) {
			cri.setSearchType("");
		}
		return repository.listCriteria(cri);
	}

	/* 게시글 개수 구하기 */
	public int listCountCriteria(SearchCriteria cri) throws Exception {
		return repository.countPaging(cri);
	}

	/* 조회수 */
	public void increaseViewCount(int boardId) throws Exception {
		repository.increaseViewCount(boardId);
	}

	/* 게시글 작성자 가져오기 */
	public int checkUser(int boardId) throws Exception {
		return repository.checkUser(boardId);
	}
}
