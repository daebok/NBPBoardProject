<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core"%>
<div class="notice-wrapper pull-right">
	<div class="notice-title">Notice</div>
	<div class="notice-body">
		<c:forEach var="notice" items="${notice}">
			<h6>
				<span class="glyphicon glyphicon-bullhorn" style="color:#777;"></span>
				<a href="${path}/admin/notice${pageMaker.makeSearch(pageMaker.criteria.page)}&noticeNo=${notice.noticeNo}" class="notice-content">
					<c:out value="${notice.noticeTitle}" escapeXml="true"/>
				</a>
			</h6>
		</c:forEach>
	</div>
</div>

<link type="text/css" rel="stylesheet" 	href="<c:url value='/resources/common/css/notice.css'/>">