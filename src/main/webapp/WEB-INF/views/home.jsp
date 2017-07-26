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
				<%@include file="common/search.jsp"%>
				<b>★ Top 10</b>
			<div class="col-md-12">
				<hr>
				<c:forEach var="board" items="${model}">
					<h1>
						<a href="<c:url value='/board/answer?boardNo=${board.boardNo}'/>" id="boardNo">${board.boardTitle}</a>
					</h1>
					<p>${board.boardContent}</p>
					<div>
						<span class="badge">Posted By ${board.userName}</span>
						<span class="badge" style="background-color:#ffffff; color:#8c8c8c">Posted ${board.boardDate}</span>
						<div class="pull-right">
							<span class="label label-success">댓글: ${board.commentCount}</span>
							<span class="label label-primary">조회수: ${board.boardViewCount}</span>
							<span class="label label-warning">${board.categoryItem}</span>
						</div>
					</div>
					<hr>
				</c:forEach>
			</div>
		</div>
	</div>

</body>
</html>
