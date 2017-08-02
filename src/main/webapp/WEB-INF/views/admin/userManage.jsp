<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<title>Home</title>
<script>
	$(document).ready(function() {
		$(".user-authority").click(function() {
			var userNo = $(this).attr('user-no');
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
		});
		$(".user-delete").click(function() {
			var userNo = $(this).attr('user-no');
			var result = confirm('회원을 삭제하시겠습니까?');
			if (result) {
				var data = "userNo=" + userNo;
				$.ajax({
					type : 'GET',
					url : '/admin/userDelete',
					dataType : 'text',
					processData : false,
					contentType : false,
					data : data,
					success:function(result){
						$("#"+userNo).hide();
					}
				});
			}
		});
	});
</script>
<style>
	.list-group-item{
		overflow:hidden;
	}
	.list-1{
		float:left;
		width:25%;
	}
	.list-2{
		float:left;
		width:25%;
	}
	.list-3{
		float:left;
		width:25%;
	}
	.list-4{
		float:left;
		width:25%;
	}
</style>
</head>
<body>
	<!-- header start -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">회원 정보</div>
			<div class="panel-body">
				<p>회원 정보를 볼 수 있고, 회원을 관리할 수 있습니다.</p>
			</div>
			<div class="list-group">
				<div class="list-group-item">
					<div class="list-1">아이디</div>
					<div class="list-2">이름</div>
					<div class="list-3">등급</div>
					<div class="list-4">삭제</div>
				</div>
					<c:forEach var="user" items="${userList}">
						<form name="form" method="get" action="/admin/categoryDelete" class="form-horizontal" id="${user.userNo}">
							<div class="list-group-item">
								<div class="list-1">${user.userId}</div>
								<div class="list-2">${user.userName}</div>
								<div class="list-3">
									<select name="userAuthority">
										<c:choose>
											<c:when test="${user.userAuthority == 'ROLE_ADMIN'}">
												<option selected value="ROLE_ADMIN">관리자</option>
												<option value="ROLE_USER">회원</option>
											</c:when>
											<c:otherwise>
												<option value="ROLE_ADMIN">관리자</option>
												<option selected value="ROLE_USER">회원</option>
											</c:otherwise>
										</c:choose>
									</select>
									<button type="button" class="user-authority btn btn-default" user-no="${user.userNo}">ok</button>
								</div>
								<div class="list-4">
									<button type="button" class="user-delete btn btn-success" user-no="${user.userNo}">Delete</button>
								</div>
							</div>
						</form>
					</c:forEach>
			</div>
		</div>
	</div>
</body>
</html> 