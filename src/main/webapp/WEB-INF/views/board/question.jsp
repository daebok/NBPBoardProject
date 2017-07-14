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
			 height : 350,
		        onImageUpload : function(files, editor, welEditable) {
		            sendFile(files[0], editor, welEditable);
		        },
		});
		$("#questionButton").click(function() {
			var title = $("#title").val();
			var content = $("#content").val();
			if (title == "") {
				alert("제목을 입력하세요.");
				$("#title").focus();
				return;
			}
			if (content == "") {
				alert("내용를 입력하세요.");
				$("#content").focus();
				return;
			}
			document.form.action = "question/ask"
			document.form.submit();
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
			<form action="question/ask" method="post"
				enctype="multipart/form-data" name="form">
				Title <input type="text" name="TITLE" maxlength="80" id="title" />
				<select name="ITEM">
					<c:forEach var="category" items="${list}">
						<option value="${category.ITEM}">${category.ITEM}</option>
					</c:forEach>
				</select> 
				<br /><br />
				<textarea class="summernote" name="CONTENT" maxlength="500"
					id="content"></textarea>
				<br /> 첨부파일 등록<input type="file" name="FILE">
				<div class="pull-right">
					<button type="button" id="questionButton" class="btn btn-default">Question</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
