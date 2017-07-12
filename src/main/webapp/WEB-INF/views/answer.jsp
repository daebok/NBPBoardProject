<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>BOARD</title>
</head>
<body>
	<!-- header start -->
	<%@include file="header.jsp"%>
	<!-- header end -->
	<div class="container">
		<!-- 본인이 쓴 게시물만 수정, 삭제가 가능하도록 처리 -->
		<c:if test="${sessionScope.UID == dto.UID}">
			<a href="<c:url value='/modify.do?id=${dto.BID}'/>" id="MODIFY"
				class="btn btn-danger">Modify</a>
			<a href="<c:url value='/delete.do?id=${dto.BID}'/>" id="DELETE"
				class="btn btn-danger">Delete</a>
		</c:if>
		<div class="container-fluid">
			<div class="col-md-12">
				<h1>${dto.TITLE}</h1>
				<p>${dto.CONTENT}</p>
				<hr>
			</div>
		</div>
	</div>
</body>
</html>
