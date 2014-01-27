<%@ page language="java" 
         contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"
         import="com.library.model.User"
         import="com.library.service.UserService"
   %>
 
   <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
   "http://www.w3.org/TR/html4/loose.dtd">

   <html>

      <head>
         <meta http-equiv="Content-Type" 
            content="text/html; charset=windows-1256">
         <title>   User Logged Successfully   </title>
      </head>
	
      <body>

         <center>
            <%
             
            User currentUser = (User) session.getAttribute("sessionCurrentUser");
            
            %>
			
            Welcome <%= currentUser.getFirstName() + " " + currentUser.getLastName()%>
         </center>

      </body>
	
   </html>
