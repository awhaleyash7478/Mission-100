package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import javax.naming.spi.DirStateFactory.Result;

public class ShowSelection {
   int seatno;
   ArrayList<Integer>seatValidate=new ArrayList<>();
   ArrayList<Integer> storedSeats=new ArrayList<>();
   
   int removed=0;
  int remainingSeats;
//   int generated_cus_id;
  String status;
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
             String showInsertion="update bookingdetails set show_id=? where movie_id=?";
           PreparedStatement ps2=conn.prepareStatement(showInsertion);
           ps2.setInt(1, showID);
           ps2.setInt(2, obj.movieID);
           int rows=ps2.executeUpdate();
           if(rows>0)
           {
            System.out.println("inserted show id:"+showID);
           }else {
            System.out.println("unable to insert");
           }
            
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
       

        System.out.println();
        
     
      
      System.out.println("\t=======================================================================\n");
      System.out.println("\t\t\t\t--------");
   
      System.out.println("\t\t\t\t|SCREEN|");
      

      System.out.println("\t\t\t\t--------");
      System.out.println("\t=======================================================================\n");
      
      int book=3;
     
        
 char alphabet='A';


               try{
            String query="select * from bookSeats";
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
              
                while (rs.next()) {
                    storedSeats.add(rs.getInt("seat_no"));

                    
                    
                }
                
                 Collections.sort(storedSeats);
                seatValidate.addAll(storedSeats);
 

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
            System.out.println("\t[X] Booked\t\t\t\t\tVIP(1-20)->400 rs");
            System.out.println("\t\t\t\t\t\t\tPREMIUM(20-30)->300 rs");
            System.out.println("\t[ ] Avaiilable\t\t\t\t\tSTANDARD(30-50)->200 rs");  
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
    
          int storeRemainingSeats=0;
         try 
         {
            String query="select remainingSeats from remainingSeatsValue";
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
            remainingSeats=rs.getInt("remainingSeats");
            storeRemainingSeats=remainingSeats;
           
            remainingSeats-=seats;
            }else 
            {
                System.out.println("no seats");
            }
         }catch(Exception e)
         {
            e.printStackTrace();
         }
        if(seats<=0)
            {
                System.out.println("seats can't be negative or zero");
            return;
            }else if(seats>remainingSeats)
            {
                System.out.println("Remainig seats: "+storeRemainingSeats);
                return;

            }
           
try 
{
    String query="update  remainingSeatsValue SET remainingSeats=?";
    PreparedStatement ps=conn.prepareStatement(query);
    ps.setInt(1, remainingSeats);
   int rows= ps.executeUpdate();
    if(rows<=0)
    {
        System.out.println("remaing seats value inserted in the table");
    }

}catch(SQLException e)
{
    e.printStackTrace();
}

// cusidGeneration();

            
            for(int i=0;i<seats;i++)
            {
                System.out.println("Enter the seat number:");
                try 
                {
                seatno=sc.nextInt();
              
            }catch(Exception e)
            {
                System.out.println("Please enter the valid seat number (eg:1,2)");
                sc.nextLine();
                return;
            }
          
            
            

          
              if(seatno<=0)
                {
                    System.out.println("Seat number can't be negative or zero");
                return;
                }else if(seatValidate.contains(seatno))
                {
                    
                    System.out.println("This seat is already booked,pls book another seat");
                    return;
                }
                
                try 
                {
                    // MovieSelection mobj=new MovieSelection(sc, conn,this);
                String query="insert into bookSeats (cus_id,seat_no) values(?,?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, obj.generated_cus_id);
            ps.setInt(2, seatno);
            
            int rows=ps.executeUpdate();
            
            if(rows>0)
                {
                    System.out.println("no tension its ok because id:"+obj.generated_cus_id);
                    status="Locked";
                }else 
                    {    
                    System.out.println("unable to select the seat");
                    }
                   
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
           
            
            
        }
         BillCalculation billObj=new BillCalculation(this, conn,sc,obj);
                    billObj.calculateBill();
        
     }
    //  public void cusidGeneration()
    //  {
    //     Random r=new Random();
    // generated_cus_id=    r.nextInt(1000, 10000);
    
    //  }
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
         sc.nextLine();
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
