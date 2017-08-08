<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<sec:csrfMetaTags/>
<script type="text/javascript">
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).on('click','#contactPasswordCheckButton', function() {
	var data = $('.contact-password-form').serialize();
	var contactNo = ${contact};
	console.log(contactNo);
	$.ajax({
		type : 'POST',
		url : '/board/contactus/password/check',
		dataType : 'text',
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
		},
		data : data,
		success : function(result) {
			var list = $.parseJSON(result);
			console.log(list);
			if(Number(list) >= 1) {
				location.href = "/board/contactus/view?contactNo="+contactNo;
			} else {
				$('#password').val('');
				alert('비밀번호가 틀립니다!');
				return;
			}
		}

	});
});

</script>
</head>
<body>
	<div style="margin-left:20px;">
		<form:form name="form" method="post" class="contact-password-form form-horizontal">
			<div class="form-group">
				<input type="password" name="contactPassword" maxlength="4" id="password" size="4" class="form-control" style="width:200px;"/>
			</div>
			<input type="hidden" name="contactNo" value="${contact}">
			<div class="pull-left">
				<button type="button" id="contactPasswordCheckButton" class="btn btn-default" style="float:left;">확인</button>
			</div>
		</form:form>
	</div>
</body>
</html> 