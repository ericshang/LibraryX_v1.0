<%-- 
    Document   : search
    Created on : 25/03/2015, 1:50:55 PM
    Author     : Shang
--%>

<%@page import = "javax.servlet.RequestDispatcher" %>
<%@page import = "Site.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String selectBy = request.getParameter("selectBy");
    String keywords = request.getParameter("keywords");
    String WindowJump ="";

    if (selectBy==null || keywords == null || selectBy.length() <0 || selectBy.length() <0 )
    {  // there is no need to display form as name already provided
       WindowJump ="<script>window.location.href='./IndexServlet';</script>";
    }
    
    String keyword = "";
    String BooksListInHtml ="";
    BooksListInHtml = request.getAttribute("ListOfBooks").toString();
    
    //int booksLength = ((Book[])session.getAttribute("books")).length;
    if(session.getAttribute("bookSearchMode")!=null&& request.getAttribute("ListOfBooks")!=null){
        if(session.getAttribute("bookSearchMode").toString().equals("showAll"))
            keyword = "'All'";
        else{
            keyword = "\""+ selectBy +": "+ keywords +"\"";
        }
       //Book[] Books = (Book[])request.getAttribute("books");
      BooksListInHtml = request.getAttribute("ListOfBooks").toString();
    }else{
        BooksListInHtml = (session.getAttribute("books")==null)+"";
    }      
%>
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
	<h3>Book list of  <%= keyword %></h3>
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
