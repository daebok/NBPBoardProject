package com.hyunhye.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
	Logger log = LoggerFactory.getLogger(UserLoginSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication)
		throws IOException, ServletException {

		log.info(authentication.getName());
		log.info(authentication.getAuthorities().toString());
		log.info(authentication.getDetails().toString());
		log.info(authentication.getPrincipal().toString());
		for (GrantedAuthority a : authentication.getAuthorities()) {
			log.info(a.getAuthority());
		}

		UserDetails userDetails = (UserDetails)authentication.getPrincipal();

		log.info(String.valueOf(userDetails.isAccountNonExpired()));
		log.info(String.valueOf(userDetails.isAccountNonLocked()));
		log.info(String.valueOf(userDetails.isCredentialsNonExpired()));
		log.info(String.valueOf(userDetails.isEnabled()));

		response.sendRedirect(request.getContextPath() + "/board");
	}

}
