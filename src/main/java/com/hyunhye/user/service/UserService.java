package com.hyunhye.user.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hyunhye.naver.ouath.model.NaverUser;
import com.hyunhye.security.UserAuthenticationService;
import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.repository.UserRepository;

@Service
public class UserService {
	Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAuthenticationService userAuthenticationService;

	/* 회원 등록 */
	public void userRegist(UserModel model) {
		userRepository.userRegist(model);
	}

	/* 해당 아이디를 가진 사용자가 있으면, 1 리턴 */
	public int duplicationId(String userId) {
		return userRepository.duplicationId(userId);
	}

	/* 비밀번호 변경 */
	public void userPasswordChange(UserModel model) {
		userRepository.userPasswordChange(model);
	}

	/* 네이버 아이디로 로그인 시, 회원가입 별도 처리 */
	public void naverUserRegist(NaverUser naverUser) {

		int check = userRepository.selectNaverUser(naverUser.getEmail());

		if (check >= 1) {
			/* 로그인 */
			setAuthentication(naverUser);

			return;
		}

		UserModel userModel = new UserModel();
		userModel.setUserId(naverUser.getEmail());
		userModel.setUserName(naverUser.getName());

		UUID uuid = UUID.randomUUID();
		userModel.setUserPassword(uuid.toString());

		/* 회원 가입 */
		userRepository.userRegist(userModel);

		/* 로그인 */
		setAuthentication(naverUser);
	}

	public void setAuthentication(NaverUser naverUser) {
		UserDetails userDetails = userAuthenticationService.loadUserByUsername(naverUser.getEmail());

		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
	}
}
