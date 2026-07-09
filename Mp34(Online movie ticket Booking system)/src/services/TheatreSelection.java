package services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;
public class TheatreSelection {
    Connection conn;
    Scanner sc;
    MovieSelection m;
    ShowSelection obj;
    public TheatreSelection(Connection conn,Scanner sc,MovieSelection m,ShowSelection obj)
    {
        this.conn=conn;
        this.sc=sc;
        this.m=m;
        this.obj=obj;
       
    }
     int theatreID=0;
    
     
  
    public void viewTheatre()
    {

      try {
        
        
     String query="select theatres.theatre_id,theatres.theatre_name,theatres.location from shows join theatres on shows.theatre_id=theatres.theatre_id where movie_id=?";

      PreparedStatement ps=conn.prepareStatement(query);
     ps.setInt(1, m.movieID);
      ResultSet rs=ps.executeQuery();
      int found=0;
      
      System.out.printf("-------------------------------------------------------%n");
      System.out.println("Theatres currently Screening the movie");
      System.out.printf("-------------------------------------------------------%n");
      
System.out.printf("%-10s %-30s %-20s%n",
        "ID", "Theatre Name", "Location");
while (rs.next()) {
  found=1;
 System.out.printf("%-10d %-30s %-20s%n",
        rs.getInt("theatre_id"),
        rs.getString("theatre_name"),
        rs.getString("location"));
}
      System.out.printf("-------------------------------------------------------%n");
      theatreSelection();
      if(found==0)
      {
        System.out.println("No theatres Screening the mentioned movie");
      }

    }catch(SQLException e)
    {
      e.printStackTrace();
    }
  }
    public void theatreSelection()
 {
  
  try 
  {
   
    System.out.println("Enter the theatre id:");
 theatreID=sc.nextInt();
  }catch(Exception e)
  {
    System.out.println("Pls enter the diigits only");
    return;
  }
  try {
  String update="update bookingdetails set theatre_id=? where cus_id=?";
  PreparedStatement ps=conn.prepareStatement(update);
  ps.setInt(1, theatreID);
  ps.setInt(2, m.generated_cus_id);
  System.out.println("generated id:"+m.generated_cus_id);
  int rows=ps.executeUpdate();
  if(rows>0)
  {
    System.out.println("done wow");
  }else 
  {
    System.out.println("not done");
  }
  } catch (Exception e) {
    e.printStackTrace();
  }
  try 
  {
    String query="select * from theatres where theatre_id=? ";
        PreparedStatement ps=conn.prepareStatement(query);
         ps.setInt(1, theatreID);
       String theatreName=null;
       String location=null;
         ResultSet rs=ps.executeQuery();
         if(rs.next())
       {
       
       
        theatreName=rs.getString("theatre_name");
        location=rs.getString("location");

       
         System.out.println("Theatre selected: "+theatreName+"\nLocation: "+location);
         ShowSelection s=new ShowSelection(conn, m, this, sc);
         s.menu();
       }else 
       {
        System.out.println("No such Theatre found");
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
        
       
        System.out.println("1.View Theatre\n2.Exit");
  try 
  {
    ch=sc.nextInt();
  }catch(Exception e)
  {
    System.out.println("Invalid entry pls enters the digit only");
    
    

  }
  if(ch==1)
  {
    viewTheatre();
  }else if(ch==2)
  {
    return;
  }else 
  {
    System.out.println("Choose the valid option");

  }
    }
}
}
