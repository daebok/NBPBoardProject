<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
<style type="text/css">
.comment-comment-list {
	width: 530px;
	margin-left: 100px;
}
</style>
</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>

	<div class="container">
		<div class="container-fluid">
				<div class="pull-right" style="margin-bottom:10px;">
					<div id="book-mark" onclick="bookmarkCheck(${board.boardNo}, this)" class="glyphicon glyphicon-check" style="font-size:25px; color:#FF3636;"></div>
				</div>
				<div class="pull-left" style="margin-right:5px;">
					<form:form name="form" action="/board/question" method="get">
						<input type="hidden" name="boardNo" value="${board.boardNo}" /> 
						<input type="hidden" name="page" value="${criteria.page}" /> 
						<input type="hidden" name="perPageNum" value="${criteria.perPageNum}" />
						<button type="submit"  class="btn btn-primary">View Question </button>
					</form:form>
				</div>
				<div class="pull-left">
					<form:form name="form" action="/board/myfavorite" method="get">
						<input type="hidden" name="boardNo" value="${board.boardNo}" /> 
						<input type="hidden" name="page" value="${criteria.page}" /> 
						<input type="hidden" name="perPageNum" value="${criteria.perPageNum}" />
						<button type="submit" id="list" class="btn btn-primary">list</button>
					</form:form>
				</div>
			<div class="col-lg-9" style="margin-top: 50px;">
				<form:form name="form" class="memo-form">
					<input type="hidden" name="boardNo" value='${board.boardNo}'>
					<label for="content">Memo</label>
					<textarea class="memo-summernote memo-content" name="bookmarkMemo" >
						<c:out value="${memo.bookmarkMemo} " escapeXml="true"/>
					</textarea>
					<br />
					<div class="pull-right">
						<button class="memo-save-btn btn btn-default" onclick="bookmarkMemoRegist(event)">Save</button>
					</div>
				</form:form>
			</div>
			<div class="col-md-12">
				<hr>
				<span class="label label-warning">${board.categoryItem}</span>
				<h1> <c:out value="${board.boardTitle} " escapeXml="true"/></h1>
				<p> <c:out value="${board.boardContent} " escapeXml="false"/></p>
				<div>
					<c:if test="${not empty board.boardFileList}">
						<div class="panel panel-default">
							<div class="list-group">
								<div class="list-group-item">
									<div class="list-1"><b>파일명</b></div>
									<div class="list-2"><b>크기</b></div>
								</div>
								<c:forEach var="attach" items="${board.boardFileList}">
									<div class="uploadedList">
										<div class="list-group-item">
											<div class="list-1"><a href='/board/downloadFile?fileName=${attach.fileName}'>${attach.fileOriginName}</a></div>
											<div class="list-2"><file:size value="${attach.fileSize}" /> </div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:if>
					<div align="right" >
						<b><a class="board-writer"  href="javascript:otherQuestion(<user:id no='${board.userNo}'/>)"><user:id no="${board.userNo}"/>의 다른 게시물 보기<span class="glyphicon glyphicon-hand-right"></span></a></b>
					</div>
				</div>
				<hr>
			</div>
		</div>

		<!-- answer -->
		<%@include file="../../board/answer.jsp"%>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html>

<link href="<c:url value="/resources/common/css/answer.css" />" 	rel="stylesheet">
<link href="<c:url value="/resources/common/css/file.css" />" 		rel="stylesheet">
<link href="<c:url value="/resources/common/css/board.css" />" 		rel="stylesheet">
<script src="<c:url value="/resources/common/js/answer.js" />">		</script>
<script src="<c:url value="/resources/common/js/board.js" />">		</script>
<script src="<c:url value="/resources/common/js/bookmark.js" />">	</script>
