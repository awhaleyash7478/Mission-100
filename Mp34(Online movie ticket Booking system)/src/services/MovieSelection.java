package services;
import java.security.DrbgParameters.Reseed;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
public class MovieSelection {
Scanner sc;
Connection conn;
 ShowSelection s;


   public  MovieSelection(Scanner sc,Connection conn,ShowSelection s)//TheatreSelection t)
{

    this.sc=sc;
    this.conn=conn;
     this.s=s;
     
}


public int movieID;
int generated_cus_id;

 
public void viewMovies()
{
try 
{
    
    String query="Select * from movies where status='Running'";
    PreparedStatement ps=conn.prepareStatement(query);
    ResultSet rs=ps.executeQuery();
   System.out.printf("%-8s %-30s %-15s %-20s %-10s %-8s %-12s %-10s%n",
        "ID", "Movie Name", "Language", "Genre",
        "Duration", "Rating", "Certificate", "Status");
    System.out.printf("----------------------------------------------------------------------------------------------------------------------\n");

    int found=0;
while (rs.next()) {
    found=1;
    System.out.printf("%-8d %-30s %-15s %-20s %-10d %-8.1f %-12s %-10s%n",
            rs.getInt("movie_id"),
            rs.getString("movie_name"),
            rs.getString("language"),
            rs.getString("genre"),
            rs.getInt("duration"),
            rs.getDouble("rating"),
            rs.getString("certificate"),
            rs.getString("status"));
            
}
if(found==0)
{
    System.out.println("No movies available");
    return;
}
movieSelection();

}catch(SQLException e)
{
    e.printStackTrace();
}
}
public void movieSelection()
{
    
    String movieName=null;
    int duration=0;
    String language=null;
    try 
    {
        System.out.println("Enter the movie id:");
        movieID=sc.nextInt();
          cusidGeneration();
        
    }catch(Exception e)
    {
        System.out.println("pls enters the digit only");
        sc.nextLine();
            return;
    }

 
    try
    {
        String query="select * from movies where movie_id=? and status='Running'";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, movieID);
       
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
         movieName=rs.getString("movie_name");
          duration=rs.getInt("duration");
         
          language=rs.getString("language");
        

            System.out.println("Selected movie: "+movieName+"\nLanguage: "+language+"\nDuration: "+duration);

           TheatreSelection t=new TheatreSelection(conn, sc,this,s);
           String movieinserion="insert into bookingdetails(cus_id,movie_id)values(?,?)";

           PreparedStatement ps2=conn.prepareStatement(movieinserion);
         
           ps2.setInt(1, generated_cus_id);
             ps2.setInt(2, movieID);
           int rows=ps2.executeUpdate();
           if(rows>0)
           {
            System.out.println("movie id inserted in table:"+movieID);
           }else 
           {
            System.out.println("unable to insert the movieid");
           }
    
      t.viewTheatre();
        }else 
        {
            System.out.println("No such movie available");
            return;
        }
    }catch(SQLException e)
    {
        e.printStackTrace();
    }
}
 public void cusidGeneration()
     {
        Random r=new Random();
    generated_cus_id=    r.nextInt(1000, 10000);
    BillCalculation billObj=new BillCalculation(s, conn, sc, this);
     }
    public void menu()
    {
        int ch=0;
        while (true) {
            
        
        System.out.println("1.View Movies\n2.Exit");
        System.out.println("Enter your choice:");
        try 
        {
            ch=sc.nextInt();

        }catch(Exception e)
        {
            System.out.println("invalid entry pls enter the digits only");
        }
    
        if(ch==1)
        {
            viewMovies();
        }else if(ch==2) 
        {
            return;

        }else
        {
            System.out.println("invalid choice pls select the valid option only");
            menu();
        }
    }
    }

}
