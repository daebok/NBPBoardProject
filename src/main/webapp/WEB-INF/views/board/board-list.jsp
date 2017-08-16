<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>
	<!-- 본문 -->
	<div class="container">
			
		<!-- Notice-->
		<%@include file="board-notice.jsp"%>
		
		<!-- Search-->
		<%@include file="../common/search/board-search.jsp"%>

		<!-- 게시글 리스트 -->
		<div class="col-md-8" style="margin:20px 0;">
			<b>All Questions</b>
			<c:if test="${pageMaker.cri.tab == 1 }">
				<a href="<c:url value='/board/list?tab=1'/>" class="btn btn-warning btn-sm">Newest</a>
				<a href="<c:url value='/board/list?tab=2'/>" class="btn btn-default btn-sm">Views</a>
				<a href="<c:url value='/board/list?tab=3'/>" class="btn btn-default btn-sm">Answers</a>
			</c:if>
			<c:if test="${pageMaker.cri.tab == 2 }">
				<a href="<c:url value='/board/list?tab=1'/>" class="btn btn-default btn-sm">Newest</a>
				<a href="<c:url value='/board/list?tab=2'/>" class="btn btn-warning btn-sm">Views</a>
				<a href="<c:url value='/board/list?tab=3'/>" class="btn btn-default btn-sm">Answers</a>
			</c:if>
			<c:if test="${pageMaker.cri.tab == 3 }">
				<a href="<c:url value='/board/list?tab=1'/>" class="btn btn-default btn-sm">Newest</a>
				<a href="<c:url value='/board/list?tab=2'/>" class="btn btn-default btn-sm">Views</a>
				<a href="<c:url value='/board/list?tab=3'/>" class="btn btn-warning btn-sm">Answers</a>
			</c:if>
			<div class="board-list-wrapper">
				<hr>
				<c:if test="${empty list}">
					<div>검색 결과 없음</div>
				</c:if>
				<c:forEach var="board" items="${list}">
					<h4>
						<a href="${path}/board/question${pageMaker.makeSearch(pageMaker.cri.page)}&boardNo=${board.boardNo}&section=1" id="boardNo">
							<c:out value="${board.boardTitle}" escapeXml="true"/>
						</a>
					</h4>
					<p id="content-summary">
						<c:out value="${board.boardContentSummary}" escapeXml="false" />
					</p>
					<!-- board list -->
					<%@include file="board-list-form.jsp"%>
					<hr>
				</c:forEach>
			</div>
			<div class="page-nation">					
				<ul class="pagination pagination-large">
					<c:if test="${pageMaker.prev}">
						<li class="disabled"><span><a href="/board/list${pageMaker.makeSearch(pageMaker.startPage-1)}">&laquo;</a></span></li>
					</c:if>
					<c:forEach var="idx" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
						<li <c:out value="${pageMaker.cri.page == idx? 'class=active' : '' }" />>
							<a href="/board/list${pageMaker.makeSearch(idx)}"><span>${idx}</span></a>
						</li>
					</c:forEach>
					<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
						<li class="disabled"><span><a href="/board/list${pageMaker.makeSearch(pageMaker.endPage + 1)}">&raquo;</a></span></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
			
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html>
<link type="text/css" rel="stylesheet" 	href="<c:url value='/resources/common/css/board.css'/>">
