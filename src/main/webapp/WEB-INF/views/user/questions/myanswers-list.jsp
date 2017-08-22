<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
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
				<c:choose>
					<c:when test="${like eq 0 || like eq null}">
						<b>My Answers</b>
					</c:when>
					<c:otherwise>
						<b>Liked Answers</b>
					</c:otherwise>
				</c:choose>
				&nbsp;
				<a href="<c:url value='/board/myquestions'/>" class="btn btn-default btn-sm">All Questions</a>
				<a href="<c:url value='/board/myquestions/answered'/>" class="btn btn-default btn-sm">Answered</a>
				<div class="pull-right">
					<c:choose>
						<c:when test="${like eq 0 || like eq null}">
							<a href="<c:url value='/board/myanswers/'/>" class="btn btn-warning btn-sm">My Answers</a>
							<a href="<c:url value='/board/answers/liked'/>" class="btn btn-default btn-sm">Liked Answers</a>
						</c:when>
						<c:otherwise>
							<a href="<c:url value='/board/myanswers/'/>" class="btn btn-default btn-sm">My Answers</a>
							<a href="<c:url value='/board/answers/liked'/>" class="btn btn-warning btn-sm">Liked Answers</a>
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
							<button type="submit" class="btn btn-primary btn-sm">View Question </button>
						</form:form>
					</div>
					<div class="row">
						<c:out value="${comment.commentContent}" escapeXml="false" />
					</div>
					<span class="badge" style="background-color:#ffffff; color:#8c8c8c">Posted <fmt:formatDate value="${comment.commentDate}" pattern="yyyy/MM/dd"/></span>
					<hr>
				</c:forEach>
			</div>
			<c:choose>
				<c:when test="${like eq 0 || like eq null}">
					<custom:paging uri="/board/myanswers" />
				</c:when>
				<c:otherwise>
					<custom:paging uri="/board/answers/liked" />
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>
