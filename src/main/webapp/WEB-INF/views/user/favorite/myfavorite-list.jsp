<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<script >
/* 즐겨찾기 해제 */
 function bookMarkCheck(boardNo){
	 var result = confirm('즐겨찾기를  해제 하시겠습니까? \n기존에 저장한 메모도 삭제 됩니다.');
	if (result) {
		var data = "boardNo=" + boardNo;
		$("#bookmark-list-"+boardNo).remove();
		$(this).css('color','#888');
		$.ajax({
			type : 'GET',
			url : '/board/bookmark/uncheck',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data
		});
	} 
}
</script>
</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>
	
	<div class="container">
		<div class="container-fluid">
			<div class="row">
				<b>My Favorite</b>
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-md-12">
				<c:forEach var="board" items="${list}">
					<div id="bookmark-list-${board.boardNo}">
						<div id="book-mark" class="pull-right glyphicon glyphicon-check" style="font-size:25px; color:#FF3636;" onclick="bookMarkCheck('${board.boardNo}')"></div>
						<h4>
							<a href="${path}/board/myfavorite/memo${pageMaker.makeQuery(pageMaker.cri.page)}&boardNo=${board.boardNo}" id="boardNo"><c:out value="${board.boardTitle}" escapeXml="false"></c:out></a>
						</h4>
						<div>
							<span class="badge">Posted By ${board.userName}</span>
							<span class="badge" style="background-color:#ffffff; color:#8c8c8c">Posted <fmt:formatDate value="${board.boardDate}" pattern="yyyy/MM/dd"/></span>
							<div class="pull-right">
								<span class="label label-success">answer: ${board.commentCount}</span>
								<span class="label label-primary">views: ${board.boardViewCount}</span>
								<span class="label label-warning">${board.categoryItem}</span>
							</div>
						</div>
						<hr>
					</div>
				</c:forEach>
			</div>

			<div class="page-nation">
				<ul class="pagination pagination-large">
					<c:if test="${pageMaker.prev}">
						<li class="disabled"><span><a href="/board/myfavorite${pageMaker.makeQuery(pageMaker.startPage-1)}">&laquo;</a></span></li>
					</c:if>

					<c:forEach var="idx" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
						<li 
							<c:out value="${pageMaker.cri.page == idx? 'class=active' : '' }" />>
							<a href="/board/myfavorite${pageMaker.makeQuery(idx)}"><span>${idx}</span></a>
						</li>
					</c:forEach>
					<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
						<li class="disabled"><span><a href="/board/myfavorite${pageMaker.makeQuery(pageMaker.endPage + 1)}">&raquo;</a></span></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>
