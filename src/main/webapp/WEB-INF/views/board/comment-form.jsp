<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:forEach var="commentComment" items="${commentComment}">
	<div class='comment-wrapper comment-comment-list' id='answer-comment-${ commentComment.commentNo}'>
		<div class='comment' id='comment-comment-text-${ commentComment.commentNo}'><c:out value=" ${commentComment.commentContent}" escapeXml="false"></c:out>
		</div>
		<span class='badge'>Commented By ${commentComment.userName}</span>
		<div class='pull-right comment-list-list'>
			<button type="button" class="btn btn-default" onclick="goToCommentModify(${ commentComment.commentNo})">Modify</button> &nbsp;
			<button type="button" class="btn btn-default" onclick="commentDelete(${ commentComment.commentNo})">Delete</button>
		</div>
	</div>
</c:forEach>