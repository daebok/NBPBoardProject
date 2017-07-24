/* 파일 업로드에 관한 자바스크립트 */

/* 
 * ajax를 통해 이미지 업로드
 * 업로드 성공 시, 섬네일 및 링크 생성
 */
function getAjax(formData){
	$.ajax({
		url : '/upload/uploadAjax',
		data : formData,
		dataType : 'text',
		processData : false,
		contentType : false,
		type : 'POST',
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			var str="";
			if(checkImageType(data)){ 
				str = "<div><a href='/upload/displayFile?fileName="+getImageLink(data)+"' class='file'>"; // link
				str += "<img src='/upload/displayFile?fileName="+data+"'/></a>";
				str += "&nbsp;&nbsp;<a data-src="+data+" class='delete'>[삭제]</a>";
				str += "<input type='hidden' name='boardFiles' value='"+getImageLink(data)+"'> </div>";
			} else { 
				str = "<div><a href='/upload/displayFile?fileName="+data+"' class='file'>"+getOriginalName(data)+"</a>"
						+"&nbsp;&nbsp;<a data-src="+data+" class='delete'>[삭제]</a>";
				str += "<input type='hidden' name='boardFiles' value='"+data+"'> </div>";
			}
			$(".newUploadedList").append(str);
		}
	});
}

/* 1. summernote를 통해 이미지 업로드 */
function sendFile(file, el) {
	var formData = new FormData();
	formData.append('file', file);
	
	getAjax(formData);
}
$(document).ready(function() {
	/* 2. 드래그를 통해 파일 업로드 */
	$(".fileDrop").on("dragenter dragover", function(event) {
		event.preventDefault();
		var files = event.originalEvent.dataTransfer.files; // 전달된 파일 데이터 업로드
		var file = files[0];
	});
	$(".fileDrop").on("drop", function(event) {
		event.preventDefault();
		var files = event.originalEvent.dataTransfer.files; // 전달된 파일 데이터 업로드
		var file = files[0];

		var formData = new FormData();
		formData.append("file",file);
		getAjax(formData);
	});

	/* 3. file버튼을 통해 파일 업로드 */
	$(".fileButton").on("change", function(event) {
		var formData = new FormData($('#registerForm'));
		formData.append("file",$(".fileButton")[0].files[0]);
		getAjax(formData);
		
	});
	
	/* [삭제]버튼 누르면, 업로드 된 파일 삭제 */
	$(".newUploadedList").on("click","a",function(event){
		var that = $(this);
		
		$.ajax({
			url: "/upload/deleteFile",
			type:"post",
			data: {fileName: $(this).attr("data-src")},
			dataType: "text",
			success:function(result){
				if(result == 'deleted'){
					that.parent('div').remove();
				}
			}
		});
	});
});