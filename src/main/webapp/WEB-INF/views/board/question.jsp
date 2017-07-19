<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>Home</title>
<style>
.fileDrop {
	width: 70%;
	height: 100px;
	border: 1px dotted gray;
	border: 1px dotted lightslategray;
	border-radius: 20px;
	margin: auto; 
	text-align: center;
	line-height: 100px;
	font-weight: border;
}

.delete {
	color: black;
	font-size:15px;
	text-decoration: none;
}
</style>
<script src="<c:url value="/resources/common/js/upload.js" />"></script>
<script src="<c:url value="/resources/common/js/fileUpload.js" />"></script>
<script>

	$(document).ready(function() {
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
			
			var that = $("#registerForm");
			var str = "";

			document.form.action = "/board/question/ask"
			document.form.submit();
		});
		
		$('.summernote').summernote({
			height : 200,
			width: 100,
			callbacks: {
				onImageUpload: function(files, editor, welEditable) {
					for (var i = files.length - 1; i >= 0; i--) {
						sendFile(files[i],this);
					}
				}
			}
		});
		
	});
</script>

</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->

	<div class="container">
		<div class="container-fluid" style="margin-bottom: 30px">
			<form action="/board/question/ask" method="post" name="form" id="registerForm" class="form-horizontal">
				<div class="form-group">
					<label for="title">Title</label>
					<input type="text" name="title" maxlength="100" id="title" size="20" class="form-control" />
				</div>
				<div class="form-group">
					<label for="category">Category</label>
					<select name="item" id="category">
						<c:forEach var="category" items="${list}">
							<option value="${category.item}">${category.item}</option>
						</c:forEach>
					</select> 
				</div>
				<textarea class="summernote" name="content" maxlength="500"
					id="content"></textarea>
				<br /> 
				<input type="file" class="fileButton" name="file">
				<div class="form-group">
					<div class="fileDrop">File Drop Here</div>
				</div>
				<div class="box-footer">
					<div class="newUploadedList"></div>
				</div>
				<div class="pull-right">
					<button type="button" id="questionButton" class="btn btn-default">Question</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
					 