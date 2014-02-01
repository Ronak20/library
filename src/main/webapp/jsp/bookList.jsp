<%@ page import="com.library.model.Book"
	import="com.library.service.UserService"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ page isELIgnored="false"%>
<%@include file="layout/header.jsp"%>
<%@include file="layout/head.jsp"%>
<table align="center" class="myTable">
	<tr>
		<th>ISBN</th>
		<th>Book name</th>
		<th>Copies</th>
		<th>Delete</th>
	</tr>
	<c:forEach var="ln" items="${bookList}">
		<tr>
			<td><c:out value="${ln.isbn}"></c:out></td>
			<td>${ln.bookName}</td>
			<td>${ln.copies}</td>
			<td><a
				href="deleteBook?bookid=${ln.bookid}">Delete</a></td>
		</tr>
	</c:forEach>
</table>
<%@include file="layout/footer.jsp"%>