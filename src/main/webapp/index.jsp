<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<body>
<h2>Please Login!</h2>
<%


	%><%@include file="jsp/createBook.jsp" %><% 
    response.sendRedirect("/LibraryManagement/book");
%>
</body>
</html>
