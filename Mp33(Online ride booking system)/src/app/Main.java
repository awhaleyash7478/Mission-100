package app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import model.*;
public class Main
{
    Scanner sc;
     static int choice=0;
     Main(Scanner sc)
     {
        this.sc=sc;
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
        }
    public static void main(String[] args) {
        
           try {
            
    Scanner sc=new Scanner(System.in);
            
            
            Connection conn = DriverManager.getConnection("jdbc:mysql://localHost:3306/demodatabase",
                    "root",
                    "Yash@7478");
            System.out.println("connection established successfully");
            Main m=new Main(sc);
           m.Mainmenu();
           
            if(choice==1)
            {
                  Admin a=new Admin(conn,sc);
      a.menu();
      m.Mainmenu();
            }else if(choice==2)
            {
      Customers c=new Customers(conn,sc);
      c.menu();
            }else 
            {
                return;
            }
    

        } catch (Exception s) {
            s.printStackTrace();
        }
           }
}

