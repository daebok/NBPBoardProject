<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>Home</title>
<script type="text/javascript">
	var idCheck = 0;
	var pwdCheck = 0;
	function checkId() {
		var id = $('#id').val();
		var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
		if (special_pattern.test(id) == true || id == " ") {
			alert('특수문자 및 공백은 사용할 수 없습니다.');
			$('#id').val(id.substring(0, id.length - 1));
			return false;
		}
		$.ajax({
			data : id,
			contentType : "application/json; charset=utf-8",
			dataType: "json",
			type : "POST",
			url : "/user/duplicationId",
			success : function(data) {
				if (id == "" && data == 0) {
					$(".signupbtn").prop("disabled", true);
					$(".signupbtn").css("background-color", "#aaaaaa");
					$("#id").css("background-color", "#FFCECE");
					idCheck = 0;
				} else if (data == 0) {
					$("#id").css("background-color", "#B0F6AC");
					idCheck = 1;
					if (idCheck == 1 && pwdCheck == 1) {
						$(".signupbtn").prop("disabled", false);
						$(".signupbtn").css("background-color", "#4CAF50");
						signupCheck();
					}
				} else if (data == 1) {
					$(".signupbtn").prop("disabled", true);
					$(".signupbtn").css("background-color", "#aaaaaa");
					$("#id").css("background-color", "#FFCECE");
					idCheck = 0;
				}
			}
		});
	}
	function checkPwd() {
		var pass = $('.pass').val();
		var repass = $('#repass').val();
		if (repass == "" && (pass != repass || pass == repass)) {
			$(".signupbtn").prop("disabled", true);
			$(".signupbtn").css("background-color", "#aaaaaa");
			$("#repass").css("background-color", "#FFCECE");
		} else if (pass == repass) {
			$("#repass").css("background-color", "#B0F6AC");
			pwdCheck = 1;
			if (idCheck == 1 && pwdCheck == 1) {
				$(".signupbtn").prop("disabled", false);
				$(".signupbtn").css("background-color", "#4CAF50");
				signupCheck();
			}
		} else if (pass != repass) {
			pwdCheck = 0;
			$(".signupbtn").prop("disabled", true);
			$(".signupbtn").css("background-color", "#aaaaaa");
			$("#repass").css("background-color", "#FFCECE");

		}
	}
	function signupCheck() {
		var name = $("#name").val();
		if (name == "") {
			$(".signupbtn").prop("disabled", true);
			$(".signupbtn").css("background-color", "#aaaaaa");
		} else {
		}
	}
	function cancel() {
		$(".cancelbtn").click(function() {
			$(".id").val('');
			$(".pass").val('');
			$(".signupbtn").prop("disabled", true);
			$(".signupbtn").css("background-color", "#aaaaaa");
		});
	}
</script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="container-fluid">
			<form name="form" action="insert" method="post" class="form-horizontal">
				<div class="form-group">
					<label for="id" class="col-sm-2 control-label"><b>ID</b></label> 
					<div class="col-sm-10">
						<input type="text" placeholder="Enter ID" name="id" class="id form-control" oninput="checkId()" id="id"> <br /> 
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label"><b>Name</b></label> 
					<div class="col-sm-10">
						<input type="text" placeholder="Enter NAME" name="name" id="name" class="form-control"> <br /> 
					</div>
				</div>
				<div class="form-group">
					<label for="pass" class="col-sm-2 control-label"><b>Password</b></label> 
					<div class="col-sm-10">
						<input type="password" placeholder="Enter PASSWORD" name="password" class="pass form-control" id="pass" oninput="checkPwd()"> <br /> 
					</div>
				</div>
				<div class="form-group">
					<label for="repass" class="col-sm-2 control-label"> <b>Repeat Password</b></label> 
					<div class="col-sm-10">
						<input type="password" placeholder="Repeat Password" name="psw-repeat" class="pass form-control" id="repass" oninput="checkPwd()">
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
			</form>
		</div>
	</div>
</body>
</html>
