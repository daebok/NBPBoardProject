<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:forEach var="answer" items="${answer}">
	<div id="answer-wrapper-${answer.commentNo}">
		<c:choose>
			<c:when test="${answer.commentLike eq 1}">
				<div class="glyphicon glyphicon-heart" id="answer-like-check" onclick="anwserLike(${answer.commentNo}, this)" style="font-size:15px; color:#FF3636;"></div>
			</c:when>
			<c:otherwise>
				<div class="glyphicon glyphicon-heart" id="answer-like-uncheck"  onclick="anwserLike(${answer.commentNo}, this)" style="font-size:15px; color:#eee;"></div>
			</c:otherwise>
		</c:choose>
		<span  id="answer-like-count-${answer.commentNo}" style="font-size:12px; color:#888;"> ${answer.commentLikeCount} </span>
		<div id="answer-${answer.commentNo}">
			<div class="answer-content-wrapper" id="answer-content-${answer.commentNo}" style="margin-bottom:10px;">
				<div class="comment" id="content-${answer.commentNo}">
					<c:out value="${answer.commentContent}" escapeXml="false"/>
				</div>
				<c:choose>
					<c:when test="${board.userNo == answer.userNo || writer == answer.userNo}">
						<span class="badge commentName" style='background-color:#d9534f;'>작성자</span>
					</c:when>
					<c:otherwise>
						<span class="badge commentName">Answered By <user:id no="${answer.userNo}"/> </span>
					</c:otherwise>
				</c:choose>
				<span class="badge answer-time" style="background-color:#ffffff; color:#8c8c8c">
					<fmt:formatDate value="${answer.commentDate}" pattern="yyyy/MM/dd"/></span>
				<div class="pull-right" class="answer-button"> 
					<c:choose>
						<c:when test="${user.userNo == answer.userNo}">
							<button type="button" class="answer-button btn btn-default" onclick="answerModify(${answer.commentNo})">Modify</button>
							<button type="button" class="answer-button btn btn-default" onclick="answerDelete(${answer.commentNo})" >Delete</button>
						</c:when>
						<c:otherwise>
							<sec:authorize access="hasRole('ROLE_ADMIN')"> <!-- 관리자 권한 -->
								<button type="button" class="answer-button btn btn-default" onclick="answerDelete(${answer.commentNo})">Delete</button>
							</sec:authorize>
						</c:otherwise>
					</c:choose>
					<button type="button" class="btn btn-default" onclick="commentListView(${answer.commentNo}, this)" id = "comment-view-${answer.commentNo}" value='closed'>${answer.commentCommentCount} Comment ▼</button>
				</div>
			</div>
			<button type="button" class="comment-add btn btn-default btn-sm" onclick="createCommentTextArea(${answer.commentNo}, ${answer.boardNo}, this)" style="margin-bottom:10px;">add a Comment</button>
			<div class='comment-wrapper'  style="margin-bottom:45px; margin-top:-40px;"></div>
		</div>
	</div>
</c:forEach>

<link href="<c:url value="/resources/common/css/answer.css" />" rel="stylesheet">