<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="nav.jsp"%>

<div class="container"  style="margin-top:80px;">
	<div class="container-fluid" >
		<div class="pull-right">
			<a href="<c:url value='/board/ask'/>" id="question" class="btn btn-primary">? Ask Question</a>
			<a href="<c:url value='/board/list'/>" id="list" class="btn btn-primary">Questions</a>
		</div>
	</div>
	<hr>
</div>