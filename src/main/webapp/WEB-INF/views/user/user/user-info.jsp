<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"				uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<div class="user-info container">
		<h6><b>아이디 :</b> &nbsp; ${user.username }</h6>
		<h6><b>이름 :</b> &nbsp; ${user.userName } </h6>
		<h6><b>게시물 개수:</b> &nbsp; <a href="<c:url value='/board/myquestions'/>">${myBoardCount } 개</a></h6>
		<a href="/user/password" id="login" class="btn btn-default">비밀번호 변경하기</a>
	</div>
</body>
</html>
<link href="<c:url value='/resources/common/css/user.css' />">
