package com.hyunhye.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class UserLoginFailureHandler implements AuthenticationFailureHandler {
	Logger log = LoggerFactory.getLogger(UserLoginFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authentication)
		throws IOException, ServletException {
		log.info(authentication.getLocalizedMessage());
		log.info(authentication.getMessage());
		for (StackTraceElement s : authentication.getStackTrace()) {
			log.info(s.getClassName());
			log.info(s.getFileName());
			log.info(s.getMethodName());
			log.info(s.getLineNumber() + "");
			log.info(s.isNativeMethod() + "");
		}
		request.setAttribute("errorMessage", authentication.getMessage());
		request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);

	}

}
