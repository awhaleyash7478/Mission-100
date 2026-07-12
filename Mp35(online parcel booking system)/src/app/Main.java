package app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


import  services.*;
public class Main {
   
    public static void main(String[]args)
    {
        
         Scanner sc=new Scanner(System.in);
            
            try 
            {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localHost:3306/demodatabase",
                    "root",
                    "Yash@7478");
                    
            System.out.println("connection established successfully");
            
                  ParcelBooking parObj=new ParcelBooking(conn,sc);
              CustomerVerification cusObj=new CustomerVerification(sc, conn,parObj);
            
           
              cusObj.menu();


             
    }catch(SQLException e)
    {
        e.printStackTrace();
    }
}

}
