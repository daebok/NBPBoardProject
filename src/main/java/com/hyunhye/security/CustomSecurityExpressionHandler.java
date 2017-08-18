package com.hyunhye.security;

import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

public class CustomSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {


	@Override
	protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
		FilterInvocation fi) {
		CustomSecurityExpressionRoot root = new CustomSecurityExpressionRoot(authentication, fi);
		root.setPermissionEvaluator(getPermissionEvaluator());
		root.setRoleHierarchy(getRoleHierarchy());

		return root;
	}
}
