package com.hyunhye.user.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hyunhye.user.model.UserModel;

@Repository
public interface UserRepository {
	public void userRegist(UserModel model);

	public void userDelete(UserModel model);

	public int loginCheck(UserModel model);

	public UserModel viewUser(UserModel model);

	public int userSelect(String id);

	public Map<String, Object> selectUser(String userId);
}
