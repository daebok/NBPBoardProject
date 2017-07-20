package com.hyunhye.user.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	public UserService service;

	/* 회원가입 페이지 이동 */
	@RequestMapping("signup")
	public String signup() {
		return "user/signup";
	}

	/* 로그인 페이지 이동 */
	@RequestMapping("login")
	public String login() {
		return "user/login";
	}

	/* 회원 등록 */
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String userRegist(@ModelAttribute UserModel model, RedirectAttributes rttr) throws Exception {
		service.userRegist(model);
		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/user/login";
	}

	/* 아이디, 비밀번호 일치 확인 */
	@RequestMapping(value = "logincheck", method = RequestMethod.POST)
	public String loginCheck(@ModelAttribute UserModel model, HttpSession session, RedirectAttributes rttr)
		throws Exception {
		int result = service.loginCheck(session, model);
		if (result == 1) {
			session.setAttribute("loginCheck", result);
			return "home";
		} else {
			rttr.addFlashAttribute("msg", "FAILURE");
			session.setAttribute("loginCheck", result);
			return "user/login";
		}
	}

	/* 로그아웃 */
	@RequestMapping("logout")
	public String logout(HttpSession session, RedirectAttributes rttr) throws Exception {
		service.logout(session);
		rttr.addFlashAttribute("msg", "LOGOUT");
		return "redirect:/user/login";
	}

	/* 아이디 중복 확인 */
	@RequestMapping(value = "duplicationId", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody int duplicationId(@RequestBody String id) throws Exception {
		return service.userSelect(id);
	}
}
