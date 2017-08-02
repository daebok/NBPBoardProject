<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
<title>Home</title>
<script type="text/javascript">
	var idCheck = 0;
	var pwdCheck = 0;
	function checkId() {
		var userId = $('#userId').val();
		var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		if (special_pattern.test(userId) == true || userId == " ") {
			alert('특수문자 및 공백은 사용할 수 없습니다.');
			$('#userId').val(userId.substring(0, userId.length - 1));
			return false;
		}
		$.ajax({
			data : userId,
			contentType : "application/json; charset=utf-8",
			dataType: "json",
			type : "POST",
			url : "/user/duplicationId",
			beforeSend: function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success : function(data) {
				if (userId == "" && data == 0) {
					$(".signupbtn").prop("disabled", true);
					$(".signupbtn").css("background-color", "#aaaaaa");
					$("#userId").css("background-color", "#FFCECE");
					idCheck = 0;
				} else if (data == 0) {
					$("#userId").css("background-color", "#B0F6AC");
					idCheck = 1;
					if (idCheck == 1 && pwdCheck == 1) {
						$(".signupbtn").prop("disabled", false);
						$(".signupbtn").css("background-color", "#4CAF50");
						signupCheck();
					}
				} else if (data == 1) {
					$(".signupbtn").prop("disabled", true);
					$(".signupbtn").css("background-color", "#aaaaaa");
					$("#userId").css("background-color", "#FFCECE");
					idCheck = 0;
				}
			}
		});
	}
	function checkPwd() {
		var userPassword = $('.userPassword').val();
		var reUserPassword = $('#reUserPassword').val();
		if (reUserPassword == "" && (userPassword != reUserPassword || userPassword == reUserPassword)) {
			$(".signupbtn").prop("disabled", true);
			$(".signupbtn").css("background-color", "#aaaaaa");
			$("#reUserPassword").css("background-color", "#FFCECE");
		} else if (userPassword == reUserPassword) {
			$("#reUserPassword").css("background-color", "#B0F6AC");
			pwdCheck = 1;
			if (idCheck == 1 && pwdCheck == 1) {
				$(".signupbtn").prop("disabled", false);
				$(".signupbtn").css("background-color", "#4CAF50");
				signupCheck();
			}
		} else if (userPassword != reUserPassword) {
			pwdCheck = 0;
			$(".signupbtn").prop("disabled", true);
			$(".signupbtn").css("background-color", "#aaaaaa");
			$("#reUserPassword").css("background-color", "#FFCECE");

		}
	}
	function signupCheck() {
		var userName = $("#userName").val();
		if (userName == "") {
			$(".signupbtn").prop("disabled", true);
			$(".signupbtn").css("background-color", "#aaaaaa");
		} else {
		}
	}
	function cancel() {
		$(".cancelbtn").click(function() {
			$(".userId").val('');
			$(".userPassword").val('');
			$(".signupbtn").prop("disabled", true);
			$(".signupbtn").css("background-color", "#aaaaaa");
		});
	}
</script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>
	
	<div class="container">
		<div class="container-fluid">
			<form:form name="form" action="insert" method="post" class="form-horizontal">
				<div class="form-group">
					<label for="userId" class="col-sm-2 control-label"><b>ID</b></label> 
					<div class="col-sm-10">
						<input type="text" placeholder="Enter ID" name="userId" class="userId form-control" oninput="checkId()" id="userId"> <br /> 
					</div>
				</div>
				<div class="form-group">
					<label for="userName" class="col-sm-2 control-label"><b>Name</b></label> 
					<div class="col-sm-10">
						<input type="text" placeholder="Enter NAME" name="userName" id="userName" class="form-control"> <br /> 
					</div>
				</div>
				<div class="form-group">
					<label for="userPassword" class="col-sm-2 control-label"><b>Password</b></label> 
					<div class="col-sm-10">
						<input type="password" placeholder="Enter PASSWORD" name="userPassword" class="userPassword form-control" id="userPassword" oninput="checkPwd()"> <br /> 
					</div>
				</div>
				<div class="form-group">
					<label for="reUserPassword" class="col-sm-2 control-label"> <b>Repeat Password</b></label> 
					<div class="col-sm-10">
						<input type="password" placeholder="Repeat Password" name="reUserPassword" class="reUserPassword form-control" id="reUserPassword" oninput="checkPwd()">
					</div>
					<br /> 
				</div>
				<div class="clearfix">
					<div class="form-group">
   						 <div class="col-sm-offset-2 col-sm-10">
							<button type="button" class="cancelbtn btn btn-default" onclick="cancel">Cancel</button>
							<button type="submit" class="signupbtn btn btn-default" disabled="disabled">SignUp</button>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html>
