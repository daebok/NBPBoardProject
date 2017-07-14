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
import org.springframework.ui.Model;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.repository.BoardRepositoryImpl;
import com.hyunhye.common.FileUtils;

@Service(value = "BoardService")
public class BoardServiceImpl implements com.hyunhye.board.service.BoardService {

	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

	@Autowired
	public BoardRepositoryImpl repository;

	@Resource(name="fileUtils")
	    private FileUtils fileUtils;


	@Override
	public void listAll(Model model) {
		// TODO Auto-generated method stub
		List<BoardModel> list;
		list = repository.listAll();
		model.addAttribute("list", list);
	}

	@Override
	public void regist(HttpSession session, BoardModel model) {
		// TODO Auto-generated method stub

		int UID = (Integer) session.getAttribute("UID");
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);

		model.setDATE(currentTime);
		model.setUID(UID);

		repository.regist(model);
	}

	@Override
	public BoardModel read(int id) {
		// TODO Auto-generated method stub
		return repository.read(id);
	}

	// 05. All Questions List
	@Override
	public void listAll(Model model, String searchOption, String keyword) throws Exception {
		List<BoardModel> list;
		list = repository.listAll(searchOption, keyword);

		model.addAttribute("list", list);
	}

	// 06. Questions Count
	@Override
	public int countArticle(String searchOption, String keyword) throws Exception {
		return repository.countArticle(searchOption, keyword);
	}

	// 07. Question Modify
	@Override
	public BoardModel modify(HttpSession session, BoardModel model) {
		// TODO Auto-generated method stub
		int UID = (Integer) session.getAttribute("UID");

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);

		model.setDATE(currentTime);
		model.setUID(UID);
		model.setCID(1);

		return repository.modify(model);
	}

	@Override
	public void delete(int bid, BoardModel model) {
		// TODO Auto-generated method stub
		model.setBID(bid);
		repository.delete(model);
	}

	@Override
	public List<BoardModel> listAll(int start, int end, String searchOption, String keyword) {
		// TODO Auto-generated method stub
		return repository.listAll(start, end, searchOption, keyword);
	}

	@Override
	public List<CategoryModel> categoryListAll() {
		// TODO Auto-generated method stub
		return repository.categoryListAll();
	}
}
