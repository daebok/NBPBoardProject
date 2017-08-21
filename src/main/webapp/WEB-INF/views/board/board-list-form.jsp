<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div>
	<span class="badge">Posted By <user:id no="${board.userNo}"/></span>
	<span class="badge" style="background-color:#ffffff; color:#8c8c8c"><fmt:formatDate value="${board.boardDate}" pattern="yyyy/MM/dd"/></span>
	<div class="pull-right">
		<span class="label label-success">answer: ${board.commentCount}</span>
		<span class="label label-primary">views: ${board.boardViewCount}</span>
		<span class="label label-warning">${board.categoryItem}</span>
		<span style="font-size:12px;"> Attach
			<span class="badge" style="font-size:12px;"><c:out value="${board.fileCount}"/></span>
		</span>
	</div>
</div>