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
	var result = confirm('문의사항을 삭제하시겠습니까?');
	if (result) {
		location.replace('/contact/delete?contactNo=${model.contactNo}');
	} 
});
</script>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>
	
	<div class="container" style="height:100%;">
		<div class="container-fluid">
			<div class="col-md-12">
				<h3><html:unescape>${model.contactTitle}</html:unescape></h3>
				<p><html:unescape>${model.contactContent}</html:unescape></p>
				<hr>
				<div class="pull-right">
					<c:if test="${user.userNo == model.userNo}">
						<button id="delete" class="btn btn-primary">Delete</button>
					</c:if>
				</div>
			</div>
		</div>
	</div>
		
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html>
