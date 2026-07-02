package app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import model.*;
public class Main
{
    Admin aa;
    Scanner sc;
    Main m;
    Connection conn;
     static int choice=0;
     Main(Scanner sc,Admin aa,Connection conn)
     {
        this.aa=aa;
        this.sc=sc;
        
        this.conn=conn;
    }

     void Mainmenu()
            {
            System.out.println("1.Admin\n2.Customer\n3.Exit");
            
            
            try 
            {
            choice=sc.nextInt();
        
            }catch(Exception e)
            {
                System.out.println("Invalid entry");
            }
             if(choice==1)
            {
                 
      aa.menu();
      Mainmenu();
            }else if(choice==2)
            {
      Customers c=new Customers(conn,sc);
      c.menu();
      Mainmenu();
            }else 
            {
                return;
            }
           
            
        }
    public static void main(String[] args) {
        
           try {
           
            
    Scanner sc=new Scanner(System.in);
            
            
            Connection conn = DriverManager.getConnection("jdbc:mysql://localHost:3306/demodatabase",
                    "root",
                    "Yash@7478");
                    
            System.out.println("connection established successfully");
              Admin a=new Admin(conn,sc);
            Main m=new Main(sc,a,conn);
            
           m.Mainmenu();
           
           
            if(choice==1)
            {
                 
      a.menu();
      m.Mainmenu();
            }else if(choice==2)
            {
      Customers c=new Customers(conn,sc);
      c.menu();
      m.Mainmenu();
            }else 
            {
                return;
            }
    
        
        } catch (Exception s) {
            s.printStackTrace();
        }
           }
}

