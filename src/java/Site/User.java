package Site;
import java.io.Serializable;

/**
 *
 * @author Shang
 */
public class User implements Serializable{
    private int uid;
    private String username;
    private String password;
    
    //constructors
    public User(int id, String username, String pw){
        setUid(id);
        setUsername(username);
        setPassword(pw);
    }
    public User(){}
    
    //getters;
    public int getUid(){
        return this.uid;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    
    //setters;
    public void setUid(int id){
        this.uid = id;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String pw){
        this.password = pw;
    }
    public String toString(){
        return "User ID: "+ getUid() +" Username: "+getUsername();
    }
}
