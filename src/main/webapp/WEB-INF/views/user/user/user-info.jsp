<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
	<div class="user-info container">
		<h6><b>아이디 :</b> ${user.username }</h6>
		<h6><b>이름 :</b>${user.userName } </h6>
		<a href="/user/password" id="login" class="btn btn-default">비밀번호 변경하기</a>
	</div>
</body>
</html>
<link href="<c:url value='/resources/common/css/user.css' />">
