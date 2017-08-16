<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<sec:csrfMetaTags/>
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
				<div class="list-group-item">
					<div class="list-1"><b>제목</b></div>
					<div class="list-2"><b>작성자</b></div>
					<div class="list-3"><b>날짜</b></div>
				</div>
				<c:forEach var="contact" items="${contact}">
					<div class="list-group-item">
						<div  class="list-1">
							<c:choose>
								<c:when test="${contact.contactPassword ne null}">
									<a href="javascript:passwordIsCheck(${contact.contactNo})"><c:out value="${contact.contactTitle}" escapeXml="false"></c:out></a>
									<span class="label label-danger" id="secret-label">비밀글</span>
								</c:when>
								<c:otherwise>
									<a href="${path}/contact/view${pageMaker.makeQuery(pageMaker.cri.page)}&contactNo=${contact.contactNo}&option=0"><c:out value="${contact.contactTitle}" escapeXml="false"></c:out></a>
								</c:otherwise>
							</c:choose>
							<c:if test="${contact.contactCommentCount > 0}">
								<span class="label label-warning" id="secret-label">답변: ${contact.contactCommentCount}</span>
							</c:if>
						</div>
						<div class="list-2"> ${contact.userId} </div>
						<div class="list-3"> <fmt:formatDate value="${contact.contactDate}" pattern="yyyy/MM/dd"/></div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="page-nation">
			<ul class="pagination pagination-large">
				<c:if test="${pageMaker.prev}">
					<li class="disabled"><span><a href="/contact/list${pageMaker.makeQuery(pageMaker.startPage-1)}">&laquo;</a></span></li>
				</c:if>
				<c:forEach var="idx" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
					<li <c:out value="${pageMaker.cri.page == idx? 'class=active' : '' }" />>
						<a href="/contact/list${pageMaker.makeQuery(idx)}"><span>${idx}</span></a>
					</li>
				</c:forEach>
				<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
					<li class="disabled"><span><a href="/contact/list${pageMaker.makeQuery(pageMaker.endPage + 1)}">&raquo;</a></span></li>
				</c:if>
			</ul>
		</div>
	</div>
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
	<div id="contact-password-dialog"></div>
</body>
</html>

<link type="text/css" rel="stylesheet" 	href="<c:url value='/resources/common/css/contact.css'/>">
<script src="<c:url value="/resources/common/js/contact.js" />"></script>

					 