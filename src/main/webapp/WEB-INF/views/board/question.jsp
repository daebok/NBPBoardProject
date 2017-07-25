<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
<title>Home</title>
<style>
.fileDrop {
	width: 70%;
	height: 150px;
	border: 1px dotted gray;
	border: 1px dotted lightslategray;
	border-radius: 20px;
	margin: auto; 
	text-align: center;
	line-height: 150px;
	font-weight: border;
}

.delete {
	color: black;
	font-size:15px;
	text-decoration: none;
}
</style>
<script src="<c:url value="/resources/common/js/upload.js" />"></script>
<script>
	function getAjax(formData){
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			url : '/upload/uploadAjax',
			data : formData,
			dataType : 'text',
			processData : false,
			contentType : false,
			type : 'POST',
			beforeSend: function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success : function(data) {
				var str="";
				if(checkImageType(data)){ 
					str = "<div><a href='/upload/displayFile?fileName="+getImageLink(data)+"' class='file'>"; // link
					str += "<img src='/upload/displayFile?fileName="+data+"'/></a>";
					str += "&nbsp;&nbsp;<a data-src="+data+" class='delete'>[삭제]</a>";
					str += "<input type='hidden' name='boardFiles' value='"+getImageLink(data)+"'> </div>";
				} else { 
					str = "<div><a href='/upload/displayFile?fileName="+data+"' class='file'>"+getOriginalName(data)+"</a>"
							+"&nbsp;&nbsp;<a data-src="+data+" class='delete'>[삭제]</a>";
					str += "<input type='hidden' name='boardFiles' value='"+data+"'> </div>";
				}
				$(".newUploadedList").append(str);
			}
		});
	}
	
	/* 1. summernote를 통해 이미지 업로드 */
	function sendFile(file, el) {
		var formData = new FormData();
		formData.append('file', file);
		
		getAjax(formData);
	}
	$(document).ready(function() {
		/* 2. 드래그를 통해 파일 업로드 */
		$(".fileDrop").on("dragenter dragover", function(event) {
			event.preventDefault();
			var files = event.originalEvent.dataTransfer.files; // 전달된 파일 데이터 업로드
			var file = files[0];
		});
		$(".fileDrop").on("drop", function(event) {
			event.preventDefault();
			var files = event.originalEvent.dataTransfer.files; // 전달된 파일 데이터 업로드
			var file = files[0];
	
			var formData = new FormData();
			formData.append("file",file);
			getAjax(formData);
		});
	
		/* 3. file버튼을 통해 파일 업로드 */
		$(".fileButton").on("change", function(event) {
			var formData = new FormData($('#registerForm'));
			formData.append("file",$(".fileButton")[0].files[0]);
			getAjax(formData);
			
		});
		
		/* [삭제]버튼 누르면, 업로드 된 파일 삭제 */
		$(".newUploadedList").on("click","a",function(event){
			var that = $(this);
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			$.ajax({
				url: "/upload/deleteFile",
				type:"post",
				data: {fileName: $(this).attr("data-src")},
				dataType: "text",
				beforeSend: function(xhr){
					xhr.setRequestHeader(header, token);
				},
				success:function(result){
					if(result == 'deleted'){
						that.parent('div').remove();
					}
				}
			});
		});
	});
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
					<input type="text" name="boardTitle" maxlength="100" id="title" size="20" class="form-control" />
				</div>
				<div class="form-group">
					<label for="category">Category</label>
					<select name="categoryItem" id="category">
						<c:forEach var="category" items="${list}">
							<option value="${category.categoryItem}">${category.categoryItem}</option>
						</c:forEach>
					</select> 
				</div>
				<textarea class="summernote" name="boardContent" maxlength="500" id="content"></textarea>
				<br /> 
				<input type="file" class="fileButton" name="boardFiles">
				<div class="form-group">
					<div class="fileDrop">File Drop Here</div>
				</div>
				<div class="box-footer">
					<div class="newUploadedList"></div>
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				<div class="pull-right">
					<button type="button" id="questionButton" class="btn btn-default">Question</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
					 