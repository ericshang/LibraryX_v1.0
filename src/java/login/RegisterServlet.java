/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import Site.Book;
import Site.DB;
import Site.User;
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
 *
 * @author Shang
 */
public class RegisterServlet extends HttpServlet {
    private Connection conn;
    private PreparedStatement stmt;
    private PreparedStatement stmt2;
    HttpSession session;
    
    public RegisterServlet()
          throws SQLException, ClassNotFoundException, IOException
    {
        Class.forName(DB.dbDriver);
        conn = DriverManager.getConnection(DB.dbUrl, DB.dbUsername, DB.dbPw);
    }

    
    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException
    {
        session = request.getSession(false);//check if the session has initiated or not  
        //if ser has logged in
        if(session !=null){
            session.invalidate();
        }
        doPost(request,response);

    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException{

        String username = request.getParameter("username");
        String pw1 = request.getParameter("pw1");
        String pw2 = request.getParameter("pw2");
        
        if (username ==null || pw1==null || pw2 ==null || !pw1.equals(pw2) || username.length()<1){//go to register page
            RequestDispatcher dispatcher = getServletContext()
                    .getRequestDispatcher("/register.jsp");
            dispatcher.forward(request, response);
        }else{
            processRequest(request, response);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String pw1 = request.getParameter("pw1");
        String pw2 = request.getParameter("pw2");
        
        String sql ="Insert Into users(username, password)"
                                    + "values('"+username+"', '"+pw1+"')";
        
        String sql2 = "SELECT * FROM users WHERE username = '"+username+"'";
        ResultSet rs;
        User user = new User();
        user.setUsername(username);
        user.setPassword(pw1);
        int uid = -1;
        String sqlExc ="";
        
        try {
            synchronized(this) // synchronize access to stmt
            {
               stmt = conn.prepareStatement(sql);
               stmt.executeUpdate();//insert into db
               stmt2 = conn.prepareStatement(sql2);
               rs =stmt2.executeQuery();//check db
               if(rs.next()){
                    uid = rs.getInt("uid");
                    user.setUid(uid);
               }
            }
            
            session = request.getSession(true);
            session.setAttribute("user", user);//passing the ResultSet directly to the jsp
            session.setAttribute("UID", uid);
            session.setAttribute("username", username);
            System.out.println("user registered");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
            
        }catch (SQLException exc) {
            sqlExc = exc.toString();
            System.out.println(exc);
        }
    }
}
