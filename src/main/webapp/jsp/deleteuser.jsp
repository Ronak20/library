<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.List" import="com.library.model.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function checkBoxValidation()
{
	
	event.preventDefault();
	var j = 0;
for(var i=0; i < document.Deleteform.deleteThisUser.length; i++)
{
if(document.Deleteform.deleteThisUser[i].checked)
	{
	
	j++;
	break;
	}
}

if(j==0)
	{
		alert("Please select ateleast one user to delete");
		return false;
	}
else
	document.forms[0].submit();

}
</script>
<title>Insert title here</title>
</head>
<body>

<form name="Deleteform" action="DeleteUser" method="post" onsubmit="return checkBoxValidation()">
<%
//<!-- String notDeletedUsers= request.getAttribute("notDeletedUsers").toString();
//notDeletedUsers = "somedummyvalue";-->
if((request.getAttribute("notDeletedUsers")) != null)
{%>
<h6>UserIds: <% request.getAttribute("notDeletedUsers").toString(); %> could not be deleted because they may have outstanding lone</h6>
 
<%}
%>
<TABLE BORDER="5"    WIDTH="50%"   CELLPADDING="4" CELLSPACING="3">
   <TR>
      <TH COLSPAN="1"><BR><H3>User Name  </H3>
      </TH>
     <!--   <TH COLSPAN="2"><BR><H3>User ID  </H3>
      </TH> -->
      <TH COLSPAN="1"><BR><H3>Delete  </H3>
      </TH>
   </TR>
  <%
  List<User> userList = (List<User>) request.getAttribute("userlist");
  int size = userList.size();
  String userName;
  String userID;
  for(int i= 0; i < size; i++)
  {
	  if(userList.get(i).getRole().toString() == "admin")
		  continue;
	  userName = userList.get(i).getUsername();
	  userID = userList.get(i).getUserId();
	  
 %>
	  <TR ALIGN="CENTER">
      <TD><%=userName %></TD>
      <!--  <TD><% userList.get(i).getUserId();%></TD> -->
      <TD><input type="checkbox" name="deleteThisUser" value="<%=userID %>"></TD>
      </TR>
	  
 <%} %>
  
 
</TABLE>
<br>
<input type="submit" value="submit">
</form>

</body>
</html>