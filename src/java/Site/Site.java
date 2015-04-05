package Site;

import javax.servlet.http.HttpSession;

/**
 * Static class to provide site information
 * @author Shang
 */
public class Site {
    public static final String headNav = 
            "<ul class=\"headNavUl\">\n" +
                "<li><a href=\"./index.jsp\">Home</a></li>\n" +
                "<li><a href=\"UserCenterServlet\">User Center</a></li>\n" +
            "</ul>";
    public static final String siteName = "LibraryX";
    
    public Site(){
    }
    
    public static String getHeaderLoginStr(HttpSession session){
        String headerLoginStr;
        if(session.getAttribute("UID")!=null && session.getAttribute("username")!=null){
            headerLoginStr = "Welcome, <a href='UserCenterServlet'>"+session.getAttribute("username")+"</a>! | "
                + " <a href='login.jsp?act=logout'>Logout</a> |";
        }else{
            headerLoginStr = "Welcome! | <a href='LoginServlet'>Login</a> | "
            + "<a href='RegisterServlet?act=register'>Register</a>";
        }
        return headerLoginStr;
    }
    
}
