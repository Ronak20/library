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
		<c:when test="${requestScope.message == 'FeePending'}">
			<font color="red">Pay fees to renew books.</font>
		</c:when>
		<c:when test="${requestScope.message == 'Renewed'}">
			<font color="green">Book is Renewed</font>
		</c:when>
		<c:when test="${requestScope.message == 'Expired'}">
			<font color="red">Loan is expired.Return it.</font>
		</c:when>
		<c:when test="${requestScope.userHasLateFee == 'true'}">
			<font color="red">You need to pay the late fee to be able to return the book</font>
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
					href="${pageContext.request.contextPath}/unrentBook?aLoan=${ln.loanId}&userid=${sessionScope.user.userId}">Return</a></td>
				<td><a
					href="${pageContext.request.contextPath}/renewBook?aLoan=${ln.loanId}&userid=${sessionScope.user.userId}">Renew</a></td>
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

	<a href="${pageContext.request.contextPath}/listBooksRent"><b>Rent
			new book here </b></a>

</center>
<%@include file="layout/footer.jsp"%>
