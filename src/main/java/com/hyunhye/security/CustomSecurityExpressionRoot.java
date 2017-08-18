package com.hyunhye.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import com.hyunhye.board.service.BoardService;
import com.hyunhye.comment.service.CommentService;
import com.hyunhye.contact.service.ContactService;
import com.hyunhye.notice.service.NoticeService;
import com.hyunhye.taglib.SpringBeanFactory;
import com.hyunhye.utils.UserSessionUtils;

public class CustomSecurityExpressionRoot extends WebSecurityExpressionRoot {
	private FilterInvocation filterInvocation;

	public CustomSecurityExpressionRoot(Authentication authentication, FilterInvocation filterInvocation) {
		super(authentication, filterInvocation);
		setTrustResolver(new AuthenticationTrustResolverImpl());
		this.filterInvocation = filterInvocation;
	}

	public boolean hasBoardAuthority() {
		BoardService boardService = SpringBeanFactory.getBean(BoardService.class);

		String boardNoS = this.filterInvocation.getHttpRequest().getParameter("boardNo");

		if (!isAuthenticated()) {
			return false;
		}

		if (StringUtils.isBlank(boardNoS)) {
			return true;
		}

		int boardNo = Integer.parseInt(boardNoS);
		int userNo = boardService.checkUser(boardNo);

		if (userNo == 0) {
			return false;
		}

		if (!isUriNeedCheckUser()) {
			return true;
		}

		if (!UserSessionUtils.hasRole("ROLE_ADMIN") && UserSessionUtils.currentUserNo() != userNo) {
			return false;
		}
		return true;
	}

	public boolean hasCommentAuthority() {
		CommentService commentService = SpringBeanFactory.getBean(CommentService.class);

		String commentNoS = this.filterInvocation.getHttpRequest().getParameter("commentNo");

		if (!isAuthenticated()) {
			return false;
		}

		if (StringUtils.isBlank(commentNoS)) {
			return true;
		}

		int commentNo = Integer.parseInt(commentNoS);
		int userNo = commentService.checkUser(commentNo);

		if (userNo == 0) {
			return false;
		}

		if (!isUriNeedCheckUser()) {
			return true;
		}

		if (!UserSessionUtils.hasRole("ROLE_ADMIN") && UserSessionUtils.currentUserNo() != userNo) {
			return false;
		}
		return true;
	}

	public boolean hasContactAuthority() {
		ContactService contactService = SpringBeanFactory.getBean(ContactService.class);

		String contactNoS = this.filterInvocation.getHttpRequest().getParameter("contactNo");

		if (!isAuthenticated()) {
			return false;
		}

		if (StringUtils.isBlank(contactNoS)) {
			return true;
		}

		int contactNo = Integer.parseInt(contactNoS);

		int userNo = contactService.checkUser(contactNo);
		if (userNo == 0) {
			return false;
		}

		if (!isUriNeedCheckUser()) {
			return true;
		}

		if (!UserSessionUtils.hasRole("ROLE_ADMIN") && UserSessionUtils.currentUserNo() != userNo) {
			return false;
		}
		return true;
	}

	public boolean hasNoticeAuthority() {
		NoticeService noticeService = SpringBeanFactory.getBean(NoticeService.class);

		String noticeNoS = this.filterInvocation.getHttpRequest().getParameter("noticeNo");

		if (!isAuthenticated()) {
			return false;
		}

		if (StringUtils.isBlank(noticeNoS)) {
			return true;
		}

		int noticeNo = Integer.parseInt(noticeNoS);

		int isExistedNotice = noticeService.isExistedNotice(noticeNo);
		if (isExistedNotice <= 0) {
			return false;
		}

		return true;
	}

	private boolean isUriNeedCheckUser() {
		boolean modifyCheckUser = this.filterInvocation.getRequest().getRequestURI().contains("modify");
		boolean deleteCheckUser = this.filterInvocation.getRequest().getRequestURI().contains("delete");

		return !modifyCheckUser && !deleteCheckUser ? false : true;
	}
}
