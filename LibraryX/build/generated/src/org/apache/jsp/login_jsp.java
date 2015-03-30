package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write('\n');
      out.write('\n');

    String act="";
    String WindowJump ="";
    String actStr = "";
    
    if (request.getAttribute("act") != null && request.getAttribute("act").toString().equals("logout")) {
        actStr = request.getAttribute("act").toString();
        WindowJump ="<script>window.location.href='./LoginServlet?act=logout';</script>";
    }
    /*String username = "", password = "";
    boolean unpw = true;

    if (request.getAttribute("username") != null) {
        username = (String) request.getAttribute("username");
    }
    if (request.getAttribute("password") != null) {
        password = (String) request.getAttribute("password");
    }
    if ((Boolean) request.getAttribute("unpw") == null) {
        unpw = true;
    } else {
        unpw = (Boolean) request.getAttribute("unpw");
    }
            */

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.print( WindowJump );
      out.write("\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
      out.write("<link href=\"images/decault.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("<title>");
      out.print( Site.Site.siteName);
      out.write(" -Online Library</title>\n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("<!--Header Starts-->\n");
      out.write("act str is: ");
      out.print( actStr );
      out.write("\n");
      out.write("<div class=\"headBox\">\n");
      out.write("\t<div class=\"headTopBox\">\n");
      out.write("        ");
      out.print( Site.Site.getHeaderLoginStr(session) );
      out.write("\n");
      out.write("    </div>\n");
      out.write("    <h1>");
      out.print( Site.Site.siteName);
      out.write("</h1>\n");
      out.write("    <div class=\"headSearch\">\n");
      out.write("        <div class=\"Nav\">\n");
      out.write("            <div class=\"headTopBoxLeft\">\n");
      out.write("                ");
      out.print( Site.Site.headNav);
      out.write("\n");
      out.write("            </div>        \n");
      out.write("            <div  class=\"headTopBoxRight\">\n");
      out.write("                <form method=\"get\" action=\"./CheckBooks\">\n");
      out.write("                <select name=\"selectBy\">\n");
      out.write("                    <option value=\"title\">Book Title</option>\n");
      out.write("                    <option value=\"author\">Author</option>\n");
      out.write("                    <option value=\"year\">Year</option>\n");
      out.write("                </select>\n");
      out.write("                <input type=\"text\" name=\"keywords\" /> <input type=\"submit\" value=\"Search\" />\n");
      out.write("                 </form>\n");
      out.write("            </div>\n");
      out.write("            <div class=\"clear\"></div>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("</div>\n");
      out.write("<!--Header ends-->\n");
      out.write("\n");
      out.write("<!--Main Starts-->\n");
      out.write("<div class=\"mainBox\">\n");
      out.write("    <h3>User Login</h3>\n");
      out.write("    <div class=\"loginContainer\">\n");
      out.write("        <form action=\"LoginServlet\" method=\"post\">\n");
      out.write("            <p>\n");
      out.write("                Username: <br />\n");
      out.write("                <input type=\"text\" name=\"username\" value=\"\" maxlength=\"20\" />\n");
      out.write("            </p>\n");
      out.write("            <p>\n");
      out.write("                Your Password: <br />\n");
      out.write("                <input type=\"password\" name=\"password\" maxlength=\"30\" />\n");
      out.write("            </p>\n");
      out.write("            <p><input type=\"submit\" value=\"Login\" /> | <a href=\"RegisterServlet\">Register</a></p>\n");
      out.write("\n");
      out.write("        </form>\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("\n");
      out.write("</div>\n");
      out.write("<!--Main ends-->\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!--footer Starts-->\n");
      out.write("<div class=\"footerBox\">\n");
      out.write("\t<div class=\"footer\">\n");
      out.write("    \tAs assignment 1 for Distributed Mobile System \n");
      out.write("    </div>\n");
      out.write("</div>\n");
      out.write("<!--footer ends-->\n");
      out.write("</body>\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
