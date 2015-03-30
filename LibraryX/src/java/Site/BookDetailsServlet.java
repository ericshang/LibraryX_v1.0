package Site;

import Site.DB;
import java.io.IOException;
import java.io.PrintWriter;
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
 * Controller to show book details
 * @author Shang
 */
public class BookDetailsServlet extends HttpServlet {

    private  Connection conn;
    private PreparedStatement stmt;
    private int bid;
    //initialize the servlet
    public BookDetailsServlet()
            throws SQLException, ClassNotFoundException, IOException
    {
        Class.forName(DB.dbDriver);
        conn = DriverManager.getConnection(DB.dbUrl, DB.dbUsername, DB.dbPw);
    }
    
    public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException
    {  // obtain the values of the form data automatically URL decoded
        String bookId =request.getParameter("bid");
      
        if (bookId==null){// show page with form to obtain client name
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/IndexServlet");
            dispatcher.forward(request, response);
        }else{  // put client name into a bean
            bid = Integer.parseInt(bookId);
            String sql = "SELECT * FROM books Where bid = "+bid;
            ResultSet rs;
            Book book;

            try {
                synchronized(this) // synchronize access to stmt
                {
                    stmt = conn.prepareStatement(sql);
                    rs = stmt.executeQuery();
                }
                if(rs.next()){
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String intro = rs.getString("intro");
                    int quantity = rs.getInt("quantity");
                    int year = rs.getInt("year");
                    book = new Book(bid,title,author,intro,quantity,year);
                    //
                    HttpSession session = request.getSession(true);
                    session.setAttribute("book", book);//passing the ResultSet directly to the jsp

                    request.setAttribute("book", book);

                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/bookDetails.jsp");
                    dispatcher.forward(request, response);
                }else{
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/IndexServlet");
                    dispatcher.forward(request, response);
                }        
            } catch (SQLException exc) {
                System.out.println(exc);
            }

        }
   }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException{  
       doGet(request, response);
   }
    
}
