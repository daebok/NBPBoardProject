package com.hyunhye.utils;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hyunhye.user.model.BoardUserDetails;

/* 현재 시큐리티에 저장된 사용자 정보 가져오는 클래스 */
public class UserSessionUtils {

	public static String currentUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		BoardUserDetails user = (BoardUserDetails)authentication.getPrincipal();
		return user.getUserName();
	}

	public static int currentUserNo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		BoardUserDetails user = (BoardUserDetails)authentication.getPrincipal();
		return user.getUserNo();
	}

	public static String currentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		BoardUserDetails user = (BoardUserDetails)authentication.getPrincipal();
		return user.getUsername();
	}

	public static String currentUserPassword() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		BoardUserDetails user = (BoardUserDetails)authentication.getPrincipal();
		return user.getPassword();
	}

	public static BoardUserDetails currentUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		BoardUserDetails user = (BoardUserDetails)authentication.getPrincipal();
		return user;
	}

	public static boolean hasRole(String role) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		return authorities.stream().filter(o -> o.getAuthority().equals(role)).findAny().isPresent();
	}

}
