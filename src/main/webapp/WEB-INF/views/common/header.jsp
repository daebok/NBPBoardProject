<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<div class="container header">
	<h1>
		<a href="<c:url value='/board'/>" style="text-decoration:none; color:">BOARD</a>
	</h1>
	<c:if test="${sessionScope.userId != null}">
		<h5 class="home welcome">${sessionScope.name}(${sessionScope.id})님환영합니다.</h5>
	</c:if>
	<div class="container-fluid">
		<div class="row">
			<c:choose>
				<c:when test="${sessionScope.id == null}">
					<a href="<c:url value='/user/login'/>" id="LOGIN"
						class="btn btn-primary">LogIn</a>
					<a href="<c:url value='/user/signup'/>" id="SIGNUP"
						class="btn btn-danger">SingUp</a>
				</c:when>
				<c:otherwise> ${sessionScope.name}님이 로그인중입니다.
						<a href="<c:url value='/user/logout'/>" id="LOGOUT"
						class="btn btn-primary">LogOut</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<hr />
</div>