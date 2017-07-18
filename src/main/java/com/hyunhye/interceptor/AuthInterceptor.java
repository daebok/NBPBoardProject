package com.hyunhye.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hyunhye.user.service.UserService;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	UserService service;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("login") == null) {
			saveDest(request);
			response.sendRedirect("/user/login");
			return false;
		}
		return true;
	}

	private void saveDest(HttpServletRequest req) {
		String uri = req.getRequestURI();

		String query = req.getQueryString();

		if (query == null || query.equals("null")) {
			query = "";
		} else {
			query = "?" + query;
		}

		if (req.getMethod().equals("GET")) {
			req.getSession().setAttribute("dest", uri + query);
		}

	}

}
