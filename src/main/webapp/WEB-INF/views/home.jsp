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
					<a href="<c:url value='/board/question'/>" id="question"
						class="btn btn-danger">Ask Question</a>
				<a href="<c:url value='/board/list'/>" id="list"
					class="btn btn-danger">Questions</a>
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-md-12">
				<h1>Top 50</h1>
				<c:forEach var="board" items="${model}">
					<h1>
						<a href="<c:url value='/board/answer?boardId=${board.boardId}'/>" id="boardId">${board.title}</a>
					</h1>
					
					<p>${board.content}</p>
					<div>
						<span class="badge">Posted ${board.date}</span>
						<span class="badge">Posted By ${board.name}</span>
						<div class="pull-right">
							<span class="label label-primary">조회수: ${board.viewCount}</span>
							<span class="label label-warning">${board.item}</span>
						</div>
					</div>
					<hr>
				</c:forEach>
			</div>
		</div>
	</div>

</body>
</html>
