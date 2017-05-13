<%-- 
    Document   : login
    Created on : May 2, 2017, 5:32:32 PM
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
        <link rel='shortcut icon' type='image/x-icon' href='favicon.png'/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="scripts/wineAdmin.css">
       <link href="https://fonts.googleapis.com/css?family=Montserrat:700|Open+Sans" rel="stylesheet">
        <title>GRR Wines</title>
    </head>

    <body>
        <jsp:include page ="adminHeader.jsp" /> 
                <div id="content">
 <div class ="col-md-6">
            <div class="container" id="form-login-container">  
        <form id="signInForm" role="form" class="form-inline" method='POST' action="<c:url value='j_spring_security_check' />">
            <sec:csrfInput />
            
            
            <h3>Log In </h3>
                <h4>Please log in to access member benefits and shop new exclusive wines.</h4>
                <div class ="col-md-12"> 
                  <label for="login_username">Username: </label>
                <div class="form-group">
                   
                    <input tabindex="1" class="form-control" id="login_username" name="j_username" placeholder="Email address" type="text" required autofocus />
                     </div>
                  <br>
                
                      <label for="login_password">Password: </label>
                    <div class="form-group">
                       
                    <input tabindex="2" class="form-control" id="login_password" name="j_password" type="password" placeholder="password"  required/>
                </div>
                </div>
                <div class="form-group">
                 
                    <input type="submit" tabindex="3" name="saveForm" class="btTxt submit" id="btn-login-submit" value="Sign In"/>
                </div>
          
        </form>
            </div>
            </div>
   <div class ="col-md-6">          
           <div class="container">
  
  <div id="myCarousel" class="carousel slide" data-ride="carousel">
      <h3>Great River Wine Road Spring Highlights! </h3>  
    <!-- Indicators -->
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="1"></li>
      <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner">
      <div class="item active">
        <img src="images/car_one.jpg" alt="" style="width:100%;"/>
      </div>

      <div class="item">
         <img src="images/car_two.jpg" alt="" style="width:100%;"/>
      </div>
    
      <div class="item">
        <img src="images/car_three.jpg" alt=""style="width:100%;"/>
       
      </div>
    </div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
  </div>
</div> 
     </div>       
  <jsp:include page ="adminFooter.jsp" /> 
            
            
            
            
            
            
            

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    </body>
</html>