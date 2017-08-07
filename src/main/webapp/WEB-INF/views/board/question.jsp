<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
<title>BOARD</title>
<link href="<c:url value="/resources/common/css/file-css.css" />" rel="stylesheet">
<link href="<c:url value="/resources/common/css/answer-css.css" />" rel="stylesheet">
<script src="<c:url value="/resources/common/js/answer.js" />"></script>

<script>
/* 1. 댓글 달기 textarea 생성 */
$(document).on("click",".comment-comment",function() {
	var commentNo = $(this).attr('comment-no');
	$(this).attr("disabled",true);
	var str="";
	str += '<div class="comment-comment-write">';
	str += '<form name="form" class="comment-form">';
	str += '<input type="hidden" name="boardNo" value="'+${model.boardNo}+'">';
	str += '<input type="hidden" name="commentNo" value="'+commentNo+'">';
	str += '<textarea cols="60" size="8" class="comment-comment-textarea" id="comment-comment-textarea" name="commentContent"></textarea>';
	str += '<div><button type="button" class="comment-comment-button btn btn-default btn-sm" comment-no="'+commentNo+'">Ok</button>';
	str += '<button type="button" class="comment-comment-cancel-button btn btn-default btn-sm">Cancel</button></div>';
	str += '</form></div>';
	$('#comment-'+commentNo+' > .comment-comment-wrapper').append(str);
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
});

/* 게시글 삭제 */
$(document).on('click','#delete',function() {
	if (confirm('게시물을 삭제하시겠습니까?')) {
		if('${answerCount.commentCount}' > 0) {
			alert("게시글에 답변이 있으므로 삭제 할 수 없습니다.\n삭제를 원하시면 관리자에게 문의하세요.");
			return;
		} else {
			location.replace('/board/delete?boardNo=${model.boardNo}');
		}
	} 
});

/* 섬머노트 */
$(document).ready(
	function() {
		$('.summernote').summernote({
			height : 200,
			onImageUpload : function(files,editor, welEditable) {
					sendFile(files[0], editor,welEditable);
			}
		});
});

</script>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>

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
									<div class="list-1"><a href='/board/downloadFile?fileName=${attach.fileName}'>${attach.fileOriginName}</a></div>
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
				<c:if test="${section eq 1}">
					<form:form name="form" action="list" method="get">
						<input type="hidden" name="boardNo" value="${model.boardNo}" /> 
						<input type="hidden" name="page" value="${cri.page}" /> 
						<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
						<input type="hidden" name="searchType" value="${cri.searchType}" /> 
						<input type="hidden" name="categoryType" value="${cri.categoryType}" />
						<input type="hidden" name="keyword" value="${cri.keyword}" />
						<button type="submit" id="list" class="btn btn-primary">List</button>
					</form:form>
				</c:if>
				<c:if test="${section eq 2}">
					<form:form name="form" action="myquestions" method="get">
						<input type="hidden" name="boardNo" value="${model.boardNo}" /> 
						<input type="hidden" name="page" value="${cri.page}" /> 
						<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
						<input type="hidden" name="searchType" value="${cri.searchType}" /> 
						<input type="hidden" name="categoryType" value="${cri.categoryType}" />
						<input type="hidden" name="keyword" value="${cri.keyword}" />
						<button type="submit" id="list" class="btn btn-primary">List</button>
					</form:form>
				</c:if>
				<c:if test="${section eq 3}">
					<form:form name="form" action="myanswers" method="get">
						<input type="hidden" name="boardNo" value="${model.boardNo}" /> 
						<input type="hidden" name="page" value="${cri.page}" /> 
						<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
						<input type="hidden" name="searchType" value="${cri.searchType}" /> 
						<input type="hidden" name="categoryType" value="${cri.categoryType}" />
						<input type="hidden" name="keyword" value="${cri.keyword}" />
						<button type="submit" id="list" class="btn btn-primary">List</button>
					</form:form>
				</c:if>
				</div>
			</div>
		</div>
		<!-- answer -->
		<%@include file="answer.jsp"%>
		
		<div class="col-lg-9" style="margin-top: 50px;">
			<form:form name="form" method="get" class="answer-form">
				<input type="hidden" name="boardNo" value="${model.boardNo}">
				<label for="content">Your Answer</label>
				<textarea class="summernote" name="commentContent" id="comment-content"></textarea>
				<br />
				<div class="pull-right">
					<button id="comment-button" class="btn btn-default">Answer</button>
				</div>
			</form:form>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html>
