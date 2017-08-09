package com.hyunhye.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import com.hyunhye.board.service.BoardService;
import com.hyunhye.comment.service.CommentService;
import com.hyunhye.common.UserSessionUtils;

public class CustomSecurityExpressionRoot extends WebSecurityExpressionRoot {

	Logger logger = LoggerFactory.getLogger(CustomSecurityExpressionRoot.class);

	private BoardService boardService;
	private CommentService commentService;
	private FilterInvocation filterInvocation;

	public CustomSecurityExpressionRoot(Authentication authentication, FilterInvocation filterInvocation,
		BoardService boardService, CommentService commentService) {
		super(authentication, filterInvocation);
		setTrustResolver(new AuthenticationTrustResolverImpl());
		this.boardService = boardService;
		this.commentService = commentService;
		this.filterInvocation = filterInvocation;
	}

	public boolean hasBoardRole() {

		String boardNoString = this.filterInvocation.getHttpRequest().getParameter("boardNo");
		int boardNo = Integer.parseInt(boardNoString);
		int userNo = boardService.checkUser(boardNo);

		/* 현재 로그인 중인 아이디와 게시글 작성자와 비교하여 잘못된 경우 다시 목록으로 이동 */
		if (!UserSessionUtils.hasRole("ROLE_ADMIN") && UserSessionUtils.currentUserNo() != userNo) {
			return false;
		}
		return true;
	}

	public boolean hasAnswerRole() {

		String commentNoString = this.filterInvocation.getHttpRequest().getParameter("commentNo");
		int commentNo = Integer.parseInt(commentNoString);
		int userNo = commentService.checkUser(commentNo);

		/* 현재 로그인 중인 아이디와 답변 작성자와 비교하여 잘못된 경우 다시 목록으로 이동 */
		if (!UserSessionUtils.hasRole("ROLE_ADMIN") && UserSessionUtils.currentUserNo() != userNo) {
			return false;
		}
		return true;
	}

}
