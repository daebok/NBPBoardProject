<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="uri" 	required="true"	%>

<div class="page-nation">
	<ul class="pagination pagination-large">
		<c:if test="${pageMaker.prev}">
			<li class="disabled"><span><a href="${uri}${pageMaker.makeQuery(pageMaker.startPage-1)}">&laquo;</a></span></li>
		</c:if>

		<c:forEach var="idx" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
			<li 
				<c:out value="${pageMaker.criteria.page == idx? 'class=active' : '' }" />>
				<a href="${uri}${pageMaker.makeQuery(idx)}"><span>${idx}</span></a>
			</li>
		</c:forEach>
		<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
			<li class="disabled"><span><a href="${uri}${pageMaker.makeQuery(pageMaker.endPage + 1)}">&raquo;</a></span></li>
		</c:if>
	</ul>
</div>