<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<sec:csrfMetaTags/>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>

	<div class="container">
		<div style="margin-bottom:20px; ">
			<a href="<c:url value='/contact/regist'/>" class="btn btn-default">문의하기</a>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">문의사항</div>
			<div class="panel-body">
				<p> 신고 및 문의 사항은 아래 주소로 연락 바랍니다. </p>
				<a href="mailto:wikiki413@gmail.com" class="glyphicon glyphicon-send">&nbsp; wikiki413@gmail.com</a>
			</div>
			<!-- contact-list -->
			<%@include file="contact-list-form.jsp"%>
			
		</div>
		<custom:paging uri="/contact/list" />
	</div>
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
	<div id="contact-password-dialog"></div>
</body>
</html>

<link type="text/css" rel="stylesheet" 	href="<c:url value='/resources/common/css/contact.css'/>">
<script src="<c:url value="/resources/common/js/contact.js" />"></script>

					 