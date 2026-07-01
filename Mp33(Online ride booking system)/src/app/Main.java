package app;
import java.sql.Connection;
import java.sql.DriverManager;

import model.*;
public class Main
{
    public static void main(String[] args) {
        
           try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localHost:3306/demodatabase",
                    "root",
                    "Yash@7478");
            System.out.println("connection established successfully");
      Customers c=new Customers(conn);
      c.menu();
        } catch (Exception s) {
            s.printStackTrace();
        }
           }
}

