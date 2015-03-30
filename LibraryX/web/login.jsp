<%-- 
    Document   : login
    Created on : 23/03/2015, 6:03:30 PM
    Author     : tcm4102
--%>

<%
    String WindowJump ="";
    if (request.getParameter("act") != null && request.getParameter("act").toString().equals("logout")) {
        WindowJump ="<script>window.location.href='./LoginServlet?act=logout';</script>";
    }
%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%= WindowJump %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="images/decault.css" rel="stylesheet" type="text/css"/>
<title><%= Site.Site.siteName%> -Online Library</title>
</head>

<body>
<!--Header Starts-->
<div class="headBox">
	<div class="headTopBox">
        <%= Site.Site.getHeaderLoginStr(session) %>
    </div>
    <h1><%= Site.Site.siteName%></h1>
    <div class="headSearch">
        <div class="Nav">
            <div class="headTopBoxLeft">
                <%= Site.Site.headNav%>
            </div>        
            <div  class="headTopBoxRight">
                <form method="get" action="./CheckBooks">
                <select name="selectBy">
                    <option value="title">Book Title</option>
                    <option value="author">Author</option>
                    <option value="year">Year</option>
                </select>
                <input type="text" name="keywords" /> <input type="submit" value="Search" />
                 </form>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
<!--Header ends-->

<!--Main Starts-->
<div class="mainBox">
    <h3>User Login</h3>
    <div class="loginContainer">
        <form action="LoginServlet" method="post">
            <p>
                Username: <br />
                <input type="text" name="username" value="" maxlength="20" />
            </p>
            <p>
                Your Password: <br />
                <input type="password" name="password" maxlength="30" />
            </p>
            <p><input type="submit" value="Login" /> | <a href="RegisterServlet">Register</a></p>

        </form>
    </div>


</div>
<!--Main ends-->



<!--footer Starts-->
<div class="footerBox">
	<div class="footer">
    	As assignment 1 for Distributed Mobile System 
    </div>
</div>
<!--footer ends-->
</body>
</html>