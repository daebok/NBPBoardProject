package com.hyunhye.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class UserLoginFailureHandler implements AuthenticationFailureHandler {

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
