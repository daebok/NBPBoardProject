package com.hyunhye.user.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.hyunhye.common.UserSessionUtils;
import com.hyunhye.naver.ouath.model.NaverUser;
import com.hyunhye.naver.ouath.service.NaverLoginService;
import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);

	private static OAuth2AccessToken oauthToken;

	@Autowired
	public UserService service;

	@Autowired
	private NaverLoginService naverLoginService;

	/**
	 * 회원가입 페이지 이동
	 */
	@RequestMapping("signup")
	public String goSignupPage() {
		return "user/user/signup";
	}

	/**
	 * 로그인 페이지 이동
	 * @param session
	 * @param model
	 */
	@RequestMapping("loginPage")
	public String goLoginPage(HttpSession session, Model model) {

		/* 네아로 인증 URL을 생성하기 위하여 getAuthorizationUrl을 호출 */
		String naverAuthUrl = naverLoginService.getAuthorizationUrl(session);

		/* 생성한 인증 URL을 View로 전달 */
		model.addAttribute("url", naverAuthUrl);
		return "user/user/login";
	}

	/**
	 * 네이버 아이디로 로그인
	 * @param code
	 * @param state
	 * @param session
	 * @param model
	 */
	@RequestMapping("callback")
	public String callback(@RequestParam String code, @RequestParam String state, HttpSession session, Model model)
		throws IOException, InterruptedException, ExecutionException {

		/* 네아로 인증이 성공적으로 완료되면 code 파라미터가 전달되며 이를 통해 access token을 발급 */
		oauthToken = naverLoginService.getAccessToken(session, code, state);

		logger.info("oauthToken:{}", oauthToken);

		NaverUser naverUser = naverLoginService.getUserProfile(oauthToken);
		service.naverUserInsert(naverUser);

		return "redirect:/board";
	}

	/**
	 * 회원 등록
	 * @param model
	 */
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String userInsert(@ModelAttribute UserModel model) {
		service.userInsert(model);
		return "redirect:/user/loginPage";
	}

	/**
	 * 아이디 중복 확인
	 * @param userId
	 */
	@RequestMapping(value = "duplicationId", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody int checkIdDuplication(@RequestBody String userId) {
		return service.checkIdDuplication(userId);
	}

	/**
	 * 내 정보보기
	 * @param model
	 */
	@RequestMapping("info")
	public String userinfo(Model model) {
		model.addAttribute("user", UserSessionUtils.currentUserInfo());
		return "user/user/user-info";
	}

	/**
	 * 비밀번호 변경 페이지로 이동
	 * @param model
	 * @return
	 */
	@RequestMapping("password")
	public String goPasswordChangePage(Model model) {
		return "user/user/password-change";
	}

	/**
	 * 비밀번호 변경 페이지에서, 현재 비밀번호와 일치하는지 확인
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "password/check", method = RequestMethod.POST)
	public ResponseEntity<Boolean> passwordCheck(@ModelAttribute UserModel model) {
		return new ResponseEntity<Boolean>(service.passwordCheck(model), HttpStatus.OK);
	}

	@RequestMapping(value = "password/change", method = RequestMethod.POST)
	public String passwordUpdate(@ModelAttribute UserModel model) {
		service.passwordUpdate(model);
		return "redirect:/board";
	}
}
