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
	border: 1px dotted #c9302c;
	border-radius: 10px;
}

.commentTitle {
	font-weight: bolder;
}

.commentName {
	background-color: #000000;
}
.comment-comment-write {
	overflow: hidden;
}
.comment-comment-textarea {
	float: left;
	margin-left: 30px;
	margin-bottom: 20px;
}
.comment-comment-button {
	margin:10px;
	float: left;
}
.comment-comment-modify-button {
	margin:10px;
	float: left;
}
.comment-comment-cancel-button {
	margin-top:10px;
	float: left;
}
.comment-comment-list {
	margin-left: 60px;
	margin-top: -10px;
	border-color: #989898;
}
</style>
<script>
	/* 댓글 삭제 버튼 클릭 이벤트 */
	$(document).on("click",".comment-delete", function() {
		var commentNo = $(this).attr("comment-no");
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
					$("#"+commentNo).remove();
				}
			});
		}
	});
	/* 댓글 수정 버튼 클릭 이벤트 */
	$(document).on("click",".comment-modify",function() {
		var commentNo = $(this).attr('comment-no');
		var result = confirm('답변을 수정하시겠습니까?');
		var data = "commentNo=" + commentNo;
		if (result) {
			$.ajax({
				type : 'GET',
				url : '/comment/select',
				dataType : 'text',
				processData : false,
				contentType : false,
				data : data,
				success : function(result) {
					var list = $.parseJSON(result);
					$('#'+commentNo).css('background-color','#ededed');
					$('#comment-button').html('Modify');
					$('#comment-button').attr('comment-no',commentNo);
					$('.summernote').summernote('code', list.commentContent);
					$('#comment-list > *').attr("disabled",true);
				}
			});
		}
	});
	
	/* 대댓글  */
	/* 1. 대댓글 달기 textarea 생성 */
	$(document).on("click",".comment-comment",function() {
		var commentNo = $(this).attr('comment-no');
		$(this).attr("disabled",true);
		var str="";
		str += '<div class="comment-comment-write"><textarea cols="50" class="comment-comment-textarea" id="comment-comment-textarea"></textarea>';
		str += '<div><button type="button" class="comment-comment-button btn btn-default" comment-no="'+commentNo+'">Ok</button>';
		str += '<button type="button" class="comment-comment-cancel-button btn btn-default">Cancel</button></div></div>';
		$('#comment-'+commentNo+' > .comment-comment-wrapper').append(str);
	});
	/* 2. 대댓글 취소 */
	$(document).on("click",".comment-comment-cancel-button",function() {
		var commentNo = $(this).attr('comment-no');
		$('.comment-comment').attr("disabled",false);
		$('.comment-comment-write').remove();
	});
	/* 3. 대댓글 달기 */
	$(document).on("click",".comment-comment-button",function() {
		var result = confirm('답변에 답변을 하시겠습니까?');
		if (result) {
			var boardNo = ${model.boardNo};
			var commentNo = $(this).attr('comment-no');
			var commentContent = $('#comment-comment-textarea').val();
			var data = "boardNo=" + boardNo + "&commentContent=" + commentContent + "&commentNo=" + commentNo;
			$.ajax({
				type : 'GET',
				url : '/comment/regist',
				dataType : 'text',
				processData : false,
				contentType : false,
				data : data,
				success : function(result) {
					var list = $.parseJSON(result);
					$('.comment-comment-write').remove();
					$('.comment-comment').attr("disabled",false);
					$('#comment-view-'+commentNo).click();
				}
			});
		}
	});
	
	/* 4. 대댓글 리스트 보기 */
	$(document).on("click",".comment-comment-selct",function() {
		$('.comment-comment-write').remove();
		$('.comment-comment').attr("disabled",false);
		var commentNo = $(this).attr('comment-no');
		if($(this).val() == 'closed'){
			$(this).html("Comment ▲");
			$(this).val('open');
			var data = "commentNo=" + commentNo;
			$.ajax({
				type : 'GET',
				url : '/comment/comment/select',
				dataType : 'text',
				processData : false,
				contentType : false,
				data : data,
				success : function(result) {
					var list = $.parseJSON(result);
					for (var i = 0; i < list.commentCommentContent.length; i++) {
						$('#comment-'+commentNo+' > .comment-comment-wrapper').append("<div class='comment-wrapper comment-comment-list' id='"+list.commentCommentContent[i].commentNo+"'> "
							+ "<div class='comment' id='comment-comment-text-"+list.commentCommentContent[i].commentNo+"'>"+list.commentCommentContent[i].commentContent + "</div><span class='badge'>Commented By "+list.commentCommentContent[i].userName+"</span>"
							+ "<div class='pull-right comment-list-list' >"
							+ '<button type="button" class="comment-comment-modify btn btn-default" comment-no="'+list.commentCommentContent[i].commentNo+'">Modify</button>&nbsp;'
							+ '<button type="button" class="comment-delete btn btn-default" comment-no="'+list.commentCommentContent[i].commentNo+'">Delete</button></div></div>'
						);
					}
				}
			});
		} else if($(this).val() == 'open'){
			$(this).html("Comment ▼");
			$(this).val('closed');
			$('#comment-'+commentNo+' > .comment-comment-wrapper').children().remove();
		}
	});
	/* 5. 대댓글 수정 버튼 클릭 이벤트 */
	$(document).on("click",".comment-comment-modify",function() {
		var commentNo = $(this).attr('comment-no');
		var data = "commentNo=" + commentNo;
		$.ajax({
			type : 'GET',
			url : '/comment/select',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				var list = $.parseJSON(result);
				$('#'+commentNo).css('background-color','#ededed');
				$('#comment-button').attr('comment-no',commentNo);
				$('#comment-list > *').attr("disabled",true);
				$('.comment-list-list > *').attr("disabled",true);
				$(this).attr("disabled",true);
				$('#' + commentNo ).after('<div class="comment-comment-write">'
					+ '<textarea cols="50" class="comment-comment-textarea" id="comment-comment-textarea">'+list.commentContent+'</textarea>'
					+ '<button type="button" class="comment-comment-modify-button btn btn-default" comment-no="'+commentNo+'">Ok</button></div>');
			}
		});
		
	});
	/* 5. 대댓글 수정  */
	$(document).on("click",".comment-comment-modify-button",function() {
		var commentContent = $('#comment-comment-textarea').val();
		console.log(commentContent);
		var commentNo = $(this).attr('comment-no');
		var data = "commentNo=" + commentNo + "&commentContent=" + commentContent;
		$.ajax({
			type : 'GET',
			url : '/comment/modify',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				alert('답글이 수정되었습니다.');
				$("#comment-comment-text-"+commentNo).html(commentContent);
				$('#comment-button').html('Comment');
				$('#'+commentNo).css('background-color','#ffffff');
				$('#comment-list > *').attr("disabled",false);
				$('.comment-list-list > *').attr("disabled",false);
				$('.comment-comment-write').remove();
			}
		});
	});
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
			
			
			$('#comment-button').on('click', function(event) {
				var contentObj = $('#comment-content');
				var commentContent = $('#comment-content').val();
				if($(this).html() == 'Comment'){
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
							var list = $.parseJSON(result);
							$(".emptyContent").remove();
							alert('답글이 달렸습니다.');
							$("#listComment").append("<div id='comment-"+list.commentNo+"'> <div class='comment-wrapper id='"+list.commentNo+"'><div class='comment'>"
									+ list.commentContent + "</div><span class='badge'>Commented By  ${user.userName}</span>"
									+ "<div class='pull-right' class='comment-list'>"
									+ '<button type="button" class="comment-modify btn btn-default" comment-no="'+list.commentNo+'">Modify</button>&nbsp;'
									+ '<button type="button" class="comment-delete btn btn-default" comment-no="'+list.commentNo+'">Delete</button>&nbsp'
									+ '<button type="button" class="comment-comment btn btn-default" comment-no="'+list.commentNo+'">Comment</button>&nbsp;'
									+ '<button type="button" class="comment-comment-selct btn btn-default '+list.commentNo+' comment-no="'+list.commentNo+'" value="closed">Comment ▼</button></div></div>'
									+ '<div class="comment-comment-wrapper" id="comment-comment-list"></div></div>'
								);
							$('.summernote').summernote('code', '');
						}
					});
				} else if ($(this).html() == 'Modify') {
					var commentNo = $(this).attr('comment-no');
					var data = "commentNo=" + commentNo + "&commentContent=" + commentContent;
					$.ajax({
						type : 'GET',
						url : '/comment/modify',
						dataType : 'text',
						processData : false,
						contentType : false,
						data : data,
						success : function(result) {
							alert('답글이 수정되었습니다.');
							$("#content-"+commentNo).html(commentContent);
							$('.summernote').summernote('code', '');
							$('#comment-button').html('Comment');
							$('#'+commentNo).css('background-color','#ffffff');
							$('#comment-list > *').attr("disabled",false);
						}
				});
			}
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
				<span class="label label-warning">${model.categoryItem}</span>
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
						<button type="submit" id="list" class="btn btn-danger">list</button>
					</form>
				</div>
			</div>
		</div>

		<div class="container-fluid" style="margin-top: 70px; margin-bottom: 50px" >
			<div class="col-lg-8">
				<span class="commentTitle">Comment</span>
				<div id="listComment" class="col-lg-12">
					<c:if test='${empty comment}'>
						<div class="emptyContent">답변이 없습니다.</div>
					</c:if>
					<c:forEach var="comment" items="${comment}">
						<div id="comment-${comment.commentNo}" class="comment-wrapper-wrapper">
							<div class="comment-wrapper" id="${comment.commentNo}" >
								<div class="comment" id="content-${comment.commentNo}">${comment.commentContent}</div>
									<c:choose>
										<c:when test="${model.userName == comment.userName}">
											<span class="badge commentName" style='background-color:#d9534f;'>작성자</span>
										</c:when>
										<c:otherwise>
											<span class="badge commentName">Commented By ${comment.userName}</span>
										</c:otherwise>
									</c:choose>
									<span class="badge commentName" style="background-color:#ffffff; color:#8c8c8c">${comment.commentDate}</span>
									<div class="pull-right" class="comment-list" id="comment-list">
										<c:if test="${user.username == comment.userId}">
											<button type="button" class="comment-modify btn btn-default" comment-no="${comment.commentNo}">Modify</button>
											<button type="button" class="comment-delete btn btn-default" comment-no="${comment.commentNo}">Delete</button>
										</c:if>
										<c:if test="${user.username != comment.userId}"> <!-- 관리자 권한 -->
											<sec:authorize access="hasRole('ROLE_ADMIN')">
												<button type="button" class="comment-delete btn btn-default" comment-no="${comment.commentNo}">Delete</button>
											</sec:authorize>
										</c:if>
										<button type="button" class="comment-comment btn btn-default" comment-no="${comment.commentNo}">Comment</button>
										<button type="button" class="comment-comment-selct btn btn-default" id = "comment-view-${comment.commentNo}" comment-no="${comment.commentNo}" value='closed'>Comment ▼</button>
									</div>
								</div>
								<div class='comment-comment-wrapper' id='comment-comment-list'>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<sec:authorize access="isAuthenticated()">
				<div class="col-lg-12" style="margin-top: 50px;">
					<label for="content">Your Answer</label>
					<textarea class="summernote" name="commentContent" id="comment-content" maxLength="500"></textarea>
					<br />
					<div class="pull-right">
						<button id="comment-button" class="btn btn-default">Comment</button>
					</div>
				</div>
			</sec:authorize>
		</div>
	</div>
</body>
</html>
