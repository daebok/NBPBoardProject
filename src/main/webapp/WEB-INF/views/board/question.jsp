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
<script>
	$(document).ready(function() {
		$(".fileDrop").on("dragenter dragover", function(event) {
			event.preventDefault();
			var files = event.originalEvent.dataTransfer.files;
			var file = files[0];
			console.log(file);
		});
		$(".fileDrop").on("drop", function(event) {
			event.preventDefault();
			var files = event.originalEvent.dataTransfer.files;
			var file = files[0];
			console.log(file);

			var formData = new FormData();
			formData.append("file",file);

			$.ajax({
				url : '/upload/uploadAjax',
				data : formData,
				dataType : 'text',
				processData : false,
				contentType : false,
				type : 'POST',
				success : function(data) {
					var str="";
					console.log(data);
					if(checkImageType(data)){ 
						str = "<div><a href='/upload/displayFile?fileName="+getImageLink(data)+"' class='file'>"; // link
						str += "<img src='/upload/displayFile?fileName="+data+"'/></a>";
						str += "&nbsp;&nbsp;<a data-src="+data+" class='delete'>[삭제]</a>";
						str += "<input type='hidden' name='files' value='"+getImageLink(data)+"'> </div>";
					} else { 
						str = "<div><a href='/upload/displayFile?fileName="+data+"' class='file'>"+getOriginalName(data)+"</a>"
								+"&nbsp;&nbsp;<a data-src="+data+" class='delete'>[삭제]</a>";
						str += "<input type='hidden' name='files' value='"+data+"'> </div>";
					}
					$(".uploadedList").append(str);

				}
			});
		});
		$(".uploadedList").on("click","a",function(event){
			var that = $(this);
			
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
			onImageUpload : function(files, editor, welEditable) {
				sendFile(files[0], editor, welEditable);
			}
		});
		function sendFile(file, editor, welEditable) {
			data = new FormData();
			data.append("uploadFile", file);
			$.ajax({
				data : data,
				type : "POST",
				url : "/upload/imageUpload",
				dataType : 'text',
				cache : false,
				contentType : false,
				processData : false,
				success : function(data) {
					editor.insertImage(welEditable, data.url);
				}
			});
		}

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
				<div class="form-group">
					<div class="fileDrop">File Drop Here</div>
				</div>
				<div class="box-footer">
					<div class="uploadedList"></div>
				</div>
				<div class="pull-right">
					<button type="button" id="questionButton" class="btn btn-default">Question</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
					 