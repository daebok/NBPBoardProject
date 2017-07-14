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
			document.form.action = "logincheck"
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
			<form name="form" method="post">
				<label for="id"><b>ID</b></label><input type="text" name="ID" id="id" /> <br /> <label for="password"><b>Password</b></label><input
					type="password" name="PASSWORD" id="password" /> <br />
				<!-- <button id="btnLogin">로그인</button>-->
				<button type="button" id="loginButton" class="btn btn-default" >Log In</button>

				<c:if test="${msg == 'failure'}">
					<div style="color: red">아이디 또는 비밀번호가 일치하지 않습니다.</div>
				</c:if>
				<c:if test="${msg == 'logout'}">
					<div style="color: red">로그아웃되었습니다.</div>
				</c:if>
			</form>
		</div>
	</div>
</body>
</html>
