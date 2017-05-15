<%-- 
    Document   : quickList
    Created on : May 7, 2017, 18:08:38 PM
    Author     : jennifer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"  %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Quick List</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel='shortcut icon' type='image/x-icon' href='favicon.png'/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="scripts/wineAdmin.css">    
        <link href="scripts/quickListStyle.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css?family=Montserrat:700|Open+Sans" rel="stylesheet">
    </head>
    <body>
        <jsp:include page ="adminHeader.jsp" />

        <br>

        <p class="errMsg">${errMessage == null ? "" : "Sorry, there was a problem: " + errMessage}</p>

        <div id="container">
            <sec:authorize access="hasAnyRole('ROLE_USER')">Looks like you are lost! Use the home button at the top of the page to return back the main page!</sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
                <div id="adminAreaOnly">
                    <div class="leftArea">
                        <h3 style="border-bottom: 1px solid olivedrab">Current List</h3>
                        <ul id="wineList">
                        </ul>
                    </div>

                    <form id="wineForm">

                        <div class="mainArea">
                            <button id="btnDelete" name="Update" type="button" value="Delete">Delete</button>
                            <label>Id:</label>
                            <input id="wineId" name="wineId" type="text" readonly />

                            <label>Name:</label>
                            <input type="text" id="name" name="name" required>

                            <label>Price</label>
                            <input type="text" id="price" name="price"/>

                            <label>Date Added</label>
                            <input type="text" id="dateAdded" name="dateAdded"/>



                            <button id="btnSave" name="Update" type="button" value="Save">Save</button>

                        </div>

                        <div class="rightArea">

                            <button id="btnAdd">New Wine</button>

                        </div>

                    </form>

                </div>
            </sec:authorize>
        </div>

        <jsp:include page ="adminFooter.jsp" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script scr="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.16.0/additional-methods.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="scripts/sorttable.js" type="text/javascript"></script>
        <script src="scripts/jsonFunctions.js"></script>
    </body>
</html>
