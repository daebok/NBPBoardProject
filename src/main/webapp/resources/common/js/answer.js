var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ajaxStart(function() {
	$('html').css("cursor", "wait");
});
$(document).ajaxStop(function() {
	$('html').css("cursor", "auto");
});

$(document).ready(
	function() {
		$('.memo-summernote').summernote({
			height : 500
		});
	}
);

function createCommentTextArea(commentNo, boardNo, commentViewButton){
	$(commentViewButton).attr("disabled",true);
	var str="";
	str += '<div class="comment-comment-write">';
	str += '<form name="form" class="comment-form">';
	str += '<input type="hidden" name="boardNo" value="'+boardNo+'">';
	str += '<input type="hidden" name="commentNo" value="'+commentNo+'">';
	str += '<textarea cols="60" size="8" class="comment-comment-textarea" id="comment-comment-textarea" name="commentContent"></textarea>';
	str += '<div><button type="button" class="btn btn-default btn-sm" onclick="commentRegist('+commentNo+')">Ok</button>';
	str += '<button type="button" class="btn btn-default btn-sm" onclick="commentCancel('+commentNo+')">Cancel</button></div>';
	str += '</form></div>';
	$('#comment-'+commentNo+' > .comment-comment-wrapper').append(str);
}


function answerRegist(answerButton, event){
	event.preventDefault();
	var commentContent = $('#comment-content').val();
	var data = $('.answer-form').serialize()
	if($(answerButton).html() == 'Answer'){
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
				$(".emptyContent").remove();
				$('#answer-tab a[href="#newest"]').tab('show');
				$('#answer-tab a:first').tab('show');
				$("#listComment").append(result);
				$('.answer-summernote').summernote('code', '');
			}
		});
	} else if ($(answerButton).html() == 'Modify') {
		var commentNo = $(answerButton).attr('comment-no');
		$.ajax({
			type : 'POST',
			url : '/comment/modify',
			dataType : 'text',
			beforeSend: function(xhr){
				xhr.setRequestHeader(header, token);
			},
			data : $('.answer-form').serialize(),
			success : function(result) {
				alert('답글이 수정되었습니다.');
				$("#content-"+commentNo).html(commentContent);
				$('.answer-summernote').summernote('code', '');
				$('#comment-button').html('Comment');
				$('#'+commentNo).css('background-color','#ffffff');
				$('#comment-list > *').attr("disabled",false);
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
				$('#answer-tab a:first').tab('show');
				$("#whole-wrapper-"+commentNo).remove();
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
				$('#answer-tab a[href="#newest"]').tab('show');
				$('#answer-tab a:first').tab('show');
				$('#'+commentNo).css('background-color','#ededed');
				$('#comment-button').html('Modify');
				$('#comment-button').attr('comment-no',commentNo);
				$('#comment-list > *').attr("disabled",true);
				$('.answer-summernote').focus();
				$('.answer-summernote').summernote('code', list.commentContent);
			}
		});
	}
}

function commentCancel(commentNo){
	$('.comment-comment').attr("disabled",false);
	$('.comment-comment-write').remove();
}

function commentRegist(commentNo){
	var result = confirm('답변에 댓글을 달겠습니까?');
	var data = $('.comment-form').serialize();
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
				var count = parseInt($('#comment-view-'+commentNo).html());
				$('.comment-comment-write').remove();
				$('.comment-comment').attr("disabled",false);
				$('#comment-'+commentNo+' > .comment-comment-wrapper').children().remove();
				$('#comment-view-'+commentNo).val('closed');
				$('#comment-view-'+commentNo).html((count+1) + " Comment ▲");
				$('#comment-view-'+commentNo).click();
			}
		});
	}
}

function commentListView(commentNo, commentListButton){
	$('.comment-comment-write').remove();
	$('.comment-comment').attr("disabled",false);
	var count = parseInt($(commentListButton).html());
	if($(commentListButton).val() == 'closed'){
		$(commentListButton).html(count + " Comment ▲");
		$(commentListButton).val('open');
		var data = "commentNo=" + commentNo;
		$.ajax({
			type : 'GET',
			url : '/comment/comment/select',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				$('#comment-'+commentNo+' > .comment-comment-wrapper').append(result).hide().slideDown("fast");
			}
		});
	} else if($(commentListButton).val() == 'open'){
		$(commentListButton).html(count + " Comment ▼");
		$(commentListButton).val('closed');
		$('#comment-'+commentNo+' > .comment-comment-wrapper').children().remove();
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
			$('#'+commentNo).css('background-color','#ededed');
			$('#comment-button').attr('comment-no',commentNo);
			$('#comment-list > *').attr("disabled",true);
			$('.comment-list-list > *').attr("disabled",true);
			$(this).attr("disabled",true);
			$('#answer-comment-' + commentNo ).after('<div class="comment-comment-write">'
				+ '<textarea cols="50" class="comment-comment-textarea" id="comment-comment-textarea">'+list.commentContent+'</textarea>'
				+ '<button type="button" class="btn btn-default" onclick="commentModify('+commentNo+')">Ok</button></div>');
		}
	});
}

function commentModify(commentNo){
	var commentContent = $('#comment-comment-textarea').val();
	$.ajax({
		type : 'POST',
		url : '/comment/modify',
		dataType : 'text',
		processData : false,
		contentType : false,
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
		},
		data : $('.comment-form').serialize(),
		success : function(result) {
			alert('답글이 수정되었습니다.');
			$("#comment-comment-text-"+commentNo).html(commentContent);
			$('#comment-button').html('Comment');
			$('#'+commentNo).css('background-color','#ffffff');
			$('#comment-list > *').attr("disabled",false);
			$('.comment-list-list > *').attr("disabled",false);
			$('.comment-comment-write').remove();
		}
	});
}

function commentDelete(commentNo) {
	var data = "commentNo=" + commentNo;
	var result = confirm('댓글을 삭제하시겠습니까?');
	if (result) {
		$.ajax({
			type : 'GET',
			url : '/comment/comment/delete',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				$("#answer-comment-"+commentNo).remove();
			}
		});
	}
}

function anwserLike(commentNo, answerLikeButton) {
	var data = "commentNo=" + commentNo;
	var check = $(answerLikeButton).attr('id');
	
	if(check=="answer-like-uncheck") {
		$(answerLikeButton).attr('id','answer-like-check');
		$(answerLikeButton).css('color','#FF3636');
		$.ajax({
			type : 'GET',
			url : '/comment/like',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				var count = $('#answer-like-count-'+commentNo).html();
				$('#answer-like-count-'+commentNo).html(Number(count)+1);
			}
		});
	} else {
		$(answerLikeButton).attr('id','answer-like-uncheck');
		$(answerLikeButton).css('color','#eee');
		$.ajax({
			type : 'GET',
			url : '/comment/hate',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				var count = $('#answer-like-count-'+commentNo).html();
				$('#answer-like-count-'+commentNo).html(Number(count)-1);
			}
		});
	}
}
