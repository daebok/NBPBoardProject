<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script>
	$(function() {
		$('#fromDate, #toDate').datepicker({
			dateFormat : 'yy-mm-dd'
		});
	}); 
</script>
<label class="search-label" for="categoryType"> Category </label>
<select name="categoryType" id="categoryType" style="padding: 2px;">
	<option value='all'
		<c:out value="${pageMaker.criteria.categoryType eq 'all' ?'selected':''}"/>>전체</option>
	<c:forEach var="category" items="${categoryList}">
		<option value="${category.categoryItem}"
			<c:out value="${pageMaker.criteria.categoryType eq category.categoryItem ?'selected':''}"/>>${category.categoryItem}</option>
	</c:forEach>
</select>
<br>
<label class="search-label" for="date"> Date </label>
<input type="text" name="fromDate" id="fromDate" size="10" placeholder="시작일" value="${pageMaker.criteria.fromDate}"> ~
<input type="text" name="toDate" id="toDate" size="10" placeholder="종료일" value="${pageMaker.criteria.toDate}">
<br>
<label class="search-label" for="searchType"> SearchType </label>
<select name="searchType" id="searchType" style="padding: 2px;">
	<option value="title_content"
		<c:out value="${pageMaker.criteria.searchType eq 'title_content'?'selected':''}"/>>제목+내용</option>
	<option value="content"
		<c:out value="${pageMaker.criteria.searchType eq 'content'?'selected':''}"/>>내용</option>
	<option value="title"
		<c:out value="${pageMaker.criteria.searchType eq 'title'?'selected':''}"/>>제목</option>
	<option value="writer"
		<c:out value="${pageMaker.criteria.searchType eq 'writer'?'selected':''}"/>>작성자(아이디)</option>
</select>
<input type="text" name="keyword" id="keyword" size="40" value="<c:out value="${pageMaker.criteria.keyword}" escapeXml="true"/>">
<input type="submit" id="searchButton" class="btn btn-default btn-sm" value="검색">

<link type="text/css" rel="stylesheet" 	href="<c:url value='/resources/common/css/search.css'/>">