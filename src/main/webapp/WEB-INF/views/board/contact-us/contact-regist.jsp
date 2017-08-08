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
		var special_pattern = /[\\<>]/gi;
		
		var title = $("#title").val();
		var content = $("#content").val();
		
		if (title.replace(/\s|　/gi, '') == '') {
			alert("제목를 입력하세요.");
			$("#title").focus();
			return;
		}
		
		if (special_pattern.test(title) == true) {
			alert('제목에 \<>는 사용할 수 없습니다.');
			$('#title').focus();
			return false;
		}

		var htmlRemoveContent = content.replace(/<\/?([a-z][a-z0-9]*)\b[^>]*>/gi, "");
		htmlRemoveContent.replace(/&nbsp;/g, "");
		if (htmlRemoveContent.replace(/(\s*)/g, '') == '') {
			alert("내용를 입력하세요.");
			$("#content").focus();
			return;
		}

		document.form.action = "/board/contactus/regist"
		document.form.submit();
	});
	
	$('.summernote').summernote({
		height : 200
	});
});
$(document).on("change","#password-check",function(){
	var check = $(this).prop("checked");
	if(check) {
		$('#password').attr('disabled', false);
	} else {
		$('#password').attr('disabled', true);
	}
});
</script>
</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>

	<div class="container">
		<form:form name="form" method="post" class="form-horizontal">
			<div class="form-group">
				<label for="title">Title</label>
				<input type="text" name="contactTitle" maxlength="100" id="title" size="20" class="form-control" />
			</div>
			<div class="form-group">
				<div class="col-sm-10">
					<textarea class="summernote" name="contactContent" id="content"></textarea>
				</div>
			</div>
			<label for="password">비밀번호</label>
			<input type="checkbox" id="password-check">
			<input type="password" name="contactPassword" maxlength="4" id="password" size="4" disabled />
			<div class="pull-right">
				<button type="button" id="noticeButton" class="category-add-button btn btn-default" style="float:left;">문의하기</button>
			</div>
		</form:form>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html> 