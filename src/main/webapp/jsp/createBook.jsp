<%@ page import="com.library.model.Book"
	import="com.library.service.UserService"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="layout/header.jsp"%>
<%@include file="layout/head.jsp"%>
<a href="<%=request.getContextPath()%>/jsp/admincontrol.jsp">Admin Panel</a>
<c:choose>
	<c:when test="${book == null}">
		<form method="post" action="../book">
			<table align="center">
				<tr>
					<td>Book Name</td>
					<td><input type="text" name="bookname"></td>
				</tr>
				<tr>
					<td>Copies</td>
					<td><input type="text" name="copies"></td>
				</tr>
				<tr>
					<td>isbn</td>
					<td><input type="text" name="isbn"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit"></td>
				</tr>
			</table>
		</form>
	</c:when>
	<c:when test="${book != null}">
		<form method="post" action="book">
			<table align="center">
				<input type="hidden" name="bookid"
					value="<c:out value="${book.bookid}"></c:out>">
				<tr>
					<td>Book Name</td>
					<td><input type="text" name="bookname"
						value="<c:out value="${book.bookName}"></c:out>" readonly></td>
				</tr>
				<tr>
					<td>Copies</td>
					<td><input type="text" name="copies"
						value="<c:out value="${book.copies}"></c:out>"></td>
				</tr>
				<tr>
					<td>isbn</td>
					<td><input type="text" name="isbn"
						value="<c:out value="${book.isbn}"></c:out>" readonly></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit"></td>
				</tr>
			</table>
		</form>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>

<%@include file="layout/footer.jsp"%>