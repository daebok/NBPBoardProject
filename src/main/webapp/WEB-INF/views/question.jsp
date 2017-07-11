<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Home</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
	href="<c:url value='/css/bootstrap.min.css'/>">
<script src="http://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>

<script type="text/script" src="<c:url value='/js/bootstrap.min.js'/>"></script>
<script type="text/script" src="<c:url value='/js/common.js'/>"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						// 파일 업로드 영역에서 기본효과를 제한
						$(".fileDrop").on("dragenter dragover", function(e) {
							e.preventDefault(); // 기본효과 제한
						});
						// 드래그해서 드롭한 파일들 ajax 업로드 요청
						$(".fileDrop")
								.on(
										"drop",
										function(e) {
											e.preventDefault(); // 기본효과 제한
											var files = e.originalEvent.dataTransfer.files; // 드래그한 파일들
											var file = files[0]; // 첫번째 첨부파일
											var formData = new FormData(); // 폼데이터 객체
											formData.append("file", file); // 첨부파일 추가
											$
													.ajax({
														url : "${path}/upload/uploadAjax",
														type : "post",
														data : formData,
														dataType : "text",
														processData : false, // processType: false - header가 아닌 body로 전달
														contentType : false,
														// ajax 업로드 요청이 성공적으로 처리되면
														success : function(data) {
															console.log(data);
															// 첨부 파일의 정보
															var fileInfo = getFileInfo(data);
															// 하이퍼링크
															var html = "<a href='"+fileInfo.getLink+"'>"
																	+ fileInfo.fileName
																	+ "</a><br>";
															// hidden 태그 추가
															html += "<input type='hidden' class='file' value='"+fileInfo.fullName+"'>";
															// div에 추가
															$("#uploadedList")
																	.append(
																			html);
														}
													});
										});
					});
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="container-fluid">
			<form action="question/ask" method="post"
				enctype="multipart/form-data">
				Title <input type="text" name="title" /> <br />
				<textarea cols="100" rows="30" name="content"></textarea>
				<br /> 첨부파일 등록<input type="file" name="file"> <input
					type="submit" />
				<div>
					<!-- 첨부파일 등록영역 -->
					<div class="fileDrop"></div>
					<!-- 첨부파일의 목록 출력영역 -->
					<div id="uploadedList"></div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
