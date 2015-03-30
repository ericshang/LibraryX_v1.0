package Site;


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The lending class store  all lending data
 * @author Shang
 */
public class Lending implements Serializable {
    private int bid;//book id
    private int uid;//user id
    private long lendingTime;//the time lended, in timestamp format
    private int renewTimes; //how many times the lending has been renewed
    private Book book;
    
    //constructors
    public Lending(int bookId, int userId, long timestamp, int times){
        setBookId(bookId);
        setUserId(userId);
        setLendingTime(timestamp);
        setRenewTimes(times);
    }
    public Lending(int bookId, int userId, long timestamp, int times, Book book){
        this(bookId, userId, timestamp, times);
        this.setBook(book);
    }
    
    //getters
    public int getBookId(){
        return bid;
    }
    public int getUserId(){
        return uid;
    }
    public long getLendingTime(){
        return this.lendingTime;
    }
    public int getRenewTimes(){
        return this.renewTimes;
    }
    //setters
    public void setBookId(int id){
        this.bid = id;
    }
    public void setUserId(int id){
        this.uid = id;
    }
    public void setLendingTime(long timestamp){
        this.lendingTime = timestamp;
    }
    public void setRenewTimes(int times){
        this.renewTimes = times;
    }
    public void setBook(Book book){
        this.book = book;
    }
    public Book getBook(){
        return this.book;
    }
    public static String doLendingHtml(Lending[] lendings){
        String BooksListInHtml="";
        try{
            for(int i=0; i<lendings.length; i++){
                long timeStamp = lendings[i].getLendingTime();
                long now = new Date().getTime();
                int timePassed = (int)(now-timeStamp);//in milli second
                double daysPassed =timePassed/(24*60*60*1000);
                int daysLeft = (int)(14 - daysPassed);
                long dueTime = timeStamp + 14*(24*60*60*1000);
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dueDate = dateFormat.format(dueTime);
                
                BooksListInHtml +=  "<div class='bookContainer'>"+
                                    "<h4><a href='./BookDetailsServlet?bid="+lendings[i].getBook().getBid()+"'>"+lendings[i].getBook().getTitle()+"</a></h4>"+
                                    "<p>Days Left: "+daysLeft+"</p>"+
                                    "<p class='bookContainerPInto'> Due time: "+ dueDate +"</p>"+
                                    "<div class='bookContainerOps'></div>"+
                                "</div>";
             }
        }catch(NullPointerException exc){
            System.out.println(exc);
        }
        return BooksListInHtml;
    }
    
    public static Lending[] populateLendings(ResultSet rs)
        throws SQLException{
        int numOfRecords = 0;
        rs.last();
        numOfRecords = rs.getRow();
        Lending[] lendings = new Lending[numOfRecords];
        rs.beforeFirst();
        int i = 0;
        while(rs.next()){
             int bid = rs.getInt("bid");
             String title = rs.getString("title");
             String author= rs.getString("author");
             String intro= rs.getString("intro");
             int quantity=rs.getInt("quantity");
             int year=rs.getInt("year");
             
             int uid = rs.getInt("uid");
             long lendingTime = rs.getLong("lendingtime");
             int renewTimes =0; //rs.getInt("renew_times");
             lendings[i]= new Lending(bid, uid, lendingTime, renewTimes, 
                                new Book(bid, title, author,intro, quantity, year));
             
             i++;
        }
        return lendings;
    }
    
}
