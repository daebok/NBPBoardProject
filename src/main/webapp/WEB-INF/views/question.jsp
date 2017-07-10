<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<form action="question/ask" method="post">
	Title <input type="text" name="title"/> <br/>
	<textarea cols="100" rows="30" name="content"></textarea> <br/>
	<input type="submit"/> 
</form>
</body>
</html>
