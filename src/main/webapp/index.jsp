<%-- 
    Document   : index
    Created on : Mar 12, 2017, 8:32:36 PM
    Author     : Jennifer
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="scripts/wineAdmin.css">
       <link href="https://fonts.googleapis.com/css?family=Montserrat:700|Open+Sans" rel="stylesheet">
        <title>GRR Wines</title>
    </head>

    <body>
        <jsp:include page ="adminHeader.jsp" /> 
        <div id="content">
 
            <div class="container">         
              
              <div class="col-md-4">
                        
                        
                  <div id="login_mgr_info">

            
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
              <br>   <br> <h2>Admin Notifications</h2>
                        <h3>Application was last restarted:</h3>

                        <p> <fmt:formatDate pattern="M/d/yyyy hh:mm a" value="${appStarted}"></fmt:formatDate></p>
                            <h3>Current visitors:</h3>
                            <p>The total number of users who are currently using this application are: 
                            ${hitCount}
                       
                        </p>
                        </sec:authorize>
                        </div>
                        <div id="login_user_video" >  
   <sec:authorize access="hasAnyRole('ROLE_USER')">
            <br>
            <br>
                        <h3>May is German Wine Month! </h3>    
                        <br>
                        
                        
                        
 <iframe width="560" height="315" src="https://www.youtube-nocookie.com/embed/c9yyzkx5g78" frameborder="0" allowfullscreen></iframe>                       
                        
                      </sec:authorize>   
                  </div>       
                    </div>
                <div class="col-md-4"></div>

                    <div class="col-md-4">
                        <h3>Explore Wisconsin Wines:</h3>
                        <h4>New Wines Addded Every Month!</h4>
                       <div class="row"> 
                           <button class="button" id="goToListButton"> <a style="color: #59821e;" href=<%=response.encodeURL("WineController?requestType=wineList")%>>View Current Wines</a></button> 
                         
                         
                          
                     <button class="button" id="gotToSearchButton"> <a style="color: #59821e;" href=<%=response.encodeURL("WineController?requestType=enterSearch")%>>Search For a Wine</a></button>
                    </div>
                     </div>
                   
                            <p id="errorMsg">${errMsg}</p>
          </div>
                    
            </div>
        <jsp:include page ="adminFooter.jsp" /> 
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    </body>
</html>
