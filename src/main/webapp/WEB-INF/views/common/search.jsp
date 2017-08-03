<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<form:form name="form" method="post" action="/board/list">
	<select name="categoryType" id="categoryType" >
		<option value="null" 
			<c:out value="${cri.categoryType eq 'null'?'selected':''}"/>>---</option>
		<c:forEach var="category" items="${categoryList}">
			<option value="${category.categoryItem}"
				<c:out value="${cri.categoryType eq category.categoryItem ?'selected':''}"/>> ${category.categoryItem}</option>
		</c:forEach>
	</select> 
	<select name="searchType" id="searchType">
		<option value="title_content"
			<c:out value="${cri.searchType eq 'title_content'?'selected':''}"/>>제목 +내용</option>
		<option value="content"
			<c:out value="${cri.searchType eq 'content'?'selected':''}"/>>내용</option>
		<option value="title"
			<c:out value="${cri.searchType eq 'title'?'selected':''}"/>>제목</option>
		<option value="writer"
			<c:out value="${cri.searchType eq 'writer'?'selected':''}"/>>작성자</option>
	</select> 
	<input name="keyword" id="keyword" value="${cri.keyword}"> 
	<input type="submit" id="searchButton" class="btn btn-default btn-sm" value="검색">
	<a href="<c:url value='/board/list'/>" id="list" class="btn btn-default btn-sm">Clear</a>
</form:form>
