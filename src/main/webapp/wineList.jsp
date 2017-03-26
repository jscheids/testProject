<%-- 
    Document   : wineList
    Created on : Mar 18, 2017, 7:04:06 PM
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

        <title>
            Administrative Wine List
        </title> 
        <link rel="stylesheet" href="wineAdmin.css">
        <link href="https://fonts.googleapis.com/css?family=Cormorant+Garamond:700|Cormorant+Upright:400,700|Proza+Libre" rel="stylesheet">
    </head>
    <body>
        <jsp:include page ="adminHeader.jsp" />
        
        <div class="container"> 
            <h1>Wine List</h1>
            <h2>
                <p id="heading"> Current Wines</p>
               
            </h2>

            <form id="wineFormDelete" name="wineFormDelete" method="POST" action=<%=response.encodeURL("WineController?requestType=deleteWine")%>
                  <br>
                 <div id="buttons">
                    <button type="submit" formaction="WineController?requestType=addWine" name="add" id="add">New Wine</button>
                    <input type="submit" name="delete" id="delete" value="Delete">
                </div>
                <br>
                <div class="table-responsive">
                    <table class="table table-bordered, table table-striped, table table-hover">

                        <tr>
                            <th>

                            </th>
                            <th>
                                Wine Image
                            </th>
                            <th>
                                Wine ID
                            </th>
                            <th>
                                Wine Name
                            </th>
                            <th>
                                Wine Price
                            </th>
                            <th>
                                Date Added
                            </th>
                            <th>
                                Delete?
                            </th>
                        </tr>    
                        <c:forEach var="wine" items="${wines}" varStatus="varStatus">
                            <tr> 

                                <td>
                                    <button type="submit" formaction="WineController?requestType=editWine&id=${wine.wineID}" value="${wine.wineID}" name="editWine" id="editWine">Edit</button>
                                </td>
                                <td>
                                    <img class ='img-responsive' src="${pageContext.request.contextPath}/images/${wine.wineImgUrl}">
                                </td>
                                <td>
                                    ${wine.wineID}
                                </td>
                                <td>
                                    ${wine.wineName}
                                </td>
                                <td>
                                    ${wine.winePrice}
                                </td>
                                <td>
                                    <fmt:formatDate pattern="M/d/yyyy" value="${wine.dateAdded}"/>                                 
                                </td>
                                <td>
                                    <input type="checkbox" name="wineId" value="${wine.wineID}">
                                </td>        
                            </tr>     

                        </c:forEach>             
                    </table>
                </div>
               
            </form>  
        </form>       
        <br>
        <br>

    </div>
    <jsp:include page ="adminFooter.jsp" /> 
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
