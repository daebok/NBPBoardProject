<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div id="contact-comment-whole-wrapper-${contactComment.contactCommentNo}" style="margin-bottom:10px;" class="contact-comment-whole-wrapper">
	<div id="contact-comment-content-${contactComment.contactCommentNo}"><html:unescape>${contactComment.contactCommentContent}</html:unescape></div>
	<span class="badge" style="background-color:#ffffff; color:#8c8c8c">
	<fmt:formatDate value="${contactComment.contactCommentDate}" pattern="yyyy/MM/dd"/></span>
	<sec:authorize access="hasRole('ROLE_ADMIN')"> <!-- 관리자 권한 -->
		<button type="button" class="btn btn-default  btn-sm" onclick="contactCommentDelete(${contactComment.contactCommentNo})">Delete</button>
	</sec:authorize>
</div>
<link type="text/css" rel="stylesheet" 	href="<c:url value='/resources/common/css/contact.css'/>">
<script src="<c:url value="/resources/common/js/contact.js" />"></script>
