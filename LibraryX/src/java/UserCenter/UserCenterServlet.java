/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserCenter;

import Site.Book;
import Site.DB;
import Site.Lending;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shang
 */
public class UserCenterServlet extends HttpServlet {

    private  Connection conn;
    private PreparedStatement stmt;
    HttpSession session;
    int uid = -1;
    
    public UserCenterServlet()
          throws SQLException, ClassNotFoundException, IOException
    {
        Class.forName(DB.dbDriver);
        conn = DriverManager.getConnection(DB.dbUrl, DB.dbUsername, DB.dbPw);
    }
    
    public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException{
        session = request.getSession(false);
        if(session ==null || session.getAttribute("UID") == null){//user is not logged in
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/LoginServlet");
            dispatcher.forward(request, response);
        }
        
        uid = Integer.parseInt(session.getAttribute("UID").toString());

        String sql ="SELECT * FROM lendings l, books b WHERE b.bid=l.bid and l.uid="+uid;
        System.out.println(sql);
        ResultSet rs;
        Book[] books;
        Lending[] lendings;
        String BooksHtml ="";
        try {
            synchronized(this) // synchronize access to stmt
            {
               stmt = conn.prepareStatement(sql);
               rs = stmt.executeQuery(); 
            }

            books = Book.populateBooks(rs);//get array of books according to the sql query
            lendings = Lending.populateLendings(rs);
            HttpSession session = request.getSession(true);
            session.setAttribute("books", books);//passing the ResultSet directly to the jsp
            session.setAttribute("userCenter", "default");

            BooksHtml += Lending.doLendingHtml(lendings);
            if(BooksHtml.length()<1){
                BooksHtml=  "<div class='bookContainer'>"+
                                    "<h4>You have not borrowed any books yet!</h4>"+
                                "</div>";
            }
            request.setAttribute("BooksBorrowed", BooksHtml);
            System.out.println(BooksHtml);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/userCenter.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException exc) {
            System.out.println(exc);
        }
        
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException{  
        doGet(request, response);
    }

}
