package com.hyunhye.user.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.hyunhye.user.model.UserModel;

public interface UserService {

	public void regist(UserModel model) throws Exception;

	public boolean loginCheck(HttpSession session, UserModel model) throws Exception;

	public UserModel viewUser(UserModel model) throws Exception;

	public int select(String id) throws Exception;

	public void keepLogin(String id, String sessionId, Date next) throws Exception;

	public UserModel checkLoginBefore(String value);

	public void logout(HttpSession session);
}
