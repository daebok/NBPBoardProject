<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>

	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">카테고리</div>
			<div class="panel-body">
				<form:form name="form" method="get" class="form-horizontal" id="category-add-form">
					<div class="form-group">
						<label for="id" class="col-sm-2 control-label"><b>Add</b></label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  name="categoryItem" id="category-item" placeholder="Category Item" style="width:70%; float:left;"/>
							<button type="button" class="btn btn-default" onclick="categoryRegist()" style="float:left;">Add</button>
						</div>
					</div>
				</form:form>
			</div>
			<div class="list-group">
				<div class="list-group-item">
					<div class="list-1">
						<b>Category List</b>
					</div>
				</div>
				<c:forEach var="category" items="${categoryList}">
					<div class="category-list-group-item">
						<form:form name="${category.categoryNo}" method="get" action="/admin/categoryDelete" class="form-horizontal" id="form-${category.categoryNo}">
							<div class="category-list-1">
								<span>${category.categoryItem}</span>
							</div>
							<div class="category-list-2">
								<button type="button" class="btn btn-success"  onclick="categoryDelete(${category.categoryNo})">Delete</button>
							</div>
							<input type="hidden" name="categoryNo" value="${category.categoryNo}"/>
						</form:form>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html> 

<link type="text/css" rel="stylesheet" 	href="<c:url value='/resources/common/css/category.css'/>">
<script src="<c:url value="/resources/common/js/category.js" />"> </script>