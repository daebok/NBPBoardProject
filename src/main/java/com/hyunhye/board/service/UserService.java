package com.hyunhye.board.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.hyunhye.board.dto.UserDto;

public interface UserService {
	public void regist(Model model, UserDto dto);

	public void delete(Model model, UserDto dto);
	
	public boolean loginCheck(HttpSession session, Model model,  UserDto dto);

	public void logout(HttpSession session);
}
