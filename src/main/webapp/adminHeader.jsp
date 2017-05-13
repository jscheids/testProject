<%-- 
    Document   : adminHeader
    Created on : Mar 22, 2017, 5:51:32 PM
    Author     : Jennifer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<link rel='shortcut icon' type='image/x-icon' href='favicon.png'/>
<link href="https://fonts.googleapis.com/css?family=Montserrat:700|Open+Sans" rel="stylesheet">
    <nav class="navbar navbar-default navbar-static-top" style="background-color: #dae3cd;">
       
        <div class="container">
            <div class="navbar-header">
                <p style="float: left;"><img class="img-responsive" style="max-width:80px; margin-top: 7px; margin-right: 1px;"
                                                src="images/riverLogo.png"></p>
             
                <h2 style="display:inline-block;font-family: 'Montserrat', sans-serif;font-weight: bolder;font-size: xx-large;   padding-top: 15px; padding-left: 25px; color:#59821e;">Wisconsin Great River Road Wines</h2>
                <br> <h4  style="display:inline-block;font-family:  'Montserrat', sans-serif;"> Discover Wisconsin with locally produced wines. </h4>
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#collapse-menu" >
                    <span class="sr-only">Toggle Navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse" id="collapse-menu"  >
                <ul class="nav navbar-nav navbar-right">
                    <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
                    <li ><a href='<%= response.encodeURL("index.jsp")%>' style=" border-radius: 6px; margin-top:25px;  left: 85px; color: #59821e;"> Home</a></li>
                     </sec:authorize>
                    <li style="border-radius: 6px; margin-top:40px;  left: 90px; color: #59821e; font-weight: 200; ">     <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            <sec:authentication property="principal.username"></sec:authentication>
           
        </sec:authorize></li>
                    <li>    <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
                  
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>' style="  border-radius: 6px; margin-top:25px;  left: 103px; color: #59821e;">Log Out </a>
        </sec:authorize>
                      </li>
                </ul>


            </div>
            
    </nav>


