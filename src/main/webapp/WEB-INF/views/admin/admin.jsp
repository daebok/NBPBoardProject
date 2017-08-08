<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>

	<div class="container"  style="height:100%;">
		<div class="container-fluid">
			<a href="<c:url value='/admin/user'/>" id="login" class="notice btn btn-default">User Management</a> 
			<a href="<c:url value='/admin/category'/>" id="login" class="notice btn btn-default">Category Management</a>
			<a href="<c:url value='/admin/notice'/>" id="login" class="notice btn btn-default">Notice Management</a>
		</div>
		<br><br>
		<div class="panel panel-default">
			<div class="panel-heading">문의사항</div>
			<div class="list-group">
				<c:forEach var="contact" items="${contact}">
					<div class="list-group-item">
						<a href="<c:url value='/board/contactus/view?contactNo=${contact.contactNo}'/>">${contact.contactTitle}</a>
						<c:if test="${contact.contactPassword ne null}">
							<span class="label label-danger">비밀글</span>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="page-nation">
			<ul class="pagination pagination-large">
				<c:if test="${pageMaker.prev}">
					<li class="disabled"><span><a href="/board/contactUs${pageMaker.makeQuery(pageMaker.startPage-1)}">&laquo;</a></span></li>
				</c:if>
				<c:forEach var="idx" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
					<li <c:out value="${pageMaker.cri.page == idx? 'class=active' : '' }" />>
						<a href="/board/contactUs${pageMaker.makeQuery(idx)}"><span>${idx}</span></a>
					</li>
				</c:forEach>
				<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
					<li class="disabled"><span><a href="/board/contactUs${pageMaker.makeQuery(pageMaker.endPage + 1)}">&raquo;</a></span></li>
				</c:if>
			</ul>
		</div>
	</div>
	

	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html> 