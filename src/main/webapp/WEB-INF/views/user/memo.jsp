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
<link href="<c:url value="/resources/common/css/answer-css.css" />" rel="stylesheet">
<style type="text/css">
.comment-comment-list {
	width: 530px;
	margin-left: 100px;
}
</style>
<script type="text/javascript">
/* 메모 작성*/
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
/* 4. 댓글 리스트 보기 */
$(document).on("click",".comment-comment-selct",function() {
	$('.comment-comment-write').remove();
	var commentNo = $(this).attr('comment-no');
	var count = parseInt($(this).html());
	if($(this).val() == 'closed'){
		$(this).html(count + " Comment ▲");
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
					);
				}
			}
		});
	} else if($(this).val() == 'open'){
		$(this).html(count + " Comment ▼");
		$(this).val('closed');
		$('#comment-'+commentNo+' > .comment-comment-wrapper').children().remove();
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

/* 답변 좋아요 해제 */
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
		data : data
	});
});

/* 즐겨찾기 해제 */
$(document).on('click','#book-mark',function(){
	var result = confirm('즐겨찾기를  해제 하시겠습니까? \n기존에 저장한 메모도 삭제 됩니다.');
	if (result) {
		var data = "boardNo=" + ${model.boardNo};

		$(this).attr('id','book-mark-uncheck');
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
});

/* 썸머 노트*/
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
				<div class="pull-left" style="margin-right:5px;">
					<form:form name="form" action="/board/answer" method="get">
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

		<div class="container-fluid" style="margin-top: 20px; margin-bottom: 50px" >
			<div class="col-lg-8">
				<span class="commentTitle">${answerCount.commentCount} Answer</span>
				<div id="listComment" class="col-lg-12">
					<c:if test='${empty comment}'>
						<div class="emptyContent">답변이 없습니다.</div>
					</c:if>
					<c:forEach var="comment" items="${comment}">
						<div class="whole-wrapper" id="whole-wrapper-${comment.commentNo}">
							<c:choose>
								<c:when test="${comment.userNo eq 1}">
									<div class="answer-like glyphicon glyphicon-heart" comment-no="${comment.commentNo}" style="font-size:15px; color:#FF3636;"></div>
								</c:when>
								<c:otherwise>
									<div class="answer-hate glyphicon glyphicon-heart" comment-no="${comment.commentNo}" style="font-size:15px; color:#eee;"></div>
								</c:otherwise>
							</c:choose>
							<span  id="answer-like-count-${comment.commentNo}" style="font-size:12px; color:#888;"> ${comment.commentLikeCount} </span>
							<div id="comment-${comment.commentNo}" class="comment-wrapper-wrapper">
								<div class="comment-wrapper" id="${comment.commentNo}" style="margin-bottom:10px;">
									<div class="comment" id="content-${comment.commentNo}"> ${comment.commentContent} </div>
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
										<button type="button" class="comment-comment-selct btn btn-default" 
												id = "comment-view-${comment.commentNo}" comment-no="${comment.commentNo}" value='closed'>${comment.commentCommentCount} Comment ▼</button>
									</div>
								</div>
								<div class='comment-comment-wrapper' id='comment-comment-list' style="margin-bottom:20px; margin-top:20px;"></div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
