package com.hyunhye.interceptor;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hyunhye.board.service.BoardService;
import com.hyunhye.user.model.UserModelDetails;

import lombok.extern.log4j.Log4j;

@Log4j
public class BoardInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	BoardService service;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {

		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		int userNo = service.checkUser(boardNo);

		/* 현재 로그인 중인 아이디와 게시글 작성자와 비교하여 잘못된 경우 다시 목록으로 이동 */
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!hasRole(user, "ROLE_ADMIN") && user.getUserNo() != userNo) {
			log.info("Interceptor!!!");
			response.sendRedirect("/board/list");
			return false;
		}

		return true;
	}

	private boolean hasRole(UserModelDetails user, String role) {
		Collection<GrantedAuthority> authorities = user.getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			hasRole = authority.getAuthority().equals(role);
			if (hasRole) {
				break;
			}
		}
		return hasRole;
	}
}
