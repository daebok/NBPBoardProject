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
			$('.comment-delete').click(function(){
				var commentNo = $('.comment-delete').attr("comment-no");
				var data = "commentNo=" + commentNo;
				var result = confirm('답변을 삭제하시겠습니까?');
				if (result) {
					$.ajax({
						type : 'GET',
						url : '/comment/delete',
						dataType : 'text',
						processData : false,
						contentType : false,
						data : data,
						success : function(result) {
							$("#"+commentNo).hide();
						}
					});
				}
			});
			$('#commentButton').click(function() {
				var contentObj = $('#commentContent');
				var commentContent = $('#commentContent').val();
				var boardNo = ${model.boardNo};
				var data = "boardNo=" + boardNo + "&commentContent=" + commentContent;
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
						$("#listComment").append("<div class='comment-wrapper' id='0'> <div class='comment'>"
								+ commentContent + "</div><span class='badge'>Commented By  ${user.userName}</span><div class='pull-right'>"
								+ '<button type="button" class="comment-modify btn btn-default" comment-no="0">Modify</button>&nbsp;'
								+ '<button type="button" class="comment-delete btn btn-default" comment-no="0">Delete</button></div></div>'
							);
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
						<a href='/upload/displayFile?fileName=${attach.fileName}'> 
							<img src='/upload/displayFile?fileName=${attach.fileName}'
								 onerror="this.style.display='none'" alt='' width='100%' />${attach.fileOriginName}
						</a>
						<br>
					</c:forEach>
				</div>

				<div class="pull-right">
					<span class="badge">Posted By ${model.userName}</span>
				</div>
				<hr>
					<c:if test="${user.username == model.userId}">
						<a href="<c:url value='/board/modify?boardNo=${model.boardNo}'/>" id="modify" class="btn btn-danger">Modify</a>
						<button id="delete" class="btn btn-danger">Delete</button>
					</c:if>
					<c:if test="${user.username != model.userId}"> <!-- 관리자 권한 -->
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<button id="delete" class="btn btn-danger">Delete</button>
						</sec:authorize>
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

		<div class="container-fluid" style="margin-top: 70px; margin-bottom: 50px">
			<div class="col-lg-8">
				<span class="commentTitle">Comment</span>
				<div id="listComment" class="col-lg-12">
					<c:if test='${empty comment}'>
						<div class="emptyContent">답변이 없습니다.</div>
					</c:if>
					<c:forEach var="comment" items="${comment}">
						<div class="comment-wrapper" id="${comment.commentNo}">
							<div class="comment">${comment.commentContent}</div>
							<span class="badge commentName">Commented By ${comment.userName}</span>
							<div class="pull-right">
								<c:if test="${user.username == comment.userId}">
									<button type="button" class="comment-modify btn btn-default" comment-no="${comment.commentNo}">Modify</button>
									<button type="button" class="comment-delete btn btn-default" comment-no="${comment.commentNo}">Delete</button>
								</c:if>
								<c:if test="${user.username != comment.userId}"> <!-- 관리자 권한 -->
									<sec:authorize access="hasRole('ROLE_ADMIN')">
										<button type="button" class="comment-delete btn btn-default" comment-no="${comment.commentNo}">Delete</button>
									</sec:authorize>
								</c:if>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<sec:authorize access="isAuthenticated()">
				<div class="col-lg-12" style="margin-top: 50px;">
					<label for="content">Your Answer</label>
					<textarea class="summernote" name="commentContent" id="commentContent"></textarea>
					<br />
					<div class="pull-right">
						<button id="commentButton" class="btn btn-default">Comment</button>
					</div>
				</div>
			</sec:authorize>
		</div>
	</div>
</body>
</html>
