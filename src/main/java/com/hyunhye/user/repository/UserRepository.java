package com.hyunhye.user.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hyunhye.user.model.UserModel;

@Repository
public interface UserRepository {
	public void userRegist(UserModel model);

	public int duplicationId(String id);

	public Map<String, Object> selectUser(String userId);

	public void userPasswordChange(UserModel model);

	public List<UserModel> userSelectList();

	public void userAuthorityUpdate(UserModel userModel);

	public void userWithBoardDelete(UserModel userModel);

	public void onlyUserDelete(UserModel userModel);

	public int selectNaverUser(String email);

}
