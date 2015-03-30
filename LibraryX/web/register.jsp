<%-- 
    Document   : login
    Created on : 23/03/2015, 6:03:30 PM
    Author     : tcm4102
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="images/decault.css" rel="stylesheet" type="text/css"/>
<title><%= Site.Site.siteName%> Online Library</title>
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
    <h3>Register New User</h3>
    <div class="loginContainer">
        <form method="post" action="RegisterServlet">
            <p>Username: <br /><input name="username"/></p>
            <p>Your Password: <br /><input type="password" name="pw1" /></p>
            <p>Repeat Password: <br /><input type="password" name="pw2" /></p>
            <p><input type="submit" value="Register" /> | <a href="LoginServlet">Login</a></p>
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