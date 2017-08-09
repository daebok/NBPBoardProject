<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
<title>Home</title>
<script>
	$(document).on('click',".category-add-button", function() {
		var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?<>]/gi;
		var blank_pattern = /[\s]/g;
		
		var categoryItem = $("#category-item").val();
		if (blank_pattern.test(categoryItem) == true) {
			alert("카테고리 항목을 입력하세요.");
			$("#category-item").focus();
			return;
		}
		if (special_pattern.test(categoryItem) == true) {
			alert("카테고리에 특수문자를 입력 할 수 없습니다.");
			$("#category-item").focus();
			return;
		}
		var result = confirm('카테고리를 추가하시겠습니까?');
		if (result) {
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			$.ajax({
				type: 'POST', 
				url: '/admin/categoryCheck', 
				dataType : 'text',
				beforeSend : function(xhr){
					xhr.setRequestHeader(header, token);
				},
				data: $('#category-add-form').serialize(),
				success: function (result) {
					var list = $.parseJSON(result);
					if(parseInt(list) > 0) {
						alert("카테고리가 이미 존재합니다.");
						$("#category-item").focus();
						return;
					} else {
						document.form.action = "/admin/categoryAdd"
						document.form.submit();
					}
				}
			});
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
							<input type="text" name="categoryItem" id="category-item" placeholder="Category Item" class="form-control" style="width:70%; float:left;"/>
							<button type="button" class="category-add-button btn btn-default" style="float:left;">Add</button>
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
								<button type="button" class="category-delete btn btn-success"  value="${category.categoryNo}">Delete</button>
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