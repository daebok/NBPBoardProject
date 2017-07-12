package com.hyunhye.user.service;

import javax.servlet.http.HttpSession;

import com.hyunhye.user.model.UserModel;

public interface UserService {
	
	public void logout(HttpSession session);

	void regist(UserModel dto);

	UserModel viewUser(UserModel dto);

	boolean loginCheck(HttpSession session, UserModel dto);
}
