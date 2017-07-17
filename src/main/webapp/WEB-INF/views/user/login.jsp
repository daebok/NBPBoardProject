<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>Home</title>
<script>
	$(document).ready(function() {
		$("#loginButton").click(function() {
			var id = $("#id").val();
			var password = $("#password").val();
			var name = $("#name").val();
			if (id == "") {
				alert("아이디를 입력하세요.");
				$("#id").focus();
				return;
			}
			if (password == "") {
				alert("비밀번호를 입력하세요.");
				$("#password").focus();
				return;
			}
			document.form.action = "/user/logincheck"
			document.form.submit();
		});
	});
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="container-fluid">
				<form name="form" method="post" class="form-horizontal">
					<div class="form-group">
						<label for="id" class="col-sm-2 control-label"><b>ID</b></label>
						<div class="col-sm-10">
							<input type="text" name="id" id="id" placeholder="Enter ID" class="form-control" /> <br /> 
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-sm-2 control-label"><b>Password</b></label>
						<div class="col-sm-10">
							<input type="password" name="password" id="password" placeholder="Enter PASSWORD" class="form-control" /> <br />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" id="loginButton" class="btn btn-default">Log In</button>
						</div>
					</div>
					<c:if test="${msg == 'FAILURE'}">
						<div style="color: red; margin-top: 10px;">아이디 또는 비밀번호가 일치하지 않습니다.</div>
					</c:if>
					<c:if test="${msg == 'LOGOUT'}">
						<div style="color: red; margin-top: 10px;">로그아웃되었습니다.</div>
					</c:if>
				</form>
			
		</div>
	</div>
</body>
</html>
