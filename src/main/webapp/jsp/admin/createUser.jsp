<%@include file="../layout/header.jsp"%>
<%@include file="../layout/head.jsp"%>
<a href="${pageContext.request.contextPath}/jsp/admin/adminControl.jsp">Admin
	Panel</a>
<br />
<form method="post" action="../../createUser" id="createUserForm">
	<table align="center">
		<tr>
			<td>First Name :</td>
			<td><input type="text" name="firstname"></td>
		</tr>
		<tr>
			<td>Last Name :</td>
			<td><input type="text" name="lastname"></td>
		</tr>
		<tr>
			<td>Username :</td>
			<td><input type="text" name="username"></td>
		</tr>
		<tr>
			<td>Password :</td>
			<td><input type="password" name="password"></td>
		</tr>
		<tr>
			<td>Role :</td>
			<td><select name="role">
					<option value="Student">Student</option>
					<option value="Administrator">Administrator</option>
			</select></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" name="submitbutton"></td>
		</tr>
	</table>
</form>
