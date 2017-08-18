/* 유저 정보 다이얼로그 */
function userInfo() {
	$.ajax({
		type : "GET",
		url : "/user/info",
		success : function(result) {
			$("#user-info-dialog").html(result);
		}
	});
	$("#user-info-dialog").dialog({
			autoOpen: true,
			modal: true,
			resizable:false,
			width:300,
			position: { my: "top", at: "bottom", of: "#user-info-button" },
			title: "회원 정보"
	});
}

