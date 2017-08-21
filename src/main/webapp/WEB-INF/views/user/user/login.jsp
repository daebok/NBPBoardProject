<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>
	
	<div class="container">
		<div class="container-fluid" style="height:100%;">
			<div align="center">
				<c:if test="${param.error != null}">
					<c:set var="alert" value="<script type='text/javascript'>alert('아이디와 비밀번호가 잘못되었습니다.');</script>" />
					${alert}
				</c:if>
				<c:if test="${param.logout != null}">
					<c:set var="alert" value="<script type='text/javascript'>alert('로그아웃 하였습니다.');</script>" />
					${alert}
				</c:if>
			</div>
			<br><br>
			<form:form name="form" action="/loginProcess" method="post" class="form-horizontal">
				<div class="form-group">
					<label for="id" class="col-sm-2 control-label"><b>ID</b></label>
					<div class="col-sm-10" >
						<input type="text" name="userId" id="userId" placeholder="아이디를 입력하세요" class="form-control" /> <br /> 
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label"><b>Password</b></label>
					<div class="col-sm-10">
						<input type="password" name="userPassword" id="userPassword" placeholder="비밀번호를 입력하세요" class="form-control" /> <br />
					</div>
				</div>
				<div align="left" class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="button" onclick="login()" class="btn btn-default">Log In</button>
					</div>
				</div>	
				<!-- 네이버 아이디로 로그인 -->
				<div align="left" class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<a href="${url}"><img src="<c:url value="/resources/common/img/naverid_login_button.png" />" height=34 /></a>
					</div>
				</div>		
			</form:form>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>

<script src="<c:url value="/resources/common/js/user.js" />"></script>
