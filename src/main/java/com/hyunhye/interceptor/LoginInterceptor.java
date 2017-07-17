package com.hyunhye.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("id") != null) {
			session.removeAttribute("id");
			session.removeAttribute("uid");
			session.removeAttribute("name");
		}
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		/*
				ModelMap modelMap = modelAndView.getModelMap();
				Object userModel = modelMap.get("userModel");
				if (userModel != null) {
					session.setAttribute("login", userModel);
				}*/

		Object dest = session.getAttribute("dest");
		response.sendRedirect(dest != null ? (String)dest : "/board");
	}

}
