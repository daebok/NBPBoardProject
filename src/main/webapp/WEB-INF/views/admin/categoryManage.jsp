<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>Home</title>
<script>
	$(document).on('click',".category-add-button", function() {
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
	$(document).on('click',".category-delete", function() {
		var categoryNo = $(this).val();
		var data = "categoryNo=" + categoryNo;
		$.ajax({
			type : 'GET',
			url : '/admin/categoryCount',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				var list = $.parseJSON(result);
				console.log(list);
				if(list > 0) {
					var result = confirm(list+ '개의 게시글이 있습니다. 그래도 카테고리를 삭제 하시겠습니까? \n(카테고리를 삭제해도 기존의 글은 남게 됩니다.)');
					if (result) {
						$('#form-'+categoryNo).action = "/admin/categoryDelete"
						$('#form-'+categoryNo).submit();
					} 
				} else {
					var result = confirm('카테고리를 삭제 하시겠습니까?');
					if (result) {
						$('#form-'+categoryNo).action = "/admin/categoryDelete"
						$('#form-'+categoryNo).submit();
					} 
				}
			}
		});
	});
</script>
<style>
	.category-list-title {
		text-align: center;
	}
	.list-group-item {
		overflow:hidden;
		width:50%;
	}
	.list-1{
		float:left;
		width:50%;
		text-align: center;
	}
	.list-2{
		float:left;
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
							<input type="text" name="categoryItem" id="category-item" placeholder="Category Item" 
																			class="form-control" style="width:70%; float:left;"/>
							<button type="button" class="category-add-button btn btn-default" style="float:left;">Add</button>
						</div>
					</div>
				</form>
			</div>
			<div class="list-group">
				<div class="list-group-item">
					<div class="list-1">
						<b>Category List</b>
					</div>
				</div>
				<c:forEach var="category" items="${categoryList}">
					<div class="list-group-item">
						<form:form name="${category.categoryNo}" method="get" action="/admin/categoryDelete" class="form-horizontal" id="form-${category.categoryNo}">
							<div class="list-1">
								<span>${category.categoryItem}</span>
							</div>
							<div class="list-2">
								<button type="button" class="category-delete btn btn-success"  value="${category.categoryNo}">Delete</button>
							</div>
							<input type="hidden" name="categoryNo" value="${category.categoryNo}"/>
						</form:form>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html> 