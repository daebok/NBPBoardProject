package com.hyunhye.user.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hyunhye.user.model.UserModel;

@Repository
public interface UserRepository {

	/** 회원등록  **/
	public void userInsert(UserModel model);

	public int checkIdDuplication(String id);

	public Map<String, Object> userSelect(String userId);

	public void userPasswordChange(UserModel model);

	public List<UserModel> userSelectList();

	public void userAuthorityUpdate(UserModel userModel);

	public void userWithBoardDelete(UserModel userModel);

	public void onlyUserDelete(UserModel userModel);

	public int naverUserselect(String email);

	public void passwordUpdate(UserModel model);

}
