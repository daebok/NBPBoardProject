var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

function otherQuestion(userId){
	location.href="/board/list?searchType=writer&keyword="+userId;
}

$(document).ready(function() {
	$('.board-summernote').summernote({
	height : 300,
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
});



function questionRegist(){
	var title = $("#title").val();
	var content = $("#content").val();

	if (title.replace(/\s|　/gi, '') == '') {
		alert("제목를 입력하세요.");
		$("#title").focus();
		return;
	}
	
	if($('.btn-codeview').hasClass('active')){
		alert('codeView 상태에서는 저장을 하실 수 없습니다.')
		
		$('.board-summernote').deactivate();
		$('.note-editable').trigger('focus');
		return;
	}

	var htmlRemoveContent = content.replace(/<(\/?)p>/gi,"");
	htmlRemoveContent = htmlRemoveContent.replace(/<br[^>]*>/gi, "");
	htmlRemoveContent = htmlRemoveContent.replace(/&nbsp;/g, "");
	htmlRemoveContent = htmlRemoveContent.replace(/(\s*)/g, "");
	if (htmlRemoveContent == "") {
		alert("내용를 입력하세요.");
		$('.note-editable').trigger('focus');
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
				$(".note-editable").focus();
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

function perPageNumSelect(){
	var form = document.forms['perPageNum-list'];	
	form.action = "/board/list";
	form.submit();
}
