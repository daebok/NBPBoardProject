<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<body>
	<div class="container" >
		<c:forEach var="board" items="${model}">
			<h4>
				<a href="<c:url value='/board/question?boardNo=${board.boardNo}'/>" id="boardNo">
					<c:out value="${board.boardTitle}" escapeXml="true" />
				</a>
			</h4>
			<!-- board list -->
			<%@include file="board/board-list-form.jsp"%>
			<hr>
		</c:forEach>
	</div>
</body>
</html>
