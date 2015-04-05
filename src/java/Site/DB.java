package Site;

/**
 * A controller class for process database
 * @author Shang
 */
public class DB {
    //set up db envorionment
    public static final String dbDriver ="com.mysql.jdbc.Driver"; //db driver type
    public static final String dbName = "library";  //database name
    public static final String dbUrl ="jdbc:mysql://localhost:3306/"+dbName;
    public static final String dbUsername ="root"; //db username
    public static final String dbPw = "********"; //db password
  
}
