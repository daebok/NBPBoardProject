package com.hyunhye.user.repository;

import javax.servlet.http.HttpSession;

import com.hyunhye.user.model.UserModel;

public interface UserRepository {
	public void regist(UserModel model) throws Exception;

	public void delete(UserModel model) throws Exception;

	boolean loginCheck(UserModel model) throws Exception;

	public UserModel viewUser(UserModel model) throws Exception;

	public void logout(HttpSession session) throws Exception;

	public int select(String id) throws Exception;
}
