<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@include file="layout/header.jsp"%>
<%@include file="layout/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add User</title>
</head>
<body>
<a href="<%=request.getContextPath()%>/jsp/admincontrol.jsp">Admin Panel</a>
<br>
<form method="post" action="../user">
<table align="center">
	<tr><td>Username</td><td><input type="text" name="username"></td></tr>
	<tr><td>First Name</td><td><input type="text" name="firstname"></td></tr>
	<tr><td>Last Name</td><td><input type="text" name="lastname"></td></tr>
	<tr><td>Password</td><td><input type="password" name="password"></td></tr>
	<tr><td>Role</td><td><input type="text" name="role"></td></tr>
	<tr><td colspan="2"><input type="submit"></td></tr>
</table>
</form>
</body>
</html>