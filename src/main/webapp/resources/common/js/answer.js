var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ajaxStart(function() {
	$('html').css("cursor", "wait");
});
$(document).ajaxStop(function() {
	$('html').css("cursor", "auto");
});

$('.answer-summernote').summernote({
	height : 200,
	onImageUpload : function(files,editor, welEditable) {
			sendFile(files[0], editor,welEditable);
	}
});

function createCommentTextArea(commentNo, boardNo, commentViewButton){
	$(commentViewButton).prop("disabled",true);
	var str="";
	str += '<div class="comment-write">';
	str += '<form name="form" id="comment-form">';
	str += '<input type="hidden" name="boardNo" value="'+boardNo+'">';
	str += '<input type="hidden" name="commentNo" value="'+commentNo+'">';
	str += '<textarea cols="40" size="5" class="comment-textarea" id="comment-textarea" name="commentContent"></textarea><br>';
	str += '<div class="pull-right"><button type="button" class="btn btn-default btn-sm" onclick="commentRegist('+commentNo+', event)">Ok</button>';
	str += '<button type="button" class="btn btn-default btn-sm" onclick="commentCancel('+commentNo+')">Cancel</button></div>';
	str += '</form></div>';
	$('#answer-'+commentNo+' > .comment-wrapper').append(str);
}

function answerRegist(answerButton, event){

	event.preventDefault();
	var commentContent = $('#comment-content').val();

	if($('.btn-codeview').hasClass('active')){
		alert('codeView 상태에서는 저장을 하실 수 없습니다.')
		
		$('.note-editable').trigger('focus');
		return;
	}

	var htmlRemoveContent = commentContent.replace(/<(\/?)p>/gi,"");
	htmlRemoveContent = htmlRemoveContent.replace(/<br[^>]*>/gi, "");
	htmlRemoveContent = htmlRemoveContent.replace(/&nbsp;/g, "");
	htmlRemoveContent = htmlRemoveContent.replace(/(\s*)/g, "");
	if (htmlRemoveContent == "") {
		alert("내용를 입력하세요.");
		$('.note-editable').trigger('focus');
		return;
	}
	
	var data = $('.answer-form').serialize()
	var answerButton = $(answerButton);
	if(answerButton.html() == 'Answer'){
		$.ajax({
			type : 'POST',
			url : '/comment/regist',
			dataType : 'text',
			beforeSend: function(xhr){
				xhr.setRequestHeader(header, token);
			},
			data : data,
			success : function(result) {
				alert('답변이 달렸습니다.');
				$(".empty-answer").remove();
				$("#listComment *").remove();
				$("#listComment").append(result);
				$('.note-editable').empty();
				
				$('#answer-tab a[href="#newest"]').tab('show');
			}
		});
	} else if (answerButton.html() == 'Modify') {
		var commentNo = answerButton.prop('comment-no');
		$.ajax({
			type : 'POST',
			url : '/comment/modify',
			dataType : 'text',
			beforeSend: function(xhr){
				xhr.setRequestHeader(header, token);
			},
			data : $('.answer-form').serialize(),
			success : function(result) {
				alert('답변이 수정되었습니다.');
				$("#content-"+commentNo).empty().html(commentContent);
				$('.note-editable').empty();
				answerButton.html('Answer')
				$('#answer-content-'+commentNo).css('background-color','#ffffff');
				$('.answer-button').prop("disabled",false);
				
				$('#answer-tab a[href="#newest"]').tab('show');
			}
	});
	}
	return false;
}

function answerDelete(commentNo){
	var data = "commentNo=" + commentNo;
	var result = confirm('답변을 삭제하시겠습니까?');
	if (result) {
		$.ajax({
			type : 'GET',
			url : '/comment/delete',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				$('#answer-tab a[href="#newest"]').tab('show');
				$("#answer-wrapper-"+commentNo).remove();
			}
		});
	}
}

function answerModify(commentNo){
	var result = confirm('답변을 수정하시겠습니까?');
	var data = "commentNo=" + commentNo;
	if (result) {
		$.ajax({
			type : 'GET',
			url : '/comment/select',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				var list = $.parseJSON(result);
				var answerRegistButton = $('#answer-regist-button');
				var summernoteEditor = $('.note-editable');
				$('#answer-tab a[href="#newest"]').tab('show');
				$('#answer-content-'+commentNo).css('background-color','#ededed');
				$('#answer-no').val(commentNo);
				$('.answer-button').prop("disabled",true);
				answerRegistButton.empty().html('Modify');
				answerRegistButton.prop('comment-no',commentNo);
				summernoteEditor.empty().html(list.commentContent);
				summernoteEditor.trigger('focus');
			}
		});
	}
}

function commentCancel(commentNo){
	$('.comment-add').prop("disabled",false);
	$('#answer-button').prop("disabled",false);
	$('.comment-list-button > *').prop("disabled",false);
	$('.comment-write').remove();
}

function commentRegist(commentNo, event){
	event.preventDefault();
	var result = confirm('답변에 댓글을 달겠습니까?');
	var data = $('#comment-form').serialize();
	console.log(data)
	if (result) {
		$.ajax({
			type : 'POST',
			url : '/comment/regist',
			dataType : 'text',
			beforeSend: function(xhr){
				xhr.setRequestHeader(header, token);
			},
			data : data,
			success : function(result) {
				$('#answer-'+commentNo+' > .comment-wrapper').children().remove();
				$('.comment-write').remove();
				$('.comment-add').prop("disabled",false);
				var commentViewButton = $('#comment-view-'+commentNo);
				var count = parseInt(commentViewButton.html());
				commentViewButton.val('closed');
				commentViewButton.empty().html((count+1) + " Comment ▲");
				commentViewButton.click();
			}
		});
	}
}

function commentListView(commentNo, commentListButton){
	var commentListButton = $(commentListButton);
	$('.comment-write').remove();
	$('.comment').prop("disabled",false);
	$('.comment-add').prop("disabled",false);
	var count = parseInt($(commentListButton).html());
	
	if(commentListButton.val() == 'closed'){
		commentListButton.empty().html(count + " Comment ▲");
		commentListButton.val('open');
		var data = "commentNo=" + commentNo;
		$.ajax({
			type : 'GET',
			url : '/comment/comment/select',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				$('#answer-'+commentNo+' > .comment-wrapper').append(result).hide().slideDown("fast");
			}
		});
	} else if(commentListButton.val() == 'open'){
		commentListButton.empty().html(count + " Comment ▼");
		commentListButton.val('closed');
		$('#answer-'+commentNo+' > .comment-wrapper').children().remove();
	}
}

function goToCommentModify(commentNo){
	var data = "commentNo=" + commentNo;
	$.ajax({
		type : 'GET',
		url : '/comment/select',
		dataType : 'text',
		processData : false,
		contentType : false,
		data : data,
		success : function(result) {
			var list = $.parseJSON(result);
			$('#answer-button').prop("disabled",true);
			$('.comment-list-button > *').prop("disabled",true);
			$(this).prop("disabled",true);
			
			var str = "";
			str += '<div class="comment-write">';
			str += '<form name="form" id="comment-form">';
			str += '<input type="hidden" name="commentNo" value="'+commentNo+'">';
			str += '<textarea cols="40" size="5" class="comment-textarea" id="comment-textarea" name="commentContent">'+list.commentContent+'</textarea><br>';
			str += '<div class="pull-right"><button type="button" class="btn btn-default btn-sm" onclick="commentModify('+commentNo+')">Ok</button>';
			str += '<button type="button" class="btn btn-default btn-sm" onclick="commentCancel('+commentNo+')">Cancel</button></div>';
			str += '</form></div>';
			
			$('#answer-comment-' + commentNo ).after(str);
		}
	});
}

function commentModify(commentNo){
	var commentContent = $('#comment-textarea').val();
	$.ajax({
		type : 'POST',
		url : '/comment/modify',
		dataType : 'text',
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
		},
		data : $('#comment-form').serialize(),
		success : function(result) {
			alert('답글이 수정되었습니다.');
			$("#comment-text-"+commentNo).empty().html(commentContent);
			$('#answer-content-'+commentNo).css('background-color','#ffffff');
			$('#answer-button > *').prop("disabled",false);
			$('.comment-list-button > *').prop("disabled",false);
			$('.comment-write').remove();
		}
	});
}

function commentDelete(answerNo, commentNo) {
	var data = "commentNo=" + commentNo;
	var result = confirm('댓글을 삭제하시겠습니까?');
	if (result) {
		$.ajax({
			type : 'GET',
			url : '/comment/delete',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				$("#answer-comment-"+commentNo).remove();
				var commentViewButton = $('#comment-view-'+answerNo);
				var count = parseInt(commentViewButton.html());
				commentViewButton.empty().html((count-1) + " Comment ▲");
			}
		});
	}
}

function anwserLike(commentNo, answerLikeButton) {
	var data = "commentNo=" + commentNo;
	var check = $(answerLikeButton).prop('id');
	
	var answerLikeButton = $(answerLikeButton);
	var answerLikeCount = $('#answer-like-count-'+commentNo);
	
	if(check=="answer-like-uncheck") {
		answerLikeButton.prop('id','answer-like-check');
		answerLikeButton.css('color','#FF3636');
		$.ajax({
			type : 'GET',
			url : '/comment/like',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				var count = answerLikeCount.html();
				answerLikeCount.empty().html(Number(count)+1);
			}
		});
	} else {
		answerLikeButton.prop('id','answer-like-uncheck');
		answerLikeButton.css('color','#eee');
		$.ajax({
			type : 'GET',
			url : '/comment/hate',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				var count = answerLikeCount.html();
				answerLikeCount.empty().html(Number(count)-1);
			}
		});
	}
}
