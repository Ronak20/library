<%@include file="../layout/header.jsp"%>
<title>Administrator Panel</title>
<%@include file="../layout/head.jsp"%>
<div>
	<TABLE BORDER="5" WIDTH="50%" CELLPADDING="4" CELLSPACING="3">
		<TR>
			<TH COLSPAN="2"><BR>
				<H3>
					Welcome
					<c:out value="${sessionScope.user.firstName}" />
				</H3></TH>
		</TR>
		<TR>
			<TH><a
				href="${pageContext.request.contextPath}/jsp/admin/createUser.jsp">Create
					Users</a></TH>
			<TH><a href="${pageContext.request.contextPath}/userList">Show
					Users</a></TH>
		</TR>
		<tr>
			<th><a
				href="${pageContext.request.contextPath}/jsp/admin/createBook.jsp">Add
					Books</a></th>
			<th><a href="${pageContext.request.contextPath}/book">Show
					Books</a></th>
		</tr>
		<%-- <tr>
			<th colspan="2"><a
				href="${pageContext.request.contextPath}/logs/LibraryManagement.log.txt">Logs
			</a></th>
		</tr> --%>
	</TABLE>
</div>
<%@include file="../layout/footer.jsp"%>