<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>Page Error</title>
</head>
<body>
	<div id="wrapper">
		<div align="center">
			<c:out value='${msg}'/> <br>
			<a href="<c:url value='/board'/>">Home으로 가기</a>
		</div>
	</div>
</body>
</html>