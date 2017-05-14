<%-- 
    Document   : editWine
    Created on : Mar 18, 2017, 7:04:42 PM
    Author     : Jennifer
edit wine page for admins. inputs only visible to users with mgr role. Displays error message/no input to non mgr role visitors. 
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
      <link href="https://fonts.googleapis.com/css?family=Montserrat:700|Open+Sans" rel="stylesheet">
        <link rel="stylesheet" href="scripts/wineAdmin.css">
    </head>
    <body>
        <jsp:include page ="adminHeader.jsp" />

        <div class="container">
             <sec:authorize access="hasAnyRole('ROLE_USER')"><p>Whoops,looks like you might be lost. Click on the home button to return to your home page.</p></sec:authorize>
             <sec:authorize access="hasAnyRole('ROLE_MGR')">
            <div class="panel panel-default">
                <div class="panel-heading">
            <h1 class="panel-title">Edit Wine</h1>
                </div>
                <div class="panel-body">

            <img class ='img-responsive' src="${pageContext.request.contextPath}/images/${wineImgUrl}"><br>
            <form id="cancelButtonForm" name="cancelButtonForm" method="POST" action=<%=response.encodeURL("WineController?requestType=cancel")%>>

                <input action="<%= response.encodeURL("WineListController?requestType=cancel")%>" type="submit" value="Cancel" name="cancel" id="cancel"/>
            </form>
            <form id="editWineForm" name="editWineForm" method="POST" action=<%=response.encodeURL("WineController?requestType=saveWine")%>>

                <br>
                <p id="errorMsg"> ${errMsg} </p>
                <table class="table">  
                    <tr>
                        <td>
                            Wine ID     
                        </td>
                        <td>
                            <input type="text" id="wineId" name="wineId" readonly="readonly" value="${wineId}">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Wine Name   
                        </td>
                        <td>
                            <input type="text" id="wineName" name="wineName" value="${wineName}">
                        </td>
                    </tr>
                    <sec:authorize access="hasAnyRole('ROLE_MGR')">
                    <td>
                        Wine Price   
                    </td>
                    
                    <td>
                        <input type="text" id="winePrice" name="winePrice" value="${winePrice}">
                    </td>
                    </sec:authorize>
                    </tr>
                    <tr>
                        <td>
                            Image URL Value 
                        </td>
                        <td>
                            <input type="text" id="wineImgUrl" name="wineImgUrl" value=<c:out value='${wineImgUrl}'/>>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Date Added
                        </td>
                        <td>
                            <input type="text" id="dateAdded" name="dateAdded" readonly="readonly" value="${dateAdded}">
                        </td>
                    </tr>  
                </table>
                <br>
                <input type="submit" name="submit" id="submit" value="Submit">
                <br>
                <br>

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
        <script src="scripts/editWineFormValidations.js" type="text/javascript"></script>

    </body>
</html>
