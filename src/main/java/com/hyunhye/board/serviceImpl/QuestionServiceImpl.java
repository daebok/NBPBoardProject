package com.hyunhye.board.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.hyunhye.board.daoImpl.QuestionDaoImpl;
import com.hyunhye.board.dto.QuestionDto;

@Service(value = "QuestionService")
public class QuestionServiceImpl implements com.hyunhye.board.service.QuestionService {

	@Autowired
	public QuestionDaoImpl dao;

	@Override
	public void listAll(Model model) {
		// TODO Auto-generated method stub
		List<QuestionDto> list;
		list = dao.listAll();
		model.addAttribute("list", list);
	}

	@Override
	public void regist(HttpSession session, Model model, QuestionDto dto) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String TITLE = request.getParameter("title");
		String CONTENT = request.getParameter("content");
		int UID = (Integer) session.getAttribute("UID");

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);

		dto.setTITLE(TITLE);
		dto.setCONTENT(CONTENT);
		dto.setDATE(currentTime);
		dto.setUID(UID);
		dto.setCID(1);

		dao.regist(dto);
	}

	@Override
	public QuestionDto read(Model model, QuestionDto dto) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		int BID = Integer.parseInt(request.getParameter("id"));
		dto.setBID(BID);

		return dao.read(dto);
	}

	// 05. All Questions List
	@Override
	public void listAll(Model model, String searchOption, String keyword) throws Exception {
		List<QuestionDto> list;
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
	public QuestionDto modify(HttpSession session, Model model, QuestionDto dto) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		int BID = Integer.parseInt(request.getParameter("bid"));
		int UID = (Integer) session.getAttribute("UID");
		String TITLE = request.getParameter("title");
		String CONTENT = request.getParameter("content");

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		
		dto.setBID(BID);
		dto.setTITLE(TITLE);
		dto.setCONTENT(CONTENT);
		dto.setDATE(currentTime);
		dto.setUID(UID);
		dto.setCID(1);

		return dao.modify(dto);
	}

}
