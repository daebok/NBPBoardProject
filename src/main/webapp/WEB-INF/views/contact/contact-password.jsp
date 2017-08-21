<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="margin-left:20px;">
	<form:form name="form" method="post" class="contact-password-form form-horizontal">
		<div class="form-group">
			<input type="password" name="contactPassword" maxlength="4" id="password" size="4" class="form-control" style="width:200px;"/>
		</div>
		<input type="hidden" name="contactNo" value="${contact}">
		<div class="pull-left">
			<button type="button" class="btn btn-default" onclick="contactPasswordCheck(${contact})" style="float:left;">확인</button>
		</div>
	</form:form>
</div>