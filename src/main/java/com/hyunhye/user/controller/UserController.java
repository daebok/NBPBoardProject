package com.hyunhye.user.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

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
import com.hyunhye.board.service.BoardService;
import com.hyunhye.naver.ouath.model.NaverUser;
import com.hyunhye.naver.ouath.service.NaverLoginService;
import com.hyunhye.user.model.BoardUser;
import com.hyunhye.user.service.UserService;
import com.hyunhye.utils.UserSessionUtils;

@Controller
@RequestMapping("user")
public class UserController {

	private static OAuth2AccessToken oauthToken;

	@Autowired
	public UserService service;

	@Autowired
	public BoardService boardService;

	@Autowired
	private NaverLoginService naverLoginService;

	/**
	 * @return {@link BoardUser} 회원가입 페이지 이동
	 */
	@RequestMapping("signup")
	public String goSignupPage() {
		return "user/user/signup";
	}

	/**
	 * @return {@link BoardUser} 로그인 페이지 이동
	 */
	@RequestMapping("loginPage")
	public String goLoginPage(HttpSession session, Model model) {

		String naverAuthUrl = naverLoginService.getAuthorizationUrl(session);
		model.addAttribute("url", naverAuthUrl);
		return "user/user/login";
	}

	/**
	 * 네이버 아이디로 로그인
	 */
	@RequestMapping("callback")
	public String callbackNaverLogin(@RequestParam String code, @RequestParam String state, HttpSession session,
		Model model)
		throws IOException, InterruptedException, ExecutionException {

		oauthToken = naverLoginService.getAccessToken(session, code, state);

		NaverUser naverUser = naverLoginService.getUserProfile(oauthToken);
		service.insertNaverUser(naverUser);

		return "redirect:/board";
	}

	/**
	 * {@link BoardUser} 추가하기 (회원가입)
	 * @param user
	 * @return 로그인 페이지로 Redirect
	 */
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insertUser(@ModelAttribute BoardUser user) {
		service.insertUser(user);
		return "redirect:/user/loginPage";
	}

	/**
	 * {@link BoardUser} 아이디 중복 확인
	 * @param userId 사용자 아이디
	 */
	@RequestMapping(value = "duplicationId", method = RequestMethod.POST)
	public @ResponseBody int checkUserIdDuplication(@RequestBody String userId) {
		return service.checkUserIdDuplication(userId);
	}

	/**
	 * {@link BoardUser} 내 정보보기
	 */
	@RequestMapping("info")
	public String selectUserInfo(Model model) {
		model.addAttribute("user", UserSessionUtils.currentUserInfo());
		model.addAttribute("myBoardCount", boardService.selectMyBoardCount(UserSessionUtils.currentUserNo()));
		return "user/user/user-info";
	}

	/**
	 * @return {@link BoardUser} 비밀번호 변경 페이지로 이동
	 */
	@RequestMapping("password")
	public String goPasswordChangePage(Model model) {
		return "user/user/password-change";
	}

	/**
	 * {@link BoardUser} 비밀번호 변경 페이지에서, 현재 비밀번호와 일치하는지 확인
	 * @param user 사용자 비밀번호
	 * @return 일치하면 1, 그렇지 않으면 0
	 */
	@RequestMapping(value = "password/check", method = RequestMethod.POST)
	public ResponseEntity<Boolean> checkUserPassword(@ModelAttribute BoardUser user) {
		return new ResponseEntity<Boolean>(service.checkUserPassword(user), HttpStatus.OK);
	}

	/**
	 * {@link BoardUser} 비밀번호 업데이트
	 * @param user 새로운 비밀번호
	 */
	@RequestMapping(value = "password/change", method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute BoardUser user) {
		service.updatePassword(user);
		return "redirect:/board";
	}
}
