package com.hyunhye.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hyunhye.board.service.BoardService;
import com.hyunhye.user.model.UserModelDetails;

@Service("springSecurityFilterChain")
public class BoardFilter implements Filter {
	Logger logger = LoggerFactory.getLogger(BoardFilter.class);

	@Autowired
	BoardService service;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain arg2)
		throws IOException, ServletException {

		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		int userNo = service.checkUser(boardNo);

		/* 현재 로그인 중인 아이디와 게시글 작성자와 비교하여 잘못된 경우 다시 목록으로 이동 */
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!hasRole(user, "ROLE_ADMIN") && user.getUserNo() != userNo) {
			((HttpServletResponse)response).sendRedirect("/board/list");
			return;
		}
		return;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

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
