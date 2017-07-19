package com.hyunhye.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hyunhye.board.service.BoardService;

public class BoardInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	BoardService service;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {

		int boardId = Integer.parseInt(request.getParameter("boardId"));
		int userId = service.checkUser(boardId);

		HttpSession session = request.getSession();

		if ((Integer)session.getAttribute("userId") != userId) {
			response.sendRedirect("/board/list");
			return false;
		}

		return true;
	}
}
