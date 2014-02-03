<%@ page import="com.library.model.User" import="com.library.model.Loan"
	import="com.library.model.Book"
	import="com.library.service.UserService"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<%@ page import="com.library.dao.BookDao"%>
<%@ page isELIgnored="false"%>
<%@include file="layout/header.jsp"%>
<title>Student Panel</title>
<%@include file="layout/head.jsp"%>

<jsp:useBean id="cart" scope="session" class="com.library.model.User" />
<c:set var="currentUser" value="${sessionCurrentUser.firstName}"
	scope="session"></c:set>
<c:set var="message" value="${message}" scope="session"></c:set>

<script type="text/javascript">
window.onunload = refreshParent;
            function refreshParent() {
                window.opener.location.reload();
            }
</script>
<center>
	<h2>
		
	
		Welcome
		<c:out value="${currentUser}" />
		to your page
	</h2>
	<c:choose>
		<c:when test="${message == 'unallowed'}">
			<font color="red">Unallowed to rent books !</font>
		</c:when>
		<c:when test="${message == 'renewed'}">
			<font color="green">Book was Renewed !</font>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${paynote == 0}">
			<font color="red">You have overdue books you need to return</font>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	
	<table class="gridtable" id="rentedBooks" align="center" class="myTable">
		<tr>
			<th>Rented Book ID</th>
			<th>Expiry Date</th>
			<th>Renew Count</th>
			<th>Renew</th>
			<th></th>
		</tr>
		<c:forEach var="ln" items="${loanList}">
			<tr>
				<td id="${ln.bookId}"><c:out value="${ln.bookId}"></c:out></td>
				<td>${ln.expiryDate}</td>
				<td>${ln.renewalCount}</td>
				<td><a
					href="${pageContext.request.contextPath}/unrentBook?aLoan=${ln.loanId}&currentUser=${ln.userId}">Return</a></td>
				<td><a
					href="${pageContext.request.contextPath}/RenewBook?aLoan=${ln.loanId}&currentUser=${ln.userId}">Renew</a></td>
			</tr>
		</c:forEach>
	</table>
	
	<a href="${pageContext.request.contextPath}/listBooks?currentUser=${sessionCurrentUser.userId}"><b>Rent new book here </b></a>
	
	<table align="Center">
		<td><b><font color="blue">Name:</font></b></td>
		<td><i>${sessionCurrentUser.firstName }
				${sessionCurrentUser.lastName }</i></td>
		<td><b><font color="blue"> Username:</font></b></td>
		<td><i>${sessionCurrentUser.username}</i></td>
	</table>
</center>
<%@include file="layout/footer.jsp"%>
