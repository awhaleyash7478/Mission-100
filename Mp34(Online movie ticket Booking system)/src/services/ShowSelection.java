package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.naming.spi.DirStateFactory.Result;

public class ShowSelection {
   
    Scanner sc;
    Connection conn;
    MovieSelection obj;
    TheatreSelection obj2;
public ShowSelection(Connection conn,MovieSelection obj,TheatreSelection obj2,Scanner sc)
{
    this.sc=sc;
    this.conn=conn;
    this.obj=obj;
    this.obj2=obj2;
}
TheatreSelection t = new TheatreSelection(conn, sc, obj, this);
     public void ShowSelection()
     {
        try 
        {
            String query="select theatres.theatre_name, movies.movie_name,shows.show_time,shows.screen_number, shows.ticket_price FROM shows join movies ON shows.movie_id =movies.movie_id JOIN theatres ON theatres.theatre_id = shows.theatre_id where movies.movie_id=? and theatres.theatre_id=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, obj.movieID);
            ps.setInt(2, obj2.theatreID);
            ResultSet rs=ps.executeQuery();
            System.out.printf("%-15s %-20s %-10s %-15s %-15s%n",
        "Theatre Name",
        "Movie Name",
        "Show Time",
        "Screen No",
        "Ticket Price");

System.out.println("----------------------------------------------------------------------------------");
int found=0;
            while (rs.next()) {
                found=1;
                System.out.printf("%-15s %-20s %-10s %-15d %-10.2f%n",
        rs.getString("theatre_name"),
        rs.getString("movie_name"),
        rs.getString("show_time"),
        rs.getInt("screen_number"),
        rs.getDouble("ticket_price"));

                  
                
            }
            if(found==0)
            {
                System.out.println("No such shows found");
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }


     }
     public void menu()
     {
        int ch=0;
        System.out.println("1.Select the show\n2.Exit");
        try 
        {
            ch=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("Invalid entry pls enter the digits only");
            return;
        }
        if(ch==1)
        {
            ShowSelection();
        }else if(ch==2)
        {
            return;
        }else 
        {
            System.out.println("Invalid Choice pls select the valid choice");
            return;
        }
     }
}
