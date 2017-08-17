var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

/* 섬머노트 */
$(document).ready( function() {
	$('.summernote').summernote({
		height : 200,
		onImageUpload : function(files,editor, welEditable) {
				sendFile(files[0], editor,welEditable);
		}
	});
});

function contactDelete(contactNo){
	var result = confirm('문의사항을 삭제하시겠습니까?');
	if (result) {
		location.replace('/contact/delete?contactNo='+contactNo);
	} 
}

function contactRegist() {
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

	document.form.action = "/contact/regist/insert"
	document.form.submit();
}

function contactCommentRegist(){
	event.preventDefault();
	var data = $('.contact-comment-form').serialize()
	$.ajax({
		type : 'POST',
		url : '/admin/comment/regist',
		dataType : 'text',
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
		},
		data : data,
		success : function(result) {
			alert('답변이 달렸습니다.');
			$("#listComment").append(result);
			$('.summernote').summernote('code', '');
		}
	});
}

function contactCommentDelete(contactCommentNo) {
	var data = "contactCommentNo=" + contactCommentNo;
	var result = confirm('답변을 삭제하시겠습니까?');
	if (result) {
		$.ajax({
			type : 'GET',
			url : '/admin/comment/delete',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				$("#contact-comment-whole-wrapper-"+contactCommentNo).remove();
			}
		});
	}
}

function passwordCheck(checkbox){
	console.log(checkbox);
	var check = $(checkbox).prop("checked");
	if(check) {
		$('#password').prop('disabled', false);
	} else {
		$('#password').prop('disabled', true);
	}
}

function contactPasswordCheck(contactNo) {
	console.log("here");
	var data = $('.contact-password-form').serialize();
	console.log(contactNo);
	$.ajax({
		type : 'POST',
		url : '/contact/password/check',
		dataType : 'text',
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
		},
		data : data,
		success : function(result) {
			var list = $.parseJSON(result);
			console.log(list);
			if(Number(list) >= 1) {
				location.href = "/contact/view?contactNo="+contactNo;
			} else {
				$('#password').val('');
				alert('비밀번호가 틀립니다!');
				return;
			}
		}

	});
}

function passwordIsCheck(contactNo){
	$.ajax({
		type : "GET",
		url : "/contact/password/is",
		data: "contactNo="+contactNo,
		success : function(result) {
			console.log("success");
			$("#contact-password-dialog").html(result);
		}
	});
	
	$("#contact-password-dialog").dialog({
		autoOpen: true,
		modal: true,
		position: { my: "left", at: "right", of: "#secret-label" },
		title: "비밀번호"
	});
}
