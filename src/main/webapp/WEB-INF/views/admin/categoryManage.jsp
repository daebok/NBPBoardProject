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
<style>
	.list-group-item{
		overflow:hidden;
	}
	.list-1{
		float:left;
		width:50%;
	}
	.list-2{
		float:left;
	}
	.category-wrapper {
		position: absolute;
		left: 25%;
	}
</style>
</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">카테고리</div>
			<div class="panel-body">
				<form name="form" method="get" class="form-horizontal" id="category-add-form">
					<div class="form-group">
						<label for="id" class="col-sm-2 control-label"><b>Add</b></label>
						<div class="col-sm-10">
							<input type="text" name="categoryItem" id="category-item" placeholder="Category Item" class="form-control" />
							<div class="pull-right">
								<button type="button" id="category-add-button" class="btn btn-default">Add</button>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="list-group">
				<c:forEach var="category" items="${categoryList}">
					<div class="list-group-item">
						<form name="${category.categoryNo}" method="get" action="/admin/categoryDelete" class="form-horizontal" id="${category.categoryNo}">
							<div class="list-1">
								<input type="text" name="categoryItem" id="category-item" placeholder="Category Item" class="form-control" value="${category.categoryItem}"/>
							</div>
							<div class="list-2">
								<input type="submit" class="category-delete btn btn-success" value="Delete" />
							</div>
							<input type="hidden" name="categoryNo" value="${category.categoryNo}"/>
						</form>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html> 