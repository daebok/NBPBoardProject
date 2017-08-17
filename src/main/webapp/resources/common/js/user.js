var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

function login(){
	var userId = $("#userId").val();
	var userPassword = $("#userPassword").val();
	if (userId == "") {
		alert("아이디를 입력하세요.");
		$("#userId").focus();
		return;
	}
	if (userPassword == "") {
		alert("비밀번호를 입력하세요.");
		$("#userPassword").focus();
		return;
	}
	document.form.action = "/loginProcess"
	document.form.submit();
}

function presentPasswordCheck(passwordCheckButton){
	console.log("here!!");
	$.ajax({
		type : 'POST',
		url : '/user/password/check',
		dataType : 'text',
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
		},
		data : $('#password-check-form').serialize(),
		success : function(result) {
			var list = $.parseJSON(result);
			if(list) {
				$(passwordCheckButton).prop("disabled",true);
				$('#userPassword').prop("disabled",true);
				$('#newUserPassword').prop("disabled",false);
				$('#newReUserPassword').prop("disabled",false);
				$('#password-change-button').prop("disabled",false);
			} else {
				alert('비밀번호가 맞지 않습니다!')
				$('#userPassword').val('');
				return;
			}
		}
	});
}

var idCheck = 0;
var passwordCheck = 0;
function rePasswordCheck() {
	var userPassword = $('#newUserPassword').val();
	var reUserPassword = $('#newReUserPassword').val();
	if (reUserPassword == "" && (userPassword != reUserPassword || userPassword == reUserPassword)) {
		$("#user-button").prop("disabled", true);
		$("#user-button").css("background-color", "#aaaaaa");
		$("#newReUserPassword").css("background-color", "#FFCECE");
	} else if (userPassword == reUserPassword) {
		$("#newReUserPassword").css("background-color", "#B0F6AC");
		passwordCheck = 1;
		if (idCheck == 1 && passwordCheck == 1) {
			$("#user-button").prop("disabled", false);
			$("#user-button").css("background-color", "#4CAF50");
		}
	} else if (userPassword != reUserPassword) {
		passwordCheck = 0;
		$("#user-button").prop("disabled", true);
		$("#user-button").css("background-color", "#aaaaaa");
		$("#newReUserPassword").css("background-color", "#FFCECE");

	}
}

function rePasswordCheck2() {
	var userPassword = $('#newUserPassword').val();
	var reUserPassword = $('#newReUserPassword').val();
	if (reUserPassword == "" && (userPassword != reUserPassword || userPassword == reUserPassword)) {
		$("#user-button").prop("disabled", true);
		$("#user-button").css("background-color", "#aaaaaa");
		$("#newReUserPassword").css("background-color", "#FFCECE");
	} else if (userPassword == reUserPassword) {
		$("#newReUserPassword").css("background-color", "#B0F6AC");
		passwordCheck = 1;
		if (passwordCheck == 1) {
			$("#user-button").prop("disabled", false);
			$("#user-button").css("background-color", "#4CAF50");
		}
	} else if (userPassword != reUserPassword) {
		passwordCheck = 0;
		$("#user-button").prop("disabled", true);
		$("#user-button").css("background-color", "#aaaaaa");
		$("#newReUserPassword").css("background-color", "#FFCECE");
	}
}

function idDuplicationCheck() {
	var userId = $('#userId').val();
	var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
	if (special_pattern.test(userId) == true || userId == " ") {
		alert('특수문자 및 공백은 사용할 수 없습니다.');
		$('#userId').val(userId.substring(0, userId.length - 1));
		return false;
	}
	$.ajax({
		data : userId,
		contentType : "application/json; charset=utf-8",
		dataType: "json",
		type : "POST",
		url : "/user/duplicationId",
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (userId == "" && data == 0) {
				$("#user-button").prop("disabled", true);
				$("#user-button").css("background-color", "#aaaaaa");
				$("#userId").css("background-color", "#FFCECE");
				idCheck = 0;
			} else if (data == 0) {
				$("#userId").css("background-color", "#B0F6AC");
				idCheck = 1;
				if (idCheck == 1 && passwordCheck == 1) {
					$("#user-button").prop("disabled", false);
					$("#user-button").css("background-color", "#4CAF50");
				}
			} else if (data == 1) {
				$("#user-button").prop("disabled", true);
				$("#user-button").css("background-color", "#aaaaaa");
				$("#userId").css("background-color", "#FFCECE");
				idCheck = 0;
			}
		}
	});
}

function cancel() {
	$(".cancelbtn").click(function() {
		$(".userId").val('');
		$(".userPassword").val('');
		$("#user-button").prop("disabled", true);
		$("#user-button").css("background-color", "#aaaaaa");
	});
}

function userAuthorityModify(userNo) {
	var userAuthority = $("select[name=userAuthority] option:selected").val();
	var result = confirm('권한을 수정하시겠습니까?');
	if (result) {
		var data = "userNo=" + userNo +"&userAuthority="+userAuthority;
		$.ajax({
			type : 'GET',
			url : '/admin/userModify',
			dataType : 'text',
			processData : false,
			contentType : false,
			data : data
		});
	}
}

function userDelete(userNo){
	var result = confirm('회원을 삭제하시겠습니까?');
	if (result) {
		var result2 = confirm('회원이 작성한 게시물을 모두 삭제하시겠습니까?\n(취소를 누르면 게시글은 남고 회원만 삭제됩니다.)');
		var data = "userNo=" + userNo;
		if(result2){
			$.ajax({
				type : 'GET',
				url : '/admin/userDelete',
				dataType : 'text',
				processData : false,
				contentType : false,
				data : data,
				success:function(result){
					$("#"+userNo).remove();
				}
			});
		} else {
			$.ajax({
				type : 'GET',
				url : '/admin/onlyUserDelete',
				dataType : 'text',
				processData : false,
				contentType : false,
				data : data,
				success:function(result){
					$("#"+userNo).remove();
				}
			});
		}
	}
}
