package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ParcelBooking {
    Connection conn;
    Scanner sc;
    public ParcelBooking(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }

public void senderDetails()
{
    String senderName=null;
    String sender_add=null;
    System.out.println("Enter the sender's name:");
    try
    {
        senderName=sc.nextLine();
    }catch(Exception e)
    {
        System.out.println("Pls enter the valid name only");
        sc.nextLine();
        return;
    }
    System.out.println("Enter the sender's address:");
    
       
         sender_add=sc.nextLine().trim();
   String regex = "^[A-Za-z0-9\\s,./#()'-]{5,100}$";
    if( !sender_add.matches(regex))
    {
        System.out.println("Invalid address pls enter the valid address");
        return;
    }
    
    try 
    {
        String query="insert into sender (sender_name,sender_add)values(?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, senderName);
        ps.setString(2, sender_add);
        int rows=ps.executeUpdate();
        if(rows>0)
        {
            System.out.println("\t\t-------------------------------");
            System.out.println("\t\tSender details added succesfully");
             System.out.println("\t\t-------------------------------");
             receiverDetails();
        }else 
        {
             System.out.println("\t\t-------------------------------");
             System.out.println("\t\tUnable to add sender");
              System.out.println("\t\t-------------------------------");
        }
    }catch(SQLException e)
    {
        e.printStackTrace();
    }


}
public void receiverDetails()
{
    String receiverName=null;
    String receiver_add=null;
     System.out.println("Enter the Reciever's name:");
    try
    {
        receiverName=sc.nextLine();
    }catch(Exception e)
    {
        System.out.println("Pls enter the valid name only");
        sc.nextLine();
        return;
    }
    System.out.println("Enter the Receiver's address:");
    
        
     receiver_add=sc.nextLine().trim();
   String regex = "^[A-Za-z0-9\\s,./#()'-]{5,100}$";
    if( !receiver_add.matches(regex))
    {
        System.out.println("Invalid address pls enter the valid address");
        return;
    }
    try 
    {
        String query="insert into receiver (receiver_name,receiver_add)values(?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, receiverName);
        ps.setString(2, receiver_add);
        int rows=ps.executeUpdate();
        if(rows>0)
        {
            System.out.println("\t\t-------------------------------");
            System.out.println("\t\treceiver details added succesfully");
             System.out.println("\t\t-------------------------------");
        }else 
        {
             System.out.println("\t\t-------------------------------");
             System.out.println("\t\tUnable to add receiver");
              System.out.println("\t\t-------------------------------");
        }
    }catch(SQLException e)
    {
        e.printStackTrace();
    }

    
}

}
