<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>
	
	<div class="container" style="height:100%;">
		<div class="container-fluid" >
			<div class="col-md-12">
				<h2><c:out value="${model.noticeTitle}" escapeXml="true"/></h2>
				<p><c:out value="${model.noticeContent}" escapeXml="false"/></p>
				
				<hr>
				<div class="pull-right">
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<a href="<c:url value='/admin/notice/modifyPage?noticeNo=${model.noticeNo}'/>" id="modify" class="btn btn-primary">Modify</a>
						<button onclick="noticeDelete(${model.noticeNo})" class="btn btn-primary">Delete</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</div>
		
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>

<script src="<c:url value="/resources/common/js/notice.js" />"></script>
