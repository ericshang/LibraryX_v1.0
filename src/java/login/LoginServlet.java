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
public class LoginServlet extends HttpServlet {
    private  Connection conn;
    private PreparedStatement stmt;
    HttpSession session;
    
    public LoginServlet()
          throws SQLException, ClassNotFoundException, IOException
    {
        Class.forName(DB.dbDriver);
        conn = DriverManager.getConnection(DB.dbUrl, DB.dbUsername, DB.dbPw);
    }
    
    public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException
    {
        session = request.getSession(false);//check if the session has initiated or not
        String act = request.getParameter("act");
        if(act!=null && act.equals("logout")){
            session.invalidate();
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }else{
            if(session==null || session.getAttribute("UID") ==null){
            //jump to login page
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            }else{
                doPost(request,response);
            }
        }
        
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException,NullPointerException{  
        String username = request.getParameter("username");
        String pw = request.getParameter("password");
        
        if (username ==null || pw==null || session==null){//go to register page
            doGet(request,response);            
        }else{
            processRequest(request, response);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String pw = request.getParameter("password");

        String sql = "SELECT * FROM users WHERE username = '"+username+"' and password ='"+ pw +"'";
        ResultSet rs;
        User user = new User();
        user.setUsername(username);
        user.setPassword(pw);
        int uid = -1;
        String sqlExc ="";
        
        try {
            synchronized(this) // synchronize access to stmt
            {
               stmt = conn.prepareStatement(sql);
               rs =stmt.executeQuery();//check db
               if(rs.next()){
                    uid = rs.getInt("uid");
                    user.setUid(uid);
               }
            }
            System.out.println("user id is: "+uid);
            System.out.println("user id <0 : "+(uid<0));
            if(uid>0){
                session = request.getSession(true);
                session.setAttribute("user", user);//passing the ResultSet directly to the jsp
                session.setAttribute("UID", uid);
                session.setAttribute("username", username);
                System.out.println("user logged");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }else{
                System.out.println("user not logged in ");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/LoginServlet");
                dispatcher.forward(request, response);
            }
            
        }catch (SQLException exc) {
            sqlExc = exc.toString();
            System.out.println(exc);
        }
    }
    
}
