<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
</head>
<body>
	<div class="container">
		<div class="container-fluid">
			<div class="col-md-12">
				<c:forEach var="board" items="${model}">
					<h4>
						<a href="<c:url value='/board/question?boardNo=${board.boardNo}'/>" id="boardNo"><html:unescape>${board.boardTitle}</html:unescape></a>
					</h4>
					<div>
						<span class="badge">Posted By ${board.userName}</span>
						<span class="badge" style="background-color:#ffffff; color:#8c8c8c">Posted <fmt:formatDate value="${board.boardDate}" pattern="yyyy/MM/dd"/></span>
						<div class="pull-right">
							<span class="label label-success">answers: ${board.commentCount}</span>
							<span class="label label-primary">views: ${board.boardViewCount}</span>
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
