<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>
	
	<div class="container">
		<div class="container-fluid">
			<div class="col-md-12">
				<h2><c:out value="${model.contactTitle}" escapeXml="true"/></h2>
				<p><c:out value="${model.contactContent}" escapeXml="false"/></p>
				<hr>
				<div class="pull-left">
					<form:form name="list"  action="${uri}" method="get">
						<input type="hidden" name="contactNo" value="${model.contactNo}" /> 
						<input type="hidden" name="page" value="${criteria.page}" /> 
						<input type="hidden" name="perPageNum" value="${criteria.perPageNum}" />
						<input type="hidden" name="option" value="${criteria.option}" />
						<input type="submit" class="btn btn-primary" value="List"/>
					</form:form>
				</div>
				<div class="pull-right">
					<c:if test="${user.userNo == model.userNo}">
						<button id="delete" class="btn btn-primary" onclick="contactDelete(${model.contactNo})">Delete</button>
					</c:if>
				</div>
			</div>
		</div>
		<div class="col-md-8">
			<div id="listComment">
				<%@ include file="contact-comment-form.jsp" %>
			</div>
		</div>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<div class="col-lg-9" style="margin-top: 50px;">
				<form:form name="form" method="get" class="contact-comment-form">
					<input type="hidden" name="contactNo" value="${model.contactNo}">
					<textarea class="contact-summernote" id="content" name="contactCommentContent"></textarea><br>
					<div class="pull-right">
						<button class="btn btn-default" onclick="contactCommentRegist()">OK</button>
					</div>
				</form:form>
			</div>
		</sec:authorize>
	</div>
		
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html>

<link type="text/css" rel="stylesheet" 	href="<c:url value='/resources/common/css/contact.css'/>">
<script src="<c:url value="/resources/common/js/contact.js" />"></script>

