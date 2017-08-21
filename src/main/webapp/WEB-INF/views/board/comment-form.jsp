<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:forEach var="comment" items="${comment}">
	<div class='comment-list' id='answer-comment-${ comment.commentNo}'>
		<div class='comment' id='comment-text-${ comment.commentNo}'>
			<c:out value=" ${comment.commentContent}" escapeXml="true"></c:out>
		</div>
		<span class='badge'>Commented By <user:id no="${comment.userNo}"/></span>
		<div class='pull-right comment-list-button'>
			<button type="button" class="btn btn-default btn-sm" onclick="goToCommentModify(${ comment.commentNo})">Modify</button> &nbsp;
			<button type="button" class="btn btn-default btn-sm" onclick="commentDelete(${ comment.commentParentNo}, ${ comment.commentNo})">Delete</button>
		</div>
	</div>
</c:forEach>