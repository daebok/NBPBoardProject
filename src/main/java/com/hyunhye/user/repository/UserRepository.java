package com.hyunhye.user.repository;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.hyunhye.user.model.UserModel;

public interface UserRepository {
	public void regist(UserModel model) throws Exception;

	public void delete(UserModel model) throws Exception;

	boolean loginCheck(UserModel model) throws Exception;

	public UserModel viewUser(UserModel model) throws Exception;

	public void logout(HttpSession session) throws Exception;

	public int select(String id) throws Exception;

	public void keepLogin(String id, String sessionId, Date next);

	public UserModel checkUserWithSessionKey(String value);
}
