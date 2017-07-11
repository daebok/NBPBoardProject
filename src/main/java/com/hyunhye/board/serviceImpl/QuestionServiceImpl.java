package com.hyunhye.board.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hyunhye.board.common.FileUtils;
import com.hyunhye.board.daoImpl.QuestionDaoImpl;
import com.hyunhye.board.dto.QuestionDto;

@Service(value = "QuestionService")
public class QuestionServiceImpl implements com.hyunhye.board.service.QuestionService {
	
	private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);
	
	@Autowired
	public QuestionDaoImpl dao;

	 @Resource(name="fileUtils")
	    private FileUtils fileUtils;

		
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

		// File Upload
		List<Map<String, Object>> list = null;
		try {
			list = fileUtils.parseInsertFileInfo(map, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
        for(int i=0, size=list.size(); i<size; i++){
            dao.insertFile(list.get(i));
        }

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

	@Override
	public void delete(int bid, QuestionDto dto) {
		// TODO Auto-generated method stub
		dto.setBID(bid);
		dao.delete(dto);
	}

	@Override
	public List<QuestionDto> listAll(int start, int end, String searchOption, String keyword, QuestionDto dto) {
		// TODO Auto-generated method stub
		return dao.listAll(start, end, searchOption, keyword, dto);
	}
}
