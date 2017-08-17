<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<div class="user-info container">
		<c:choose>
			<c:when test="${user.userId eq null}">
				<h6>해당 사용자가 없습니다.</h6>
			</c:when>
			<c:otherwise>
				<form name="form" method="get" class="form-horizontal">
					<div class="list-group-item">
						<h6><b>아이디 :</b> ${user.userId}</h6>
						<h6><b>이름 :</b>${user.userName}</h6>
						<div>
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
							<button type="button" class="btn btn-default btn-xs" onclick="userAuthorityModify(${user.userNo})">OK</button>
						</div>
						<div>
							<button type="button" class="btn btn-success btn-xs" onclick="userDelete(${user.userNo})">Delete</button>
						</div>
					</div>
				</form>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>
<link href="<c:url value='/resources/common/css/user.css' />">
