<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>

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
			<input type="checkbox" onchange="passwordCheck(this)">
			<input type="password" name="contactPassword" maxlength="4" id="password" size="4" disabled placeHolder="****" />
			<div class="pull-right">
				<button type="button" class="btn btn-default" onclick="contactRegist()" style="float:left;">문의하기</button>
			</div>
		</form:form>
	</div>
	
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html> 
<link type="text/css" rel="stylesheet" 	href="<c:url value='/resources/common/css/contact.css'/>">
<script src="<c:url value="/resources/common/js/contact.js" />"></script>
