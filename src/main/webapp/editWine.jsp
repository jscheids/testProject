<%-- 
    Document   : editWine
    Created on : Mar 18, 2017, 7:04:42 PM
    Author     : Jennifer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="https://fonts.googleapis.com/css?family=Cormorant+Garamond:700|Cormorant+Upright:400,700|Proza+Libre" rel="stylesheet">
        <link rel="stylesheet" href="scripts/wineAdmin.css">
    </head>
    <body>
        <jsp:include page ="adminHeader.jsp" />

        <div class="container"> 
            <h1>Edit Wine</h1>
            <img class ='img-responsive' src="${pageContext.request.contextPath}/images/${wineImgUrl}"><br>
            <form id="cancelButtonForm" name="cancelButtonForm" method="POST" action=<%=response.encodeURL("WineController?requestType=cancel")%>>

                <input action="<%= response.encodeURL("WineListController?requestType=cancel")%>" type="submit" value="Cancel" name="cancel" id="cancel"/>
            </form>
            <form id="editWineForm" name="editWineForm" method="POST" action=<%=response.encodeURL("WineController?requestType=saveWine")%>>

                <br>
                <p id="errorMsg"> Error: ${errMsg} </p>
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
                    <td>
                        Wine Price   
                    </td>
                    <td>
                        <input type="text" id="winePrice" name="winePrice" value="${winePrice}">
                    </td>
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

        </div>
        <jsp:include page ="adminFooter.jsp" /> 
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="jquery_3.2.0.js" type="text/javascript"></script>                 
        <script src="scripts/jqueryValidationCore.js" type="text/javascript"></script>
        <script src="scripts/editWineFormValidations.js" type="text/javascript"></script>

    </body>
</html>
