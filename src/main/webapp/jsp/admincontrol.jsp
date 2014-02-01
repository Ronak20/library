<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="sessionuser" class="com.library.model.User" scope="session"> 
</jsp:useBean>
<TABLE BORDER="5"    WIDTH="50%"   CELLPADDING="4" CELLSPACING="3">
   <TR>
      <TH COLSPAN="2"><BR><H3>Welcome admin <jsp:getProperty property="username" name="sessionuser"/> </H3>
      </TH>
   </TR>
   <TR>
   	  <TH><a href="/LibraryManagement/jsp/DeleteUser">Create Users</a></TH>
      <TH><a href="/LibraryManagement/jsp/DeleteUser">Delete Users</a></TH>
      
     <!--<TH>Column B</TH>-->
   </TR>
   <!-- <TR ALIGN="CENTER">
      <TD>Data 1</TD>
      <TD>Data 2</TD>
   </TR> -->
</TABLE>
</body>
</html>