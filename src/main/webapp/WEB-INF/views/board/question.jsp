<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>Home</title>
<script>
$(document).ready(function() {
	$('.summernote').summernote({
		height : 300,
		
	});
});
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	
	<div class="container">
		<div class="container-fluid">
			<form action="question/ask" method="post" enctype="multipart/form-data">
				<select name="ITEM">
					<c:forEach var="category" items="${list}">
						<option value="${category.ITEM}">${category.ITEM}</option>
					</c:forEach>
				</select>
				Title <input type="text" name="TITLE" maxlength="80"/> <br />
				<textarea class="summernote" name="CONTENT" maxlength="500"></textarea>
				<br /> 첨부파일 등록<input type="file" name="FILE"> 
				<input type="submit" />
			</form>
		</div>
	</div>
</body>
</html>
