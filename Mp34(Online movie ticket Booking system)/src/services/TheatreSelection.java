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
    public TheatreSelection(Connection conn,Scanner sc,MovieSelection m)
    {
        this.conn=conn;
        this.sc=sc;
        this.m=m;
    }
    
    public void viewTheatre()
    {int showTheatreid=0;

      try {
        
        String query="select * from  shows where movie_id =?";
        PreparedStatement ps1=conn.prepareStatement(query);
        
        ps1.setInt(1, m.movieID);
        System.out.println("movieid:"+m.movieID);
        
        ResultSet rs1=ps1.executeQuery();
        if(rs1.next())
        {
     showTheatreid=rs1.getInt("theatre_id");
        }

      
      
         
        String sql="select * from theatres where theatre_id=? ";
        PreparedStatement ps=conn.prepareStatement(sql);
           
         System.out.println("showTheatreid = " + showTheatreid);
          ps.setInt(1, showTheatreid);
        ResultSet rs = ps.executeQuery();
        ArrayList<String[]> theatres = new ArrayList<>();

        int found=0;
while (rs.next()) {
    theatres.add(new String[] {
      
        String.valueOf(rs.getInt("theatre_id")),
        rs.getString("theatre_name"),
        rs.getString("location")
        
    });
    found=1;


}
if(found==0)
{
  System.out.println("No theatres found");
}
for (String[] theatre : theatres) {
    System.out.printf("%-10s %-25s %-20s%n",
            theatre[0],   // theatre_id
            theatre[1],   // theatre_name
            theatre[2]);  // location
}

       /*  System.out.println();
        System.out.println();
        
        System.out.printf("%-12s %-25s %-20s%n",
        "Theatre ID",
        "Theatre Name",
        "Location");
            System.out.printf("------------------------------------------------------\n");

        int found=0;
        while (rs2.next()) {
          
          
          
          
          found=1;
          System.out.printf("%-12d %-25s %-20s%n",
        rs.getInt("theatre_id"),
        rs.getString("theatre_name"),
        rs.getString("location"));

          
        }
        if(found==0)
        {
          System.out.println("Theatres not found");
          return;
        }
        theatreSelection();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      */
    }catch(SQLException e)
    {
      e.printStackTrace();
    }
  }
    public void theatreSelection()
 {
   int theatreID=0;
  
  try 
  {
   
    System.out.println("Enter the theatre id:");
 theatreID=sc.nextInt();
  }catch(Exception e)
  {
    System.out.println("Pls enter the diigits only");
    return;
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
