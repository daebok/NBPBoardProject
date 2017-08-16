<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
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
							<a href="${path}/board/myfavorite/memo${pageMaker.makeQuery(pageMaker.cri.page)}&boardNo=${board.boardNo}" id="boardNo"><c:out value="${board.boardTitle}" escapeXml="false"></c:out></a>
						</h4>
						<!-- board list -->
						<%@include file="../../board/board-list-form.jsp"%>
						<hr>
					</div>
				</c:forEach>
			</div>

			<div class="page-nation">
				<ul class="pagination pagination-large">
					<c:if test="${pageMaker.prev}">
						<li class="disabled"><span><a href="/board/myfavorite${pageMaker.makeQuery(pageMaker.startPage-1)}">&laquo;</a></span></li>
					</c:if>

					<c:forEach var="idx" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
						<li 
							<c:out value="${pageMaker.cri.page == idx? 'class=active' : '' }" />>
							<a href="/board/myfavorite${pageMaker.makeQuery(idx)}"><span>${idx}</span></a>
						</li>
					</c:forEach>
					<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
						<li class="disabled"><span><a href="/board/myfavorite${pageMaker.makeQuery(pageMaker.endPage + 1)}">&raquo;</a></span></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>
<script src="<c:url value="/resources/common/js/bookmark.js" />"></script>
