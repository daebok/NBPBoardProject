package com.hyunhye.user.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.repository.UserRepositoryImpl;

@Service(value = "UserService")
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepositoryImpl dao;

	// Sign Up
	@Override
	public void regist(UserModel dto) {
		// TODO Auto-generated method stub
		dao.regist(dto);
	}

	// Login
	@Override
	public boolean loginCheck(HttpSession session, UserModel dto) {
		// TODO Auto-generated method stub
		boolean result = dao.loginCheck(dto);
		if (result) {
			UserModel dto2 = viewUser(dto);
			
			session.setAttribute("UID",dto2.getUID());
			session.setAttribute("ID", dto2.getID());
			session.setAttribute("NAME", dto2.getNAME());
		}
		return result;
	}

	// Login Info
	@Override
	public UserModel viewUser(UserModel dto) {
		return dao.viewUser(dto);
	}

	// Logout
	@Override
	public void logout(HttpSession session) {
		// TODO Auto-generated method stub
		session.invalidate();
	}

}
