<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<form name="form" method="post" action="${path}/board/list">
	<select name="categoryType" id="categoryType"><%-- 
		<option value="n" 
			<c:out value="${cri.categoryType == null?'selected':''}"/>>---</option> --%>
		<c:forEach var="category" items="${list}">
			<option value="${category.item}"> ${category.item}</option>
		</c:forEach>
	</select> 
	<select name="searchType" id="searchType">
		<option value="n" 
			<c:out value="${cri.searchType == null?'selected':''}"/>>---</option>
		<option value="title"
			<c:out value="${cri.searchType eq 'title'?'selected':''}"/>>내용</option>
		<option value="content"
			<c:out value="${cri.searchType eq 'content'?'selected':''}"/>>제목</option>
		<option value="title_content"
			<c:out value="${cri.searchType eq 'title_content'?'selected':''}"/>>제목 +내용</option>
	</select> 
	<input name="keyword" id="keyword" value="${cri.keyword}"> 
	<input type="submit" id="searchButton" class="btn btn-default" value="검색">
</form>