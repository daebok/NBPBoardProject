<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>BOARD</title>
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
		$('.summernote').summernote({
			height : 300,
			callbacks: {
				onImageUpload: function(files, editor, welEditable) {
					for (var i = files.length - 1; i >= 0; i--) {
						sendFile(files[i],this);
					}
				}
			}
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
			document.form.action = "/board/question/modify"
			document.form.submit();
		});
		$("#category").val('${model.categoryItem}').prop("selected", true);
		
		$(".uploadedList").on("click","a",function(){
			var that = $(this);
			$('#registerForm').append("<input type='hidden' name='boardFilesNo' value="+$(this).attr("data-id")+">");
			$.ajax({
				url: "/upload/deleteFile",
				type:"post",
				data: {fileName: $(this).attr("data-src")},
				dataType: "text",
				success:function(result){
					if(result == 'deleted'){
						that.parent('div').remove();
					}
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
		<div class="container-fluid" style="margin-bottom: 30px">
			<form action="question/modify" method="post" name="form" id="registerForm" class="form-horizontal">
				<div class="form-group">
					<label for="title">Title</label>
					<input type="text" name="boardTitle" value="${model.boardTitle}" maxlength="80" id="title" /> 
				</div>
				<div class="form-group">
					<label for="category">Category</label>
					<select name="categoryItem" id="category">
						<c:forEach var="category" items="${list}">
							<option value="${category.categoryItem}">${category.categoryItem}</option>
						</c:forEach>
					</select> 
				</div>
				<textarea class="summernote" cols="100" rows="30" name="boardContent" maxlength="500" id="content">${model.boardContent}</textarea>
				<input type="file" class="fileButton" name="boardFiles">
				<div class="form-group">
					<div class="fileDrop">File Drop Here</div>
				</div>
				<div>
					<div class="box-footer">
						<c:forEach var="attach" items="${attach}">
							<div class="uploadedList">
								<div>
									<a href='/upload/displayFile?fileName=${attach.fileName}' > ${attach.fileOriginName}</a> 
									&nbsp;&nbsp;
									<a class='delete' data-src="${attach.fileName}" data-id="${attach.fileNo}">[삭제]</a>
									<br>
								</div>
							</div>
						</c:forEach>
						<div class="newUploadedList"></div>
					</div>
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				<input type="hidden" name="boardNo" value="${model.boardNo}">
				<div class="pull-right">
					<button type="button" id="modifyButton" class="btn btn-default">Modify</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
