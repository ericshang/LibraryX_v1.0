<%-- 
    Document   : Index
    Created on : 21/03/2015, 2:15:17 PM
    Author     : Shang
--%>
<%@page import = "javax.servlet.RequestDispatcher" %>
<%@page import = "Site.Book"%>
<%
    String selectBy = request.getParameter("selectBy");
    String keywords = request.getParameter("keywords");
    String keyword = "";
    String BooksListInHtml ="";
    String WindowJump ="";
    //if it has GET values, direct to CheckBooks servlet to do search
    if (selectBy!=null && keywords != null)
    {  // there is no need to display form as name already provided
       RequestDispatcher dispatcher = getServletContext().
          getRequestDispatcher("/CheckBooks?selectBy="+ selectBy +"&keywords="+keywords);
       dispatcher.forward(request, response);
    }
    if(session.getAttribute("bookSearchMode")==null){
        WindowJump ="<script>window.location.href='./IndexServlet';</script>";
    }else{
        //if it hasn't been searched throgh IndexServlet, go there and search
        if(!session.getAttribute("bookSearchMode").toString().equals("showAll")){
            RequestDispatcher dispatcher = getServletContext().
              getRequestDispatcher("/IndexServlet");
           dispatcher.forward(request, response);
        }

        if(request.getAttribute("ListOfBooks")!=null){
            keyword = "'All'";
            BooksListInHtml = request.getAttribute("ListOfBooks").toString();
        }
    }
        
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
<%
    session.setAttribute("bookSearchMode",null);
%>