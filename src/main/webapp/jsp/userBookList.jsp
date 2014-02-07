<%@include file="layout/header.jsp"%>
<%@include file="layout/head.jsp"%>
<div>
<a href="userPanel">User Panel</a>
</div>
<div>
	<c:choose>
		<c:when test="${requestScope.hasOutstandingLoan eq true}">
			User has outstanding loan.
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
</div>
<div>
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
				<td id="<c:out value="${ln.bookid}"></c:out>"><c:out
						value="${ln.bookid}"></c:out></td>
				<td id="<c:out value="${ln.isbn}"></c:out>"><c:out
						value="${ln.isbn}"></c:out></td>
				<td>${ln.bookName}</td>
				<td>${ln.copies}</td>
				<td><a href="rentBook?bookid=${ln.bookid}">Rent</a></td>
			</tr>
		</c:forEach>
	</table>
</div>
<%@include file="layout/footer.jsp"%>