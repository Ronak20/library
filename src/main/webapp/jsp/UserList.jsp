<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.List" import="com.library.model.User"%>
<%@include file="layout/header.jsp"%>
<%@include file="layout/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<a href="jsp/admincontrol.jsp">Admin Panel</a>
<br>
<TABLE BORDER="5" id="userListTable"   WIDTH="50%"   CELLPADDING="4" CELLSPACING="3">
   <TR>
   <TH COLSPAN="1"><BR><H3>User ID</H3>
      </TH>
      <TH COLSPAN="1"><BR><H3>User Name  </H3>
      </TH>
     <!--   <TH COLSPAN="2"><BR><H3>User ID  </H3>
      </TH> -->
      
       <TH COLSPAN="1"><BR><H3>First Name</H3>
      </TH>
      <TH COLSPAN="1"><BR><H3>Last Name</H3>
      </TH>
      <TH COLSPAN="1"><BR><H3>User Role</H3>
      </TH>
   </TR>
  <%
  List<User> userList = (List<User>) request.getAttribute("userlist");
  int size = userList.size();
  String userName;
  String userID;
  String firstName;
  String lastName;
  String userRole;
  for(int i= 0; i < size; i++)
  {
	  
	  userName = userList.get(i).getUsername();
	  userID = userList.get(i).getUserId();
	  firstName = userList.get(i).getFirstName();
	  lastName = userList.get(i).getLastName();
	  userRole = userList.get(i).getRole().toString();
 %>
	  <TR ALIGN="CENTER">
	  <TD><%=userID %></TD>
      <TD id="<%=userName%>"><%=userName %></TD>
      <!--  <TD><% userList.get(i).getUserId();%></TD> -->
      <TD><%=firstName %></TD>
      <TD><%=lastName %></TD>
      <TD><%=userRole %></TD>
      </TR>
	  
 <%} %>
  
 
</TABLE>
</body>
</html>