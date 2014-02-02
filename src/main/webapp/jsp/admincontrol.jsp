<%@include file="layout/header.jsp"%>
<title>Administrator Panel</title>
<%@include file="layout/head.jsp"%>
<jsp:useBean id="sessionuser" class="com.library.model.User"
	scope="session">
</jsp:useBean>
<c:set var="currentUser" value="${sessionCurrentUser.firstName}"
	scope="session"></c:set>
<TABLE BORDER="5" WIDTH="50%" CELLPADDING="4" CELLSPACING="3">
	<TR>
		<TH COLSPAN="2"><BR>
			<H3>
				Welcome admin
				<c:out value="${currentUser}" />
			</H3></TH>
	</TR>
	<TR>
		<TH><a href="<%=request.getContextPath()%>/jsp/createUser.jsp">Create Users</a></TH>
		<TH><a href="<%=request.getContextPath()%>/jsp/DeleteUser">Delete Users</a></TH>
	</TR>
	<tr>
		<th><a href="<%=request.getContextPath()%>/jsp/createBook.jsp">Add Books</a></th>
		<th><a href="book">Show Books</a></th>
	</tr>
</TABLE>
<%@include file="layout/footer.jsp"%>