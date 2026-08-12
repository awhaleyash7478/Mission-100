package services;
import java.util.*;

import javax.naming.spi.DirStateFactory.Result;

import java.sql.*;
public class DashBoard {
    Connection conn;
    Scanner sc;
    public DashBoard(Connection conn,Scanner sc)
    {
         this.conn=conn;
         this.sc=sc;
    }
    String userName=null;
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
        try 
        {
            String query="insert into users (user_name,amount,source,salary)values(?,?,?,?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, this.userName);
            ps.setDouble(2,amount);
            ps.setString(3, source);
            ps.setDouble(4, salary);
            int rows=ps.executeUpdate();
            if(rows>0)
            {
                System.out.println("Income details added successfully");
            }else 
            {
                System.out.println("Unable to add income details");

            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        
            

    }
    public void addExpense()
    {
        double tempAmount=0.0;
        int category=0;
        String selectedCat=null,other=null;
        System.out.println("Select the category\n1.Food\n" + //
                        "2.Travel\n" + //
                        "3.Shopping\n" + //
                        "4.Bills\n" + //
                        "5.Medical\n" + //
                        "6.Education\n" + //
                        "7.Entertainment\n" + //
                        "8.Fuel\n" + //
                        "9.Rent\n" + //
                        "10.Other");
                        try 
                        {
         category=sc.nextInt();
                        }catch(Exception e)
                        {
                            System.out.println("Pls enter the digits only [eg:1 for Food]");
                        }
                        switch (category) {
                            case 1:
                                selectedCat="Food";
                                break;
                            case 2:
                                selectedCat="Travel";
                                break;
                            case 3:
                                selectedCat="Shopping";
                                 break;
                             case 4:
                                selectedCat="Bills";
                                break;
                            case 5:
                                selectedCat="Medical";
                                
                                break;
                            case 6:
                                selectedCat="Education";
                                break;
                            case 7:
                                selectedCat="Entertainment";
                                break;
                            case 8:
                                selectedCat="Fuel";
                                break;
                            case 9:
                                selectedCat="Rent";
                                break;
                            case 10:
                                selectedCat="Other";
                                System.out.println("Pls enter the cateogory: ");
                                other=sc.nextLine();
                                break;
                        
                            default:
                                break;
                        }
                        while (true) {
                            
                        
                        try 
                        {
                            System.out.println("Enter the amount for "+selectedCat+": ");
                            tempAmount=sc.nextDouble();
                              if(tempAmount<=0)
                            {
                                System.out.println("Invalid amount pls enter the valid amount");
                                sc.nextLine();
                                continue;
                            }
                            sc.nextLine();
                            try 
                            {
                                String query="Select amount from users where user_name=?";
                                PreparedStatement ps=conn.prepareStatement(query);
                              
                                ps.setString(1, userName);
                                ResultSet rs=ps.executeQuery();
                                if(rs.next())
                                {
                                    double validateAmount=rs.getDouble("amount");
                                    if(tempAmount>validateAmount)
                                    {
                                        System.out.println("Monthly Budget Exceeded");
                                        System.out.println("Wallet Balance: "+validateAmount);
                                        
                                        continue;
                                    }

                                       String description;
                     System.out.println("Enter the description: ");
                     description=sc.nextLine();
                     System.out.println("Expense info added successfully");
                     break;
                                }else 
                                {
                                   
                                   
                                   
                                    
                                        System.out.println("Pls add the income First");
                                        System.out.println("1.Add Income\n2.Exit");
                                        int ch=sc.nextInt();
                                        if(ch==1)
                                        {
                                            addIncome();
                                        }else 
                                        {
                                            return;
                                        }
                                   
                                }
                                
                            }catch(SQLException e)
                            {
                                e.printStackTrace();
                            }
                           
                          
                            
                        }catch(Exception e)
                        {
                            System.out.println("Pls enter the valid amount only");
                            sc.nextLine();
                            continue;
                        }
                       

                    }
                 

        
    }
    public void viewDashboard(String userName)
    {
        
            this.userName=userName;
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
            case 2:
                addExpense();
                System.out.println("hey came here");
                break;
        
            default:
                System.out.println("Pls enter the valid choice between 1-8");
                break;
        }
    }
    
}
