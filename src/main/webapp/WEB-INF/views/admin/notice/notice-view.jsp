<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>Contact Us</title>
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
	<%@include file="../../common/header.jsp"%>
	
	<div class="container" style="height:100%;">
		<div class="container-fluid" >
			<div class="col-md-12">
				<h3><html:unescape>${model.noticeTitle}</html:unescape></h3>
				<p><html:unescape>${model.noticeContent}</html:unescape></p>
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
	<%@include file="../../common/footer.jsp"%>
</body>
</html>
