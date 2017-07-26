<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<style>
	.wrapper {
		height: 40px;
	}
	.wrapper-2 {
		margin-left:20px;
		line-height: 40px;
	}
</style>
<div class="container"  style="margin-top:40px;">
	<div class="container-fluid" >
		<h1>
			<a href="<c:url value='/board'/>" style="text-decoration: none; color:#000000; ">BOARD</a>
		</h1>
		<div>
			<div class="pull-left wrapper">
				<sec:authorize access="isAuthenticated()">
					<div class="pull-left wrapper-1">
						<form:form action="/user/logout" method="POST"  class="pull-left">
							<input type="submit" id="logout" class="btn btn-default" value="LogOut">
						</form:form>
					</div>
					<div class="pull-left wrapper-2">
						<sec:authentication property="name"/>님 환영합니다.
					</div>
				</sec:authorize>
			</div>
			<sec:authorize access="isAnonymous()">
				<a href="<c:url value='/user/loginPage'/>" id="login" class="btn btn-default">LogIn</a>
				<a href="<c:url value='/user/signup'/>" id="signup" class="btn btn-default">SingUp</a>
			</sec:authorize>
			
			<div class="pull-right">
				<sec:authorize access="isAuthenticated()">
						<a href="<c:url value='/board/myQuestions'/>" id="my-qustions" class="btn btn-default">My Questions</a>
						<a href="<c:url value='/user/favorite'/>" id="favorite" class="btn btn-default">Favorite</a>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<a href="<c:url value='/admin/admin'/>" id="admin" class="btn btn-success">ADMIN</a>
						</sec:authorize>
				</sec:authorize>
			</div>

		</div>

	</div>		
	<div class="container-fluid">
		<div class="pull-right">
			<a href="<c:url value='/board/question'/>" id="question" class="btn btn-danger">Ask Question</a>
			<a href="<c:url value='/board/list'/>" id="list" class="btn btn-danger">Questions</a>
		</div>
	</div>
	<hr>
</div>