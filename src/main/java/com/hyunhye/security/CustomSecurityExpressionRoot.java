package com.hyunhye.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import com.hyunhye.board.service.BoardService;
import com.hyunhye.user.model.UserModelDetails;

public class CustomSecurityExpressionRoot extends WebSecurityExpressionRoot {

	Logger logger = LoggerFactory.getLogger(CustomSecurityExpressionRoot.class);

	private BoardService service;
	private FilterInvocation filterInvocation;

	public CustomSecurityExpressionRoot(Authentication authentication, FilterInvocation filterInvocation,
		BoardService service) {
		super(authentication, filterInvocation);
		setTrustResolver(new AuthenticationTrustResolverImpl());
		this.service = service;
		this.filterInvocation = filterInvocation;
	}

	public boolean hasModifyRole() {

		String boardNoString = this.filterInvocation.getHttpRequest().getParameter("boardNo");
		int boardNo = Integer.parseInt(boardNoString);

		int userNo = service.checkUser(boardNo);

		/* 현재 로그인 중인 아이디와 게시글 작성자와 비교하여 잘못된 경우 다시 목록으로 이동 */
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!hasRole(user, "ROLE_ADMIN") && user.getUserNo() != userNo) {
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
