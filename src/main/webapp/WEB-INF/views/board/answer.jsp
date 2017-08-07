<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>

<sec:csrfMetaTags/>
</head>
<body>
	<div class="container-fluid" style="margin-bottom: 50px" >
		<div role="tabpanel">
			<ul class="nav nav-tabs" role="tablist" id="answer-tab">
				<li role="presentation" class="active"><a href="#newest" aria-controls="newest" role="tab" data-toggle="tab">Newest</a></li>
				<li role="presentation"><a href="#likes" aria-controls="likes" role="tab" data-toggle="tab">Likes</a></li>
			</ul>
			<div class="tab-content" style="margin:20px;">
				<div role="tabpanel" class="tab-pane active" id="newest"><c:import url="/comment/list/tab?tab=1" ></c:import></div>
				<div role="tabpanel" class="tab-pane" id="likes"><c:import url="/comment/list/tab?tab=2" ></c:import></div>
			</div>
		</div>
	</div>
</body>
</html>
