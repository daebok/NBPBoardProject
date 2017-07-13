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
		var id = $('.id').val();
		
		var special_pattern =  /[`~!@#$%^&*|\\\'\";:\/?]/gi;
		if(special_pattern.test(id) == true || id == " "){
		    alert('특수문자 및 공백은 사용할 수 없습니다.');
		    $('.id').val(id.substring(0, id.length - 1));
		    return false;
		}
		$.ajax({
			data : id,
			contentType : "application/json",
			type : "POST",
			url : "duplicationId",
			success : function(data) {
				console.log(data);
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
			<form name="form" action="insertUser" method="post">
				<label for="id"><b>ID</b></label> <input type="text"
					placeholder="Enter ID" name="ID" class="id" oninput="checkId()"
					id="id"> <br /> <label for="name"><b>Name</b></label> <input
					type="text" placeholder="Enter NAME" name="NAME" id="name">
				<br /> <label for="pass"><b>Password</b></label> <input
					type="password" placeholder="Enter PASSWORD" name="PASSWORD"
					class="pass" id="pass" oninput="checkPwd()"> <br /> <label
					for="repass"> <b>Repeat Password</b>
				</label> <input type="password" placeholder="Repeat Password"
					name="psw-repeat" class="pass" id="repass" oninput="checkPwd()">
				<br /> <input type="checkbox" checked="checked"> Remember
				me
				<div class="clearfix">
					<button type="button" class="cancelbtn" onclick="cancel">Cancel</button>
					<button type="submit" class="signupbtn" disabled="disabled">SignUp</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
