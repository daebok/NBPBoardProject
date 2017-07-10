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
	<div class="container">
		<!-- 본인이 쓴 게시물만 수정, 삭제가 가능하도록 처리 -->
		<c:if test="${sessionScope.UID == dto.UID}">
			<a href="<c:url value='/modify.do'/>" id="QUESTION"
				class="btn btn-danger">Modify</a>
			<a href="<c:url value='/delete.do'/>" id="QUESTION"
				class="btn btn-danger">Delete</a>
		</c:if>
		<div class="container-fluid">
			<div class="col-md-12">
					<h1>${dto.TITLE}</h1>
					<p>${dto.CONTENT}</p>

					<hr>
			</div>
		</div>
	</div>
</body>
</html>
