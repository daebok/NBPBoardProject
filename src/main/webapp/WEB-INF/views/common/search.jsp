<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<form name="form1" method="post" action="${path}/board/list.do">
	<select name="searchOption">
		<option value="all"
			<c:out value="${map.searchOption == 'all'?'selected':''}"/>>제목+내용</option>
		<option value="CONTENT"
			<c:out value="${map.searchOption == 'CONTENT'?'selected':''}"/>>내용</option>
		<option value="TITLE"
			<c:out value="${map.searchOption == 'TITLE'?'selected':''}"/>>제목</option>
	</select> <input name="keyword" value="${map.keyword}"> <input
		type="submit" class="btn btn-default" value="검색">
</form>