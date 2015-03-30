package Site;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * to represent Book entity
 * @author Shang
 */
public class Book implements Serializable {
    private int bid;
    private String title;
    private String author;
    private String intro;
    private int quantity;
    private int year;

    //constructor
    public Book(){
    }
    public Book(int id, String t, String au,String in, int qty, int yr ){
        setBid(id);
        setTitle(t);
        setAuthor(au);
        setIntro(in);
        setQuantity(qty);
        setYear(yr);
    }
    
    //getters
    public int getBid(){
        return bid;
    }
    public String getTitle(){
        return this.title;
    }
    public String getAuthor(){
        return this.author;
    }
    public String getIntro(){
        return this.intro;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public int getYear(){
        return this.year;
    }
    //setters
    public void setBid(int id){
        this.bid=id;
    }
    public void setTitle(String t){
        this.title = t;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setIntro(String intro){
        this.intro = intro;
    }
    public void setQuantity(int q){
        this.quantity = q;
    }
    public void setYear(int y){
        this.year = y;
    }

    public static Book[] populateBooks(ResultSet rs)
        throws SQLException{
        int numOfRecords = 0;
        rs.last();
        numOfRecords = rs.getRow();
        Book[] books = new Book[numOfRecords];
        rs.beforeFirst();
        int i = 0;
        while(rs.next()){
             int bid = rs.getInt("bid");
             String title = rs.getString("title");
             String author= rs.getString("author");
             String intro= rs.getString("intro");
             int quantity=rs.getInt("quantity");
             int year=rs.getInt("year");
             books[i]= new Book(bid, title, author,intro,quantity,year );
             i++;
        }
        return books;
    }
    
    public static String doBookHtml(Book[] books){
        String BooksListInHtml="";
        try{
            for(int i=0; i<books.length; i++){
                BooksListInHtml += "<div class='bookContainer'>"+
                                    "<h4><a href='./BookDetailsServlet?bid="+books[i].getBid()+"'>"+books[i].getTitle()+"</a></h4>"+
                                    "<p>Author: "+books[i].getAuthor()+" | Year: "+books[i].getYear()+"</p>"+
                                    "<p class='bookContainerPInto'>"+books[i].getIntro()+"</p>"+
                                    "<div class='bookContainerOps'><a href='BorrowServlet?bid="+books[i].getBid()+"'>+ Borrow</a></div>"+
                                "</div>";
             }
        }catch(NullPointerException exc){
            System.out.println(exc);
        }
        return BooksListInHtml;
    }
    
    @Override
    public String toString(){
        return "id: "+getBid()+" | "+getTitle()+" | "+getAuthor()+" | Available: "+getQuantity();
    } 
}
