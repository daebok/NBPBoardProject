package com.hyunhye.board.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.repository.BoardRepositoryImpl;
import com.hyunhye.common.UploadFileUtils;

@Service(value = "BoardService")
public class BoardServiceImpl implements BoardService {

	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

	@Autowired
	public BoardRepositoryImpl repository;

	@Resource(name="fileUtils")
	private UploadFileUtils fileUtils;


	@Override
	public void listAll(Model model) {
		List<BoardModel> list;
		list = repository.listAll();
		model.addAttribute("list", list);
	}

	@Transactional
	@Override
	public void regist(HttpSession session, BoardModel model) {
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
	public BoardModel read(int boardId) {
		return repository.read(boardId);
	}

	@Override
	public List<FileModel> getAttach(int boardId) {
		return repository.getAttach(boardId);
	}

	@Override
	public BoardModel modify(HttpSession session, BoardModel model) {
		int userId = (Integer)session.getAttribute("userId");

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);

		model.setDate(currentTime);
		model.setUserId(userId);
		model.setCategoryId(1);

		return repository.modify(model);
	}

	@Override
	public void delete(int boardId, BoardModel model) {
		model.setBoardId(boardId);
		repository.delete(model);
	}

	@Override
	public List<CategoryModel> categoryListAll() {
		return repository.categoryListAll();
	}

	@Override
	public List<BoardModel> listCriteria(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return repository.listCriteria(cri);
	}

	@Override
	public int listCountCriteria(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return repository.countPaging(cri);
	}
}
