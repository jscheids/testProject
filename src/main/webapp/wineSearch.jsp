<%-- 
    Document   : wineSearch
    Created on : May 3, 2017, 4:42:04 AM
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
        
        <div class="col-md-6">
             
       <h1>Wine Search</h1>
        <p><b>Please Enter Search Criteria:</p>
        <br>

        <form method="POST" id="formWineSearch" action="WineController?requestType=search">
           
           
<table class="table" id="searchTable">  

                    <tr>
                        <td>
                            Product ID:   
                        </td>
                        <td>
                           <select id="wineSearchId" name="wineSearchId">
                            <option value='All' selected>All</option>
                            <c:forEach var="wine" items="${wines}" varStatus="rowCount">
                                <option value='${wine.wineId}'>${wine.wineId}</option>
                            </c:forEach>
                        </select>
                        </td>
                      <td><b>Wine Name (all or starting with): </b></td>
                    <td><input type='text' id="wineSearchName" name="wineSearchName"/></td>
                       
                    </tr>
                    <tr> 
<td><b>Min Price: </b></td>
                    <td><input type='text' id="wineSearchMinPrice" name="wineSearchMinPrice"/></td>
                    <td><b>Max Price: </b></td>
                    <td><input type='text' id="wineSearchMaxPrice" name="wineSearchMaxPrice"/></td>
        </tr> 
                </table> 
            
            
            
            
                     <input type="submit"  value="Search" name="mysubmit" id="search" />
           
           
        </form>
        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
            </c:if>  
            </div>
        <div class="col-md-6">
            More coming soon! 
        </div>
            <jsp:include page ="adminFooter.jsp" /> 
 
            
              <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
              <script scr="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.16.0/additional-methods.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="jquery_3.2.0.js" type="text/javascript"></script>                 
        <script src="scripts/jqueryValidationCore.js" type="text/javascript"></script>
        <script src="scripts/wineSearchValidation.js" type="text/javascript"></script>

    </body>
</html>

