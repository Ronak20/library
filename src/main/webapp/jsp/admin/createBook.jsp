<%@include file="../layout/header.jsp"%>
<%@include file="../layout/head.jsp"%>
<div>
<a href="${pageContext.request.contextPath}/jsp/admin/adminControl.jsp">Admin Panel</a>
</div>
<div>
<c:choose>
	<c:when test="${book == null}">
		<form id="addBookForm" method="post" action="../../book">
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
					<td colspan="2"><input name="addBookSubmit" type="submit"></td>
				</tr>
			</table>
		</form>
	</c:when>
	<c:when test="${book != null}">
		<form id="editBookForm" method="post" action="book">
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
					<td colspan="2"><input name="updateBookSubmit" type="submit"></td>
				</tr>
			</table>
		</form>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>
</div>
<%@include file="../layout/footer.jsp"%>