<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style>
.wrapper {
	height: 40px;
}
.wrapper-2 {
	margin-left:20px;
	line-height: 40px;
}
</style>
<script>

function userInfo() {
	$.ajax({
		type : "GET",
		url : "/user/info",
		success : function(result) {
			$("#user-info-dialog").html(result);
		}
	});
	$("#user-info-dialog").dialog({
			autoOpen: true,
			modal: true,
			resizable:false,
			position: { my: "left", at: "right", of: "#user-info-button" },
			title: "회원 정보"
	});
}

</script>

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
						<a href='javascript:userInfo()'  class="glyphicon glyphicon-user" id="user-info-button" style="color:black"></a>
						<div id="user-info-dialog"></div>
					</div>
				</sec:authorize>
			</div>
			<sec:authorize access="isAnonymous()">
				<a href="<c:url value='/user/loginPage'/>" id="login" class="btn btn-default">LogIn</a>
				<a href="<c:url value='/user/signup'/>" id="signup" class="btn btn-default">SingUp</a>
			</sec:authorize>
			
			<div class="pull-right">
				<sec:authorize access="isAuthenticated()">
						<a href="<c:url value='/board/myquestions'/>" id="my-qustions" class="btn btn-default">My Questions</a>
						<a href="<c:url value='/board/myfavorite'/>" id="favorite" class="btn btn-default">My Favorite</a>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<a href="<c:url value='/admin/admin'/>" id="admin" class="btn btn-success">ADMIN</a>
						</sec:authorize>
				</sec:authorize>
			</div>

		</div>

	</div>		
	<div class="container-fluid">
		<div class="pull-right">
			<a href="<c:url value='/board/ask'/>" id="question" class="btn btn-primary">? Ask Question</a>
			<a href="<c:url value='/board/list'/>" id="list" class="btn btn-primary">Questions</a>
		</div>
	</div>
	<hr>
</div>