<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<div class="container"  style="margin-top:40px;">
	<div class="container-fluid" >
		<h1>
			<a href="<c:url value='/board'/>" style="text-decoration: none;">BOARD</a>
		</h1>
		<div>
			<sec:authorize access="isAuthenticated()">
				<sec:authentication property="name"/>님 환영합니다.
				<form action="/user/logout" method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
					<input type="submit" id="logout" class="btn btn-default" value="LogOut">
				</form>
			</sec:authorize>
			<sec:authorize access="isAnonymous()">
				<a href="<c:url value='/user/loginPage'/>" id="login" class="btn btn-default">LogIn</a>
				<a href="<c:url value='/user/signup'/>" id="signup" class="btn btn-default">SingUp</a>
			</sec:authorize>
			<div  class="pull-right">
				<sec:authorize access="isAuthenticated()">
						<a href="<c:url value='/user/myQuestions'/>" id="logout" class="btn btn-default">My Questions</a>
						<a href="<c:url value='/user/favorite'/>" id="logout" class="btn btn-default">Favorite</a>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<a href="<c:url value='/admin/admin'/>" id="admin" class="btn btn-warning">ADMIN</a>
						</sec:authorize>
				</sec:authorize>
			</div>
		</div>
	</div>
	<hr>
</div>