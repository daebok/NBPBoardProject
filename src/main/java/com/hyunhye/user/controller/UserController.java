package com.hyunhye.user.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.service.ShaEncoder;
import com.hyunhye.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	public UserService service;

	@Resource(name = "shaEncoder")
	private ShaEncoder encoder;

	/* 회원가입 페이지 이동 */
	@RequestMapping("/signup")
	public String signup() {
		return "user/signup";
	}

	/* 로그인 페이지 이동 */
	@RequestMapping("/loginPage")
	public String login() {
		return "user/login";
	}

	/* 회원 등록 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String userRegist(@ModelAttribute UserModel model, RedirectAttributes rttr) {
		model.setUserPassword(encoder.encoding(model.getUserPassword()));
		service.userRegist(model);
		return "redirect:/user/loginPage";
	}

	/* 아이디 중복 확인 */
	@RequestMapping(value = "/duplicationId", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody int duplicationId(@RequestBody String userId) {
		return service.userSelect(userId);
	}
}
