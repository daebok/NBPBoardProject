<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>BOARD</title>
<style type="text/css">
	.comment-wrapper{
		margin: 20px;
		padding: 10px;
		border: 1px dotted #989898;
		border-radius: 10px;
	}
	
	.commentTitle{
		font-weight: bolder;
	}
	
	.commentName {
		background-color: #000000;
	}
</style>
<script>
	$(document).ready(function() {
		$('#delete').click(function() {
			var result = confirm('Are you sure you want to delete this?');
			if (result) {
				location.replace('/board/delete?boardId=${model.boardId}');
			}
		});
		$('#list').click(function() {
			document.form.method = "get";
			document.form.action = "/board/list"
			document.form.submit();
		});

		$('.summernote').summernote({
			height : 200,
			onImageUpload : function(files, editor, welEditable) {
				sendFile(files[0], editor, welEditable);
			}
		});
		
		$('#commentButton').click(function() {
			var contentObj = $('#content');
			var content = $('#content').val();
			var boardId = ${model.boardId};
			var userId = ${sessionScope.userId};
			var data="boardId="+boardId+"&userId="+userId+"&content="+content;
			$.ajax({
				type: 'GET',
				url : 'comment/regist',
				dataType : 'text',
				processData : false,
				contentType : false,
				data : data,
				success : function(result) {
					alert('답글이 달렸습니다.');
					$("#listComment").append("<div class='comment-wrapper'> <div class='comment'>"+content+"</div><span class='badge'>Commented By  ${sessionScope.id}</span></div>");
											
					contentObj.value = "";
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
		<c:if test="${sessionScope.userId == model.userId}">
			<a href="<c:url value='/board/modify?boardId=${model.boardId}'/>" id="modify"
				class="btn btn-danger">Modify</a>
			<button id="delete" class="btn btn-danger">Delete</button>
		</c:if>
		<div class="container-fluid">
			<div class="col-md-12">
				<div class="pull-right">
					<span class="label label-warning">${model.item}</span>
				</div>
				<h1>${model.title}</h1>
				<p>${model.content}</p>
				<div class="pull-right">
					<span class="badge">Posted By ${model.name}</span>
				</div>
				<hr>
				<c:forEach var="attach" items="${attach}">
					<a href='/board/upload/displayFile?fileName=${attach.fileName}'>
						<img src='/board/upload/displayFile?fileName=${attach.fileName}'
						onerror="this.style.display='none'" alt='' />${attach.fileName}
					</a>
					<br>
				</c:forEach>
				<form name="form" action="list" method="post">
					<input type="hidden" name="id" value="${model.boardId}" /> <input
						type="hidden" name="page" value="${cri.page}" /> <input
						type="hidden" name="perPageNum" value="${cri.perPageNum}" />
					<button type="submit" id="list" class="btn btn-warning">list</button>
				</form>

			</div>
		</div>


		<!-- comment -->
		<div class="container-fluid">
			<div class="col-lg-8" style="margin-top: 70px; margin-bottom: 20px">
					<span class="commentTitle">Comment</span>
					<div id="listComment" class="col-lg-12" >
						<c:forEach var="comment" items="${comment}" >
							<div class="comment-wrapper">
								<div class="comment"> ${comment.content} </div>
								<span class="badge commentName">Commented By ${comment.name}</span> 
							</div>
						</c:forEach>
					</div>
			</div>
			<div class="col-lg-12" style="margin-top: 50px; margin-bottom: 100px">
				<label for="content">Your Answer</label>
				<textarea class="summernote" name="content" id="content"></textarea>
				<br />
				<div class="pull-right">
					<button id="commentButton" class="btn btn-default">Comment</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
