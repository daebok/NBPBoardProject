<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BOARD</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value='/css/bootstrap.min.css'/>">
<script type="text/script" src="<c:url value='/js/bootstrap.min.js'/>"></script>

<script src="http://code.jquery.com/jquery-3.2.1.min.js"
	type="text/javascript"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"
	type="text/javascript"></script>
<link type="text/css" rel="stylesheet"
	href="<c:url value='/resources/summernote/summernote.css'/>">
<script type="text/script"
	src="<c:url value='/resources/summernote/summernote.js'/>"></script>
<script>
	$(document).ready(function() {
		$('.summernote').summernote({
			height : 300
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
			<form action="question/modify" method="post">
				Title <input type="text" name="title" value="${dto.TITLE}" /> <br />
				<textarea class="summernote" cols="100" rows="30" name="content">${dto.CONTENT}</textarea>

				<input type="hidden" name="bid" value="${dto.BID}"> <br />
				<input type="submit" />
			</form>
		</div>
	</div>
</body>
</html>
