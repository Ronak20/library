<%@include file="../layout/header.jsp"%>
<%@include file="../layout/head.jsp"%>
<a href="jsp/admin/adminControl.jsp">Admin Panel</a>

<div>
	<c:choose>
		<c:when test="${requestScope.isBookDeleted eq false}">
			Book is rented.Cannot delete.
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
</div>
<div>
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
				<td id="<c:out value="${ln.bookid}"></c:out>"><c:out
						value="${ln.bookid}"></c:out></td>
				<td id="<c:out value="${ln.isbn}"></c:out>"><c:out
						value="${ln.isbn}"></c:out></td>
				<td>${ln.bookName}</td>
				<td>${ln.copies}</td>
				<td><a href="book?bookid=${ln.bookid}">Update</a></td>
				<td><a href="deleteBook?bookid=${ln.bookid}">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
</div>
<%@include file="../layout/footer.jsp"%>