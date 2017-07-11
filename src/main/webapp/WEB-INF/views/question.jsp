<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Home</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
	href="<c:url value='/css/bootstrap.min.css'/>">
<script src="http://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>

<script type="text/script" src="<c:url value='/js/bootstrap.min.js'/>"></script>
</head>
<body>
	<!-- header start -->
	<%@include file="header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="container-fluid">
			<form action="question/ask" method="post" enctype="multipart/form-data">
				Title <input type="text" name="title" /> <br />
				<textarea cols="100" rows="30" name="content"></textarea> <br /> 
				 <input type="file" name="file">
				<input type="submit" />
			</form>
		</div>
	</div>
</body>
</html>
