package services;
import java.util.*;
import java.sql.*;
public class DashBoard {
    Connection conn;
    Scanner sc;
    public DashBoard(Connection conn,Scanner sc)
    {
         this.conn=conn;
         this.sc=sc;
    }
    public void addIncome()
    {   String source;
        Double amount=0.0,salary=0.0;
        try 
        {
            while (true) {
                
            
            System.out.println("Enter the Amount:");
             amount=sc.nextDouble();
             if(amount<=0)
             {
                System.out.println("Invalid amount");
                continue;
             }
             break;
            }
            }catch(Exception e)
            {
                System.out.println("Invalid amount");
                 sc.nextLine();
            }
            
             sc.nextLine();
             System.out.println("Enter the Source:");
             source=sc.nextLine();
             while(true) {
                
             
            System.out.println("Enter the Salary:");
            try 
            {
            salary=sc.nextDouble();
            }catch(Exception e)
            {
                System.out.println("Invalid salary");
                sc.nextLine();
                continue;
               
        
            }
            if(salary<=0)
            {
                System.out.println("Invalid amount");
             continue;
            }
            break;
        }
        
            

    }
    public void viewDashboard()
    {
        int ch=0;
        try 
        {
            System.out.println("1.Add Income\n2.Add Expense\n3.Set Monthly Salary\n4.View Balance\n5.View Income History\n6.View Expense History\n7.View Reports\n8.Exit");
      
            System.out.println("Enter your choice[1-8]:");
            
            ch=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("Pls enter the digits only[eg:1 for Add Income]");
            System.out.println(e);
            //viewDashboard();
        }
        switch (ch) {
            case 1:
                addIncome();
                
                break;
        
            default:
                System.out.println("Pls enter the valid choice between 1-8");
                break;
        }
    }
    
}
