<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
<script>
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).on('click','#passowrd-check',function(){
	$.ajax({
		type : 'POST',
		url : '/user/password/check',
		dataType : 'text',
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
		},
		data : $('#password-check-form').serialize(),
		success : function(result) {
			var list = $.parseJSON(result);
			if(list) {
				$(this).attr("disabled",true);
				$('#userPassword').attr("disabled",true);
				$('#newUserPassword').attr("disabled",false);
				$('#newReUserPassword').attr("disabled",false);
				$('#password-change-button').attr("disabled",false);
			} else {
				alert('비밀번호가 맞지 않습니다!')
				$('#userPassword').val('');
				return;
			}
		}
	});
});

function checkPwd() {
	var userPassword = $('#newUserPassword').val();
	var reUserPassword = $('#newReUserPassword').val();
	if (reUserPassword == "" && (userPassword != reUserPassword || userPassword == reUserPassword)) {
		$(".password-change-button").prop("disabled", true);
		$(".password-change-button").css("background-color", "#aaaaaa");
		$("#newReUserPassword").css("background-color", "#FFCECE");
	} else if (userPassword == reUserPassword) {
		$("#newReUserPassword").css("background-color", "#B0F6AC");
		pwdCheck = 1;
		if (idCheck == 1 && pwdCheck == 1) {
			$(".password-change-button").prop("disabled", false);
			$(".password-change-button").css("background-color", "#4CAF50");
			signupCheck();
		}
	} else if (userPassword != reUserPassword) {
		pwdCheck = 0;
		$(".password-change-button").prop("disabled", true);
		$(".password-change-button").css("background-color", "#aaaaaa");
		$("#newReUserPassword").css("background-color", "#FFCECE");

	}
}

</script>
</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>
	
	<div class="container">
		<div style="margin:40px;">
			<b>비밀번호 변경</b>
		</div>
		<!-- 비밀번호 확인 -->
		<form:form name="form" method="post" class="form-horizontal" id="password-check-form">
			<div class="form-group">
				<label for="userPassword" class="col-sm-2 control-label"><b>Password</b></label> 
				<div class="col-sm-10">
					<input type="password" placeholder="현재 비밀번호" name="userPassword" class="userPassword form-control" id="userPassword"> 
				</div>
			</div>
			<div class="form-group">
 					<div class="col-sm-offset-2 col-sm-10">
					<button type="button" class="btn btn-default" id="passowrd-check" >Ok</button>
				</div>
			</div>
		</form:form>
		<br /> <br /> 
		<!-- 비밀번호 변경 -->
		<form:form name="form" action="/user/password/change" method="post" class="form-horizontal" id="password-change-form">
			<div class="form-group">
				<label for="userPassword" class="col-sm-2 control-label"><b>New Password</b></label> 
				<div class="col-sm-10">
					<input type="password" placeholder="새 비밀번호" name="userPassword" class="newUserPassword form-control" id="newUserPassword" disabled oninput="checkPwd()">
				</div>
			</div>
			<div class="form-group">
				<label for="reUserPassword" class="col-sm-2 control-label"> <b>Repeat New Password</b></label> 
				<div class="col-sm-10">
					<input type="password" placeholder="새 비밀번호 확인" name="reUserPassword" class="reUserPassword form-control" id="newReUserPassword" disabled oninput="checkPwd()">
				</div>
			</div>
			<div class="clearfix">
				<div class="form-group">
  					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" disabled class="btn btn-default" id="password-change-button">Ok</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>
