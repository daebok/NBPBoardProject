package com.hyunhye.user.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepository repository;

	@Override
	public void regist(UserModel model) throws Exception {
		repository.regist(model);
	}

	@Override
	public boolean loginCheck(HttpSession session, UserModel model) throws Exception {
		boolean result = repository.loginCheck(model);
		if (result) {
			UserModel model2 = viewUser(model);

			session.setAttribute("userId", model2.getUserId());
			session.setAttribute("id", model2.getId());
			session.setAttribute("name", model2.getName());
		}
		return result;
	}

	@Override
	public UserModel viewUser(UserModel model) throws Exception {
		return repository.viewUser(model);
	}

	@Override
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@Override
	public int select(String id) throws Exception {
		return repository.select(id);
	}
}
