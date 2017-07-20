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

		/* 아이디와 비밀번호가 틀린 경우, 다시 로그인 페이지로 이동 */
		if ((int)session.getAttribute("loginCheck") != 1) {
			response.sendRedirect("/user/login");
			return;
		}

		/* 로그인 성공하면 세션에 로그인 정보 저장 후, 원래 경로로 이동 */
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
