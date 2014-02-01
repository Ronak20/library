<%@include file="layout/header.jsp" %>
<%@include file="layout/head.jsp"%>
<jsp:useBean id="sessionuser" class="com.library.model.User" scope="session"> 
</jsp:useBean>
<c:set var="currentUser" value="${sessionCurrentUser.firstName}"
	scope="session"></c:set>
<TABLE BORDER="5"    WIDTH="50%"   CELLPADDING="4" CELLSPACING="3">
   <TR>
      <TH COLSPAN="2"><BR><H3>Welcome admin <c:out value="${currentUser}" /></H3>
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
<%@include file="layout/footer.jsp" %>