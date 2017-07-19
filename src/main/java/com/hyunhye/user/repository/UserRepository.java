package com.hyunhye.user.repository;

import org.springframework.stereotype.Repository;

import com.hyunhye.user.model.UserModel;

@Repository
public interface UserRepository {
	public void userRegist(UserModel model) throws Exception;

	public void userDelete(UserModel model) throws Exception;

	public int loginCheck(UserModel model) throws Exception;

	public UserModel viewUser(UserModel model) throws Exception;

	public int userSelect(String id) throws Exception;
}
