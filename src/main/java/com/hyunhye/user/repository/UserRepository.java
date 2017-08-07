package com.hyunhye.user.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hyunhye.user.model.User;

@Repository
public interface UserRepository {

	/** 회원등록  **/
	public void userInsert(User model);

	public int checkIdDuplication(String id);

	public Map<String, Object> userSelect(String userId);

	public void userPasswordChange(User model);

	public List<User> userSelectList();

	public void userAuthorityUpdate(User userModel);

	public void userWithBoardDelete(User userModel);

	public void onlyUserDelete(User userModel);

	public int naverUserselect(String email);

}
