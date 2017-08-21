<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<script>
	var error = "${requestScope['errorMessage']}";
	if (error != "") {
		alert(error);
	}
</script>
<body>
	<!-- loading -->
	<%@include file="common/loading.jsp"%>
	
	<!-- header -->
	<%@include file="common/header.jsp"%>
	
	<div class="container">
	
		<!-- search -->
		<%@include file="common/search/board-search.jsp"%>
		
		<!-- top tab -->
		<div style="margin:20px 0;" >
			<h5><b>★Top Questions</b></h5>
			<a class="top10-update pull-right" href="<c:out value='/board/home/update'/>">5분마다 업데이트<span class="glyphicon glyphicon-refresh"></span></a>
			<div role="tabpanel">
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a href="#newest" aria-controls="newest" role="tab" data-toggle="tab">Newest</a></li>
					<li role="presentation"><a href="#views" aria-controls="views" role="tab" data-toggle="tab">Views</a></li>
					<li role="presentation"><a href="#answers" aria-controls="answers" role="tab" data-toggle="tab">Answers</a></li>
				</ul>
				<div class="tab-content" style="margin:20px 0;">
					<div role="tabpanel" class="tab-pane active" id="newest"><c:import url="/board/home?tab=1" ></c:import></div>
					<div role="tabpanel" class="tab-pane" id="views"><c:import url="/board/home?tab=2" ></c:import></div>
					<div role="tabpanel" class="tab-pane" id="answers"><c:import url="/board/home?tab=3" ></c:import></div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="common/footer.jsp"%>
</body>
</html>
