package com.hyunhye.user.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	public UserRepository repository;

	/* 회원 등록 */
	public void userRegist(UserModel model) throws Exception {
		repository.userRegist(model);
	}

	/* 로그인 정보 일치 시, 세션에 값 저장 */
	public int loginCheck(HttpSession session, UserModel model) throws Exception {
		int result = repository.loginCheck(model);

		if (result == 1) {
			UserModel userModel = viewUser(model);

			session.setAttribute("userId", userModel.getUserId());
			session.setAttribute("id", userModel.getId());
			session.setAttribute("name", userModel.getName());
		}
		return result;
	}

	/* 사용자 정보 가져오기 */
	public UserModel viewUser(UserModel model) throws Exception {
		return repository.viewUser(model);
	}

	/* 로그 아웃 시, 세션 제거 */
	public void logout(HttpSession session) {
		session.removeAttribute("login");
		session.removeAttribute("id");
		session.removeAttribute("name");
		session.removeAttribute("userId");
		session.invalidate();
	}

	/* 해당 아이디를 가진 사용자가 있으면, 1 리턴 */
	public int userSelect(String id) throws Exception {
		return repository.userSelect(id);
	}
}
