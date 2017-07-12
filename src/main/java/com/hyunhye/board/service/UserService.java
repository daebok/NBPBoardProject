package com.hyunhye.board.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.hyunhye.board.dto.UserDto;

public interface UserService {
	// public void regist(Model model, UserDto dto);
	
	public void logout(HttpSession session);

	void regist(UserDto dto);

	UserDto viewUser(UserDto dto);

	boolean loginCheck(HttpSession session, UserDto dto);
}
