<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value='/css/bootstrap.min.css'/>">
<script src="http://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>

<script type="text/script" src="<c:url value='/js/bootstrap.min.js'/>"></script>

</head>
<body>
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td>ID</td>
			<td>PASSWORD</td>
			<td>NAME</td>
		</tr>
		<c:forEach var="user" items="${list}" >
			<tr>
				<td>${user.ID}</td>
				<td>${user.PASSWORD}</td>
				<td>${user.NAME}</td>
				<td><a href="/board/delete?id=${user.ID}">삭제</a></td>
			</tr>
		</c:forEach>
	</table>


</body>
</html>