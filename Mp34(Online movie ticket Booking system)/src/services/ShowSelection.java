package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.naming.spi.DirStateFactory.Result;

public class ShowSelection {
   int seatno;
   int removed=0;
    Scanner sc;
    int tempRemoved;
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
            String query="select shows.show_id,theatres.theatre_name, movies.movie_name,shows.show_time,shows.screen_number, shows.ticket_price FROM shows join movies ON shows.movie_id =movies.movie_id JOIN theatres ON theatres.theatre_id = shows.theatre_id where movies.movie_id=? and theatres.theatre_id=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, obj.movieID);
            ps.setInt(2, obj2.theatreID);
            ResultSet rs=ps.executeQuery();
            int found=0;
           System.out.println("---------------------------------------------------------------------------------------------------------");
System.out.printf("%-8s %-20s %-20s %-15s %-12s %-12s%n",
        "Show ID", "Theatre Name", "Movie Name", "Show Time", "Screen No", "Price");
System.out.println("---------------------------------------------------------------------------------------------------------");

while (rs.next()) {
    found=1;
    System.out.printf("%-8d %-20s %-20s %-15s %-12d ₹%-10.2f%n",
            rs.getInt("show_id"),
            rs.getString("theatre_name"),
            rs.getString("movie_name"),
            rs.getString("show_time"),
            rs.getInt("screen_number"),
            rs.getDouble("ticket_price"));
}

System.out.println("---------------------------------------------------------------------------------------------------------");
            if(found==0)
            {
                System.out.println("No such shows found");
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        int showID=0;
        try 
        {
            System.out.println("Enter the show id:");
            showID=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("invalid id pls enters the digits only");
            sc.nextLine();
        }
        String showTime=null;
        try 
        {
           String query="select * from shows where show_id=? and movie_id=?";
           PreparedStatement ps=conn.prepareStatement(query);
           ps.setInt(1, showID);
           ps.setInt(2, obj.movieID);

           ResultSet rs=ps.executeQuery();
           
           if(rs.next())
           {
              showTime=rs.getString("show_time");
           }else 
           {
            System.out.println("Show does not exist");
            return;
           }
           LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        
        if(showTime.equals(currentTime))
        {
            System.out.println("--------------------------");
            System.out.println("Show has already started");
            System.out.println("--------------------------");

            return;
        }
        seatLayout();
        

        }catch(Exception e)
        {
            e.printStackTrace();
        }



     }
     public void seatLayout()
     {
        int value=1;
       // int seat=2;

        System.out.println();
        
     
      
      System.out.println("\t=======================================================================\n");
      System.out.println("\t\t\t\t--------");
   
      System.out.println("\t\t\t\t|SCREEN|");
      

      System.out.println("\t\t\t\t--------");
      System.out.println("\t=======================================================================\n");
      
      int book=3;
     
        ArrayList<Integer> storedSeats=new ArrayList<>();
 char alphabet='A';


               try{
            String query="select * from bookSeats";
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
              
                while (rs.next()) {
                    storedSeats.add(rs.getInt("seat_no"));
                    
                }
                
                 Collections.sort(storedSeats);

 

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        int tempValue=0;
        for(int i=0;i<5;i++)
      {
       
        System.out.print("\t");
        System.out.print(alphabet++);
        
        int tempSeat=0;
       
        for(int j=0;j<10;j++)
        {
               
                
                
                
                      
             if(i==0)
                      {
                      

             if(!storedSeats.isEmpty()&&storedSeats.get(0)<=10&&value==storedSeats.get(0)) 
              removed= storedSeats.remove(0);
           
            }else if(i==1)
            {
           

               if(!storedSeats.isEmpty()&&storedSeats.get(0)<=20&&value==storedSeats.get(0)) 
              removed= storedSeats.remove(0);
           

            }else if(i==2)
            {
           

               if(!storedSeats.isEmpty()&&storedSeats.get(0)<=30&&value==storedSeats.get(0)) 
              removed= storedSeats.remove(0);
            }else if(i==3)
            {
           

               if(!storedSeats.isEmpty()&&storedSeats.get(0)<=40&&value==storedSeats.get(0)) 
              removed= storedSeats.remove(0);

            }else if(i==4) 
            {



               if(!storedSeats.isEmpty()&&storedSeats.get(0)<=50&&value==storedSeats.get(0)) 
              
              {  removed= storedSeats.remove(0);
                tempRemoved=removed;
      
              }}
      
            
            

            if(removed==value)
            {
               
                System.out.print("  "+"[X]"+" ");
            }else if(book==value)
                {
                    System.out.print(" "+"[V]"+" ");
                }else 
            {
                System.out.print("  "+"["+value+"]"+" ");
            }
            
           
           value ++;
           tempValue=value;
        }
         System.out.println();
         System.out.println();
        
      }
            System.out.print("\t=======================================================================\n");
            System.out.println("\t[X] Booked");
            System.out.println("\t[V] Your selection");
            System.out.println("\t[ ] Avaiilable");  
            System.out.println("\t=======================================================================\n");

          bookSeats();  

     }
     public void bookSeats()
     {
        int seats=0;

        try 
        {
            System.out.println("Enter the number of seats you want to book:");
            
            seats=sc.nextInt();

            

        }catch(Exception e)
        {
            System.out.println("invalid entry please enter the valid digit");
        }
        if(seats<=0)
            {
                System.out.println("seats can't be negative or zero");
            return;
            }
            
            for(int i=0;i<seats;i++)
            {
                System.out.println("Enter the seat number:");
                try 
                {
                seatno=sc.nextInt();
              
            }catch(Exception e)
            {
                System.out.println("Please enter the valid seat number (eg:1,2)");
                return;
            }
              if(seatno<=0)
                {
                    System.out.println("Seat number can't be negative or zero");
                return;
                }
                try 
                {
                String query="insert into bookSeats (seat_no) values(?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, seatno);
            int rows=ps.executeUpdate();
            if(rows>0)
                {
                    System.out.println("seat selected successfully");
                }else 
                    {
                        System.out.println("unable to book");
                    }    
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
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
