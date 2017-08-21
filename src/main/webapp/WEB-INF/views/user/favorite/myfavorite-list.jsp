<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>
	
	<div class="container">
		<div class="container-fluid">
			<div class="row">
				<b>My Favorite</b>
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-md-12">
				<c:forEach var="board" items="${list}">
					<div id="bookmark-list-${board.boardNo}">
						<div id="book-mark" class="pull-right glyphicon glyphicon-check" style="font-size:25px; color:#FF3636;" onclick="bookMarkUnCheck('${board.boardNo}')"></div>
						<h4>
							<a href="${path}/board/myfavorite/memo${pageMaker.makeQuery(pageMaker.criteria.page)}&boardNo=${board.boardNo}" id="boardNo"><c:out value="${board.boardTitle}" escapeXml="false"></c:out></a>
						</h4>
						<!-- board list -->
						<%@include file="../../board/board-list-form.jsp"%>
						<hr>
					</div>
				</c:forEach>
			</div>
			<custom:paging uri="/board/myfavorite" />
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>
<script src="<c:url value="/resources/common/js/bookmark.js" />"></script>
