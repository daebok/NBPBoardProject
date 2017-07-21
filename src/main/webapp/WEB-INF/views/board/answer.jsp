<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>BOARD</title>
<style type="text/css">
.comment-wrapper {
	margin: 20px;
	padding: 10px;
	border: 1px dotted #989898;
	border-radius: 10px;
}

.commentTitle {
	font-weight: bolder;
}

.commentName {
	background-color: #000000;
}
</style>
<script>
	$(document).ready(
		function() {
			$('#delete').click(function() {
				var result = confirm('게시물을 삭제하시겠습니까?');
				if (result) {
					console.log(result);
					location.replace('/board/delete?boardNo=${model.boardNo}');
				} 
			});
			$('#list').click(function() {
				document.form.method = "get";
				document.form.action = "/board/list"
				document.form.submit();
			});
			$('.summernote').summernote({
				height : 200,
				onImageUpload : function(files,editor, welEditable) {
						sendFile(files[0], editor,welEditable);
				}
			});
			$('#commentButton').click(function() {
				var contentObj = $('#commentContent');
				var commentContent = $('#commentContent').val();
				var boardNo = ${model.boardNo};
				var userNo = ${sessionScope.userNo};
				var data = "boardNo=" + boardNo + "&userNo=" + userNo + "&commentContent=" + commentContent;
				$.ajax({
					type : 'GET',
					url : '/comment/regist',
					dataType : 'text',
					processData : false,
					contentType : false,
					data : data,
					success : function(result) {
						$(".emptyContent").hide();
						alert('답글이 달렸습니다.');
						$("#listComment").append("<div class='comment-wrapper'> <div class='comment'>"
								+ commentContent + "</div><span class='badge'>Commented By  ${sessionScope.userName}</span></div>");
						$('.summernote').empty();
					}
				});
			});
	});
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<div class="container">

		<div class="container-fluid">
			<div class="col-md-12">
				<div class="pull-right">
					<span class="label label-warning">${model.categoryItem}</span>
				</div>
				<h1>${model.boardTitle}</h1>
				<p>${model.boardContent}</p>
				<div>
					<c:forEach var="attach" items="${attach}">
						<a href='/upload/displayFile?fileName=${attach.fileName}'> <img
							src='/upload/displayFile?fileName=${attach.fileName}'
							onerror="this.style.display='none'" alt='' width='100%' />${attach.fileOriginName}
						</a>
						<br>
					</c:forEach>
				</div>

				<div class="pull-right">
					<span class="badge">Posted By ${model.userName}</span>
				</div>
				<hr>
				<c:if test="${sessionScope.userNo == model.userNo}">
					<a href="<c:url value='/board/modify?boardNo=${model.boardNo}'/>" id="modify" class="btn btn-danger">Modify</a>
					<button id="delete" class="btn btn-danger">Delete</button>
				</c:if>
				<div class="pull-right">
					<form name="form" action="list" method="post">
						<input type="hidden" name="boardNo" value="${model.boardNo}" /> 
						<input type="hidden" name="page" value="${cri.page}" /> 
						<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
						<input type="hidden" name="searchType" value="${cri.searchType}" /> 
						<input type="hidden" name="categoryType" value="${cri.categoryType}" />
						<input type="hidden" name="keyword" value="${cri.keyword}" />
						<button type="submit" id="list" class="btn btn-warning">list</button>
					</form>
				</div>
			</div>
		</div>

		<div class="container-fluid">
			<div class="col-lg-8" style="margin-top: 70px; margin-bottom: 20px">
				<span class="commentTitle">Comment</span>
				<div id="listComment" class="col-lg-12">
					<c:if test='${empty comment}'>
						<div class="emptyContent">답변이 없습니다.</div>
					</c:if>
					<c:forEach var="comment" items="${comment}">
						<div class="comment-wrapper">
							<div class="comment">${comment.commentContent}</div>
							<span class="badge commentName">Commented By ${comment.userName}</span>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="col-lg-12" style="margin-top: 50px; margin-bottom: 100px">
				<label for="content">Your Answer</label>
				<textarea class="summernote" name="commentContent" id="commentContent"></textarea>
				<br />
				<div class="pull-right">
					<button id="commentButton" class="btn btn-default">Comment</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
