<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="container-fluid">
			<a href="<c:url value='/admin/user'/>" id="login" class="btn btn-default">User Management</a>
			<a href="<c:url value='/admin/category'/>" id="login" class="btn btn-default">Category Management</a>
			<a href="<c:url value='/admin/notice'/>" id="login" class="btn btn-default">Notice</a>
		</div>
	</div>
</body>
</html> 