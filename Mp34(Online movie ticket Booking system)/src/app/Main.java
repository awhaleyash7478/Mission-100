package app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


import  services.*;
public class Main {
   static ShowSelection obj;
    public Main(ShowSelection obj)
    {
        this.obj=obj;
    }
    public static void main(String[]args)
    {
        
         Scanner sc=new Scanner(System.in);
            
            try 
            {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localHost:3306/demodatabase",
                    "root",
                    "Yash@7478");
                    
            System.out.println("connection established successfully");
              MovieSelection m=new MovieSelection(sc,conn,obj);
            CustomerVerification l=new CustomerVerification(sc,conn,m);
          

            l.menu();
            
                
              
    }catch(SQLException e)
    {
        e.printStackTrace();
    }
}

}
