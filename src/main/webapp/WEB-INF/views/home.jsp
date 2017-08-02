<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<script type="text/javascript">
	$('#myTab a[href="#newest"]').tab('show'); // Select tab by name
	$('#myTab a:first').tab('show'); // Select first tab
	$('#myTab a:last').tab('show'); // Select last tab
	$('#myTab li:eq(2) a').tab('show'); // Select third tab (0-indexed)

	var error = '${requestScope["errorMessage"]}';
	if(error != "") {
		alert(error);
	}
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
				<li role="presentation" class="active">
					<a href="#newest" aria-controls="newest" role="tab" data-toggle="tab">Newest</a>
				</li>
				<li role="presentation">
					<a href="#views" aria-controls="views" role="tab" data-toggle="tab">Views</a>
				</li>
				<li role="presentation">
					<a href="#answers" aria-controls="answers" role="tab" data-toggle="tab">Answers</a>
				</li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content" style="margin:20px;">
				<div role="tabpanel" class="tab-pane active" id="newest"><c:import url="/board/newest" ></c:import></div>
				<div role="tabpanel" class="tab-pane" id="views"><c:import url="/board/views" ></c:import></div>
				<div role="tabpanel" class="tab-pane" id="answers"><c:import url="/board/answers" ></c:import></div>
			</div>

		</div>
	</div>
</body>
</html>
