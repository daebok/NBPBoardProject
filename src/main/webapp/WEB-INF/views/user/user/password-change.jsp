<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
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
					<button type="button" class="btn btn-default" onclick="presentPasswordCheck(this)" >OK</button>
				</div>
			</div>
		</form:form>
		<br /> <br /> 
		<!-- 비밀번호 변경 -->
		<form:form name="form" action="/user/password/change" method="post" class="form-horizontal" id="password-change-form">
			<div class="form-group">
				<label for="userPassword" class="col-sm-2 control-label"><b>New Password</b></label> 
				<div class="col-sm-10">
					<input type="password" placeholder="새 비밀번호" name="userPassword" class="newUserPassword form-control" id="newUserPassword" disabled oninput="rePasswordCheck2()">
				</div>
			</div>
			<div class="form-group">
				<label for="reUserPassword" class="col-sm-2 control-label"> <b>Repeat New Password</b></label> 
				<div class="col-sm-10">
					<input type="password" placeholder="새 비밀번호 확인" name="reUserPassword" class="reUserPassword form-control" id="newReUserPassword" disabled oninput="rePasswordCheck2()">
				</div>
			</div>
			<div class="clearfix">
				<div class="form-group">
  					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" disabled class="btn btn-default" id="user-button">Ok</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>

<script src="<c:url value="/resources/common/js/user.js" />"></script>