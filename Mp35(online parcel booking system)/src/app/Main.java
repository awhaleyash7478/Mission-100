package app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


import  services.*;
import threads.DeliveryThread;
import threads.DriverAssignmentThread;
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
             ParcelDetails parObj2=new ParcelDetails(sc,conn);
             

            
            
                  ParcelBooking parObj=new ParcelBooking(conn,sc,parObj2);
              CustomerVerification cusObj=new CustomerVerification(sc, conn,parObj);
              PaymentManagement prObj=new PaymentManagement(parObj2,parObj,conn,sc,cusObj);
                 ParcelHistory hisObj=new ParcelHistory(conn);
       DeliveryThread d=new DeliveryThread(cusObj,conn);
    
             
            
           
              cusObj.menu();


             
    }catch(SQLException e)
    {
        e.printStackTrace();
    }
}

}
