package com.hyunhye.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();

		if ((int)session.getAttribute("loginCheck") != 1) {
			response.sendRedirect("/user/login");
			return;
		}

		ModelMap modelMap = modelAndView.getModelMap();
		Object userModel = modelMap.get("userModel");
		if (userModel != null) {
			session.setAttribute("login", userModel);

			Object dest = session.getAttribute("dest");
			session.removeAttribute("dest");
			response.sendRedirect(dest != null ? (String)dest : "/board");
		}
	}

}
