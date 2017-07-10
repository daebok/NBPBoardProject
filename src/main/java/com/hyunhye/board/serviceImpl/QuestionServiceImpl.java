package com.hyunhye.board.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.hyunhye.board.daoImpl.QuestionDaoImpl;
import com.hyunhye.board.dto.QuestionDto;

@Service(value = "QuestionService")
public class QuestionServiceImpl implements com.hyunhye.board.service.QuestionService {

	@Autowired
	public QuestionDaoImpl dao;

	// QuestionDto dto = new QuestionDto();
	
	@Override
	public void listAll(Model model) {
		// TODO Auto-generated method stub
		List<QuestionDto> list;
		list = dao.listAll();
		model.addAttribute("list", list);
	}

	@Override
	public void regist(Model model, QuestionDto dto) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String TITLE = request.getParameter("title");
		String CONTENT = request.getParameter("content");

		Date dt = new Date();
		SimpleDateFormat sdf = 
		     new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);

		
		dto.setTITLE(TITLE);
		dto.setCONTENT(CONTENT);
		dto.setDATE(currentTime);

		dao.regist(dto);
	}

	@Override
	public void read(Model model, QuestionDto dto) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		int BID = Integer.parseInt(request.getParameter("id"));
		dto.setBID(BID);
		
		List<QuestionDto> list = dao.read(dto);
		model.addAttribute("list", list);
	}
	
	// 05. 게시글 전체 목록 boardDAO.listAll메서드 호출
	@Override
	public void listAll(Model model, String searchOption, String keyword) throws Exception {
		List<QuestionDto> list;
		list = dao.listAll(searchOption, keyword);
		
		model.addAttribute("list", list);
	}
	
	// 07. 게시글 레코드 갯수 boardDao.countArticle메서드 
	@Override
	public int countArticle(String searchOption, String keyword) throws Exception {
	     return dao.countArticle(searchOption, keyword);
	}


}
