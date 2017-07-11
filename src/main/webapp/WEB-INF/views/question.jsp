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
<link type="text/css" rel="stylesheet"
	href="<c:url value='/css/summernote.css'/>">
<script src="http://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script type="text/script" src="<c:url value='/js/bootstrap.min.js'/>"></script>
<script type="text/script" src="<c:url value='/js/summernote.js'/>"></script>
<script type="text/script"
	src="<c:url value='/lang/summernote-ko-KR.js'/>"></script>
<script>
	$(function() {
		$('.summernote').summernote({
			height : 300,
			minHeight : null,
			maxHeight : null,
			focus : true,
			lang : 'ko-KR'
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
			<form action="question/ask" method="post"
				enctype="multipart/form-data">
				Title <input type="text" name="title" /> <br />
				<div class="row">
					<div class="col-md-12">
						<textarea class="summernote" name="content"></textarea>
					</div>
				</div>

				<br /> 첨부파일 등록<input type="file" name="file"> <input
					type="submit" />
			</form>
		</div>
	</div>
</body>
</html>
