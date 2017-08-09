<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>Notice</title>

</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>

	<div class="container" >
		<div style="margin-bottom:20px; ">
			<a href="<c:url value='/admin/noticeRegist'/>" class="btn btn-default">New Notice</a>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Notice</div>
			<div class="list-group">
				<c:forEach var="notice" items="${noticeList}">
					<div class="list-group-item">
						<h6><a href="<c:url value='/board/notice?&noticeNo=${notice.noticeNo}'/>"><c:out value="${notice.noticeTitle}" escapeXml="false"/></a></h6>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html> 