package com.hyunhye.user.service;

import java.util.Date;

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
		session.removeAttribute("login");
		session.removeAttribute("id");
		session.removeAttribute("name");
		session.removeAttribute("userId");
		session.invalidate();
	}

	@Override
	public int select(String id) throws Exception {
		return repository.select(id);
	}

	@Override
	public void keepLogin(String id, String sessionId, Date next) throws Exception {
		repository.keepLogin(id, sessionId, next);
	}

	@Override
	public UserModel checkLoginBefore(String value) {
		return repository.checkUserWithSessionKey(value);
	}
}
