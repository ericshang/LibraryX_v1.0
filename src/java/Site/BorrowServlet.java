package Site;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
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
 * A controller to finish borrowing books
 * @author Shang
 */
public class BorrowServlet extends HttpServlet {
    private  Connection conn;
    private PreparedStatement stmt;
    private PreparedStatement stmt2;
    private PreparedStatement stmt3;
    private Book book;
    private boolean isLended = false;
    private String failureReason = "";
    
    HttpSession session;
    public BorrowServlet()
      throws SQLException, ClassNotFoundException, IOException
    {
        Class.forName(DB.dbDriver);
        conn = DriverManager.getConnection(DB.dbUrl, DB.dbUsername, DB.dbPw);
    }
    
    public void doGet(HttpServletRequest request,HttpServletResponse response)
        throws ServletException, IOException
    {  
        session = request.getSession(false);//check if the session has initiated or not  
        //check if user has logged in
        System.out.println(session==null);
        if(session==null || session.getAttribute("UID") == null || session.getAttribute("username")==null){
        //jump to login page
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/LoginServlet");
            dispatcher.forward(request, response);
        }
        
         processRequest(request, response);
   }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {  
        doGet(request, response);
    }
    
    private boolean isEntityInDB(String sql){
        boolean isEndityFound = false;
        ResultSet rs;
        PreparedStatement stmt;
        try {
            synchronized(this) // synchronize access to stmt
            {
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();   
            }  
          // pass bean to appropriate page for displaying response
            if (rs.next()){//result is not empty
                isEndityFound = true;                
            }
        } catch (SQLException exc) {
            System.out.println(exc);
        }
        return isEndityFound;
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String msg1 ="";
        String msg2 ="";
        String bookLendedSql="";
        String sql="";
        String userSQL = "";
        int lendedQuantity = 0;
        String lendingSql = "";
        
        
        String bookId = request.getParameter("bid");//essential parameter foor book id
        int bid = Integer.parseInt(bookId);
        int uid = (int) session.getAttribute("UID");

        if(bid<1 || uid<1){//illegal bid parameter
            //RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/");
            //dispatcher.forward(request, response);
        }else{
            //check if the book exists
            sql = "SELECT * FROM books WHERE bid = "+bid;
            userSQL = "SELECT * FROM users WHERE uid = "+uid;
            boolean bookFound = isEntityInDB(sql);//check if book in db
            boolean userFound = isEntityInDB(userSQL);//user is in table
            
            if(bookFound && userFound){
                ResultSet rs;
                ResultSet lendedRs;
                //check how many books have lended out
                bookLendedSql = "SELECT count(*) lended FROM lendings WHERE bid = "+bid;
                try {
                    synchronized(this) // synchronize access to stmt
                    {
                        stmt = conn.prepareStatement(sql);
                        rs = stmt.executeQuery();//check books
                        stmt2 = conn.prepareStatement(bookLendedSql);
                        lendedRs = stmt2.executeQuery();//check book lended
                        msg1="stmt, stmt2 executed";
                    }  
                  // pass bean to appropriate page for displaying response
                    if (rs.next()){//result is not empty
                        String title = rs.getString("title");
                        String author = rs.getString("author");
                        String intro = rs.getString("intro");
                        int bookQuantity = rs.getInt("quantity");//total quantity
                        int year = rs.getInt("year");
                        book = new Book(bid,title,author,intro,bookQuantity,year);
                        msg2 = "msg 2 executed";
                        //number of books lended out
                        
                        if(lendedRs.next()){
                            lendedQuantity = Integer.parseInt(lendedRs.getString("lended"));
                        }
                        // !important
                        int numberOfBooksAvailable = bookQuantity-lendedQuantity;
                        if(numberOfBooksAvailable<=0){//no book availible
                            isLended = false;
                            failureReason = "No Book available";
                            session.setAttribute("isLended", isLended);
                            session.setAttribute("failureReason", failureReason);
                            book = new Book(bid,title,author,intro,bookQuantity,year);
                            session.setAttribute("bookLended", book);
                            System.out.println(book.toString());
                            //this should jump to UserCenterServlet
                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/borrowFailure.jsp");
                            dispatcher.forward(request, response);
                        }else{
                            //do lending
                            Date now = new Date();       
                            long timestamp = now.getTime();
                            //!important update lendings table
                            lendingSql ="Insert Into lendings(bid,uid,lendingtime)"
                                    + "values('"+bid+"', '"+uid+"', '"+timestamp+"')"; 
                            synchronized(this) // synchronize access to stmt
                            {
                                stmt3 = conn.prepareStatement(lendingSql);
                                stmt3.executeUpdate();
                                isLended = true;
                                failureReason = "Lended";

                            } 
                            
                            session.setAttribute("isLended", isLended);
                            session.setAttribute("failureReason", failureReason);
                            session.setAttribute("bookLended", book);
                            //this should jump to UserCenterServlet
                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserCenterServlet");
                            dispatcher.forward(request, response);
                        }
                    }
                } catch (SQLException exc) {
                    System.out.println(exc);
                }
            }
        }
    }
}
