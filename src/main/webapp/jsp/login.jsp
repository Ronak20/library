<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Login Page</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->

<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>


<!-- <link rel="stylesheet" href="css/jquery-ui.min.css" />
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui.min.js"></script> -->


</head>

<body>
	<div>
		<form id="loginForm" action="login" method="post">
			<table align="center">
				<tr>
					<td>Enter Username : </td>
					<td><input type="text" name="username"/></td>
				</tr>
				<tr>
					<td>Enter Password  : </td>
					<td><input type="password" name="password"/></td>
				</tr>
				<tr>
					<td align="center"><input type="submit" name="loginSubmit"
						value="submit"></td>
				</tr>
			</table>
		</form>
	</div>
	<div align="center">
		<c:out value="${errorMessage}"></c:out>
	</div>
</body>
</html>
