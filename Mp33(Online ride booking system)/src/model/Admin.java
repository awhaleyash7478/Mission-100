package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin {
    private Scanner sc;
    private Connection conn;
    public Admin(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
    public void registerDriver()
    {
        int driID=0;
        String driName=null;
        String vehicle=null;
        String status="Free";
        try 
        {
            System.out.println("Enter the driver id:");
            driID=sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the driver name:");
            driName=sc.nextLine();
            System.out.println("Enter the Vehicle type:");
            vehicle=sc.nextLine();
            
        }catch(Exception e)
        {
            System.out.println("Invalid entry");
        }
        try 
        {
            String query="insert into drivers (driver_id,driver_name,vehicle,status)values(?,?,?,?)";
PreparedStatement ps=conn.prepareStatement(query);
ps.setInt(1, driID);
ps.setString(2, driName);
ps.setString(3, vehicle);
ps.setString(4, status);
int rows=ps.executeUpdate();
if(rows>0)
{
    System.out.println("Driver registered successfully");
}else 
{
    System.out.println("Something went wrong try again");
}

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
public void viewDrivers()
{
        try 
    {
String query="select * from drivers";
PreparedStatement ps=conn.prepareStatement(query);
ResultSet rs=ps.executeQuery();
int found=0;
System.out.printf("%-12s %-15s %-12s %-10s%n",
        "driver_id", "driver_name", "vehicle", "status");

while(rs.next())
    {
        System.out.printf("%-12d %-15s %-12s %-10s%n",
        rs.getInt("driver_id"),
        rs.getString("driver_name"),
        rs.getString("vehicle"),
        rs.getString("status"));
     found=1;
   
    }       
    if(found==0)
        System.out.println("No drivers found"); 


    }catch(SQLException e)
    {
        e.printStackTrace();
    }
}
public void viewAllRides()
{

   try 

    {
        
       
        String query="select * from rides ";
        PreparedStatement ps=conn.prepareStatement(query);
        
        ResultSet rs=ps.executeQuery();
             System.out.printf("%-8s %-12s %-10s %-15s %-15s %-10s %-15s%n",
        "RideID", "CustomerID", "DriverID", "Pickup", "Destination", "Fare", "Status");
        while (rs.next()) {
       
        System.out.printf("%-8d %-12d %-10d %-15s %-15s %-10.2f %-15s%n",
        rs.getInt("ride_id"),
        rs.getInt("customer_id"),
        rs.getInt("driver_id"),
        rs.getString("pickup"),
        rs.getString("destination"),
        rs.getDouble("fare"),
        rs.getString("status"));


            
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
            System.out.println("Welcome to A.empires Admin Panel");
            System.out.println("1.Register Driver\n" + 
                                "2.View Drivers\n" + 
                                "3.View All Rides\n4.Exit");
            System.out.println("Enter your choice:");
            try{
                ch=sc.nextInt();
            }catch(Exception e)
        {
            System.out.println("Pls enter the valid choice");
        }
 switch (ch) {
    case 1:
        registerDriver();

        
        break;
     case 2:
        viewDrivers();
        break;
    case 3:
        viewAllRides();
        break;
    case 4:
        return;
    
    default:
        System.out.println("invalid choice");
        break;
 }           
            
            
        }
    }
}
