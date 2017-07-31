<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
<title>BOARD</title>
<link href="<c:url value="/resources/common/css/file-css.css" />" rel="stylesheet">
<style type="text/css">
.whole-wrapper{
	margin:15px;
}
.comment-wrapper {
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
.comment {
	word-break:break-all
}
</style>
<script>
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
/* 답변 달기 */
	$(document).on("click","#comment-button", function(event) {
		event.preventDefault();
		var commentContent = $('#comment-content').val();
		if($(this).html() == 'Answer'){
			var boardNo = ${model.boardNo};
			$.ajax({
				type : 'POST',
				url : '/comment/regist',
				dataType : 'text',
				beforeSend: function(xhr){
					xhr.setRequestHeader(header, token);
				},
				data : $('.answer-form').serialize(),
				success : function(result) {
					var list = $.parseJSON(result);
					alert('답글이 달렸습니다.');
					$(".emptyContent").remove();
					$("#listComment").append("<div id='comment-"+list.commentNo+"' class='comment-wrapper-wrapper'> <div class='comment-wrapper' id='"+list.commentNo+"'>"
							+ "<div class='comment' id='content-"+list.commentNo+"'>"+ list.commentContent + "</div><span class='badge'>Commented By  ${user.userName}</span>"
							+ "<div class='pull-right' class='comment-list'>"
							+ '<button type="button" class="comment-modify btn btn-default" comment-no="'+list.commentNo+'">Modify</button>&nbsp;'
							+ '<button type="button" class="comment-delete btn btn-default" comment-no="'+list.commentNo+'">Delete</button>&nbsp'
							+ '<button type="button" class="comment-comment btn btn-default" comment-no="'+list.commentNo+'">Comment</button>&nbsp;'
							+ '<button type="button" class="comment-comment-selct btn btn-default '+list.commentNo+'" comment-no="'+list.commentNo+'" id="comment-view-'+list.commentNo+'" value="closed">Comment ▼</button></div></div>'
							+ '<div class="comment-comment-wrapper" id="comment-comment-list"></div></div>'
						);
					$('.summernote').summernote('code', '');
				}
			});
		} else if ($(this).html() == 'Modify') {
			var commentNo = $(this).attr('comment-no');
			$.ajax({
				type : 'POST',
				url : '/comment/modify',
				dataType : 'text',
				beforeSend: function(xhr){
					xhr.setRequestHeader(header, token);
				},
				data : $('.answer-form').serialize(),
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
	/* 답변 삭제 버튼 클릭 이벤트 */
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
					var list = $.parseJSON(result);
					if(Number(list) < 0) {
						$("#comment-"+commentNo).remove();
					} else if(Number(list) > 0) {
						$("#content-"+commentNo).html("삭제 된 답변입니다.");
						$("#" + commentNo + " .comment-modify").remove();
						$("#" + commentNo + " .comment-delete").remove();
						$("#" + commentNo + " .comment-comment").remove();
					}
				}
			});
		}
	});
	/* 답변 수정 버튼 클릭 이벤트 */
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
	
	/* 댓글  */
	/* 1. 댓글 달기 textarea 생성 */
	$(document).on("click",".comment-comment",function() {
		var commentNo = $(this).attr('comment-no');
		$(this).attr("disabled",true);
		var str="";
		str += '<div class="comment-comment-write">';
		str += '<form name="form" class="comment-form">';
		str += '<input type="hidden" name="boardNo" value="'+${model.boardNo}+'">';
		str += '<input type="hidden" name="commentNo" value="'+commentNo+'">';
		str += '<textarea cols="50" class="comment-comment-textarea" id="comment-comment-textarea" name="commentContent"></textarea>';
		str += '<div><button type="button" class="comment-comment-button btn btn-default" comment-no="'+commentNo+'">Ok</button>';
		str += '<button type="button" class="comment-comment-cancel-button btn btn-default">Cancel</button></div>';
		str += '</form></div>';
		$('#comment-'+commentNo+' > .comment-comment-wrapper').append(str);
	});
	/* 2. 댓글 취소 */
	$(document).on("click",".comment-comment-cancel-button",function() {
		var commentNo = $(this).attr('comment-no');
		$('.comment-comment').attr("disabled",false);
		$('.comment-comment-write').remove();
	});
	/* 3. 댓글 달기 */
	$(document).on("click",".comment-comment-button",function() {
		var result = confirm('답변에 댓글을 달겠습니까?');
		if (result) {
			var commentNo = $(this).attr('comment-no');
			$.ajax({
				type : 'POST',
				url : '/comment/regist',
				dataType : 'text',
				beforeSend: function(xhr){
					xhr.setRequestHeader(header, token);
				},
				data : $('.comment-form').serialize(),
				success : function(result) {
					var list = $.parseJSON(result);
					$('.comment-comment-write').remove();
					$('.comment-comment').attr("disabled",false);
					$('#comment-view-'+commentNo).val('closed');
					$('#comment-view-'+commentNo).click();
				}
			});
		}
	});
	
	/* 4. 댓글 리스트 보기 */
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
						$('#comment-'+commentNo+' > .comment-comment-wrapper').append("<div class='comment-wrapper comment-comment-list' id='answer-comment-"+list.commentCommentContent[i].commentNo+"'> "
							+ "<div class='comment' id='comment-comment-text-"+list.commentCommentContent[i].commentNo+"'>"+list.commentCommentContent[i].commentContent + "</div>"
							+ "<span class='badge'>Commented By "+list.commentCommentContent[i].userName+"</span>"
							+ "<div class='pull-right comment-list-list' >"
							+ '<button type="button" class="comment-comment-modify btn btn-default" comment-no="'+list.commentCommentContent[i].commentNo+'">Modify</button>&nbsp;'
							+ '<button type="button" class="answer-comment-delete btn btn-default" comment-no="'+list.commentCommentContent[i].commentNo+'">Delete</button></div></div>'
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
	/* 5. 댓글 수정 버튼 클릭 이벤트 */
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
				$('#answer-comment-' + commentNo ).after('<div class="comment-comment-write">'
					+ '<textarea cols="50" class="comment-comment-textarea" id="comment-comment-textarea">'+list.commentContent+'</textarea>'
					+ '<button type="button" class="comment-comment-modify-button btn btn-default" comment-no="'+commentNo+'">Ok</button></div>');
			}
		});
		
	});
	/* 5. 댓글 수정  */
	$(document).on("click",".comment-comment-modify-button",function() {
		var commentContent = $('#comment-comment-textarea').val();
		var commentNo = $(this).attr('comment-no');
		$.ajax({
			type : 'POST',
			url : '/comment/modify',
			dataType : 'text',
			processData : false,
			contentType : false,
			beforeSend: function(xhr){
				xhr.setRequestHeader(header, token);
			},
			data : $('.comment-form').serialize(),
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
	/* 답변 삭제 버튼 클릭 이벤트 */
	$(document).on("click",".answer-comment-delete", function() {
		var commentNo = $(this).attr("comment-no");
		var data = "commentNo=" + commentNo;
		var result = confirm('댓글을 삭제하시겠습니까?');
		if (result) {
			$.ajax({
				type : 'GET',
				url : '/comment/delete',
				dataType : 'text',
				processData : false,
				contentType : false,
				data : data,
				success : function(result) {
					$("#answer-comment-"+commentNo).remove();
				}
			});
		}
	});
	
	/* 답변 좋아요 */
	$(document).on('click', '.answer-hate', function(){
		var commentNo = $(this).attr('comment-no');
		var data = "commentNo=" + commentNo;
		$(this).attr('class','answer-like glyphicon glyphicon-heart');
		$(this).css('color','#FF3636');
		$.ajax({
			type : 'GET',
			url : '/comment/like',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				var count = $('#answer-like-count-'+commentNo).html();
				$('#answer-like-count-'+commentNo).html(Number(count)+1);
			}
		});
	});
	
	$(document).on('click', '.answer-like', function(){
		var commentNo = $(this).attr('comment-no');
		var data = "commentNo=" + commentNo;
		$(this).attr('class','answer-hate glyphicon glyphicon-heart');
		$(this).css('color','#eee');
		$.ajax({
			type : 'GET',
			url : '/comment/hate',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				var count = $('#answer-like-count-'+commentNo).html();
				$('#answer-like-count-'+commentNo).html(Number(count)-1);
			}
		});
	});
	
	/* 즐겨찾기  */
	$(document).on('click','#book-mark-uncheck',function(){
		var data = "boardNo=" + ${model.boardNo};
		$(this).attr('id','book-mark');
		$(this).css('color','#FF3636');
		$.ajax({
			type : 'GET',
			url : '/board/bookmark',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				$('.book-mark-alarm').show(1000);
				$('.book-mark-alarm').hide(2000);
			}
		});
	});
	
	/* 즐겨찾기 해제 */
	$(document).on('click','#book-mark',function(){
		var data = "boardNo=" + ${model.boardNo};
		$(this).attr('id','book-mark-uncheck');
		$(this).css('color','#888');
		$.ajax({
			type : 'GET',
			url : '/board/bookmark/uncheck',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
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
		}
	);
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="container-fluid">
			<div class="pull-right" style="margin-bottom:10px;">
				<c:choose>
					<c:when test="${model.bookmarkCheck eq 1}">
						<div id="book-mark" class="glyphicon glyphicon-check" style="font-size:25px; color:#FF3636;"></div>
					</c:when>
					<c:otherwise>
						<div id="book-mark-uncheck" class="glyphicon glyphicon-check" style="font-size:25px; color:#888;"></div>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="col-md-12">
				<span class="label label-warning">${model.categoryItem} </span>
				<h1>${model.boardTitle}</h1>
				<p>${model.boardContent}</p>
				<div class="panel panel-default">
					<div class="list-group">
						<div class="list-group-item">
							<div class="list-1"><b>파일명</b></div>
							<div class="list-2"><b>크기</b></div>
						</div>
						<c:forEach var="attach" items="${attach}">
							<div class="uploadedList">
								<div class="list-group-item">
									<div class="list-1"><a href='/upload/downloadFile?fileName=${attach.fileName}'>${attach.fileOriginName}</a></div>
									<div class="list-2">${attach.fileSize} bytes</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<hr>
				<div class="pull-right">
					<c:if test="${user.username == model.userId}">
						<a href="<c:url value='/board/modify?boardNo=${model.boardNo}'/>" id="modify" class="btn btn-primary">Modify</a>
						<button id="delete" class="btn btn-primary">Delete</button>
					</c:if>
					<c:if test="${user.username != model.userId}"> <!-- 관리자 권한 -->
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<button id="delete" class="btn btn-primary">Delete</button>
						</sec:authorize>
					</c:if>
				</div>
				<div class="pull-left">
					<form:form name="form" action="list" method="post">
						<input type="hidden" name="boardNo" value="${model.boardNo}" /> 
						<input type="hidden" name="page" value="${cri.page}" /> 
						<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
						<input type="hidden" name="searchType" value="${cri.searchType}" /> 
						<input type="hidden" name="categoryType" value="${cri.categoryType}" />
						<input type="hidden" name="keyword" value="${cri.keyword}" />
						<button type="submit" id="list" class="btn btn-primary">list</button>
					</form:form>
				</div>
			</div>
		</div>

		<div class="container-fluid" style="margin-bottom: 50px" >
			<div class="col-lg-8">
				<span class="commentTitle">${answerCount.commentCount} Answer</span>
				<div id="listComment" class="col-lg-12">
					<c:if test='${empty comment}'>
						<div class="emptyContent">답변이 없습니다.</div>
					</c:if>
					<c:forEach var="comment" items="${comment}">
						<div class="whole-wrapper">
							<div  style="diplay:block;">
								<c:choose>
									<c:when test="${comment.userNo eq 1}">
										<div class="answer-like glyphicon glyphicon-heart" comment-no="${comment.commentNo}" style="font-size:15px; color:#FF3636;"></div>
									</c:when>
									<c:otherwise>
										<div class="answer-hate glyphicon glyphicon-heart" comment-no="${comment.commentNo}" style="font-size:15px; color:#eee;"></div>
									</c:otherwise>
								</c:choose>
								<span  id="answer-like-count-${comment.commentNo}" style="font-size:12px; color:#888;"> ${comment.commentLikeCount} </span>
							</div>
							<div id="comment-${comment.commentNo}" class="comment-wrapper-wrapper">
								<div class="comment-wrapper" id="${comment.commentNo}" >
									<c:if test="${comment.commentEnabled eq 1 }">
										<div class="comment" id="content-${comment.commentNo}"> ${comment.commentContent} </div>
									</c:if>
									<c:if test="${comment.commentEnabled ne 1 }">
										<div class="comment" id="content-${comment.commentNo}">삭제 된 답변입니다.</div>
									</c:if>
									<c:choose>
										<c:when test="${model.userName == comment.userName}">
											<span class="badge commentName" style='background-color:#d9534f;'>작성자</span>
										</c:when>
										<c:otherwise>
											<span class="badge commentName">Commented By ${comment.userName} </span>
										</c:otherwise>
									</c:choose>
									<span class="badge commentName" style="background-color:#ffffff; color:#8c8c8c">${comment.commentDate}</span>
									<div class="pull-right" class="comment-list" id="comment-list">
										<c:if test="${comment.commentEnabled eq 1 }">
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
										</c:if>
										<button type="button" class="comment-comment-selct btn btn-default" id = "comment-view-${comment.commentNo}" comment-no="${comment.commentNo}" value='closed'>Comment ▼</button>
									</div>
								</div>
								<div class='comment-comment-wrapper' id='comment-comment-list'></div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<sec:authorize access="isAuthenticated()">
				<div class="col-lg-8" style="margin-top: 50px;">
					<form:form name="form" class="answer-form">
						<input type="hidden" name="boardNo" value="${model.boardNo}">
						<label for="content">Your Answer</label>
						<textarea class="summernote" name="commentContent" id="comment-content"></textarea>
						<br />
						<div class="pull-right">
							<button id="comment-button" class="btn btn-default">Answer</button>
						</div>
					</form:form>
				</div>
			</sec:authorize>
		</div>
	</div>
</body>
</html>
