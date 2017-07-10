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

	<h1>BOARD</h1>
	<c:if test="${sessionScope.ID != null}">
		<h5>${sessionScope.NAME}(${sessionScope.ID})님환영합니다.</h5>
	</c:if>
	<div class="container">
		<!-- /.row -->
		<div class="container-fluid">

			<div class="row">
				<c:choose>
					<c:when test="${sessionScope.ID == null}">
						<a href="<c:url value='/login.do'/>" id="LOGIN"
							class="btn btn-primary">LogIn</a>
					</c:when>
					<c:otherwise> ${sessionScope.NAME}님이 로그인중입니다.
					<a href="<c:url value='/logout.do'/>" id="LOGOUT"
							class="btn btn-primary">LogOut</a>
					</c:otherwise>
				</c:choose>
				<a href="<c:url value='/signup.do'/>" id="SIGNUP"
					class="btn btn-danger">SingUp</a>
			</div>
		</div>
		<hr />
		<div class="container-fluid">
			<div class="row">
				<form name="form1" method="get" action="${path}/board/search.do">
					<select name="searchOption">
						<!-- 검색조건을 검색처리후 결과화면에 보여주기위해  c:out 출력태그 사용, 삼항연산자 -->
						<option value="all"
							<c:out value="${map.searchOption == 'all'?'selected':''}"/>>제목+내용</option>
						<option value="CONTENT"
							<c:out value="${map.searchOption == 'CONTENT'?'selected':''}"/>>내용</option>
						<option value="TITLE"
							<c:out value="${map.searchOption == 'TITLE'?'selected':''}"/>>제목</option>
					</select> 
					<input name="keyword" value="${map.keyword}"> 
				</form>
				<a href="<c:url value='/question.do'/>" id="QUESTION"
					class="btn btn-danger">Ask Question</a>
			</div>
		</div>
		<!-- 레코드의 갯수를 출력 -->
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
	<div class="page-nation">
		<ul class="pagination pagination-large">
			<li class="disabled"><span>Prev</span></li>
			<li class="active"><span>1</span></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">6</a></li>
			<li><a href="#">7</a></li>
			<li><a href="#">8</a></li>
			<li><a href="#">9</a></li>
			<li class="disabled"><span>...</span></li>
			<li>
			<li><a rel="next" href="#">Next</a></li>

		</ul>
	</div>
</body>
</html>
