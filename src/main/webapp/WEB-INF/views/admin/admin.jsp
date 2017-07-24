<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>Home</title>
<script>
	$(document).ready(function() {
		$("#category-add-button").click(function() {
			var userId = $("#categoryItem").val();
			if (userId == "") {
				alert("카테고리 항목을 입력하세요.");
				$("#category-item").focus();
				return;
			}
			var result = confirm('카테고리를 추가하시겠습니까?');
			if (result) {
				document.form.action = "/admin/categoryAdd"
				document.form.submit();
			} 
		});
	});
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="container-fluid">
				<form name="form" method="get" class="form-horizontal" id="category-add-form">
					<div class="form-group">
						<label for="id" class="col-sm-2 control-label"><b>Category Add</b></label>
						<div class="col-sm-10">
							<input type="text" name="categoryItem" id="category-item" placeholder="Category Item" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" id="category-add-button" class="btn btn-default">Add</button>
						</div>
					</div>
				</form>
		</div>
		<div class="container-fluid">
			<c:forEach var="category" items="${categoryList}">
				<form name="${category.categoryNo}" method="get" action="/admin/categoryDelete" class="form-horizontal" id="${category.categoryNo}">
					<div class="form-group">
						<div class="col-sm-8">
							<input type="text" name="categoryItem" id="category-item" placeholder="Category Item" class="form-control" value="${category.categoryItem}"/>
							<input type="hidden" name="categoryNo" value="${category.categoryNo}"/>
							<input type="submit" class="category-delete btn btn-success" value="Delete" />
						</div>
					</div>
				</form>
			</c:forEach>
		</div>
	</div>
</body>
</html> 