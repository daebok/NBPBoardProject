$(document).ready(function() {
	$(".fileDrop").on("dragenter dragover", function(event) {
		event.preventDefault();
		var files = event.originalEvent.dataTransfer.files;
		var file = files[0];
		console.log(file);
	});
	$(".fileDrop").on("drop", function(event) {
		event.preventDefault();
		var files = event.originalEvent.dataTransfer.files;
		var file = files[0];
		console.log(file);

		var formData = new FormData();
		formData.append("file",file);

		$.ajax({
			url : '/upload/uploadAjax',
			data : formData,
			dataType : 'text',
			processData : false,
			contentType : false,
			type : 'POST',
			success : function(data) {
				var str="";
				console.log(data);
				if(checkImageType(data)){ 
					str = "<div><a href='/upload/displayFile?fileName="+getImageLink(data)+"' class='file'>"; // link
					str += "<img src='/upload/displayFile?fileName="+data+"'/></a>";
					str += "&nbsp;&nbsp;<a data-src="+data+" class='delete'>[삭제]</a>";
					str += "<input type='hidden' name='files' value='"+getImageLink(data)+"'> </div>";
				} else { 
					str = "<div><a href='/upload/displayFile?fileName="+data+"' class='file'>"+getOriginalName(data)+"</a>"
							+"&nbsp;&nbsp;<a data-src="+data+" class='delete'>[삭제]</a>";
					str += "<input type='hidden' name='files' value='"+data+"'> </div>";
				}
				$(".newUploadedList").append(str);

			}
		});
	});
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