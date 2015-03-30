package Site;

import Site.DB;
import Site.Site;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * A controller to search books according to keywords
 * @author Shang
 */
public class CheckBooks extends HttpServlet {
    private  Connection conn;
    private PreparedStatement stmt;
    //initialize the servlet
    public CheckBooks()
      throws SQLException, ClassNotFoundException, IOException
    {
        Class.forName(DB.dbDriver);
        conn = DriverManager.getConnection(DB.dbUrl, DB.dbUsername, DB.dbPw);
    }
    
    public void doGet(HttpServletRequest request,HttpServletResponse response)
        throws ServletException, IOException
    {  // obtain the values of the form data automatically URL decoded
        String selectBy = request.getParameter("selectBy");
        String keywords = request.getParameter("keywords");
      
        if (selectBy==null || keywords==null || keywords.length()==0){// show page with form to obtain client name
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
        }else{// put client name into a bean
            String sqlField = "";
            if(selectBy.equalsIgnoreCase("author")) sqlField="author";
            else if(selectBy.equalsIgnoreCase("year"))sqlField="year";
            else sqlField="title";

            String sql = "SELECT * FROM books WHERE "+sqlField+" LIKE '%"+keywords+"%'";
            System.out.println(sql);
            ResultSet rs;

            try {
                synchronized(this) // synchronize access to stmt
                {
                    stmt = conn.prepareStatement(sql);
                    rs = stmt.executeQuery();
                    rs.next();
                    System.out.println(rs.getRow());   
                }
                String BooksHtml ="";
                HttpSession session = request.getSession(true);
              // pass bean to appropriate page for displaying response
                if (rs.getRow()>0){//result is not empty
                    Book[] books = Book.populateBooks(rs);//get array of books according to the sql query
                    //
                    session.setAttribute("books", books);//passing the ResultSet directly to the jsp
                    session.setAttribute("bookSearchMode", "keywords");
                    
                    BooksHtml += Book.doBookHtml(books);
                    request.setAttribute("ListOfBooks", BooksHtml);
                    System.out.println(BooksHtml);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/search.jsp");
                    dispatcher.forward(request, response);
                }else{
                    
                    session.setAttribute("books", null);//passing the ResultSet directly to the jsp
                    session.setAttribute("bookSearchMode", "keywords");
                    BooksHtml=  "<div class='bookContainer'>"+
                                    "<h4>No Books found!</h4>"+
                                "</div>";
                    request.setAttribute("ListOfBooks", BooksHtml);
                    System.out.println(BooksHtml);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/search.jsp");
                    dispatcher.forward(request, response);
                }
            } catch (SQLException exc) {
                System.out.println(exc);
            }
      }
   }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {  
        doGet(request, response);
    }
}
