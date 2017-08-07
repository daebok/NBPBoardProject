<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
<title>Home</title>
<script src="<c:url value="/resources/common/js/upload.js" />"></script>
<link href="<c:url value="/resources/common/css/file-css.css" />" rel="stylesheet">
<script>
	
	/* 파일 삭제 */
	$(document).on('click','.file-delete-button',function(){
		var fileId = $(this).attr('id');
		$('#file-list-'+fileId).remove();
		$(".newUploadedList").append( "<input type='hidden' name='boardFilesNo' value='"+fileId+"'/>");
	});

	$(document).ready(function() {
		var special_pattern = /[\\<>]/gi;
		
		$("#questionButton").click(function() {
			var title = $("#title").val();
			var content = $("#content").val();

			if (title.replace(/\s|　/gi, '') == '') {
				alert("제목를 입력하세요.");
				$("#title").focus();
				return;
			}
			
			if (special_pattern.test(title) == true) {
				alert('제목에 \<>는 사용할 수 없습니다.');
				$('#title').focus();
				return false;
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
						document.form.action = "/board/question/ask"
						document.form.submit();
					}
				}
			});
			
			
		});
		
		/* 썸머 노트 */
		$('.summernote').summernote({
			height : 200,
			width: 100,
			callbacks: {
				onImageUpload: function(files, editor, welEditable) {
					var form = $('.file')[0];
					var formData = new FormData(form);
					for (var index = files.length - 1; index >= 0; index--) {
						if(  files[index].size > 10485760 ) {
							alert('10MB가 넘는 파일은 업로드 할 수 없습니다!!');
							$('.file').val('');
							break;
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
		
		
		/* 파일 업로드 */
		$(".file").on("change", function(event) {
			$(".newUploadedList > * ").remove();
			var form = $('.file')[0];
			var formData = new FormData(form);
			for(var index = 0 ; index < $(this)[0].files.length; index++) {
				if( $(this)[0].files[index].size > 10485760 ) {
					alert('10MB가 넘는 파일은 업로드 할 수 없습니다!!');
					$(this).val('');
					break;
				}
				var str = "<div class='list-group-item' id='file-list-"+index+"'>";
				str += "<div class='list-1'>" + $(this)[0].files[index].name +"</div>";
				str += "<div class='list-2'>" + $(this)[0].files[index].size + " bytes </div>";
				str += "<div class='list-3'> <a class='file-delete-button' id='"+index+"'>[삭제]</a></div></div>";
				$(".newUploadedList").append(str);
			}
		});
		
	});
</script>

</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>

	<div class="container">
		<div class="container-fluid" style="margin-bottom: 30px">
			<form:form action="/board/question/ask?${_csrf.parameterName}=${_csrf.token}" method="post" name="form" id="register-form" class="form-horizontal" enctype="multipart/form-data">
				<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
				<div class="form-group">
					<label for="title">Title</label>
					<input type="text" name="boardTitle" maxlength="100" id="title" size="20" class="form-control" />
				</div>
				<div class="form-group">
					<label for="category">Category</label>
					<%-- <select name="categoryNo" id="category">
						<c:forEach var="category" items="${list}">
							<option value="${category.categoryNo}">${category.categoryItem}</option>
						</c:forEach>
					</select> --%> 
					<category:category />
				</div>
				<textarea class="summernote" name="boardContent" id="content"></textarea>
				<br /> 
				<div class="form-group">
					<div class="filebox"> 
						<input class="upload-name" value="파일선택" disabled="disabled" > 
						<label for="input-file">업로드</label> 
						<input type="file" name="files" class="file upload-hidden" id="input-file" maxlength="5" multiple>
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
					<button type="button" id="questionButton" class="btn btn-default">Question</button>
				</div>
			</form:form>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html>
					 