<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>My Questions</title>
</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>
	<div class="container">
		<div class="container-fluid">
			<%@include file="../../common/search/myquestions-search.jsp"%>
			<div class="row">
				<b>My Questions</b>&nbsp;
				<c:choose>
					<c:when test="${check eq 0 || check eq null}">
						<a href="<c:url value='/board/myquestions/answered'/>" class="btn btn-default btn-sm">Answered</a>
					</c:when>
					<c:when test="${check eq 1}">
						<a href="<c:url value='/board/myquestions'/>" class="btn btn-default btn-sm">All Questions</a>
					</c:when>
				</c:choose>
				<div class="pull-right">
					<a href="<c:url value='/board/myanswers'/>" class="btn btn-default btn-sm">My Answers</a>
					<a href="<c:url value='/board/answers/liked'/>" class="btn btn-default btn-sm">Liked Answers</a>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-md-12">
				<c:forEach var="board" items="${list}">
					<h4>
						<a href="${path}/board/question${pageMaker.makeQuery(pageMaker.cri.page)}&boardNo=${board.boardNo}&section=2" id="boardNo"><c:out value="${board.boardTitle}" escapeXml="false"></c:out></a>
					</h4>
					<div>
						<span class="badge" style="background-color:#ffffff; color:#8c8c8c">Posted <fmt:formatDate value="${board.boardDate}" pattern="yyyy/MM/dd"/></span>
						<div class="pull-right">
							<span class="label label-success">answer: <c:out value="${board.commentCount}"/></span>
							<span class="label label-primary">views: <c:out value="${board.boardViewCount}"/></span>
							<span class="label label-warning"><c:out value="${board.categoryItem}"/></span>
						</div>
					</div>
					<hr>
				</c:forEach>
			</div>

			<c:choose>
				<c:when test="${check eq 0 || check eq null}">
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
				</c:when>
				<c:otherwise>
					<div class="page-nation">
						<ul class="pagination pagination-large">
							<c:if test="${pageMaker.prev}">
								<li class="disabled"><span><a href="/board/myquestions/answered${pageMaker.makeSearch(pageMaker.startPage-1)}">&laquo;</a></span></li>
							</c:if>
		
							<c:forEach var="idx" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
								<li 
									<c:out value="${pageMaker.cri.page == idx? 'class=active' : '' }" />>
									<a href="/board/myquestions/answered${pageMaker.makeSearch(idx)}"><span>${idx}</span></a>
								</li>
							</c:forEach>
							<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
								<li class="disabled"><span><a href="/board/myquestions/answereds${pageMaker.makeSearch(pageMaker.endPage + 1)}">&raquo;</a></span></li>
							</c:if>
						</ul>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>
