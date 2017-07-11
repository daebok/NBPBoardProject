<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BOARD</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value='/css/bootstrap.min.css'/>">
<script src="http://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script type="text/script" src="<c:url value='/js/bootstrap.min.js'/>"></script>

</head>
<body>
	<!-- header start -->
	<%@include file="header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="container-fluid">
			<div class="row">
				<form name="form1" method="post" action="${path}/board/list.do">
					<select name="searchOption">
						<!-- 검색조건을 검색처리후 결과화면에 보여주기위해  c:out 출력태그 사용, 삼항연산자 -->
						<option value="all"
							<c:out value="${map.searchOption == 'all'?'selected':''}"/>>제목+내용</option>
						<option value="CONTENT"
							<c:out value="${map.searchOption == 'CONTENT'?'selected':''}"/>>내용</option>
						<option value="TITLE"
							<c:out value="${map.searchOption == 'TITLE'?'selected':''}"/>>제목</option>
					</select> <input name="keyword" value="${map.keyword}"> <input
						type="submit" value="조회">
				</form>
				<!-- 로그인한 사용자만 글쓰기 버튼을 활성화 -->
				<c:if test="${sessionScope.ID != null}">
					<a href="<c:url value='/question.do'/>" id="QUESTION"
						class="btn btn-danger">Ask Question</a>
				</c:if>
				<a href="<c:url value='/list.do'/>" id="LIST"
					class="btn btn-danger">Questions</a>
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-md-12">
				<c:forEach var="question" items="${list}">
					<h1>
						<a href="<c:url value='/answer.do?id=${question.BID}'/>" id="BID">${question.TITLE}</a>
					</h1>
					<p>${question.CONTENT}</p>
					<div>
						<span class="badge">Posted ${question.DATE}</span>
						<div class="pull-right">
							<span class="label label-default">alice</span> <span
								class="label label-primary">story</span> <span
								class="label label-success">blog</span> <span
								class="label label-info">personal</span> <span
								class="label label-warning">Warning</span> <span
								class="label label-danger">Danger</span>
						</div>
					</div>
					<hr>
				</c:forEach>
			</div>
		</div>
	</div>

</body>
</html>
