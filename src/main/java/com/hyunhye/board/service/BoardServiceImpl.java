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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.repository.BoardRepositoryImpl;
import com.hyunhye.common.FileUtils;

@Service(value = "BoardService")
public class BoardServiceImpl implements com.hyunhye.board.service.BoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	@Autowired
	public BoardRepositoryImpl dao;

	@Resource(name="fileUtils")
	    private FileUtils fileUtils;

		
	@Override
	public void listAll(Model model) {
		// TODO Auto-generated method stub
		List<BoardModel> list;
		list = dao.listAll();
		model.addAttribute("list", list);
	}

	@Override
	public void regist(HttpSession session, BoardModel dto) {
		// TODO Auto-generated method stub
		int UID = (Integer) session.getAttribute("UID");
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		
		dto.setDATE(currentTime);
		dto.setUID(UID);
		dto.setCID(1);

		dao.regist(dto);
	}

	@Override
	public BoardModel read(int id) {
		// TODO Auto-generated method stub
		return dao.read(id);
	}

	// 05. All Questions List
	@Override
	public void listAll(Model model, String searchOption, String keyword) throws Exception {
		List<BoardModel> list;
		list = dao.listAll(searchOption, keyword);

		model.addAttribute("list", list);
	}

	// 06. Questions Count
	@Override
	public int countArticle(String searchOption, String keyword) throws Exception {
		return dao.countArticle(searchOption, keyword);
	}

	// 07. Question Modify
	@Override
	public BoardModel modify(HttpSession session, BoardModel dto) {
		// TODO Auto-generated method stub
		int UID = (Integer) session.getAttribute("UID");

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);

		dto.setDATE(currentTime);
		dto.setUID(UID);
		dto.setCID(1);

		return dao.modify(dto);
	}

	@Override
	public void delete(int bid, BoardModel dto) {
		// TODO Auto-generated method stub
		dto.setBID(bid);
		dao.delete(dto);
	}

	@Override
	public List<BoardModel> listAll(int start, int end, String searchOption, String keyword) {
		// TODO Auto-generated method stub
		return dao.listAll(start, end, searchOption, keyword);
	}

	@Override
	public List<CategoryModel> categoryListAll() {
		// TODO Auto-generated method stub
		return dao.categoryListAll();
	}
}
