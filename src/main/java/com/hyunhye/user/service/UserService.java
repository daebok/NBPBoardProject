package com.hyunhye.user.service;

import javax.servlet.http.HttpSession;

import com.hyunhye.user.model.UserModel;

public interface UserService {

	public void logout(HttpSession session) throws Exception;

	public void regist(UserModel model) throws Exception;

	public boolean loginCheck(HttpSession session, UserModel model) throws Exception;

	public UserModel viewUser(UserModel model) throws Exception;

	public int select(String id) throws Exception;
}
