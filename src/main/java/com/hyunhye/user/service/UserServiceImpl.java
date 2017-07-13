package com.hyunhye.user.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.repository.UserRepositoryImpl;

@Service(value = "UserService")
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepositoryImpl repository;

	// Sign Up
	@Override
	public void regist(UserModel model) {
		// TODO Auto-generated method stub
		repository.regist(model);
	}

	// Login
	@Override
	public boolean loginCheck(HttpSession session, UserModel model) {
		// TODO Auto-generated method stub
		boolean result = repository.loginCheck(model);
		if (result) {
			UserModel model2 = viewUser(model);

			session.setAttribute("UID",model2.getUID());
			session.setAttribute("ID", model2.getID());
			session.setAttribute("NAME", model2.getNAME());
		}
		return result;
	}

	// Login Info
	@Override
	public UserModel viewUser(UserModel model) {
		return repository.viewUser(model);
	}

	// Logout
	@Override
	public void logout(HttpSession session) {
		// TODO Auto-generated method stub
		session.invalidate();
	}

	public int select(String id) {
		// TODO Auto-generated method stub
		return repository.select(id);
	}
}
