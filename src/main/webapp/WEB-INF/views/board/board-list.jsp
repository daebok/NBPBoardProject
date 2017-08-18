<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
</head>	
<body>
	<!-- loading -->
	<%@include file="../common/loading.jsp"%>
	
	<!-- header -->
	<%@include file="../common/header.jsp"%>
	
	<div class="container">
		<!-- Notice-->
		<%@include file="board-notice.jsp"%>
		
		<!-- Search-->
		<%@include file="../common/search/board-search.jsp"%>

		<!-- 게시글 리스트 -->
		<div class="col-md-8" style="margin:20px 0;">
			<b>All Questions (${boardCount} 개)</b>
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
			<div class="pull-right">
				<form name="perPageNum-list">
					<select name="perPageNum" class="form-control" style="width:100px;" onchange="perPageNumSelect()">
						<option value="5" 	<c:out value="${pageMaker.cri.perPageNum eq 5 ?'selected':''}"/>>5개</option>
						<option value="10" 	<c:out value="${pageMaker.cri.perPageNum eq 10 ?'selected':''}"/>>10개</option>
						<option value="15"	<c:out value="${pageMaker.cri.perPageNum eq 15 ?'selected':''}"/>>15개</option>
						<option value="20"	<c:out value="${pageMaker.cri.perPageNum eq 20 ?'selected':''}"/>>20개</option>
						<option value="30"	<c:out value="${pageMaker.cri.perPageNum eq 30 ?'selected':''}"/>>30개</option>
						<option value="40"	<c:out value="${pageMaker.cri.perPageNum eq 40 ?'selected':''}"/>>40개</option>
						<option value="50"	<c:out value="${pageMaker.cri.perPageNum eq 50 ?'selected':''}"/>>50개</option>
					</select>
				</form>
			</div>
			<div class="board-list-wrapper">
				<hr>
				<c:if test="${empty list}">
					<div>검색 결과 없음</div>
				</c:if>
				<c:forEach var="board" items="${list}">
					<h4>
						<a href="${path}/board/question${pageMaker.makeSearch(pageMaker.cri.page)}&boardNo=${board.boardNo}" id="boardNo">
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

<link href="<c:url value='/resources/common/css/board.css'/>"  		type="text/css" rel="stylesheet" >
<script src="<c:url value="/resources/common/js/board.js" />">		</script>
