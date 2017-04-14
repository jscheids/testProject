<%-- 
    Document   : index
    Created on : Mar 12, 2017, 8:32:36 PM
    Author     : Jennifer
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="scripts/wineAdmin.css">
        <link href="https://fonts.googleapis.com/css?family=Cormorant+Garamond:700|Cormorant+Upright:400,700|Proza+Libre" rel="stylesheet">
        <title>GRR Wines</title>
    </head>

    <body>
        <jsp:include page ="adminHeader.jsp" /> 
        <div id="content">
 
            <div class="container">         
                     <h2>Admin Console</h2>
                     <h3>Welcome Back!</h3>

             <br> 
                    <div class="col-xs-6">
                        <button class="button" id="goToListButton"> <a style="color: #652e38;" href=<%=response.encodeURL("WineController?requestType=wineList")%>>View Wine List</a></button>  

                    </div>
                    <div class="col-xs-6">

                        <h3>Application was last restarted:</h3>

                        <p> <fmt:formatDate pattern="M/d/yyyy hh:mm a" value="${appStarted}"></fmt:formatDate></p>
                            <h3>Current visitors:</h3>
                            <p>The total number of users who are currently using this application are: 
                            ${hitCount}
                       
                        </p>
                    </div>
                </div>
            </div>

                            <p id="errorMsg">${errMsg}</p>
        </div>
        <jsp:include page ="adminFooter.jsp" /> 
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    </body>
</html>
