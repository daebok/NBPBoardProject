<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
</head>
<body>
	<div class="col-lg-9">
		<div id="listComment">
			<c:if test='${empty comment}'>
				<div class="emptyContent">답변이 없습니다.</div>
			</c:if>
			<c:forEach var="comment" items="${comment}">
				<%@ include file="answer-form.jsp" %>
			</c:forEach>
		</div>
	</div>
</body>
</html>
