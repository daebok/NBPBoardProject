<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
</head>
<body>
	<%@include file="../common/header.jsp"%>
	<div class="container">
		<div class="container-fluid">
			<div class="row">
				<a href="<c:url value='/board/myquestions'/>" id="my-qustions" class="btn btn-default">My Questions</a>
				<a href="<c:url value='/board/myfavorite'/>" id="favorite" class="btn btn-default">Favorite</a>
			</div>
		</div>
	</div>
</body>
</html>
