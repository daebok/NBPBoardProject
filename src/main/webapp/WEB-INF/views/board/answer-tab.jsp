<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
</head>
<body>
	<div class="container-fluid" style="margin-bottom: 50px" >
		<div class="col-lg-9">
			<span class="commentTitle">${answerCount.commentCount} Answer</span>
			<div id="listComment" class="col-lg-12">
				<c:if test='${empty comment}'>
					<div class="emptyContent">답변이 없습니다.</div>
				</c:if>
				<c:forEach var="comment" items="${comment}">
					<%@ include file="answer-form.jsp"%>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>
