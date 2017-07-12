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
		$("#btnLogin").click(function() {
			// 태크.val() : 태그에 입력된 값
			// 태크.val("값") : 태그의 값을 변경 
			var userId = $("#id").val();
			var userPw = $("#password").val();
			if (userId == "") {
				alert("아이디를 입력하세요.");
				$("#id").focus(); // 입력포커스 이동
				return; // 함수 종료
			}
			if (userPw == "") {
				alert("비밀번호를 입력하세요.");
				$("#password").focus();
				return;
			}
			// 폼 내부의 데이터를 전송할 주소
			document.form.action = "logincheck.do"
			// 제출
			document.form.submit();
		});
	});
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="container-fluid">
			<form name="form" action="logincheck.do" method="post">
				아이디: <input type="text" name="ID" id="id" /> <br /> 비밀번호: <input
					type="text" name="PASSWORD" id="password" /> <br />
				<!-- <button id="btnLogin">로그인</button>-->
				<input type="submit" id="btnLogin" />

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
