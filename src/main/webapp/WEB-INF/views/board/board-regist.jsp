<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>

	<div class="container">
		<div class="container-fluid" style="margin-bottom: 30px">
			<form:form action="/board/question/ask" method="post" name="form" id="register-form" class="form-horizontal" enctype="multipart/form-data">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="form-group">
					<label for="title">Title</label>
					<input type="text" name="boardTitle" maxlength="100" id="title" size="20" class="form-control" />
				</div>
				<div class="form-group">
					<label for="category">Category</label>
					<category:category />
				</div>
				<textarea class="board-summernote" name="boardContent" id="content"></textarea>
				<br /> 
				<div class="form-group">
					<div class="filebox"> 
						<input class="upload-name" value="파일선택" disabled="disabled" > 
						<label for="input-file">업로드</label> 
						<input type="file" name="files" id="input-file" class="file" maxlength="5" multiple onchange="fileUpload()">
					</div>
					<div class="panel panel-default">
						<div class="list-group">
							<div class="list-group-item">
								<div class="list-1"><b>파일명</b></div>
								<div class="list-2"><b>크기</b></div>
								<div class="list-3"><b>삭제</b></div>
							</div>
							<div class="list-group-item">
								<div class="newUploadedList"></div>
								<div class="summernoteUploadedList"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="pull-right">
					<button type="button" class="btn btn-default" onclick="questionRegist()">Question</button>
				</div>
			</form:form>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html>

<script src="<c:url value="/resources/common/js/board.js" />">
</script><script src="<c:url value="/resources/common/js/file.js" />"></script>
<link href="<c:url value="/resources/common/css/file.css" />" rel="stylesheet">


					 