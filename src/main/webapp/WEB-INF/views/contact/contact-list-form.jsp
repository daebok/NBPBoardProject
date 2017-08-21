<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
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
						<a class="contact-title" href="javascript:passwordIsCheck(${contact.contactNo})"><c:out value="${contact.contactTitle}" escapeXml="true"></c:out></a>
						<span class="label label-danger" id="secret-label">비밀글</span>
					</c:when>
					<c:otherwise>
						<a class="contact-title" href="${path}/contact/view${pageMaker.makeQuery(pageMaker.criteria.page)}&contactNo=${contact.contactNo}"><c:out value="${contact.contactTitle}" escapeXml="true"></c:out></a>
					</c:otherwise>
				</c:choose>
				<c:if test="${contact.contactCommentCount > 0}">
					<span class="label label-warning" id="secret-label">답변: ${contact.contactCommentCount}</span>
				</c:if>
			</div>
			<div class="list-2"> <user:id no="${contact.userNo}"/> </div>
			<div class="list-3"> <fmt:formatDate value="${contact.contactDate}" pattern="yyyy/MM/dd"/></div>
		</div>
	</c:forEach>
</div>
