package com.hyunhye.user.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hyunhye.naver.ouath.model.NaverUser;
import com.hyunhye.security.UserAuthenticationService;
import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.repository.UserRepository;
import com.hyunhye.utils.UserSessionUtils;

@Service
public class UserService {
	Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAuthenticationService userAuthenticationService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	/* 회원 등록 */
	public void userInsert(UserModel model) {
		model.setUserPassword(encoder.encode(model.getUserPassword()));
		userRepository.userInsert(model);
	}

	/* 해당 아이디를 가진 사용자가 있으면, 1 리턴 */
	public int checkIdDuplication(String userId) {
		return userRepository.checkIdDuplication(userId);
	}

	/* 비밀번호 변경 */
	public void userPasswordChange(UserModel model) {
		userRepository.userPasswordChange(model);
	}

	/* 네이버 아이디로 로그인 시, 회원가입 별도 처리 */
	public void naverUserInsert(NaverUser naverUser) {

		int check = userRepository.naverUserselect(naverUser.getEmail());

		/* 기존에 한번 로그인을 했던 사용자라면  */
		if (check >= 1) {
			/* 로그인 */
			setAuthentication(naverUser);

			return;
		}

		/* 회원 가입 */
		UserModel userModel = new UserModel();
		userModel.setUserId(naverUser.getEmail());
		userModel.setUserName(naverUser.getName());

		UUID uuid = UUID.randomUUID();
		userModel.setUserPassword(encoder.encode(uuid.toString()));


		userRepository.userInsert(userModel);

		/* 로그인 */
		setAuthentication(naverUser);
	}

	public void setAuthentication(NaverUser naverUser) {
		UserDetails userDetails = userAuthenticationService.loadUserByUsername(naverUser.getEmail());

		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
	}

	/* 현재 비밀번호 확인 */
	public boolean passwordCheck(UserModel model) {
		return encoder.matches(model.getUserPassword(), UserSessionUtils.currentUserPassword());
	}

	/* 비밀번호 변경 */
	public void passwordUpdate(UserModel model) {
		model.setUserPassword(encoder.encode(model.getUserPassword()));
		model.setUserNo(UserSessionUtils.currentUserNo());
		userRepository.passwordUpdate(model);
	}

	/** 관리자 -회원 관리 **/
	/* 회원 정보 리스트 */
	public List<UserModel> userSelectList() {
		return userRepository.userSelectList();
	}

	/* 회원 정보 변경 */
	public void userAuthorityUpdate(UserModel userModel) {
		userRepository.userAuthorityUpdate(userModel);
	}

	/* 회원 삭제 */
	public void userWithBoardDelete(UserModel userModel) {
		userRepository.userWithBoardDelete(userModel);
	}

	public void onlyUserDelete(UserModel userModel) {
		userRepository.onlyUserDelete(userModel);
	}
}
