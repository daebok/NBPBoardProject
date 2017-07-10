package com.hyunhye.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/signup.do")
	public String signup() {
		return "signup";
	}

	@RequestMapping(value = "/login.do")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/question.do")
	public String question() {
		return "question";
	}

}
