<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>Notice</title>
<script type="text/javascript">
$(document).on('click','#delete',function() {
	var result = confirm('공지사항을 삭제하시겠습니까?');
	if (result) {
		location.replace('/admin/notice/delete?noticeNo=${model.noticeNo}');
	} 
});
</script>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>
	
	<div class="container">
		<div class="container-fluid">
			<div class="col-md-12">
				<h3>${model.noticeTitle}</h3>
				<p>${model.noticeContent}</p>
				<hr>
				<div class="pull-right">
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<a href="<c:url value='/admin/notice/modifyPage?noticeNo=${model.noticeNo}'/>" id="modify" class="btn btn-primary">Modify</a>
						<button id="delete" class="btn btn-primary">Delete</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</div>
		
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html>
