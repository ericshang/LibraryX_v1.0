package Site;

import Site.DB;
import Site.Site;
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
 * To show home page, a home page controller
 * @author Shang
 */
public class IndexServlet extends HttpServlet {
    private  Connection conn;
    private PreparedStatement stmt;
    
    public IndexServlet()
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
      
        if (selectBy!=null || keywords!=null){// show page with form to obtain client name
            RequestDispatcher dispatcher = getServletContext()
                    .getRequestDispatcher("/CheckBooks?selectBy="+ selectBy +"&keywords="+keywords);
            dispatcher.forward(request, response);
        }else{  // put client name into a bean
            String sql = "SELECT * FROM books ";
            ResultSet rs;
            Book[] books;
            String BooksHtml ="";
            try {
                synchronized(this) // synchronize access to stmt
                {
                   stmt = conn.prepareStatement(sql);
                   rs = stmt.executeQuery();
                   rs.next();
                   System.out.println(rs.getRow());   
                }
                
                books = Book.populateBooks(rs);//get array of books according to the sql query

                HttpSession session = request.getSession(true);
                session.setAttribute("books", books);//passing the ResultSet directly to the jsp
                session.setAttribute("bookSearchMode", "showAll");
                
                BooksHtml += Book.doBookHtml(books);
                request.setAttribute("ListOfBooks", BooksHtml);
                System.out.println(BooksHtml);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);

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
