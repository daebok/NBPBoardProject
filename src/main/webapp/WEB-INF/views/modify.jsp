<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BOARD</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value='/css/bootstrap.min.css'/>">
<script src="http://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>

<script type="text/script" src="<c:url value='/js/bootstrap.min.js'/>"></script>

</head>
<body>

	<form action="question/ask" method="post">
		Title <input type="text" name="title" value="${dto.TITLE}"/> <br />
		<textarea cols="100" rows="30" name="content">${dto.CONTENT}</textarea>
		<br /> <input type="submit" />
	</form>

</body>
</html>