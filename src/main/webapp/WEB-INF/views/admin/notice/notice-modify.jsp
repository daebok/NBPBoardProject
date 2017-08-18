<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>

	<div class="container">
		<form:form name="form" action="/admin/notice/modify" method="post" class="form-horizontal" id="category-add-form">
			<input type="hidden" name="noticeNo" value="${model.noticeNo }"/>
			<div class="form-group">
				<label for="title">Title</label>
				<input type="text" name="noticeTitle" maxlength="100" id="title" size="20" class="form-control" value="${model.noticeTitle }"/>
			</div>
			<div class="form-group">
				<div class="col-sm-10">
					<textarea class="notice-summernote" name="noticeContent" id="content">
						<c:out value="${model.noticeContent }" escapeXml="false" />
					</textarea>
				</div>
			</div>
			<div class="pull-right">
				<button type="button" id="noticeButton" class="category-add-button btn btn-default" style="float:left;">Modify</button>
			</div>
		</form:form>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>

<script src="<c:url value="/resources/common/js/notice.js" />"></script>