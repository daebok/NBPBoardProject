<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
<title>Home</title>
</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>

	<div class="container" style="height:100%;">
		<div class="container-fluid" style="margin-bottom: 30px">
			<p> 신고 및 문의 사항은 아래 주소로 연락 바랍니다. </p>
			<a href="mailto:wikiki413@gmail.com" class="glyphicon glyphicon-send">&nbsp; wikiki413@gmail.com</a>
		</div>
		<div style="margin-bottom:20px; ">
			<a href="<c:url value='/board/contactUsRegist'/>" class="btn btn-default">문의하기</a>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">문의사항</div>
			<div class="list-group">
				<c:forEach var="notice" items="${noticeList}">
					<div class="list-group-item">
						<h6><a href="<c:url value='/board/notice?&noticeNo=${notice.noticeNo}'/>">${notice.noticeTitle}</a></h6>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>
					 