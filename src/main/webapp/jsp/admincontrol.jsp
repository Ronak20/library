<%@ page language="java" 
         contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"
         import="com.library.model.User"
         import="com.library.model.Loan"
         import="com.library.model.Book"
         import="com.library.service.UserService"
         
         
         
   %>
   <%@  taglib  prefix="c"   uri="http://java.sun.com/jsp/jstl/core"  %>
   <%@ taglib  prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
   <%@ page import="java.util.List" %>
   <%@ page import="com.library.dao.BookDao" %>
   <%@ page isELIgnored="false" %>
 
   <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
   "http://www.w3.org/TR/html4/loose.dtd">

   <html>

      <head>
         <meta http-equiv="Content-Type" 
            content="text/html; charset=windows-1256">
            <!-- Start Styles. Move the 'style' tags and everything between them to between the 'head' tags -->
<style type="text/css">
.myTable { width:400px;background-color:#eee;border-collapse:collapse; }
.myTable th { background-color:#255;color:white;width:8%; }
.myTable td, .myTable th { padding:5px;border:1px solid #000; }
</style>
            
         <title>   User Logged Successfully   </title>
      </head>
	
      <body>
      <jsp:useBean id="cart" scope="session" class="com.library.model.User" />
      <c:set var="currentUser" value="${sessionCurrentUser.firstName}" scope="session"></c:set>
 <script type="text/javascript">
window.onunload = refreshParent;
            function refreshParent() {
                window.opener.location.reload();
            }</script>
         <center>
         
         
<h2>Welcome  to your  page</h2>


 <!--  <h2>You have //loan.getLateFee()  %> Late Fees </h2> -->   



<!-- Codes by HTML.am -->

<table>

  
<c:forEach items="${loanList}" var="i" >
  <td>
  <c:out value="${i.userId}"></c:out>
  </td> 
  <br />
</c:forEach>
</table>

<!-- End Styles -->

<table align="center" class="myTable">
<tr>
<th>Rented Book ID</th><th>Expiry Date</th><th>Renew Count</th><th>Renew </th><th> </th>
</tr>

<c:forEach var="ln" items="${loanList}">

<tr>
<td><c:out value="${ln.bookId}"></c:out></td>
 <td> ${ln.expiryDate} </td>
 <td> ${ln.renewalCount}</td>
 <td><a href="${pageContext.request.contextPath}/unrentBook?aLoan=${ln.loanId}&currentUser=${ln.userId}">
							Delete
						</a></td>
 <td><button type="button" onclick="">Renew </button></td>

</tr>

</c:forEach>


	


</table>





<table align="Center">
			
			
					<td>First Name: </td>
					<td>${sessionCurrentUser.firstName }</td>
			
			
					<td> || Last Name:   </td>
					<td>${sessionCurrentUser.lastName }</td>
			
			
					<td>||  Username: </td>
					<td>${sessionCurrentUser.username}</td> 
			
			
		</table>       
            
            
<!-- list 


 
  <table border="1">
    <tr>
      <th>Book ID</th>
      <th>Book Title</th>
      <th>ISBN</th>
      <th>Copies</th>
    </tr>
    <c:forEach var="row" items="${rset.rows}">
      <tr>
        <td><c:out value="${row.id}" /></td>
        <td><c:out value="${row.title}" /></td>
        <td><c:out value="${row.author}" /></td>
        <td><c:out value="${row.price}" /></td>
        <td><c:out value="${row.qty}" /></td>
      </tr>
    </c:forEach>
  </table>

<!-- end of list -->

   </center>

      </body>
	
   </html>
