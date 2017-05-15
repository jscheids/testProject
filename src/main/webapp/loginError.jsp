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
 
  <div id="errorArea">
 <div id="errorHeaderMsg">
        <h2>Looks like you are having trouble logging In! </h2>
        </div>

 <div id="errorHelpMsg">
        <p id="helpText"> We here at GRR Wines love wine as much as you do, and we know it's a bummer to not be able to see what you are looking for.<br>
       We have a few steps outlined to help you:</p>
     
        <p id="helpSteps">
            Try these steps: <br> (1) Verify and re-enter your credentials <br> (2) 
           Verify that you are authorized to view the content you are trying to access
           <br> (3) Ensure that you log out of the app each time you leave- each username is only allowed one active login at a time
           <br> (4) Contact our admins at admin@grrwines.com for technical support. 
           <br> (5) If all else fails, come visit us in person! <br> Show us a screenshot of this page and we will give you a free sample of one of our wines of the month! 
        </p>
      <p id="loginLink">
            <a  href='<%= this.getServletContext().getContextPath() + "/login.jsp"%>'>Return to Login Page</a>
        </p>  
       
 </div>
        </div>
         <div id="errorGIF">
 <img src="images/loginErrorGif.gif" alt="" height="230" width="200" style="border: 2px solid olive"/>
 </div>
            
<jsp:include page ="adminFooter.jsp" /> 
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>
