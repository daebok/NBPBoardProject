package com.hyunhye.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {
	
	
	@RequestMapping("/")
	public String index(Model model) throws Exception {
		log.debug("IndexController input");
		return "home";
	}
	
	@RequestMapping("/board")
	public String board(Model model) throws Exception {
		log.debug("Board input");
		return "home";
	}

}
