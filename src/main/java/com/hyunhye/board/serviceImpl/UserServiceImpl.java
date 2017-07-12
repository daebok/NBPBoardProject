package com.hyunhye.board.serviceImpl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.board.daoImpl.UserDaoImpl;
import com.hyunhye.board.dto.UserDto;
import com.hyunhye.board.service.UserService;

@Service(value = "UserService")
public class UserServiceImpl implements UserService {

	@Autowired
	public UserDaoImpl dao;

	// Sign Up
	@Override
	public void regist(UserDto dto) {
		// TODO Auto-generated method stub
		dao.regist(dto);
		
		/*Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String name = request.getParameter("name");

		UserDto userVo = new UserDto(); 
		userVo.setID(id);
		userVo.setPASSWORD(password);
		userVo.setNAME(name);*/
	}

	// Login
	@Override
	public boolean loginCheck(HttpSession session, UserDto dto) {
		// TODO Auto-generated method stub
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
	@Override
	public UserDto viewUser(UserDto dto) {
		return dao.viewUser(dto);
	}

	// Logout
	@Override
	public void logout(HttpSession session) {
		// TODO Auto-generated method stub
		session.invalidate();
	}

}
