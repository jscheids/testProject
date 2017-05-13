<%-- 
    Document   : loginError
    Created on : May 2, 2017, 5:32:48 PM
    Author     : Jennifer
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <link rel='shortcut icon' type='image/x-icon' href='favicon.png'/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
      

        <title>Great River Roads:: Error Page</title>
      
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
     
      
        <link rel="stylesheet" href="scripts/wineAdmin.css">
     <link href="https://fonts.googleapis.com/css?family=Montserrat:700|Open+Sans" rel="stylesheet">
    </head>

    <body>

 <jsp:include page ="adminHeader.jsp" /> 
        <h3 style="font-weight: 200;color: #ffc400;">Ooops! Sorry partner, but there's a problem...</h3>
        <p class='helpText'>The most likely reason is that you did not logout properly last time. You must logout 
        by clicking the logout link and closing your browser.</p>
        <p class='helpText'>The other possibility is that your credentials could not be verified 
           (inaccurate entry) or are currently being used by someone else (no duplicate logins allowed). 
           Or, you are not authorized to view the content you desire.
        </p>
        <p class='helpText'>
            Try this: (1) re-enter your credentials, or (2) 
            check with your manager and confirm that you are authorized 
            to view the content your desire.
        </p>
        <p class='helpText'>
            <a style="color: #ffc400;" href='<%= this.getServletContext().getContextPath() + "/login.jsp"%>'>Back to Login Page</a>
        </p>


<jsp:include page ="adminFooter.jsp" /> 
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>
