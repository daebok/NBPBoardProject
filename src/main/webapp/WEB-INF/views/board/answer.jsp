<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>BOARD</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<script>
	$(document).ready(function() {
		$('#DELETE').click(function() {
			var result = confirm('Are you sure you want to delete this?');
			if (result) {
				location.replace('/board/delete.do?id=${model.BID}');
			}
		});
		$('#List').click(function() {
			location.href = '/board/list.do';
		});
	});
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<div class="container">
		<c:if test="${sessionScope.UID == model.UID}">
			<a href="<c:url value='/modify.do?id=${model.BID}'/>" id="MODIFY"
				class="btn btn-danger">Modify</a>
			<button id="DELETE" class="btn btn-danger">Delete</button>
		</c:if>
		<div class="container-fluid">
			<div class="col-md-12">
				<div class="pull-right">
					<span class="label label-warning">${model.ITEM}</span>
				</div>
				<h1>${model.TITLE}</h1>
				<p>${model.CONTENT}</p>
				<div class="pull-right">
					<span class="badge">Posted By ${model.NAME}</span>
				</div>
				<hr>
				<button id="List" class="btn btn-warning">List</button>
			</div>
		</div>
	</div>
</body>
</html>
