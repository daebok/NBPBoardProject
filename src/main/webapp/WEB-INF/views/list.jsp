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
<script>
    // **원하는 페이지로 이동시 검색조건, 키워드 값을 유지하기 위해 
    function list(page){
        location.href="${path}/board/list.do?curPage="+page+"&searchOption=${map.searchOption}"+"&keyword=${map.keyword}";
    }
</script>
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

			</div>
		</div>
		<div class="container-fluid">
			<div class="col-md-12">
				<c:forEach var="row" items="${map.list}">
					<h1>
						<a href="${path}/board/answer.do?id=${row.BID}&curPage=${map.boardPager.curPage}&searchOption=${map.searchOption}&keyword=${map.keyword}" id="BID">${row.TITLE}</a>			
					</h1>
					<p>${row.CONTENT}</p>
					<div>
						<span class="badge">Posted ${row.DATE}</span>
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
			<!-- <div class="page-nation">
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
			</div> -->
			<!-- **처음페이지로 이동 : 현재 페이지가 1보다 크면  [처음]하이퍼링크를 화면에 출력-->
			<c:if test="${map.boardPager.curBlock > 1}">
				<a href="javascript:list('1')">[처음]</a>
			</c:if>

			<!-- **이전페이지 블록으로 이동 : 현재 페이지 블럭이 1보다 크면 [이전]하이퍼링크를 화면에 출력 -->
			<c:if test="${map.boardPager.curBlock > 1}">
				<a href="javascript:list('${map.boardPager.prevPage}')">[이전]</a>
			</c:if>

			<!-- **하나의 블럭에서 반복문 수행 시작페이지부터 끝페이지까지 -->
			<c:forEach var="num" begin="${map.boardPager.blockBegin}"
				end="${map.boardPager.blockEnd}">
				<!-- **현재페이지이면 하이퍼링크 제거 -->
				<c:choose>
					<c:when test="${num == map.boardPager.curPage}">
						<span style="color: red">${num}</span>&nbsp;
                        </c:when>
					<c:otherwise>
						<a href="javascript:list('${num}')">${num}</a>&nbsp;
                        </c:otherwise>
				</c:choose>
			</c:forEach>

			<!-- **다음페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음]하이퍼링크를 화면에 출력 -->
			<c:if test="${map.boardPager.curBlock <= map.boardPager.totBlock}">
				<a href="javascript:list('${map.boardPager.nextPage}')">[다음]</a>
			</c:if>

			<!-- **끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝]하이퍼링크를 화면에 출력 -->
			<c:if test="${map.boardPager.curPage <= map.boardPager.totPage}">
				<a href="javascript:list('${map.boardPager.totPage}')">[끝]</a>
			</c:if>
		</div>
	</div>

</body>
</html>
