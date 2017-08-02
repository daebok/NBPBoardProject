package com.hyunhye.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	public UserRepository repository;

	/* 회원 등록 */
	public void userRegist(UserModel model) {
		repository.userRegist(model);
	}

	/* 해당 아이디를 가진 사용자가 있으면, 1 리턴 */
	public int duplicationId(String userId) {
		return repository.duplicationId(userId);
	}

	/* 비밀번호 변경 */
	public void userPasswordChange(UserModel model) {
		repository.userPasswordChange(model);
	}
}
