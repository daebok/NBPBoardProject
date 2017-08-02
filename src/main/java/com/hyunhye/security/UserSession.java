package com.hyunhye.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hyunhye.user.model.UserModelDetails;

/* 시큐리티에 저장된 사용자 정보 가져오는 클래스 */
public class UserSession {
	private static UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication()
		.getPrincipal();

	public static UserModelDetails getUserInfo() {
		return user;
	}

	public static int getUserNo() {
		return user.getUserNo();
	}

	public static String getUserName() {
		return user.getUserName();
	}

	public static String getUserId() {
		return user.getUsername();
	}

	public static Collection<GrantedAuthority> getAuthorities() {
		return user.getAuthorities();
	}
}
