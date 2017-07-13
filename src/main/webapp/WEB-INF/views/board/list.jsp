<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<script>
	function list(page) {
		location.href = "${path}/board/list.do?curPage=" + page
				+ "&searchOption=${map.searchOption}"
				+ "&keyword=${map.keyword}";
	}
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="container-fluid">
			<div class="row">
				<form name="form1" method="post" action="${path}/board/list.do">
					<select name="searchOption">
						<option value="all"
							<c:out value="${map.searchOption == 'all'?'selected':''}"/>>제목+내용</option>
						<option value="CONTENT"
							<c:out value="${map.searchOption == 'CONTENT'?'selected':''}"/>>내용</option>
						<option value="TITLE"
							<c:out value="${map.searchOption == 'TITLE'?'selected':''}"/>>제목</option>
					</select> <input name="keyword" value="${map.keyword}"> <input
						type="submit" value="조회">
				</form>
				<c:if test="${sessionScope.ID != null}">
					<a href="<c:url value='/question.do'/>" id="QUESTION"
						class="btn btn-danger">Ask Question</a>
				</c:if>

			</div>
		</div>
		<div class="container-fluid">
			<div class="col-md-12">
				<c:forEach var="board" items="${map.list}">
					<h1>
						<a
							href="${path}/board/answer.do?id=${board.BID}&curPage=${map.boardPager.curPage}&searchOption=${map.searchOption}&keyword=${map.keyword}"
							id="BID">${board.TITLE}</a>
					</h1>
					<p>${board.CONTENT}</p>
					<div>
						<span class="badge">Posted ${board.DATE}</span>
						<div class="pull-right">
							<span class="label label-warning">${board.ITEM}</span>
						</div>
					</div>
					<hr>
				</c:forEach>
			</div>

			<div class="page-nation">
				<ul class="pagination pagination-large">
					<c:if test="${map.boardPager.curBlock > 1}">
						<li class="disabled"><span><a href="javascript:list('1')">[처음]</a></span></li>
					</c:if>

					<c:if test="${map.boardPager.curBlock > 1}">
						<li class="disabled"><span><a href="javascript:list('${map.boardPager.prevPage}')">[이전]</a></span></li>
					</c:if>

					<c:forEach var="num" begin="${map.boardPager.blockBegin}" end="${map.boardPager.blockEnd}">
						<c:choose>
							<c:when test="${num == map.boardPager.curPage}">
								<li class="active"><span>${num}</span></li>
							</c:when>
							<c:otherwise>
								<li><span><a href="javascript:list('${num}')">${num}</a></span></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<c:if test="${map.boardPager.curBlock <= map.boardPager.totBlock}">
						<a href="javascript:list('${map.boardPager.nextPage}')">[다음]</a>
					</c:if>

					<c:if test="${map.boardPager.curPage <= map.boardPager.totPage}">
						<li><a href="javascript:list('${map.boardPager.totPage}')">[끝]</a></li>
					</c:if>
				</ul>

			</div>
		</div>
	</div>
</body>
</html>
