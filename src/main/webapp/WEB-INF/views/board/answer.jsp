<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>BOARD</title>
<script>
	$(document).ready(function() {
		$('#DELETE').click(function() {
			var result = confirm('Are you sure you want to delete this?');
			if (result) {
				location.replace('/board/delete?id=${model.BID}');
			}
		});
		$('#List').click(function() {
			document.form.method = "get";
			document.form.action = "/board/list"
			document.form.submit();
		});

		/* summernote */
		$('.summernote').summernote({
			height : 200,
			onImageUpload : function(files, editor, welEditable) {
				sendFile(files[0], editor, welEditable);
			}
		});
		
		$('#commentButton').click(function() {
			var contentObj = $('#content');
			var content = $('#content').val();
			var BID = ${model.BID};
			var UID = ${sessionScope.UID};
			var data="BID="+BID+"&UID="+UID+"&content="+content;
			console.log(BID);
			$.ajax({
				type: 'GET',
				url : 'comment',
				dataType : 'text',
				processData : false,
				contentType : false,
				data : data,
				success : function(result) {
					alert('답글이 달렸습니다.');
					$("#listComment").append("<div>"+content+"</div>");
					contentObj.value = "";
				}
			});
		});
	});
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<div class="container">
		<c:if test="${sessionScope.UID == model.UID}">
			<a href="<c:url value='/modify?id=${model.BID}'/>" id="MODIFY"
				class="btn btn-danger">Modify</a>
			<button id="DELETE" class="btn btn-danger">Delete</button>
		</c:if>
		<div class="container-fluid">
			<div class="col-md-12">
				<div class="pull-right">
					<span class="label label-warning">${model.ITEM}</span>
				</div>
				<h1>${model.TITLE}</h1>
				<p>${model.CONTENT}</p>
				<div class="pull-right">
					<span class="badge">Posted By ${model.NAME}</span>
				</div>
				<hr>
				<c:forEach var="attach" items="${attach}">
					<a href='/board/upload/displayFile?fileName=${attach.FILENAME}'>
						<img src='/board/upload/displayFile?fileName=${attach.FILENAME}'
						onerror="this.style.display='none'" alt='' />${attach.FILENAME}
					</a>
					<br>
				</c:forEach>
				<form name="form" action="list" method="post">
					<input type="hidden" name="id" value="${model.BID}" /> <input
						type="hidden" name="page" value="${cri.page}" /> <input
						type="hidden" name="perPageNum" value="${cri.perPageNum}" />
					<button type="submit" id="List" class="btn btn-warning">List</button>
				</form>

			</div>
		</div>


		<!-- comment -->
		<div class="container-fluid">
			<div class="col-lg-8" style="margin-top: 70px; margin-bottom: 20px">
					<div id="listComment" class="col-lg-8" >
					<c:forEach var="comment" items="${comment}">
						<div>${comment.content}</div>
						</c:forEach>
					</div>
			</div>
			<div class="col-lg-8" style="margin-top: 30px; margin-bottom: 100px">
				<label for="content">Comment</label>
				<textarea class="summernote" name="content" maxlength="500" id="content"></textarea>
				<br />
				<div class="pull-right">
					<button id="commentButton" class="btn btn-default">Comment</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
