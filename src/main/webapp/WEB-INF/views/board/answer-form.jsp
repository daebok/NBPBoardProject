<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="whole-wrapper" id="whole-wrapper-${comment.commentNo}">
	<c:choose>
		<c:when test="${comment.userNo eq 1}">
			<div class="glyphicon glyphicon-heart" id="answer-like-check" onclick="anwserLike(${comment.commentNo}, this)" style="font-size:15px; color:#FF3636;"></div>
		</c:when>
		<c:otherwise>
			<div class="glyphicon glyphicon-heart" id="answer-like-uncheck"  onclick="anwserLike(${comment.commentNo}, this)" style="font-size:15px; color:#eee;"></div>
		</c:otherwise>
	</c:choose>
	<span  id="answer-like-count-${comment.commentNo}" style="font-size:12px; color:#888;"> ${comment.commentLikeCount} </span>
	<div id="comment-${comment.commentNo}" class="comment-wrapper-wrapper">
		<div class="comment-wrapper" id="${comment.commentNo}" style="margin-bottom:10px;">
			<div class="comment" id="content-${comment.commentNo}"><html:unescape>${comment.commentContent}</html:unescape> </div>
			<c:choose>
				<c:when test="${model.userName == comment.userName}">
					<span class="badge commentName" style='background-color:#d9534f;'>작성자</span>
				</c:when>
				<c:otherwise>
					<span class="badge commentName">Commented By ${comment.userName} </span>
				</c:otherwise>
			</c:choose>
			<span class="badge commentName" style="background-color:#ffffff; color:#8c8c8c">
				<fmt:formatDate value="${comment.commentDate}" pattern="yyyy/MM/dd"/></span>
			<div class="pull-right" class="comment-list" id="comment-list"> 
				<c:if test="${user.username == comment.userId}"> <!-- 작성자만 보이도록 -->
					<button type="button" class="btn btn-default" onclick="answerModify(${comment.commentNo})">Modify</button>
					<button type="button" class="btn btn-default" onclick="answerDelete(${comment.commentNo})" >Delete</button>
				</c:if>
				<sec:authorize access="hasRole('ROLE_ADMIN')"> <!-- 관리자 권한 -->
					<button type="button" class="btn btn-default" onclick="answerDelete(${comment.commentNo})">Delete</button>
				</sec:authorize>
				<button type="button" class="btn btn-default" onclick="commentListView(${comment.commentNo}, this)"
						id = "comment-view-${comment.commentNo}" value='closed'>${comment.commentCommentCount} Comment ▼</button>
			</div>
		</div>
		<button type="button" class="btn btn-default btn-sm" onclick="createCommentTextArea(${comment.commentNo}, ${model.boardNo}, this)" style="margin-bottom:10px;">add a Comment</button>
		<div class='comment-comment-wrapper' id='comment-comment-list' style="margin-bottom:45px; margin-top:-40px;"></div>
	</div>
</div>