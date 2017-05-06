<%-- 
    Document   : wineList
    Created on : Mar 18, 2017, 7:04:06 PM
    Author     : Jennifer
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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="scripts/wineAdmin.css">
     <link href="https://fonts.googleapis.com/css?family=Montserrat:700|Open+Sans" rel="stylesheet">
        <title>
            Administrative Wine List
        </title> 
        
    </head>
    <body>
        <jsp:include page ="adminHeader.jsp" />

        <div class="container"> 
            <h1>Wine List</h1>
            <h2>
                <p id="heading"> Current Wines</p>

            </h2>

            <form id="wineFormDelete" name="wineFormDelete" method="POST" action=<%=response.encodeURL("WineController?requestType=deleteWine")%>>
                <p id="errorMsg">${errMsg}</p>
                <br>
                <sec:authorize access="hasAnyRole('ROLE_MGR')">
                <div id="listButtons">
                    <button type="submit" formaction=<%=response.encodeURL("WineController?requestType=addWine")%> name="add" id="add">New Wine</button>
                    <input type="submit" name="delete" id="delete" value="Delete">
                </div>
                    </sec:authorize>
                <br>
                <div class="table-responsive">
                    <table class="table table-bordered, table table-striped, table table-hover">

                        <tr>
                            <sec:authorize access="hasAnyRole('ROLE_MGR')">
 <th>
                            </th>
                            </sec:authorize>
                           
                            <th>
                                Winery 
                            </th>
                            <th>
                                Product ID
                            </th>
                            <th>
                                Name
                            </th>
                            <th>
                                 Price
                            </th>
                            <th>
                                Date Added
                            </th>
                            <sec:authorize access="hasAnyRole('ROLE_MGR')">
                                
                            <th>
                                Remove?
                            </th>
                            </sec:authorize>
                        </tr>    
                        <c:forEach var="wine" items="${wines}" varStatus="varStatus">
                            <tr> 
                                <sec:authorize access="hasAnyRole('ROLE_MGR')">
                                <td>

                                    <button type="submit" formaction=<%=response.encodeURL("WineController?requestType=editWine")%> value="${wine.wineId}" name="editWine" id="editWine">Edit</button>
                                </td>
                                </sec:authorize>
                                <td>
                                    <img class ='img-responsive' src="${pageContext.request.contextPath}/images/${wine.wineImgUrl}">
                                </td>
                                <td>
                                    ${wine.wineId}
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
                                <sec:authorize access="hasAnyRole('ROLE_MGR')">
                                <td>
                                    <input type="checkbox" name="wineId" value="${wine.wineId}">
                                </td>  
                                </sec:authorize>
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
