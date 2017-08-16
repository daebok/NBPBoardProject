<%@ taglib prefix="fmt"				uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c"				uri="http://java.sun.com/jsp/jstl/core"%>
<div>
	<span class="badge">Posted By ${board.userId}</span>
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