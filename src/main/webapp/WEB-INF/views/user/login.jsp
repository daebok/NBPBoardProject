<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>Home</title>
<script>
	$(document).ready(function() {
		$("#loginButton").click(function() {
			var userId = $("#userId").val();
			var userPassword = $("#userPassword").val();
			if (userId == "") {
				alert("아이디를 입력하세요.");
				$("#userId").focus();
				return;
			}
			if (userPassword == "") {
				alert("비밀번호를 입력하세요.");
				$("#userPassword").focus();
				return;
			}
			document.form.action = "/loginProcess"
			document.form.submit();
		});
	});
</script>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>
	
	<div class="container">
		<div class="container-fluid">
			<form:form name="form" method="post" class="form-horizontal">
				<c:if test="${param.error != null}">
					<p>아이디와 비밀번호가 잘못되었습니다.</p>
				</c:if>
				<c:if test="${param.logout != null}">
					<p>로그아웃 하였습니다.</p>
				</c:if>
				<div class="form-group">
					<label for="id" class="col-sm-2 control-label"><b>ID</b></label>
					<div class="col-sm-10">
						<input type="text" name="userId" id="userId" placeholder="Enter ID" class="form-control" /> <br /> 
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label"><b>Password</b></label>
					<div class="col-sm-10">
						<input type="password" name="userPassword" id="userPassword" placeholder="Enter PASSWORD" class="form-control" /> <br />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="button" id="loginButton" class="btn btn-default">Log In</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html>
