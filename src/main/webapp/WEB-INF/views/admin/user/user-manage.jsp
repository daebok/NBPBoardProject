<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>

	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">회원 정보</div>
			<div class="panel-body">
				<p>회원 정보를 볼 수 있고, 회원을 관리할 수 있습니다.</p>
				<form class="user-search-form" method="get">
					<label>아이디</label>
					<input type="text" name="userId" size="20">
					<input type="button" onclick="userInfoSearch()" id="user-search-button" class="btn btn-default btn-sm" value="검색">
				</form>
				<div id="user-info-search-dialog"></div>
			</div>
			<div class="list-group">
				<div class="list-group-item">
					<div class="list-1">아이디</div>
					<div class="list-2">이름</div>
					<div class="list-3">등급</div>
					<div class="list-4">삭제</div>
				</div>
				<c:forEach var="user" items="${userList}">
					<form name="form" method="get" class="form-horizontal" id="${user.userNo}">
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
								<button type="button" class="btn btn-default" onclick="userAuthorityModify(${user.userNo})">OK</button>
							</div>
							<div class="list-4">
								<button type="button" class="btn btn-success" onclick="userDelete(${user.userNo})">Delete</button>
							</div>
						</div>
					</form>
				</c:forEach>
			</div>
		</div>
		<custom:paging uri="/admin/user"/>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
</body>
</html> 

<script src="<c:url value="/resources/common/js/user.js" />"></script>
<link href="<c:url value="/resources/common/css/user.css" />" rel="stylesheet">