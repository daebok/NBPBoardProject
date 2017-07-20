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


	public List<BoardModel> boardListAll(Model model) throws Exception {
		return repository.boardListAll();
	}

	@Transactional
	public void boardRegist(HttpSession session, BoardModel boardModel) throws Exception {
		int userId = (Integer)session.getAttribute("userId");
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = simpleDateFormat.format(date);

		boardModel.setDate(currentTime);
		boardModel.setUserId(userId);

		repository.boardRegist(boardModel);

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

	public BoardModel boardSelect(int boardId) throws Exception {
		return repository.boardSelect(boardId);
	}

	public List<FileModel> getAttach(int boardId) throws Exception {
		return repository.getAttach(boardId);
	}

	@Transactional
	public BoardModel boardModify(HttpSession session, BoardModel boardModel) throws Exception {
		int userId = (Integer)session.getAttribute("userId");

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);

		boardModel.setDate(currentTime);
		boardModel.setUserId(userId);

		int[] filesId = boardModel.getFilesId();
		if (filesId != null) {
			int fileId = 0;
			for (int i = 0; i < filesId.length; i++) {
				fileId = filesId[i];
				repository.deleteAttach(fileId);
			}
		}

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

	public void boardDelete(int boardId) throws Exception {
		repository.boardDelete(boardId);
	}

	public List<CategoryModel> categoryListAll() throws Exception {
		return repository.categoryListAll();
	}

	public List<BoardModel> listCriteria(SearchCriteria cri) throws Exception {
		if (cri.getCategoryType() == null) {
			cri.setCategoryType("");
		}
		if (cri.getSearchType() == null) {
			cri.setSearchType("");
		}
		return repository.listCriteria(cri);
	}

	public int listCountCriteria(SearchCriteria cri) throws Exception {
		return repository.countPaging(cri);
	}

	public void increaseViewCount(int boardId) throws Exception {
		repository.increaseViewCount(boardId);
	}

	public int checkUser(int boardId) throws Exception {
		return repository.checkUser(boardId);
	}
}
