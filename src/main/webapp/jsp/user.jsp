<%@include file="layout/header.jsp"%>
<title>Student Panel</title>
<%@include file="layout/head.jsp"%>

<center>
	<h2>Welcome ${sessionScope.user.firstName}</h2>
	<table align="Center">
		<td><b><font color="blue">Name : </font></b></td>
		<td><i>${sessionScope.user.firstName }
				${sessionScope.user.lastName }</i></td>
		<td><b><font color="blue"> Username : </font></b></td>
		<td><i>${sessionScope.user.username}</i></td>
	</table>
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

	<table class="gridtable" id="rentedBooks" align="center"
		class="myTable">
		<tr>
			<th>Rented Book ID</th>
			<th>Expiry Date</th>
			<th>Renew Count</th>
			<th>Rent</th>
			<th>Renew</th>
			<th>Payment</th>
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
				<td><c:choose>
						<c:when test="${not ln.isLateFeePaid}">
							<font color="red">You have overdue books you need to
								return</font>

							<a
								href="${pageContext.request.contextPath}/payFeesServlet?loanid=${ln.loanId}&userid=${ln.userId}">Pay
								Fees</a></td>
				</c:when>

				<c:otherwise>
				</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
	</table>

	<a
		href="${pageContext.request.contextPath}/listBooks?currentUser=${sessionScope.user.userId}"><b>Rent
			new book here </b></a>

</center>
<%@include file="layout/footer.jsp"%>
