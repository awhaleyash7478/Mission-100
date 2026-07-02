package model;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Customers {
private Connection conn;
private Scanner sc;
  int bookCusID=0;
    public Customers(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
    String status;
    String rideStatus;
    

public void registerCustomers()
{
    int cusId=0;
    long mobNo=0;
    String cusName=null;
    try 
    {
        System.out.println("Enter the customer id:");
        cusId=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the customer name:");
        cusName=sc.nextLine();
        
        System.out.println("Enter the customer Mobile Number:");
        mobNo=sc.nextLong();
        
    }catch(Exception e)
    {
        System.out.println("Invalid entry");
        sc.nextLine();
        return;
    }
    try 
    {
String query="insert into ridingCustomers(cusID,cusName,cusMob)values(?,?,?)";
PreparedStatement ps=conn.prepareStatement(query);

ps.setInt(1, cusId);

int rows=0;
ps.setString(2, cusName);
ps.setLong(3, mobNo);
try 
{
 rows=ps.executeUpdate();

}catch(Exception e)
{
    System.out.println("customer with this id or mob number already exists");
    return;
}if(rows>0)
{
    System.out.println("You are registered successfully...");
}else 
{
    System.out.println("Unable to register,Pls try again!");
}

    }catch(SQLException e)
    {
        e.printStackTrace();
    }

}
public void bookRide()
{
    int flag=0;
  
    int pickup=0;
    String startLocation=null;
    String endLocation=null;
    int destination=0;
    double fare=0.0;
    int driverId=0;
    double distance=0.0;
    int xCoordinates1=0;
    int yCoordindates1=0;
int xCoordinates2=0;
    int yCoordindates2=0;
    int found=0;
    try 
    {
        try 
        {
    System.out.println("Enter the Customer id:");
      bookCusID=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("Pls enter the valid id");
            sc.nextLine();
        }

      String check="select * from ridingcustomers where cusID=?";
      PreparedStatement pc=conn.prepareStatement(check);
      pc.setInt(1, bookCusID);
      ResultSet rc=pc.executeQuery();
      if(rc.next())
      {

      flag=1;
      }else if(flag==0) 
      {
        System.out.println("Pls register Yourself first...!");
        
        return;
      }

    String query="select location_id,location_name from locations";
    PreparedStatement ps=conn.prepareStatement(query);
    ResultSet rs=ps.executeQuery();
      System.out.println("-----Pick-up Locations-----");
    while (rs.next()) {
   
      System.out.printf("%-5d %-20s%n",
            rs.getInt("location_id"),
            rs.getString("location_name"));
    
         found=1;
    }
    
    if(found==0)
    {

    System.out.println("No locations available sorry for inconvinience...");
    }
        System.out.println("Select the Pickup location:");
    try 
    {
        pickup=sc.nextInt();
    }catch(Exception e)
    {
        System.out.println("Pls enter the location id only");
    }
    ResultSet rr=ps.executeQuery();
 
    System.out.println("-----Destination Locations-----");
    while (rr.next()) {
   
      System.out.printf("%-5d %-20s%n",
            rr.getInt("location_id"),
            rr.getString("location_name"));
    
         found=1;
    }
       try 
    {
  
    if(found==0)
    {

    System.out.println("No locations available sorry for inconvinience...");
    }
      System.out.println("select the destination:");
    destination=sc.nextInt();
    if(pickup==destination)
    {
        System.out.println("Pickup and destination cant be same...!");
        return;
    }
    }catch(Exception e)
    {
        System.out.println("PLease enter the location id only");
    }
    String extract1 ="select * from locations where location_id=?";
    PreparedStatement q=conn.prepareStatement(extract1);
    q.setInt(1, pickup);
    ResultSet q1=q.executeQuery();
    if(q1.next())
    {
        startLocation=q1.getString("location_name");
    xCoordinates1=q1.getInt("x_coordinate");
        yCoordindates1=q1.getInt("y_coordinate");
    }else 
    {
        System.out.println("No location found");
    }
       String extract2 ="select * from locations where location_id=?";
    PreparedStatement q2=conn.prepareStatement(extract2);
    q2.setInt(1, destination);
    ResultSet r1=q2.executeQuery();
    if(r1.next())
    {
        endLocation=r1.getString("location_name");
        xCoordinates2=r1.getInt("x_coordinate");
        yCoordindates2=r1.getInt("y_coordinate");


    }else 
    {
        System.out.println("No location found");
    }
    distance = Math.sqrt(
    Math.pow(xCoordinates2 - xCoordinates1, 2) +
    Math.pow(yCoordindates2 - yCoordindates1, 2)
);
    fare=distance*15;

    
    rideStatus="Booked";
    

   DriverAssignmentThread d=new DriverAssignmentThread(conn,this);
     d.start();
    d.join();
      rideStatus="completed";

    String s="insert into rides(customer_id,driver_id,pickup,destination,fare,status)values(?,?,?,?,?,?)";
    PreparedStatement p=conn.prepareStatement(s);
    p.setInt(1, bookCusID);
    p.setInt(2, d.assignDriId);
    p.setString(3,startLocation );
    p.setString(4, endLocation);
    p.setDouble(5, fare);
    p.setString(6,rideStatus);
        int rows=p.executeUpdate();
        int flag2=0;
        if(rows>0)
        {
         flag2=1;
          
           
         
        
        
        }else
        {
            System.out.println("Something went wrong ,Pls try again!");
        }
    

    

}catch(Exception e)
{
    e.printStackTrace();
}
}
void viewRideHistory()
{
    int tempCusID=0;
    try 

    {
        System.out.println("Enter your Customer id:");
        try 
        {
        tempCusID=sc.nextInt();

        }catch(Exception e)
        {
            System.out.println("Pls enter the valid id");
        }
       String query="select ride_id ,customer_id,driver_id,pickup,destination,fare from rides where customer_id=? ";
       //String query="select * from rides"; 
       PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, tempCusID);
        ResultSet rs=ps.executeQuery();
             System.out.printf("%-8s %-12s %-10s %-15s %-15s %-10s %-15s%n",
        "RideID", "CustomerID", "DriverID", "Pickup", "Destination", "Fare", "Status");
        while (rs.next()) {
           
       
        System.out.printf("%-8d %-12d %-10d %-15s %-15s %-10.2f %n",
        rs.getInt("ride_id"),
        rs.getInt("customer_id"),
        rs.getInt("driver_id"),
        rs.getString("pickup"),
        rs.getString("destination"),
        rs.getDouble("fare"));
       


            
        }

    }catch(SQLException e)
    {
        e.printStackTrace();
    }
}

    public void menu() {
        int ch = 0;
        while (true) {
            System.out.println("WELCOME TO A.EMPIRES ");
            System.out.println("1.Register Customer\n" +

                    "2.Book Ride\n" +
                    "3.View Ride History\n" +
                    "4.Exit");
            try {
                ch = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid entry please enter the digitds only");
            }
            switch (ch) {
                
                case 1:
                    registerCustomers();
                    break;
                case 2:
                    bookRide();
                    break;
                case 3:
                    viewRideHistory();
                    break;
                case 4:
                    return;

                default:
                    System.out.println("Invalid choice allowed is (1-4)");
                    break;
            }

        }
    }

}
