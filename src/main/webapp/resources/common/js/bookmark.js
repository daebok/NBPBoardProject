var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
	
function bookmarkCheck(boardNo, bookmarkCheck) {
	var data = "boardNo=" + boardNo;
	var check = $(bookmarkCheck).attr('id');
	
	if(check == "book-mark-uncheck"){
		$(bookmarkCheck).attr('id','book-mark-check');
		$(bookmarkCheck).css('color','#FF3636');
		$.ajax({
			type : 'GET',
			url : '/board/bookmark',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data
		});
	} else {
		var result = confirm('즐겨찾기를  해제 하시겠습니까? \n기존에 저장한 메모도 삭제 됩니다.');
		if (result) {
			$(bookmarkCheck).attr('id','book-mark-uncheck');
			$(bookmarkCheck).css('color','#888');
			$.ajax({
				type : 'GET',
				url : '/board/bookmark/uncheck',
				dataType : 'text',
				processData : false,
				contentType : false,
				data : data
			});
		}
	}
}

function bookMarkUnCheck(boardNo){
	var result = confirm('즐겨찾기를  해제 하시겠습니까? \n기존에 저장한 메모도 삭제 됩니다.');
	if (result) {
		var data = "boardNo=" + boardNo;
		$("#bookmark-list-"+boardNo).remove();
		$(this).css('color','#888');
		$.ajax({
			type : 'GET',
			url : '/board/bookmark/uncheck',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data
		});
	} 
}

function bookmarkMemoRegist(){
	event.preventDefault();
	$.ajax({
		type : 'POST',
		url : '/board/memo',
		dataType : 'text',
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
		},
		data : $('.memo-form').serialize(),
		success : function(result) {
			alert('메모가 저장되었습니다.');
		}
	});
	
	return false;
}


