<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<style type="text/css">
<!-- 말 줄임 -->
#content-summary {
	display: -webkit-box;
	display: -ms-flexbox;
	display: box;
	margin-top: 1px;
	max-height: 80px;
	overflow: hidden;
	vertical-align: top;
	text-overflow: ellipsis;
	word-break: break-all;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 3
}
.notice-title{
	background-color: #337ab7;
	color: white;
	padding: 10px;
}
.notice-content {
	color: #337ab7;
}
</style>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>
	
	<div class="container">
		<div class="container-fluid" >
			<div class="pull-right" style="height:100%; width:22%">
				<div class="notice-title">Notice</div>
				<div style="border: 3px solid #337ab7; padding:10px;">
					<c:forEach var="notice" items="${notice}">
					<h6>
						<span class="glyphicon glyphicon-bullhorn" style="color:#777;"></span>
						<a href="${path}/board/notice${pageMaker.makeSearch(pageMaker.cri.page)}&noticeNo=${notice.noticeNo}" class="notice-content">${notice.noticeTitle}</a>
					</h6>
					</c:forEach>
				</div>
			</div>
			<%@include file="../common/search.jsp"%>
			<b>All Questions</b>
			<div class="col-md-9">
				<c:if test="${empty list}">
					<div>검색 결과 없음</div>
				</c:if>
				<c:forEach var="board" items="${list}">
					<h4>
						<a href="${path}/board/answer${pageMaker.makeSearch(pageMaker.cri.page)}&boardNo=${board.boardNo}" id="boardNo">${board.boardTitle}</a>
					</h4>
					<p id="content-summary">${board.boardContentSummary}</p>
					<div>
						<span class="badge">Posted By ${board.userName}</span> 
						<span class="badge" style="background-color: #ffffff; color: #8c8c8c">Posted <fmt:formatDate value="${board.boardDate}" pattern="yyyy/MM/dd"/></span>
						<div class="pull-right">
							<span class="label label-success">answer:${board.commentCount}</span> 
							<span class="label label-primary">views:${board.boardViewCount}</span> 
							<span class="label label-warning">${board.categoryItem}</span>
							<span style="font-size:12px;" class=""> Download
								<span class="badge" style="font-size:12px;">${board.fileCount}</span>
							</span>
						</div>
					</div>
					<hr>
				</c:forEach>
				
			<div class="page-nation">
				<ul class="pagination pagination-large">
					<c:if test="${pageMaker.prev}">
						<li class="disabled"><span><a
								href="/board/list${pageMaker.makeSearch(pageMaker.startPage-1)}">&laquo;</a></span></li>
					</c:if>

					<c:forEach var="idx" begin="${pageMaker.startPage}"
						end="${pageMaker.endPage}">
						<li
							<c:out value="${pageMaker.cri.page == idx? 'class=active' : '' }" />>
							<a href="/board/list${pageMaker.makeSearch(idx)}"><span>${idx}</span></a>
						</li>
					</c:forEach>
					<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
						<li class="disabled"><span><a
								href="/board/list${pageMaker.makeSearch(pageMaker.endPage + 1)}">&raquo;</a></span></li>
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
