package com.hyunhye.board.serviceImpl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.hyunhye.board.daoImpl.UserDaoImpl;
import com.hyunhye.board.dto.UserDto;
import com.hyunhye.board.service.UserService;

@Service(value = "UserService")
public class UserServiceImpl implements UserService {

	@Autowired
	public UserDaoImpl dao;

	// Sign Up
	@Override
	public void regist(Model model, UserDto dto) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String ID = request.getParameter("id");
		String PW = request.getParameter("password");
		String NM = request.getParameter("name");

		// UserDto dto = new UserDto();
		dto.setID(ID);
		dto.setPASSWORD(PW);
		dto.setNAME(NM);

		dao.regist(dto);
	}

	public void delete(Model model, UserDto dto) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String ID = request.getParameter("id");
		dto.setID(ID);
	}

	// Login
	public boolean loginCheck(HttpSession session, Model model, UserDto dto) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String ID = request.getParameter("id");
		String PW = request.getParameter("password");
		dto.setID(ID);
		dto.setPASSWORD(PW);
		
		boolean result = dao.loginCheck(dto);
		if (result) {
			UserDto dto2 = viewUser(dto);
			
			session.setAttribute("UID",dto2.getUID());
			session.setAttribute("ID", dto2.getID());
			session.setAttribute("NAME", dto2.getNAME());
		}
		return result;
	}

	// Login Info
	public UserDto viewUser(UserDto dto) {
		return dao.viewUser(dto);
	}

	// Logout
	public void logout(HttpSession session) {
		// TODO Auto-generated method stub
		session.invalidate();
	}

}
