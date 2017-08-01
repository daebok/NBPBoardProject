<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<style type="text/css">
	#content{ 
		display: -webkit-box; 
		display: -ms-flexbox; 
		display: box; 
		margin-top:1px; 
		max-height:80px; 
		overflow:hidden; 
		vertical-align:top; 
		text-overflow: ellipsis; 
		word-break:break-all; 
		-webkit-box-orient:vertical; 
		-webkit-line-clamp:3

	}
</style>
</head>
<body>
	<%@include file="../common/header.jsp"%>
	<div class="container">
		<div class="container-fluid">
			<div class="row">
				<b>My Favorite</b>
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-md-12">
				<c:forEach var="board" items="${list}">
					<h4>
						<a href="${path}/board/myfavorite/memo${pageMaker.makeSearch(pageMaker.cri.page)}&boardNo=${board.boardNo}" id="boardNo">${board.boardTitle}</a>
					</h4>
					<div>
						<span class="badge">Posted By ${board.userName}</span>
						<span class="badge" style="background-color:#ffffff; color:#8c8c8c">Posted ${board.boardDate}</span>
						<div class="pull-right">
							<span class="label label-success">answer: ${board.commentCount}</span>
							<span class="label label-primary">views: ${board.boardViewCount}</span>
							<span class="label label-warning">${board.categoryItem}</span>
						</div>
					</div>
					<hr>
				</c:forEach>
			</div>

			<div class="page-nation">
				<ul class="pagination pagination-large">
					<c:if test="${pageMaker.prev}">
						<li class="disabled"><span><a href="/board/myquestions${pageMaker.makeSearch(pageMaker.startPage-1)}">&laquo;</a></span></li>
					</c:if>

					<c:forEach var="idx" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
						<li 
							<c:out value="${pageMaker.cri.page == idx? 'class=active' : '' }" />>
							<a href="/board/myquestions${pageMaker.makeSearch(idx)}"><span>${idx}</span></a>
						</li>
					</c:forEach>
					<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
						<li class="disabled"><span><a href="/board/myquestions${pageMaker.makeSearch(pageMaker.endPage + 1)}">&raquo;</a></span></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
