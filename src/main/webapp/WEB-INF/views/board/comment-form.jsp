<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:forEach var="commentComment" items="${commentComment}">
	<div class='comment-wrapper comment-comment-list' id='answer-comment-${ commentComment.commentNo}'>
		<div class='comment' id='comment-comment-text-${ commentComment.commentNo}'> ${ commentComment.commentContent}
		</div>
		<span class='badge'>Commented By ${ commentComment.userName}</span>
		<div class='pull-right comment-list-list'>
			<button type="button" class="comment-comment-modify btn btn-default" comment-no="${ commentComment.commentNo}">Modify</button> &nbsp;
			<button type="button" class="answer-comment-delete btn btn-default" comment-no="${ commentComment.commentNo}">Delete</button>
		</div>
	</div>
</c:forEach>