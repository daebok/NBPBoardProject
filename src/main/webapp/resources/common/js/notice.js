$(document).ready(function() {
	$('.notice-summernote').summernote({
		height : 200,
		width: 100
	});
});

function noticeRegist() {
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

	document.form.submit();
}

function noticeDelete(noticeNo) {
	var result = confirm('공지사항을 삭제하시겠습니까?');
	if (result) {
		location.replace('/admin/notice/delete?noticeNo='+noticeNo);
	} 
}