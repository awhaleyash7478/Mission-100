package services;
import java.security.DrbgParameters.Reseed;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class MovieSelection {
Scanner sc;
Connection conn;
   public  MovieSelection(Scanner sc,Connection conn)
{
    this.sc=sc;
    this.conn=conn;
}
public int movieID;

 
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
    System.out.printf("---------------------------------------------------------------------------------------------------------------------------\n");

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
        sc.nextLine();
    }catch(Exception e)
    {
        System.out.println("pls enters the digit only");
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

            TheatreSelection t=new TheatreSelection(conn, sc,this);
    
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
