<%-- 
    Document   : index
    Created on : Mar 12, 2017, 8:32:36 PM
    Author     : Jennifer
first page logged in users see. Uses role- based spring secutiry to display different eleements to diffrent roles. 
Also includes chat window features for admins. 
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
        <link rel='shortcut icon' type='image/x-icon' href='favicon.png'/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="scripts/wineAdmin.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat:700|Open+Sans" rel="stylesheet">
        <title>GRR Wines</title>
    </head>

    <body onload="connect();" onunload="disconnect();">
        <jsp:include page ="adminHeader.jsp" /> 


        <div class="container">         

            <div class="row">
                <div class="my-col-count-style">

                    <div class="panel panel-default">

                        <div class="panel-heading">
                            <sec:authorize access="hasAnyRole('ROLE_MGR')"> 
                                <h1 class="panel-title">Admin Notifications</h1>

                            </sec:authorize>   
                            <sec:authorize access="hasAnyRole('ROLE_USER')"><h3 class="panel-title">May is German Wine Month! </h3>   </sec:authorize>
                            </div>
                            <div class="panel-body">
                            <sec:authorize access="hasAnyRole('ROLE_MGR')"> 

                                <h4>Application was last restarted:</h4>
                                <p> <fmt:formatDate pattern="M/d/yyyy hh:mm a" value="${appStarted}"></fmt:formatDate></p>
                                    <h4>Current visitors:</h4>
                                    <p>The total number of users who are currently using this application are: 
                                    ${hitCount}</p>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_USER')">
                                <iframe width="540" height="315" src="https://www.youtube-nocookie.com/embed/c9yyzkx5g78" frameborder="0" allowfullscreen>

                                </iframe> </sec:authorize>
                            </div> 
                        </div>


                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h1 class="panel-title">Explore Wisconsin Wines:</h1>
                                <h4 class="panel-title">New Wines Added Every Month!</h4>
                            </div>   


                            <div class="panel-body">

                                <a  class="btn"style="color: #59821e;"  id="goToListButton" href=<%=response.encodeURL("WineController?requestType=wineList")%>>View Current Wines</a>
                            <a class="btn" id="gotToSearchButton" style="color: #59821e;" href=<%=response.encodeURL("WineController?requestType=enterSearch")%>>Search For a Wine</a>

                            <a class="btn" id="gotToQuickListButton" style="color: #59821e;" href=<%=response.encodeURL("WineController?requestType=quickList")%>>Quick List</a>

                        </div>
                    </div>
                </div>

            </div>
                            <div id="messageArea">
             <h4>Great River Road Wines News Feed</h4>
             <sec:authorize access="hasAnyRole('ROLE_USER')"> 
               
            <input type="text" id="messages"  readonly/></sec:authorize>
           
            <sec:authorize access="hasAnyRole('ROLE_MGR')"> 
            <div class="panel input-area">
               
                <input id="messageInput" class="text-field" type="text" placeholder="enter news feed item"
                       onkeydown="if (event.keyCode == 13) sendMessage();" />

                <!--  websocket message to the endpoint on click -->
                <input class="button" id="btn-chat" type="submit" value="Send" onclick="sendMessage();" />
            </div>

       
                
</sec:authorize>
                            </div>
        </div> 
        <jsp:include page ="adminFooter.jsp" /> 

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <script src ="scripts/webSocket.js" type="text/javascript"></script>
    </body>

</html>
