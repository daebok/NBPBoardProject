<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
<title>ContactUs</title>
<script type="text/javascript">
$(document).on('click','#delete',function() {
	var result = confirm('문의사항을 삭제하시겠습니까?');
	if (result) {
		location.replace('/contact/delete?contactNo=${model.contactNo}');
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

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).on("click","#contact-comment-button", function(event) {
	event.preventDefault();
	var data = $('.contact-comment-form').serialize()
	$.ajax({
		type : 'POST',
		url : '/contact/comment/regist',
		dataType : 'text',
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
		},
		data : data,
		success : function(result) {
			alert('답변이 달렸습니다.');
			$("#listComment").append(result);
			$('.summernote').summernote('code', '');
		}
	});
});
$(document).on("click",".contact-comment-delete-button", function(event) {
	var contactCommentNo = $(this).attr("comment-no");
	var data = "contactCommentNo=" + contactCommentNo;
	var result = confirm('답변을 삭제하시겠습니까?');
	if (result) {
		$.ajax({
			type : 'GET',
			url : '/contact/comment/delete',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				$("#contact-comment-whole-wrapper-"+contactCommentNo).remove();
			}
		});
	}
});
</script>
<style>
	.contact-comment-whole-wrapper{
		border: 1px solid #aaa;
		padding:10px;
	}
</style>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>
	
	<div class="container">
		<div class="container-fluid">
			<div class="col-md-12">
				<h3><html:unescape>${model.contactTitle}</html:unescape></h3>
				<p><html:unescape>${model.contactContent}</html:unescape></p>
				<hr>
				<div class="pull-left">
					<form:form name="list" action="/contact/list"  method="get">
						<input type="hidden" name="contactNo" value="${model.contactNo}" /> 
						<input type="hidden" name="page" value="${cri.page}" /> 
						<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
						<input type="submit" class="btn btn-primary" value="List"/>
					</form:form>
				</div>
				<div class="pull-right">
					<c:if test="${user.userNo == model.userNo}">
						<button id="delete" class="btn btn-primary">Delete</button>
					</c:if>
				</div>
			</div>
		</div>
		<div class="col-md-8">
			<div id="listComment">
				<c:forEach var="contactComment" items="${contactComment}">
					<%@ include file="contact-comment-form.jsp" %>
				</c:forEach>
			</div>
		</div>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<div class="col-lg-9" style="margin-top: 50px;">
				<form:form name="form" method="get" class="contact-comment-form">
					<input type="hidden" name="contactNo" value="${model.contactNo}">
					<textarea class="summernote" name="contactCommentContent" id="contact-comment-content"></textarea><br>
					<div class="pull-right">
						<button id="contact-comment-button" class="btn btn-default">OK</button>
					</div>
				</form:form>
			</div>
		</sec:authorize>
	</div>
		
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html>
