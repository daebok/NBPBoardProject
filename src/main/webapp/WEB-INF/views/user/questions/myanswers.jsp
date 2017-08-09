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
			<div class="row">
				<b>My Answers</b>&nbsp;
				<c:choose>
					<c:when test="${check eq 0 || check eq null}">
						<a href="<c:url value='/board/myquestions/answered'/>" class="btn btn-default btn-sm">Answered</a>
					</c:when>
					<c:otherwise>
						<a href="<c:url value='/board/myquestions'/>" class="btn btn-default btn-sm">All Questions</a>
					</c:otherwise>
				</c:choose>
				<div class="pull-right">
					<c:choose>
						<c:when test="${like eq 0 || like eq null}">
							<a href="<c:url value='/board/answers/liked'/>" class="btn btn-default btn-sm">Liked Answers</a>
						</c:when>
						<c:otherwise>
							<a href="<c:url value='/board/myanswers/'/>" class="btn btn-default btn-sm">My Answers</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-md-12" style="margin-top:20px;">
				<c:forEach var="comment" items="${list}">
					<div>
						<form:form name="form" action="/board/question" method="get">
							<input type="hidden" name="boardNo" value="${comment.boardNo}" /> 
							<input type="hidden" name="section" value="3" /> 
							<button type="submit" class="btn btn-primary btn-sm">View Question </button>
						</form:form>
					</div>
					<div class="row">
						<c:out value="${comment.commentContent}" escapeXml="false"></c:out>
					</div>
					<span class="badge" style="background-color:#ffffff; color:#8c8c8c">Posted <fmt:formatDate value="${comment.commentDate}" pattern="yyyy/MM/dd"/></span>
					<hr>
				</c:forEach>
			</div>

			<div class="page-nation">
				<ul class="pagination pagination-large">
					<c:if test="${pageMaker.prev}">
						<li class="disabled"><span><a href="/board/myanswers${pageMaker.makeQuery(pageMaker.startPage-1)}">&laquo;</a></span></li>
					</c:if>

					<c:forEach var="idx" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
						<li 
							<c:out value="${pageMaker.cri.page == idx? 'class=active' : '' }" />>
							<a href="/board/myanswers${pageMaker.makeQuery(idx)}"><span>${idx}</span></a>
						</li>
					</c:forEach>
					<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
						<li class="disabled"><span><a href="/board/myanswers${pageMaker.makeQuery(pageMaker.endPage + 1)}">&raquo;</a></span></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>
