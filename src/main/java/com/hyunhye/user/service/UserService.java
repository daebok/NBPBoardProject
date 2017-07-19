package com.hyunhye.user.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	public UserRepository repository;

	public void userRegist(UserModel model) throws Exception {
		repository.userRegist(model);
	}

	public int loginCheck(HttpSession session, UserModel model) throws Exception {
		int result = repository.loginCheck(model);

		if (result == 1) {
			UserModel userModel = viewUser(model);

			session.setAttribute("userId", userModel.getUserId());
			session.setAttribute("id", userModel.getId());
			session.setAttribute("name", userModel.getName());
		}
		return result;
	}

	public UserModel viewUser(UserModel model) throws Exception {
		return repository.viewUser(model);
	}

	public void logout(HttpSession session) {
		session.removeAttribute("login");
		session.removeAttribute("id");
		session.removeAttribute("name");
		session.removeAttribute("userId");
		session.invalidate();
	}

	public int userSelect(String id) throws Exception {
		return repository.userSelect(id);
	}
}
