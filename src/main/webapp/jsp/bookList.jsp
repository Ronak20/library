<%@ page import="com.library.model.Book"
	import="com.library.service.UserService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@include file="layout/header.jsp"%>
<%@include file="layout/head.jsp"%>
<a href="jsp/admincontrol.jsp">Admin Panel</a>
<%if(request.getAttribute("bookidnotdeleted")!= null) 
{%>
<h5 style="color: red;">BookID: <%=request.getAttribute("bookidnotdeleted")%> could not be deleted because there are active loans for this book</h5>
<%} %>
<table id="bookListTable" align="center" class="gridtable">
	<tr>
		<th>Book ID</th>
		<th>ISBN</th>
		<th>Book name</th>
		<th>Copies</th>
		<th>Delete</th>
	</tr>
	<c:forEach var="ln" items="${bookList}">
		<tr>
			<td id="<c:out value="${ln.bookid}"></c:out>"><c:out value="${ln.bookid}"></c:out></td>
			<td id="<c:out value="${ln.isbn}"></c:out>"><c:out value="${ln.isbn}"></c:out></td>
			<td>${ln.bookName}</td>
			<td>${ln.copies}</td>
			<td><a href="book?bookid=${ln.bookid}">Update</a></td>
			<td><a href="deleteBook?bookid=${ln.bookid}">Delete</a></td>
		</tr>
	</c:forEach>
</table>
<%@include file="layout/footer.jsp"%>