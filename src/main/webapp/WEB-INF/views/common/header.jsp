<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<div class="container"  style="margin-top:40px;">
	<div class="container-fluid" >
		<h1>
			<a href="<c:url value='/board'/>" style="text-decoration: none;">BOARD</a>
		</h1>
		<div>
			<c:choose>
				<c:when test="${sessionScope.login == null}">
					<a href="<c:url value='/user/login'/>" id="login" class="btn btn-default">LogIn</a>
					<a href="<c:url value='/user/signup'/>" id="signup" class="btn btn-default">SingUp</a>
				</c:when>
				<c:otherwise> 
							${sessionScope.userName}님 환영합니다. &nbsp;
							<a href="<c:url value='/user/logout'/>" id="logout" class="btn btn-default">LogOut</a>
							<div  class="pull-right">
								<a href="<c:url value='/user/myQuestions'/>" id="logout" class="btn btn-default">My Questions</a>
								<a href="<c:url value='/user/favorite'/>" id="logout" class="btn btn-default">Favorite</a>
							</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<hr />
</div>