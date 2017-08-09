<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<style type="text/css">
#content-summary {
	line-height: 20px;
	width: 100%;
	text-overflow: ellipsis;
	overflow: hidden;
	-webkit-line-clamp: 3;
	-webkit-box-orient: vertical;
	display: -webkit-box;
	word-wrap: break-word;
}

.notice-title {
	background-color: #337ab7;
	color: white;
	padding: 10px;
}

.notice-content {
	color: #337ab7;
}
.notice-wrapper {
	height:100%; 
	width:250px;
	position: absolute;
	right: 120px;
}
</style>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>
	<div class="container">
		<div class="container-fluid" >
			<div class="notice-wrapper pull-right">
				<div class="notice-title">Notice</div>
				<div style="border: 3px solid #337ab7; padding:10px;">
					<c:forEach var="notice" items="${notice}">
					<h6>
						<span class="glyphicon glyphicon-bullhorn" style="color:#777;"></span>
						<a href="${path}/board/notice${pageMaker.makeSearch(pageMaker.cri.page)}&noticeNo=${notice.noticeNo}" class="notice-content"><html:unescape>${notice.noticeTitle}</html:unescape></a>
					</h6>
					</c:forEach>
				</div>
			</div>
			<!-- Search-->
			<%@include file="../common/search.jsp"%>
			<!-- Body -->
			<div class="col-md-9" style="margin-top:20px;">
				<b>All Questions</b>
					<c:if test="${pageMaker.cri.tab == 1 }">
						<a href="<c:url value='/board/list?tab=1'/>" class="tab-newest btn btn-warning btn-sm">Newest</a>
						<a href="<c:url value='/board/list?tab=2'/>" class="tab-views btn btn-default btn-sm">Views</a>
						<a href="<c:url value='/board/list?tab=3'/>" class="tab-answers btn btn-default btn-sm">Answers</a>
					</c:if>
					<c:if test="${pageMaker.cri.tab == 2 }">
						<a href="<c:url value='/board/list?tab=1'/>" class="tab-newest btn btn-default btn-sm">Newest</a>
						<a href="<c:url value='/board/list?tab=2'/>" class="tab-views btn btn-warning btn-sm">Views</a>
						<a href="<c:url value='/board/list?tab=3'/>" class="tab-answers btn btn-default btn-sm">Answers</a>
					</c:if>
					<c:if test="${pageMaker.cri.tab == 3 }">
						<a href="<c:url value='/board/list?tab=1'/>" class="tab-newest btn btn-default btn-sm">Newest</a>
						<a href="<c:url value='/board/list?tab=2'/>" class="tab-views btn btn-default btn-sm">Views</a>
						<a href="<c:url value='/board/list?tab=3'/>" class="tab-answers btn btn-warning btn-sm">Answers</a>
					</c:if>
				<c:if test="${empty list}">
					<div>검색 결과 없음</div>
				</c:if>
				<c:forEach var="board" items="${list}">
					<h4>
						<a href="${path}/board/question${pageMaker.makeSearch(pageMaker.cri.page)}&boardNo=${board.boardNo}&section=1" id="boardNo">
							<html:unescape>${board.boardTitle}</html:unescape>
						</a>
					</h4>
					<p id="content-summary">
						<c:out value="${board.boardContentSummary}" escapeXml="false"></c:out>
						
					</p>
					<div>
						<span class="badge">Posted By ${board.userName}</span> 
						<span class="badge" style="background-color: #ffffff; color: #8c8c8c">Posted <fmt:formatDate value="${board.boardDate}" pattern="yyyy/MM/dd"/></span>
						<div class="pull-right">
							<span class="label label-success">answer:<c:out value="${board.commentCount}"/></span> 
							<span class="label label-primary">views:<c:out value="${board.boardViewCount}"/></span> 
							<span class="label label-warning"><c:out value="${board.categoryItem}"/></span>
							<span style="font-size:12px;"> Attach
								<span class="badge" style="font-size:12px;"><c:out value="${board.fileCount}"/></span>
							</span>
						</div>
					</div>
					<hr>
				</c:forEach>
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
	</div>
	
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html>