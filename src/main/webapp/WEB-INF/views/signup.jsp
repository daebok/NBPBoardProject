<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<form action="insert" method="get">
	아이디: <input type="text" name="id"/> <br/>
	비밀번호: <input type="text" name="password"/> <br/>
	이름: <input type="text" name="name"/> <br/>
	<input type="submit"/> 
</form>
</body>
</html>
