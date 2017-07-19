package com.hyunhye.board.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
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
import com.hyunhye.common.UploadFileUtils;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	public BoardRepository repository;

	@Resource(name = "fileUtils")
	private UploadFileUtils fileUtils;

	@Override
	public List<BoardModel> listAll(Model model) throws Exception {
		return repository.listAll();
	}

	@Transactional
	@Override
	public void regist(HttpSession session, BoardModel model) throws Exception {
		int userId = (Integer)session.getAttribute("userId");
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = simpleDateFormat.format(date);

		model.setDate(currentTime);
		model.setUserId(userId);

		repository.regist(model);

		String[] files = model.getFiles();
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

	@Override
	public BoardModel read(int boardId) throws Exception {
		return repository.read(boardId);
	}

	@Override
	public List<FileModel> getAttach(int boardId) throws Exception {
		return repository.getAttach(boardId);
	}

	@Transactional
	@Override
	public BoardModel modify(HttpSession session, BoardModel model) throws Exception {
		int userId = (Integer)session.getAttribute("userId");

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);

		model.setDate(currentTime);
		model.setUserId(userId);

		int[] filesId = model.getFilesId();
		if (filesId != null) {
			int fileId = 0;
			for (int i = 0; i < filesId.length; i++) {
				fileId = filesId[i];
				repository.deleteAttach(fileId);
			}
		}

		String[] files = model.getFiles();
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
		return repository.modify(model);
	}

	@Override
	public void delete(int boardId) throws Exception {
		repository.delete(boardId);
	}

	@Override
	public List<CategoryModel> categoryListAll() throws Exception {
		return repository.categoryListAll();
	}

	@Override
	public List<BoardModel> listCriteria(SearchCriteria cri) throws Exception {
		if (cri.getCategoryType() == null) {
			cri.setCategoryType("");
		}
		if (cri.getSearchType() == null) {
			cri.setSearchType("");
		}
		return repository.listCriteria(cri);
	}

	@Override
	public int listCountCriteria(SearchCriteria cri) throws Exception {
		return repository.countPaging(cri);
	}

	@Override
	public void increaseViewCount(int boardId) throws Exception {
		repository.increaseViewCount(boardId);
	}
}
