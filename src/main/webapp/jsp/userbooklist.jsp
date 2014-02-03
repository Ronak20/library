<%@ page import="com.library.model.Book"
	import="com.library.service.UserService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@include file="layout/header.jsp"%>
<%@include file="layout/head.jsp"%>
<% if(request.getAttribute("HasOutStandingLoan")=="true") 
{%>
<h5 style="color: red;">Loan can not be issued because you have outstanding loan fee</h5>
<% }%>
<table align="center" id="userBookListTable" class="gridtable">
	<tr>
		<th>ID</th>
		<th>ISBN</th>
		<th>Book name</th>
		<th>Copies</th>
		<th>Rent</th>
	</tr>
    <c:set var="userId" value="${currentUser}" scope="session"></c:set>
	<c:forEach var="ln" items="${bookList}">
		<tr>
			<td id="<c:out value="${ln.bookid}"></c:out>"><c:out value="${ln.bookid}"></c:out></td>
			<td id="<c:out value="${ln.isbn}"></c:out>"><c:out value="${ln.isbn}"></c:out></td>
			<td>${ln.bookName}</td>
			<td>${ln.copies}</td>
			<td><a href="rentBook?bookid=${ln.bookid}&auserid=${currentUser}">Rent</a></td>
		</tr>
	</c:forEach>
</table>
<%@include file="layout/footer.jsp"%>