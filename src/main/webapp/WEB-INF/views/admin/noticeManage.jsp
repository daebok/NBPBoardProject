<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>Notice</title>
<script type="text/javascript">
$(document).ready(function() {
	$("#noticeButton").click(function() {
		var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
		var blank_pattern = /[\s]/g;
		
		var title = $("#title").val();
		var content = $("#content").val();
		
		if (blank_pattern.test(title) == true) {
			alert("제목를 입력하세요.");
			$("#title").focus();
			return;
		}
		
		if (special_pattern.test(title) == true) {
			alert('제목에 특수문자는 사용할 수 없습니다.');
			$('#title').focus();
			return false;
		}

		if (blank_pattern.test(content) == true) {
			alert("내용를 입력하세요.");
			$("#content").focus();
			return;
		}

		document.form.action = "/admin/notice/regist"
		document.form.submit();
	});
	
	$('.summernote').summernote({
		height : 200,
		width: 100,
		callbacks: {
			onImageUpload: function(files, editor, welEditable) {
				var form = $('.file')[0];
				var formData = new FormData(form);
				for (var index = files.length - 1; index >= 0; index--) {
					formData.append('files', files[index]);

					var str = "<div class='list-group-item' id='file-list-"+index+"'>";
					str += "<div class='list-1'>" + files[index].name +"</div>";
					str += "<div class='list-2'>" + files[index].size + " bytes </div>";
					str += "<div class='list-3'> <a class='file-delete-button' id='"+index+"'>[삭제]</a></div></div>";
					$(".newUploadedList").append(str);
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
		<form:form name="form" action="/admin/notice/regist" method="post" class="form-horizontal" id="category-add-form">
			<div class="form-group">
				<label for="title">Title</label>
				<input type="text" name="noticeTitle" maxlength="100" id="title" size="20" class="form-control" />
			</div>
			<div class="form-group">
				<div class="col-sm-10">
					<textarea class="summernote" name="noticeContent" id="content"></textarea>
				</div>
			</div>
			<div class="pull-right">
				<button type="button" id="noticeButton" class="category-add-button btn btn-default" style="float:left;">Add</button>
			</div>
		</form:form>
	</div>
</body>
</html> 