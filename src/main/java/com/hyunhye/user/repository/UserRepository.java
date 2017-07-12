package com.hyunhye.user.repository;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.hyunhye.user.model.UserModel;


public interface UserRepository {
	public List<UserModel> listAll();
	public void regist(UserModel model);
	public void delete(UserModel model);
	boolean loginCheck(UserModel model);
	public UserModel viewUser(UserModel vo);
	public void logout(HttpSession session);
}
