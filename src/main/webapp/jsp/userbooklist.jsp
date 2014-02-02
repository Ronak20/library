<%@ page import="com.library.model.Book"
	import="com.library.service.UserService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <c:set var="userId" value="${currentUser}" scope="session"></c:set>
	<c:forEach var="ln" items="${bookList}">
		<tr>
			<td><c:out value="${ln.isbn}"></c:out></td>
			<td>${ln.bookName}</td>
			<td>${ln.copies}</td>
			<td><a href="rentBook?bookid=${ln.bookid}&auserid=${currentUser}">Rent</a></td>
		</tr>
	</c:forEach>
</table>
<%@include file="layout/footer.jsp"%>