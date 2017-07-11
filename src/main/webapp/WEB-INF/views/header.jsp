<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BOARD</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value='/css/bootstrap.min.css'/>">
<script src="http://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>

<script type="text/script" src="<c:url value='/js/bootstrap.min.js'/>"></script>


</head>
<body>
	<div class="container">
		<h1>BOARD</h1>
		<c:if test="${sessionScope.ID != null}">
			<h5>${sessionScope.NAME}(${sessionScope.ID})님환영합니다.</h5>
		</c:if>
		<div class="container-fluid">
			<div class="row">
				<c:choose>
					<c:when test="${sessionScope.ID == null}">
						<a href="<c:url value='/login.do'/>" id="LOGIN"
							class="btn btn-primary">LogIn</a>
						<a href="<c:url value='/signup.do'/>" id="SIGNUP"
							class="btn btn-danger">SingUp</a>
					</c:when>
					<c:otherwise> ${sessionScope.NAME}님이 로그인중입니다.
						<a href="<c:url value='/logout.do'/>" id="LOGOUT"
							class="btn btn-primary">LogOut</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<hr />
	</div>
</body>
</html>
