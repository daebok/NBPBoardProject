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

		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		int userNo = service.checkUser(boardNo);

		HttpSession session = request.getSession();

		/* 현재 로그인 중인 아이디와 게시글 작성자와 비교하여 잘못된 경우 다시 목록으로 이동 */
		if ((Integer)session.getAttribute("userNo") != userNo) {
			response.sendRedirect("/board/list");
			return false;
		}

		return true;
	}
}
