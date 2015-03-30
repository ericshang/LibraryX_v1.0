<%-- 
    Document   : userCenter
    Created on : 28/03/2015, 8:52:39 PM
    Author     : Shang
--%>
<%
    String BooksListInHtml ="";
    String WindowJump ="";
    BooksListInHtml = request.getAttribute("BooksBorrowed").toString();
        
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
	<h3>The books you borrowed: </h3>
    <div class="bookListContainer">
        <%= BooksListInHtml %>
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