package com.hyunhye.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import com.hyunhye.board.service.BoardService;
import com.hyunhye.comment.service.CommentService;

public class CustomSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {

	@Autowired
	BoardService boardService;

	@Autowired
	CommentService commentService;

	@Override
	protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
		FilterInvocation fi) {
		CustomSecurityExpressionRoot root = new CustomSecurityExpressionRoot(authentication, fi, boardService,
			commentService);
		root.setPermissionEvaluator(getPermissionEvaluator());
		root.setRoleHierarchy(getRoleHierarchy());

		return root;
	}
}
