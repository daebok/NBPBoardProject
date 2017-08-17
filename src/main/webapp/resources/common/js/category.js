var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

function categoryRegist() {
	var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?<>]/gi;
	var blank_pattern = /[\s]/g;
	
	var categoryItem = $("#category-item").val();
	if (blank_pattern.test(categoryItem) == true) {
		alert("카테고리 항목을 입력하세요.");
		$("#category-item").focus();
		return;
	}
	if (special_pattern.test(categoryItem) == true) {
		alert("카테고리에 특수문자를 입력 할 수 없습니다.");
		$("#category-item").focus();
		return;
	}
	var result = confirm('카테고리를 추가하시겠습니까?');
	if (result) {
		$.ajax({
			type: 'POST', 
			url: '/admin/categoryCheck', 
			dataType : 'text',
			beforeSend : function(xhr){
				xhr.setRequestHeader(header, token);
			},
			data: $('#category-add-form').serialize(),
			success: function (result) {
				var list = $.parseJSON(result);
				if(parseInt(list) > 0) {
					alert("카테고리가 이미 존재합니다.");
					$("#category-item").focus();
					return;
				} else {
					document.form.action = "/admin/categoryAdd"
					document.form.submit();
				}
			}
		});
	} 
}

function categoryDelete(categoryNo){
	var data = "categoryNo=" + categoryNo;
	var form = $('#form-'+categoryNo);
	$.ajax({
		type : 'GET',
		url : '/admin/categoryCount',
		dataType : 'text',
		processData : false,
		contentType : false,
		data : data,
		success : function(result) {
			var list = $.parseJSON(result);
			console.log(list);
			if(list > 0) {
				var result = confirm(list+ '개의 게시글이 있습니다. 그래도 카테고리를 삭제 하시겠습니까? \n(카테고리를 삭제해도 기존의 글은 남게 됩니다.)');
				if (result) {
					form.action = "/admin/categoryDelete"
					form.submit();
				} 
			} else {
				var result = confirm('카테고리를 삭제 하시겠습니까?');
				if (result) {
					form.action = "/admin/categoryDelete"
					form.submit();
				} 
			}
		}
	});
}