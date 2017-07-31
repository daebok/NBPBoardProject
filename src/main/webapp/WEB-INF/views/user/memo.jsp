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
<script type="text/javascript">
$(document).on("click",".memo-ok-button", function(event) {
	event.preventDefault();
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		type : 'POST',
		url : '/board/memo',
		dataType : 'text',
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
		},
		data : $('.memo-form').serialize(),
		success : function(result) {
			alert('메모가 저장되었습니다.');
		}
	});

});
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
						+ "<div class='comment' id='comment-comment-text-"+list.commentCommentContent[i].commentNo+"'>"+list.commentCommentContent[i].commentContent + "</div>"
						+ "<span class='badge'>Commented By "+list.commentCommentContent[i].userName+"</span></div>"
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
$(document).ready(
		function() {
			$('.summernote').summernote({
				height : 500
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
		
				<div class="pull-left">
					<form:form name="form" action="/board/answer" method="get">
						<input type="hidden" name="boardNo" value="${model.boardNo}" /> 
						<input type="hidden" name="page" value="${cri.page}" /> 
						<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
						<input type="hidden" name="searchType" value="${cri.searchType}" /> 
						<input type="hidden" name="categoryType" value="${cri.categoryType}" />
						<input type="hidden" name="keyword" value="${cri.keyword}" />
						<button type="submit"  class="btn btn-primary">본문 보기</button>
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
			<div class="col-lg-12" style="margin-top: 50px;">
				<form:form name="form" class="memo-form">
					<input type="hidden" name="boardNo" value='${model.boardNo}'>
					<label for="content">Memo</label>
					<textarea class="summernote memo-content" name="bookmarkMemo" >${memo.bookmarkMemo}</textarea>
					<br />
					<div class="pull-right">
						<button class="memo-ok-button btn btn-default">Save</button>
					</div>
				</form:form>
			</div>
			<div class="col-md-12">
				<hr>
				<span class="label label-warning">${model.categoryItem}</span>
				<div class="pull-right">
					<i id="book-mark" class="book-mark glyphicon glyphicon-star-empty" style="font-size:25px; color:#FFCD12;"></i>
				</div>
				<h1>${model.boardTitle}</h1>
				<p>${model.boardContent}</p>
				<div>
					<c:forEach var="attach" items="${attach}">
						<a href='/upload/downloadFile?fileName=${attach.fileName}'> ${attach.fileOriginName} </a>
						<br>
					</c:forEach>
				</div>
				<hr>
			</div>
		</div>

		<div class="container-fluid" style="margin-top: 70px; margin-bottom: 50px" >
			<div class="col-lg-8">
				<span class="commentTitle">${answerCount.commentCount} Answer</span>
				<div id="listComment" class="col-lg-12">
					<c:if test='${empty comment}'>
						<div class="emptyContent">답변이 없습니다.</div>
					</c:if>
					<c:forEach var="comment" items="${comment}">
						<div class="pull-right">
							<i class="answer-like glyphicon glyphicon-heart-empty" comment-no="${comment.commentNo}" style="font-size:30px; color:#FF3636;"></i>
							<label class="control-label col-sm-1" for="answer-like">
								<span  id="answer-like"> ${comment.commentLikeCount} </span>
							</label>
						</div>
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
									<button type="button" class="comment-comment-selct btn btn-default" id = "comment-view-${comment.commentNo}" comment-no="${comment.commentNo}" value='closed'>Comment ▼</button>
								</div>
							</div>
							<div class='comment-comment-wrapper' id='comment-comment-list'></div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
