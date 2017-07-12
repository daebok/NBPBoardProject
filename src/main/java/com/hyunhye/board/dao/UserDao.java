package com.hyunhye.board.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.hyunhye.board.dto.UserDto;


public interface UserDao {
	public List<UserDto> listAll();
	public void regist(UserDto dto);
	public void delete(UserDto dto);
	boolean loginCheck(UserDto dto);
	public UserDto viewUser(UserDto vo);
	public void logout(HttpSession session);
}
