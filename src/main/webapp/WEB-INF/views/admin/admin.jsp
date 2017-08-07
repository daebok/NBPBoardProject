<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>

	<div class="container"  style="height:100%;">
		<div class="container-fluid">
			<a href="<c:url value='/admin/user'/>" id="login" class="btn btn-default">User Management</a> <br>
			<a href="<c:url value='/admin/category'/>" id="login" class="btn btn-default">Category Management</a> <br>
			<a href="<c:url value='/admin/notice'/>" id="login" class="btn btn-default">Notice</a> <br>
		</div>
		<div>
			<form:form action="add/badword" method="post">
				<input type="text" name="badWord">
				<input type="submit">
			</form:form>
		</div>
	</div>
	

	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html> 