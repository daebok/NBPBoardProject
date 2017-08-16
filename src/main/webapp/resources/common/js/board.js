var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function() {
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
				str += "<div class='list-3'> <a href='javascript:fileDelete("+index+")' id='"+index+"'>[삭제]</a></div></div>";
				$(".summernoteUploadedList").append(str);
			}
		}
	}
	});	
	
	$('.answer-summernote').summernote({
		height : 200,
		onImageUpload : function(files,editor, welEditable) {
				sendFile(files[0], editor,welEditable);
		}
	});
});



function questionRegist(){
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
				document.form.submit();
			}
		}
	});
}

function questionDelete(boardNo){
	if (confirm('게시물을 삭제하시겠습니까?')) {
		if('${answerCount.commentCount}' > 0) {
			alert("게시글에 답변이 있으므로 삭제 할 수 없습니다.\n삭제를 원하시면 관리자에게 문의하세요.");
			return;
		} else {
			location.replace('/board/delete?boardNo='+boardNo);
		}
	} 
}

function goToList(section){
	var form = document.forms['list'];	
	 if (section == 2) {
		form.action = "/board/myquestions";
		
	} else if (section == 3) {
		form.action = "/board/myanswers";
		
	} else if (section == 4) {
		form.action = "/board/answers/liked";
	} else {
		form.action = "/board/list";
	}
	 form.submit();
}

function fileUpload(){
	var form = $('.file')[0];
	var formData = new FormData(form);
	$(".newUploadedList > * ").remove();
	for(var index = 0 ; index < form.files.length; index++) {
		if( form.files[index].size > 10485760 ) {
			alert('10MB가 넘는 파일은 업로드 할 수 없습니다!!');
			$(this).val('');
			break;
		}
		var str = "<div class='list-group-item' id='file-list-"+index+"'>";
		str += "<div class='list-1'>" + form.files[index].name +"</div>";
		str += "<div class='list-2'>" + form.files[index].size + " bytes </div>";
		str += "<div class='list-3'> <a href='javascript:fileDelete("+index+")' id='"+index+"'>[삭제]</a></div></div>";
		$(".newUploadedList").append(str);
	}
}


function fileDelete(fileId){
	$('#file-list-'+fileId).remove();
	$(".newUploadedList").append( "<input type='hidden' name='boardFilesNo' value='"+fileId+"'/>");
}

function uploadedFileDetet(fileName, fileItem){
	$(fileItem).parent('div').parent('div').remove();
	$('.modify-form').append("<input type='hidden' name='boardFilesDelete' value="+fileName+">");
}



