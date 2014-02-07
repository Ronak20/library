<%@include file="../layout/header.jsp"%>
<title>User list</title>
<%@include file="../layout/head.jsp"%>

<a href="jsp/admin/adminControl.jsp">Admin Panel</a>
<br />
<div>
	<c:choose>
		<c:when test="${requestScope.isUserDeleted eq false}">
			User has outstanding loan.
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
</div>
<div>
	<TABLE BORDER="5" id="userListTable" WIDTH="50%" class="gridtable"
		CELLPADDING="4" CELLSPACING="3">
		<TR>
			<TH COLSPAN="1"><BR>
				<H3>User ID</H3></TH>
			<TH COLSPAN="1"><BR>
				<H3>First Name</H3></TH>
			<TH COLSPAN="1"><BR>
				<H3>Last Name</H3></TH>
			<TH COLSPAN="1"><BR>
				<H3>User Name</H3></TH>
			<TH COLSPAN="1"><BR>
				<H3>Role</H3></TH>
		</TR>
		<c:forEach var="user" items="${userlist}">
			<tr>
				<td id="<c:out value="${user.userId}"></c:out>"><c:out
						value="${user.userId}"></c:out></td>
				<td id="<c:out value="${user.firstName}"></c:out>"><c:out
						value="${user.firstName}"></c:out></td>
				<td>${user.lastName}</td>
				<td id="<c:out value="${user.username}"></c:out>">${user.username}</td>
				<td>${user.role}</td>
				<td><a href="deleteUser?userid=${user.userId}">Delete</a></td>
			</tr>
		</c:forEach>
	</TABLE>
</div>