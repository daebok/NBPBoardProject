<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script>
	$(function() {
		$('input[type=date]').datepicker({
			dateFormat : 'yy-mm-dd'
		});
	});
</script>
<label for="categoryType"> Category </label>
<select name="categoryType" id="categoryType" style="padding: 2px;">
	<option value='all'
		<c:out value="${cri.categoryType eq 'all' ?'selected':''}"/>>전체</option>
	<c:forEach var="category" items="${categoryList}">
		<option value="${category.categoryItem}"
			<c:out value="${cri.categoryType eq category.categoryItem ?'selected':''}"/>>
			${category.categoryItem}</option>
	</c:forEach>
</select>
<br>
<label for="date"> Date </label>
<input type="date" name="fromDate" id="date" value="${cri.fromDate}">
~
<input type="date" name="toDate" id="date" value="${cri.toDate}">
<br>
<label for="searchType"> SearchType </label>
<select name="searchType" id="searchType" style="padding: 2px;">
	<option value="title_content"
		<c:out value="${cri.searchType eq 'title_content'?'selected':''}"/>>제목
		+내용</option>
	<option value="content"
		<c:out value="${cri.searchType eq 'content'?'selected':''}"/>>내용</option>
	<option value="title"
		<c:out value="${cri.searchType eq 'title'?'selected':''}"/>>제목</option>
	<option value="writer"
		<c:out value="${cri.searchType eq 'writer'?'selected':''}"/>>작성자(아이디)</option>
</select>
<input type="text" name="keyword" id="keyword" size="45"
	value='<c:out value="${cri.keyword}" escapeXml="true "/>'>
<input type="submit" id="searchButton" class="btn btn-default btn-sm"
	value="검색">
