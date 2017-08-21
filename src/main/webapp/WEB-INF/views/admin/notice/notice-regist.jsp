<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>

	<div class="container">
		<div class="col-sm-12">
			<form:form name="form" action="/admin/notice/regist" method="post" class="form-horizontal" id="category-add-form">
				<div class="form-group">
					<label for="title">Title</label>
					<input type="text" name="noticeTitle" maxlength="100" id="title" size="15" class="form-control" placeholder="제목을 입력하세요"/>
				</div>
				<div class="form-group">
						<textarea class="notice-summernote" name="noticeContent" id="content"></textarea>
					
				</div>
				<div class="pull-left">
					<button type="button" onclick="noticeRegist()" class="btn btn-default" style="float:left;">Add</button>
				</div>
			</form:form>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html> 

<script src="<c:url value="/resources/common/js/notice.js" />"></script>