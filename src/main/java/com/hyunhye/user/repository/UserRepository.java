package com.hyunhye.user.repository;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.hyunhye.user.model.UserModel;


public interface UserRepository {
	public List<UserModel> listAll();
	public void regist(UserModel dto);
	public void delete(UserModel dto);
	boolean loginCheck(UserModel dto);
	public UserModel viewUser(UserModel vo);
	public void logout(HttpSession session);
}
