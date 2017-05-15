<%-- 
    Document   : addWine
    Created on : Mar 18, 2017, 7:03:35 PM
    Author     : Jennifer
add wine page for admins. inputs only visible to users with mgr role. Displays error message/no input to non mgr role visitors. 
Uses custom jquery vailidation 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='shortcut icon' type='image/x-icon' href='favicon.png'/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="scripts/wineAdmin.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat:700|Open+Sans" rel="stylesheet">

    </head>
    <body>
        <jsp:include page ="adminHeader.jsp" />
        <div class="container">
            <div class="panel panel-default"id="panel-add">
                <div class="panel-heading" >
                    <sec:authorize access="hasAnyRole('ROLE_USER')"><p>Whoops,looks like you might be lost. Click on the home button to return to your home page.</p></sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_MGR')">
                        <h1 class="panel-title">Add Wine</h1>  
                        
                    </div>
                         <div class="panel-body">
                                  <form id="cancelButtonForm" name="cancelButtonForm" method="POST" action=<%=response.encodeURL("WineController?requestType=cancel")%>>
                        <input action="<%= response.encodeURL("WineListController?requestType=cancel")%>" type="submit" value="Cancel" name="cancel" id="cancel"/>
                    </form>
                             <h4>
                            Please enter all information:
                        </h4>
                         
                    <form id="addWineForm" name="addWineForm" method="POST" action=<%=response.encodeURL("WineController?requestType=saveWine")%>>
                      
                        <p id="errorMsg">${errMsg}</p>
                        <table class="table">  
                            <tr>
                                <td>
                                    Wine Name:   
                                </td>
                               
                                <td>
                                    <input type="text" id="wineName-add" name="wineName" value="${wineName}">
                                </td>
                                 </tr>
                                 <tr>
                                <td>
                                    Price:   
                                </td>
                                <td>
                                    <input type="text" id="winePrice-add" name="winePrice" value="${winePrice}">
                                </td>
                                 </tr>
                                 <tr>
                                <td>
                                    Image URL Path:  
                                </td>
                                <td>
                                    <input type="text" id="wineImgUrl-add" name="wineImgUrl" value="${wineImgUrl}">
                                </td>
                            
                        </table>
                       
                        <input type="submit" name="submit" id="submit-add" value="Submit">
                    </form>
                   
                </sec:authorize>
            </div>   
        </div> 
</div> 
        <jsp:include page ="adminFooter.jsp" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="jquery_3.2.0.js" type="text/javascript"></script>
        <script src="scripts/jqueryValidationCore.js" type="text/javascript"></script>
        <script src="scripts/addWineFormValidation.js" type="text/javascript"></script>

    </body>
</html>
