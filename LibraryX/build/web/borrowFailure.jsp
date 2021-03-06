<%-- 
    Document   : Index
    Created on : 21/03/2015, 2:15:17 PM
    Author     : Shang
--%>
<%@page import = "javax.servlet.RequestDispatcher" %>
<%@page import = "Site.Book"%>

<jsp:useBean id="bookLended" class="Site.Book"
      scope="session" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

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
	<h3>Book: <%= bookLended.getTitle() %> </h3>
    <div class="bookListContainer">
        <h1>No Books left in library!</h2>
        <p>Book Details: </p>
        <p>Title: <%= bookLended.getTitle() %></p>
        <p>Author: <%= bookLended.getAuthor() %></p>
        <p>Year: <%= bookLended.getYear() %></p>
        <p>Introduction: <br /><%= bookLended.getIntro() %></p>
        <h3><a href=javascript:history.go(-1)>Go back</a></h3>
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
<%
    session.setAttribute("bookSearchMode",null);
%>