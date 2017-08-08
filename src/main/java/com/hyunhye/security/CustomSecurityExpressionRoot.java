package com.hyunhye.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import com.hyunhye.board.service.BoardService;
import com.hyunhye.common.UserSessionUtils;

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
		if (!UserSessionUtils.hasRole("ROLE_ADMIN") && UserSessionUtils.currentUserNo() != userNo) {
			return false;
		}
		return true;
	}

}
