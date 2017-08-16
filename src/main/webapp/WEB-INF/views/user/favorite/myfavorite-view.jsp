<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
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
					<div id="book-mark" onclick="bookmarkCheck(${model.boardNo}, this)" class="glyphicon glyphicon-check" style="font-size:25px; color:#FF3636;"></div>
				</div>
				<div class="pull-left" style="margin-right:5px;">
					<form:form name="form" action="/board/question" method="get">
						<input type="hidden" name="boardNo" value="${model.boardNo}" /> 
						<input type="hidden" name="page" value="${cri.page}" /> 
						<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
						<input type="hidden" name="searchType" value="${cri.searchType}" /> 
						<input type="hidden" name="categoryType" value="${cri.categoryType}" />
						<input type="hidden" name="keyword" value="${cri.keyword}" />
						<button type="submit"  class="btn btn-primary">View Question </button>
					</form:form>
				</div>
				<div class="pull-left">
					<form:form name="form" action="/board/myfavorite" method="get">
						<input type="hidden" name="boardNo" value="${model.boardNo}" /> 
						<input type="hidden" name="page" value="${cri.page}" /> 
						<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
						<input type="hidden" name="searchType" value="${cri.searchType}" /> 
						<input type="hidden" name="categoryType" value="${cri.categoryType}" />
						<input type="hidden" name="keyword" value="${cri.keyword}" />
						<button type="submit" id="list" class="btn btn-primary">list</button>
					</form:form>
				</div>
			<div class="col-lg-9" style="margin-top: 50px;">
				<form:form name="form" class="memo-form">
					<input type="hidden" name="boardNo" value='${model.boardNo}'>
					<label for="content">Memo</label>
					<textarea class="memo-summernote memo-content" name="bookmarkMemo" ><html:unescape>${memo.bookmarkMemo} </html:unescape></textarea>
					<br />
					<div class="pull-right">
						<button class="btn btn-default" onclick="bookmarkMemoRegist(event)">Save</button>
					</div>
				</form:form>
			</div>
			<div class="col-md-12">
				<hr>
				<span class="label label-warning">${model.categoryItem}</span>
				<h1> <html:unescape>${model.boardTitle} </html:unescape></h1>
				<p> <html:unescape>${model.boardContent} </html:unescape></p>
				<div>
					<c:forEach var="attach" items="${attach}">
						<a href='/board/downloadFile?fileName=${attach.fileName}'> ${attach.fileOriginName} </a>
						<br>
					</c:forEach>
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

<link href="<c:url value="/resources/common/css/answer.css" />" rel="stylesheet">
<script src="<c:url value="/resources/common/js/answer.js" />"></script>
<script src="<c:url value="/resources/common/js/bookmark.js" />"></script>
