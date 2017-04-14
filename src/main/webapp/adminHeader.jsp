<%-- 
    Document   : adminHeader
    Created on : Mar 22, 2017, 5:51:32 PM
    Author     : Jennifer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


    <nav class="navbar navbar-default navbar-static-top" style="background-color: #f1dba9;">

        <div class="container">
            <div class="navbar-header">
                <p style="float: left;"><img class="img-responsive" style="max-width:80px; margin-top: 7px; margin-right: 1px;"
                                             src="images/logo.png"></p>
                <h2 style="display:inline-block; font-family:'Cormorant Upright', serif;font-weight: bolder;font-size: xx-large;   padding-top: 15px; padding-left: 25px; color:#652e38;">Great River Road Wines</h2>
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#collapse-menu" >
                    <span class="sr-only">Toggle Navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse" id="collapse-menu"  >
                <ul class="nav navbar-nav navbar-right" >
                    <li class="active"><a href=<%= response.encodeURL("index.jsp")%> style="background-color: #652e38; border-radius: 6px; margin-top:25px;  left: 100px; color: #f1dba9;">Home</a></li>
                </ul>


            </div>
    </nav>


