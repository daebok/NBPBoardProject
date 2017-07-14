<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
</head>
<body>
	<!-- header start -->
	<%@include file="common/header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="container-fluid">
			<div class="row">
				<%@include file="common/search.jsp"%>
				<!-- 로그인한 사용자만 글쓰기 버튼을 활성화 -->
				<%-- <c:if test="${sessionScope.ID != null}"> --%>
					<a href="<c:url value='/question.do'/>" id="QUESTION"
						class="btn btn-danger">Ask Question</a>
				<%-- </c:if> --%>
				<a href="<c:url value='/list.do'/>" id="LIST"
					class="btn btn-danger">Questions</a>
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-md-12">
				<c:forEach var="board" items="${list}">
					<h1>
						<a href="<c:url value='/answer.do?id=${board.BID}'/>" id="BID">${board.TITLE}</a>
					</h1>
					<p>${board.CONTENT}</p>
					<div>
						<span class="badge">Posted ${board.DATE}</span>
						<span class="badge">Posted By ${board.NAME}</span>
						<div class="pull-right">
							<span class="label label-warning">${board.ITEM}</span>
						</div>
					</div>
					<hr>
				</c:forEach>
			</div>
		</div>
	</div>

</body>
</html>
