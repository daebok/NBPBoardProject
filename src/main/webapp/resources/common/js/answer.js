var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

/* 답변 달기 */
$(document).on("click","#comment-button", function(event) {
	event.preventDefault();
	var commentContent = $('#comment-content').val();
	var data = $('.answer-form').serialize()
	if($(this).html() == 'Answer'){
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
				$('.summernote').summernote('code', '');
			}
		});
	} else if ($(this).html() == 'Modify') {
		var commentNo = $(this).attr('comment-no');
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
				$('.summernote').summernote('code', '');
				$('#comment-button').html('Comment');
				$('#'+commentNo).css('background-color','#ffffff');
				$('#comment-list > *').attr("disabled",false);
			}
	});
}
});
/* 답변 삭제 버튼 클릭 이벤트 */
$(document).on("click",".comment-delete", function() {
	var commentNo = $(this).attr("comment-no");
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
});
/* 답변 수정 버튼 클릭 이벤트 */
$(document).on("click",".comment-modify",function() {
	var commentNo = $(this).attr('comment-no');
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
				$('.summernote').summernote('code', list.commentContent);
				$('#comment-list > *').attr("disabled",true);
			}
		});
	}
});

/* 댓글  */
/* 1. 댓글 취소 */
$(document).on("click",".comment-comment-cancel-button",function() {
	var commentNo = $(this).attr('comment-no');
	$('.comment-comment').attr("disabled",false);
	$('.comment-comment-write').remove();
});
/* 2. 댓글 달기 */
$(document).on("click",".comment-comment-button",function() {
	var result = confirm('답변에 댓글을 달겠습니까?');
	var data = $('.comment-form').serialize();
	if (result) {
		var commentNo = $(this).attr('comment-no');
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
});

/* 3. 댓글 리스트 보기 */
$(document).on("click",".comment-comment-selct",function() {
	$('.comment-comment-write').remove();
	$('.comment-comment').attr("disabled",false);
	var commentNo = $(this).attr('comment-no');
	var count = parseInt($(this).html());
	if($(this).val() == 'closed'){
		$(this).html(count + " Comment ▲");
		$(this).val('open');
		var data = "commentNo=" + commentNo;
		$.ajax({
			type : 'GET',
			url : '/comment/comment/select',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data,
			success : function(result) {
				$('#comment-'+commentNo+' > .comment-comment-wrapper').append(result);
			}
		});
	} else if($(this).val() == 'open'){
		$(this).html(count + " Comment ▼");
		$(this).val('closed');
		$('#comment-'+commentNo+' > .comment-comment-wrapper').children().remove();
	}
});
/* 4. 댓글 수정 버튼 클릭 이벤트 */
$(document).on("click",".comment-comment-modify",function() {
	var commentNo = $(this).attr('comment-no');
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
				+ '<button type="button" class="comment-comment-modify-button btn btn-default" comment-no="'+commentNo+'">Ok</button></div>');
		}
	});
	
});
/* 5. 댓글 수정  */
$(document).on("click",".comment-comment-modify-button",function() {
	var commentContent = $('#comment-comment-textarea').val();
	var commentNo = $(this).attr('comment-no');
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
});

/* 댓글 삭제 버튼 클릭 이벤트 */
$(document).on("click",".answer-comment-delete", function() {
	var commentNo = $(this).attr("comment-no");
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
});

/* 답변 좋아요 */
$(document).on('click', '.answer-hate', function(){
	var commentNo = $(this).attr('comment-no');
	var data = "commentNo=" + commentNo;
	$(this).attr('class','answer-like glyphicon glyphicon-heart');
	$(this).css('color','#FF3636');
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
});

/* 답변 좋아요 해제 */
$(document).on('click', '.answer-like', function(){
	var commentNo = $(this).attr('comment-no');
	var data = "commentNo=" + commentNo;
	$(this).attr('class','answer-hate glyphicon glyphicon-heart');
	$(this).css('color','#eee');
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
});