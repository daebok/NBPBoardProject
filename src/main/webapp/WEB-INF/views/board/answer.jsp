<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
<script>
$(document).on("click",".answer-tab",function(){
	$('.comment-wrapper').children().remove();
});
</script>
</head>
<body>
	<div class="container-fluid" style="margin: 30px 0" >
		<span class="answer-title">${answerCount.commentCount} Answer</span>
		<div role="tabpanel">
			<ul class="nav nav-tabs" role="tablist" id="answer-tab">
				<li role="presentation" class="answer-tab active"><a href="#newest" aria-controls="newest" role="tab" data-toggle="tab">Newest</a></li>
				<li role="presentation"class="answer-tab" ><a href="#likes" aria-controls="likes" role="tab" data-toggle="tab">Likes</a></li>
			</ul>
			<div class="tab-content" style="margin:20px;">
				<div role="tabpanel" class="tab-pane active" id="newest"><c:import url="/comment/list?tab=1" /></div>
				<div role="tabpanel" class="tab-pane" id="likes"><c:import url="/comment/list?tab=2" /></div>
			</div>
		</div>
	</div>
</body>
</html>
