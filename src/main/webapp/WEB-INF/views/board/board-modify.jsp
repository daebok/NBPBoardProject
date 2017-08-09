<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
<title>BOARD</title>
<link href="<c:url value="/resources/common/css/file-css.css" />" rel="stylesheet">
<script src="<c:url value="/resources/common/js/upload.js" />"></script>
<script>
	/* 파일 삭제 */
	$(document).on('click','.new-file-delete-button',function(){
		var fileId = $(this).attr('id');
		$('#file-list-'+fileId).remove();
		$(".newUploadedList").append( "<input type='hidden' name='boardFilesNo' value='"+fileId+"'/>");
	});
	
	$(document).ready(function() {
		$('.summernote').summernote({
			height : 300,
			callbacks: {
				onImageUpload: function(files, editor, welEditable) {
					var form = $('.file')[0];
					var formData = new FormData(form);
					for (var index = files.length - 1; index >= 0; index--) {
						if(  files[index].size > 10485760 ) {
							alert('10MB가 넘는 파일은 업로드 할 수 없습니다!!');
							$('.file').val('');
							continue;
						}
						formData.append('files', files[index]);
						
						var str = "<div class='list-group-item' id='file-list-"+index+"'>";
						str += "<div class='list-1'>" + files[index].name +"</div>";
						str += "<div class='list-2'>" + files[index].size + " bytes </div>";
						str += "<div class='list-3'> <a class='file-delete-button' id='"+index+"'>[삭제]</a></div></div>";
						$(".summernoteUploadedList").append(str);
					}
				}
		}
	});
	$("#modifyButton").click(function() {
		var title = $("#title").val();
		var content = $("#content").val();
		
		if (title.replace(/\s|　/gi, '') == '') {
			alert("제목를 입력하세요.");
			$("#title").focus();
			return;
		}
		
		var htmlRemoveContent = content.replace(/<\/?([a-z][a-z0-9]*)\b[^>]*>/gi, "");
		htmlRemoveContent.replace(/&nbsp;/g, "");
		if (htmlRemoveContent.replace(/(\s*)/g, '') == '') {
			alert("내용를 입력하세요.");
			$("#content").focus();
			return;
		}
		
		/* 비속어 처리 */
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			type: 'POST', 
			url: '/board/badWordsCheck', 
			dataType : 'text',
			beforeSend : function(xhr){
				xhr.setRequestHeader(header, token);
			},
			data: $('#register-form').serialize(),
			success: function (result) {
				var list = $.parseJSON(result);
				console.log(list);
				if(list.length != 0) {
					alert("[ "+ list+ " ] 이(가) 포함 된 단어는 작성 할 수 없습니다!");
					$("#content").focus();
				} else {
					document.form.action = "/board/question/modify"
					document.form.submit();
				}
			}
		});
	});
	
	$("#category").val('${model.categoryNo}').prop("selected", true);
	
	/* 파일 업로드 */
	$(".file").on("change", function(event) {
		$(".newUploadedList > * ").remove();
		for(var index = 0 ; index < $(".file")[0].files.length; index++) {
			if( $(this)[0].files[index].size > 10485760 ) {
				alert('10MB가 넘는 파일은 업로드 할 수 없습니다!!');
				$(this).val('');
				break;
			}
			var str = "<div class='list-group-item' id='file-list-"+index+"'>";
			str += "<div class='list-1'>" + $(".file")[0].files[index].name +"</div>";
			str += "<div class='list-2'>" + $(".file")[0].files[index].size + " bytes </div>";
			str += "<div class='list-3'> <a class='file-delete-button' id='"+index+"'>[삭제]</a></div></div>";
			$(".newUploadedList").append(str);
		}
	});
	
	/* 파일 삭제 */
	$(".file-delete-button").on("click", function(){
		$(this).parent('div').parent('div').remove();
		$('.modify-form').append("<input type='hidden' name='boardFilesDelete' value="+$(this).attr("id")+">");
	});
});
</script>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>

	<div class="container">
		<div class="container-fluid" style="margin-bottom: 30px">
			<form:form action="question/modify" method="post" name="form" class="modify-form form-horizontal" id="register-form" enctype="multipart/form-data">
				<div class="form-group">
					<label for="title">Title</label>
					<input type="text" name="boardTitle" id="title" value="${model.boardTitle}" maxlength="100" size="20" class="form-control"  /> 
				</div>
				<div class="form-group">
					<label for="category">Category</label>
					<category:category />
				</div>
				<textarea class="summernote" cols="100" rows="30" name="boardContent" maxlength="500" id="content">
					${model.boardContent}
				</textarea>
				<div class="form-group">
					<div class="filebox"> 
						<input class="upload-name" value="파일선택" disabled="disabled" > 
						<label for="input-file">업로드</label> 
						<input type="file" name="files" multiple="multiple" class="file upload-hidden" id="input-file" maxlength="5">
					</div>
					<div class="panel panel-default">
						<div class="list-group">
							<div class="list-group-item">
								<div class="list-1"><b>파일명</b></div>
								<div class="list-2"><b>크기</b></div>
								<div class="list-3"><b>삭제</b></div>
							</div>
							<c:forEach var="attach" items="${attach}">
								<div class="uploadedList">
									<div class="list-group-item">
										<div class="list-1">${attach.fileOriginName}</div>
										<div class="list-2">${attach.fileSize} bytes</div>
										<div class="list-3"><a class='file-delete-button' id='${attach.fileName}'>[삭제]</a></div>
									</div>
								</div>
							</c:forEach>
							<div class="newUploadedList"></div>
							<div class="summernoteUploadedList"></div>
						</div>
					</div>
				</div>
				<input type="hidden" name="boardNo" value="${model.boardNo}">
				<div class="pull-right">
					<button type="button" id="modifyButton" class="btn btn-default">Modify</button>
				</div>
			</form:form>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html>
