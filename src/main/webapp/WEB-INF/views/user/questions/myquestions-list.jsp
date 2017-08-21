<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>
	<div class="container">
		<div class="container-fluid">
			<c:if test="${check eq 0 || check eq null}">
				<%@include file="../../common/search/myquestions-search.jsp"%>
			</c:if>
			<div class="row">
				<c:choose>
					<c:when test="${check eq 0 || check eq null}">
						<b>My Questions</b>&nbsp;
						<a href="<c:url value='/board/myquestions/answered'/>" class="btn btn-default btn-sm">Answered</a>
					</c:when>
					<c:when test="${check eq 1}">
						<b>My Questions (Answered)</b>&nbsp;
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
			<div class="col-md-12" style="margin-top:20px;">
				<c:forEach var="board" items="${list}">
					<h4>
						<a href="${path}/board/question${pageMaker.makeQuery(pageMaker.criteria.page)}&boardNo=${board.boardNo}" id="boardNo">
							<c:out value="${board.boardTitle}" escapeXml="true"/>
						</a>
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
					<custom:search-paging uri="/board/myquestions" />
				</c:when>
				<c:otherwise>
					<custom:search-paging uri="/board/myquestions/answered" />
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>
