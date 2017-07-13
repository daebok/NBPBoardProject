package com.hyunhye.user.service;

import javax.servlet.http.HttpSession;

import com.hyunhye.user.model.UserModel;

public interface UserService {

	public void logout(HttpSession session);

	public void regist(UserModel model);

	public boolean loginCheck(HttpSession session, UserModel model);

	public UserModel viewUser(UserModel model);
}
