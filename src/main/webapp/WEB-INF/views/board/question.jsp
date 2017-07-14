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
	background-color: lightslategray;
	margin: auto; 
}

small {
	margin-left: 3px;
	font-weight: bold;
	color: gray;
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
				url : '/board/uploadAjax',
				data : formData,
				dataType : 'text',
				processData : false,
				contentType : false,
				type : 'POST',
				success : function(data) {
					var str="";
					if(checkImageType(data)){ 
						str = "<div><a href='/board/upload/displayFile?fileName="+getImageLink(data)+"' class='file'>"; // link
						str += "<img src='/board/upload/displayFile?fileName="+data+"'/></a>";
						str += "<small data-src="+data+">X</small></div>";
					} else { // download if not image file
						 str = "<div><a href='/board/upload/displayFile?fileName="+data+"' class='file'>"+getOriginalName(data)+"</a>"
								+"<small data-src="+data+">X</small></div>";
					}
					$(".uploadedList").append(str);

				}
			});
		});
		$(".uploadedList").on("click","small",function(event){
			var that = $(this);
			
			$.ajax({
				url: "deleteFile",
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

			$(".uploadedList").each(function(index){
				str += "<input type='hidden' name='files' value='"+ $(".file").attr("href") +"'> ";
			});
			that.append(str);
			
			document.form.action = "question/ask"
			document.form.submit();
		});
		
		/* summernote */
		$('.summernote').summernote({
			height : 300,
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
				url : "/board/imageUpload",
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
		<div class="container-fluid">
			<form action="question/ask" method="post" name="form" id="registerForm">
				Title <input type="text" name="TITLE" maxlength="80" id="title" />
				<select name="ITEM">
					<c:forEach var="category" items="${list}">
						<option value="${category.ITEM}">${category.ITEM}</option>
					</c:forEach>
				</select> <br /> <br />
				<textarea class="summernote" name="CONTENT" maxlength="500"
					id="content"></textarea>
				<br /> 
				<div class="form-group">
					<div class="fileDrop"><label for="fileDrop">File Drop Here</label></div>
				</div>
				<div class="box-footer">
					<hr>
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
					 