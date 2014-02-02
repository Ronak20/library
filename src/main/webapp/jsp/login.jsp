<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Login Page</title>
<!-- <link rel="stylesheet" href="css/bootstrap.min.css">
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-theme.min.css"></script> -->


<!-- <link rel="stylesheet" href="css/jquery-ui.min.css" />
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui.min.js"></script> -->


</head>

<body>
	<form id="loginForm" action="login" method="post">
		<table align="center">
			<tr>
				<td>Enter Username <input type="text" name="username" /></td>
			</tr>
			<tr>
				<td>Enter Password <input type="text" name="password" /></td>
			</tr>
			<tr>
				<td align="center"><input type="submit" name="loginSubmit"
					value="submit"></td>
			</tr>
		</table>
	</form>
</body>
</html>
