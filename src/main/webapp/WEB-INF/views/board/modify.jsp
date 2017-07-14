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
		$("#category").val('${model.ITEM}').prop("selected", true);
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
				Title <input type="text" name="TITLE" value="${model.TITLE}"
					maxlength="80" id="title" /> 
				<select name="ITEM" id="category">
					<c:forEach var="category" items="${list}">
						<option value="${category.ITEM}">${category.ITEM}</option>
						<%-- <c:choose>
							<c:when test="${category.ITEM} eq '${model.ITEM}'">
								<option value="${category.ITEM}" selected>${category.ITEM}</option>
							</c:when>
							<c:otherwise>
								<option value="${category.ITEM}">${category.ITEM}</option>
							</c:otherwise>
						</c:choose> --%>
					</c:forEach>
				</select> 
				<br /><br />
				<textarea class="summernote" cols="100" rows="30" name="CONTENT"
					maxlength="500" id="content">${model.CONTENT}</textarea>

				<input type="hidden" name="BID" value="${model.BID}"> <br />
				<div class="pull-right">
					<button type="button" id="modifyButton" class="btn btn-default">Modify</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
