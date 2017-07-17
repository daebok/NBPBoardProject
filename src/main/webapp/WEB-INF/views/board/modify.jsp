<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>BOARD</title>
<script>
	$(document).ready(function() {
		$('.summernote').summernote({
			height : 300
		});
		$("#modifyButton").click(function() {
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
			document.form.action = "question/modify"
			document.form.submit();
		});
		$("#category").val('${model.item}').prop("selected", true);
	});
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="container-fluid">
			<form action="question/modify" method="post" name="form">
				Title <input type="text" name="title" value="${model.title}"
					maxlength="80" id="title" /> 
				<select name="item" id="category">
					<c:forEach var="category" items="${list}">
						<option value="${category.item}">${category.item}</option>
					</c:forEach>
				</select> 
				<br /><br />
				<textarea class="summernote" cols="100" rows="30" name="content"
					maxlength="500" id="content">${model.content}</textarea>

				<input type="hidden" name="boardId" value="${model.boardId}"> <br />
				<div class="pull-right">
					<button type="button" id="modifyButton" class="btn btn-default">Modify</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
