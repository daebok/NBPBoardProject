<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>

	<div class="container"  style="height:100%;">
		<div class="container-fluid">
			<a href="<c:url value='/admin/user'/>" id="login" class="notice btn btn-default">User Management</a> 
			<a href="<c:url value='/admin/category'/>" id="login" class="notice btn btn-default">Category Management</a>
			<a href="<c:url value='/admin/notice'/>" id="login" class="notice btn btn-default">Notice Management</a>
		</div>
		<br><br>
		<div class="pull-right" style="margin-bottom:10px;">
			<c:choose>
				<c:when test="${cri.option == 2 }">
					<a href="<c:url value='/admin/admin?option=1'/>" class="notice btn btn-default btn-sm">all</a>
				</c:when>
				<c:otherwise>
					<a href="<c:url value='/admin/admin?option=2'/>" class="notice btn btn-default btn-sm">unanswered</a>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="panel panel-default" style="margin-top:40px;">
			<div class="panel-heading">문의사항</div>
			<div class="list-group">
				<c:forEach var="contact" items="${contact}">
					<div class="list-group-item">
						<c:choose>
							<c:when test="${cri.option == 2 }">
								<a href="<c:url value='/contact/view?contactNo=${contact.contactNo}&option=2'/>"><html:unescape>${contact.contactTitle}</html:unescape></a>
							</c:when>
							<c:otherwise>
								<a href="<c:url value='/contact/view?contactNo=${contact.contactNo}&option=1'/>"><html:unescape>${contact.contactTitle}</html:unescape></a>
							</c:otherwise>
						</c:choose>
						<c:if test="${contact.contactPassword ne null}">
							<span class="label label-danger">비밀글</span>
						</c:if>
						<c:if test="${contact.contactCommentCount > 0}">
							<span class="label label-warning" id="secret-label">답변: ${contact.contactCommentCount}</span>
						</c:if>
						<div class="pull-right">
							작성자  <span class="label label-default"> ${contact.userName} </span>
							&nbsp;&nbsp;
							<fmt:formatDate value="${contact.contactDate}" pattern="yyyy/MM/dd"/>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="page-nation">
			<ul class="pagination pagination-large">
				<c:if test="${pageMaker.prev}">
					<li class="disabled"><span><a href="/admin/admin${pageMaker.makeQuery(pageMaker.startPage-1)}">&laquo;</a></span></li>
				</c:if>
				<c:forEach var="idx" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
					<li <c:out value="${pageMaker.cri.page == idx? 'class=active' : '' }" />>
						<a href="/admin/admin${pageMaker.makeQuery(idx)}"><span>${idx}</span></a>
					</li>
				</c:forEach>
				<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
					<li class="disabled"><span><a href="/admin/admin${pageMaker.makeQuery(pageMaker.endPage + 1)}">&raquo;</a></span></li>
				</c:if>
			</ul>
		</div>
	</div>
	

	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html> 