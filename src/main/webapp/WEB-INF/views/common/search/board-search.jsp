<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<form:form name="form" method="get" action="/board/list">
	<%@ include file="search.jsp"%>
	<a href="<c:url value='/board/list'/>" id="list" class="btn btn-default btn-sm">Clear</a>
</form:form>