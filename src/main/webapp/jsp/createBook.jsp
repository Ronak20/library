<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Book</title>
</head>
<body>

<form method="post" action="book">
<table align="center">
	<tr><td>Book Name</td><td><input type="text" name="bookname"></td></tr>
	<tr><td>Copies</td><td><input type="text" name="copies"></td></tr>
	<tr><td>isbn</td><td><input type="text" name="isbn"></td></tr>
	<tr><td colspan="2"><input type="submit"></td></tr>
</table>
</form>
</body>
</html>