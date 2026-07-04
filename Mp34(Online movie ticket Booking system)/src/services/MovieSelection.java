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
