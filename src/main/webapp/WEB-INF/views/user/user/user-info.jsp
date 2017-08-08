<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>사용자정보</title>
	<style type="text/css">
		.user-info{
			width:250px;
			height:100px;
		}
	</style>
</head>
<body>
	<div class="user-info container">
		<h6><b>아이디 :</b> ${user.username }</h6>
		<h6><b>이름 :</b>${user.userName } </h6>
		<a href="/user/password" id="login" class="btn btn-default">비밀번호 변경하기</a>
	</div>
</body>
</html>