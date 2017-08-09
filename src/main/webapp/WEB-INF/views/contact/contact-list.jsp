<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
	<title>Home</title>
	<script type="text/javascript">
	function passwordCheck(contactNo){
		$.ajax({
			type : "GET",
			url : "/contact/password/is",
			data: "contactNo="+contactNo,
			success : function(result) {
				$("#contact-password-dialog").html(result);
			}
		});
		$("#contact-password-dialog").dialog({
			autoOpen: true,
			modal: true,
			position: { my: "left", at: "right", of: "#secret-label" },
			title: "비밀번호"
		});
	}
	</script>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>

	<div class="container" style="height:100%;">
		<div style="margin-bottom:20px; ">
			<a href="<c:url value='/contact/regist'/>" class="btn btn-default">문의하기</a>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">문의사항</div>
			<div class="panel-body">
				<p> 신고 및 문의 사항은 아래 주소로 연락 바랍니다. </p>
				<a href="mailto:wikiki413@gmail.com" class="glyphicon glyphicon-send">&nbsp; wikiki413@gmail.com</a>
			</div>
			<div class="list-group">
				<c:forEach var="contact" items="${contact}">
					<div class="list-group-item">
						<c:choose>
							<c:when test="${contact.contactPassword ne null}">
								<a href="javascript:passwordCheck('${contact.contactNo}')"><html:unescape>${contact.contactTitle}</html:unescape></a>
								<span class="label label-danger" id="secret-label">비밀글</span>
							</c:when>
							<c:otherwise>
								<a href="<c:url value='/contact/view?contactNo=${contact.contactNo}'/>">${contact.contactTitle}</a>
							</c:otherwise>
						</c:choose>
						<div class="pull-right">
							작성자 <span class="label label-default"> ${contact.userName} </span>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="page-nation">
			<ul class="pagination pagination-large">
				<c:if test="${pageMaker.prev}">
					<li class="disabled"><span><a href="/contact${pageMaker.makeQuery(pageMaker.startPage-1)}">&laquo;</a></span></li>
				</c:if>
				<c:forEach var="idx" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
					<li <c:out value="${pageMaker.cri.page == idx? 'class=active' : '' }" />>
						<a href="/contact${pageMaker.makeQuery(idx)}"><span>${idx}</span></a>
					</li>
				</c:forEach>
				<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
					<li class="disabled"><span><a href="/contact${pageMaker.makeQuery(pageMaker.endPage + 1)}">&raquo;</a></span></li>
				</c:if>
			</ul>
		</div>
	</div>
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
	<div id="contact-password-dialog"></div>
</body>
</html>
					 