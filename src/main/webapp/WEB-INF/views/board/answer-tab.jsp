<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
</head>
<body>
	<div class="col-lg-9">
		<c:if test='${empty answer}'>
			<div class="empty-answer">답변이 없습니다.</div>
		</c:if>
		<%@ include file="answer-form.jsp" %>
	</div>
</body>
</html>
