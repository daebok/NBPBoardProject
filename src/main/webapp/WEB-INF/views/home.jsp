<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<script type="text/javascript">
$('#myTab a[href="#profile"]').tab('show') // Select tab by name
$('#myTab a:first').tab('show') // Select first tab
$('#myTab a:last').tab('show') // Select last tab
$('#myTab li:eq(2) a').tab('show') // Select third tab (0-indexed)
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="common/header.jsp"%>
	<!-- header end -->
	<div class="container">
		<%@include file="common/search.jsp"%>
		<h5><b>Top Questions</b></h5>
		<div role="tabpanel">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a href="#home"
					aria-controls="home" role="tab" data-toggle="tab">Views</a></li>
				<li role="presentation"><a href="#profile"
					aria-controls="profile" role="tab" data-toggle="tab">Favorites</a></li>
				<li role="presentation"><a href="#answer"
					aria-controls="profile" role="tab" data-toggle="tab">Answers</a></li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content" style="margin:20px;">
				<div role="tabpanel" class="tab-pane active" id="home"><c:import url="/board/views" ></c:import></div>
				<div role="tabpanel" class="tab-pane" id="profile"><c:import url="/board/favorites" ></c:import></div>
				<div role="tabpanel" class="tab-pane" id="answer"><c:import url="/board/answers" ></c:import></div>
			</div>

		</div>
	</div>
</body>
</html>
