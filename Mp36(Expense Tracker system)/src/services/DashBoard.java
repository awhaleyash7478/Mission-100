package services;
import java.util.*;
 import java.time.Year;


import javax.naming.spi.DirStateFactory.Result;

import java.sql.*;
import java.time.Year;
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
          ps.executeUpdate();
         
           double total=0;
         
            int rows=0;
           
            String fetch="select total from total_income where user_name=?";
            PreparedStatement ppp=conn.prepareStatement(fetch);
            ppp.setString(1, userName);
            ResultSet resultSet=ppp.executeQuery();
            if(resultSet.next())
            {
            total=resultSet.getDouble("total");
            System.out.println("total: "+total);
              total=total+amount;
              System.out.println("total: "+total);

               String query2="update total_income set total=? where user_name=?";
            PreparedStatement pp=conn.prepareStatement(query2);
            pp.setDouble(1, total);
            pp.setString(2, userName);
           
            rows=pp.executeUpdate();
            if(rows>0)
            {
                System.out.println("Income details updated successfully");
               viewDashboard(userName);
            }else 
            {
                System.out.println("Unable to update income details");

            }
            }else 
            {
                
               String query2="insert into total_income(user_name,total)values(?,?)";

            PreparedStatement pp=conn.prepareStatement(query2);
         
            pp.setString(1, userName);
            System.out.println("total: "+amount);
               pp.setDouble(2, amount);
           
            int rowsss=pp.executeUpdate();
            if(rowsss>0)
            {
                System.out.println("Income details added Successfully");
               viewDashboard(userName);
            }else 
            {
                System.out.println("Unable to add income details");
            }
               
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
                                System.out.println("Invalid entry pls choose the valid option");
                                addExpense();
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
                                String query="Select budget from monthlyBudget where user_name=?";
                                PreparedStatement ps=conn.prepareStatement(query);
                              
                                ps.setString(1, userName);
                                ResultSet rs=ps.executeQuery();
                                if(rs.next())
                                {
                                      double total=0.0;
                                  
                                    double validateAmount=rs.getDouble("budget");
                                    System.out.println(validateAmount);
                                     if(tempAmount>validateAmount)
                                    {
                                        System.out.println("Monthly Budget Exceeded");
                                        System.out.println("Wallet Balance: "+validateAmount);
                                        System.out.println("1.Try Again\n2.Update Monthly budget");
                                        sc.nextLine();
                                        int choice=sc.nextInt();
                                       if(choice==1)                      
                                       {
                                        continue;
                                       }else if(choice==2)
                                       {
                                        updateMonthlyBudget();
                                       }
                                    }

                                 total =validateAmount-tempAmount;
                                 System.out.println("Wallet Balance: "+total);
                                 String update="update monthlyBudget set budget=? where user_name=?";
                                 PreparedStatement preparedStatement=conn.prepareStatement(update);
                                 preparedStatement.setDouble(1, total);
                                 preparedStatement.setString(2, userName);
                                 preparedStatement.executeUpdate();
                                 
                                       String description;
                     System.out.println("Enter the description: ");
                     description=sc.nextLine();
                     String expenses="insert into expenses(category,amount,description,user_name)values(?,?,?,?)";
                     PreparedStatement expense=conn.prepareStatement(expenses);
                     expense.setString(1, selectedCat);
                     expense.setDouble(2, tempAmount);
                     expense.setString(3, description);
                     expense.setString(4, userName);
                     int r=expense.executeUpdate();

                    
                     
                     
                                 if(r>0)
                                 {
                                    System.out.println("Expenses Updated successfully");
                                    viewDashboard(userName);
                                    break;
                                 }else
                                 {
                                    System.out.println("Unable to update the expenses");
                                 }
                                    
                                   
                                   
                                    
                                   
                                }else
                                {
                                   
                                   
                                   
                                    
                                        System.out.println("Pls add the Monthly Budget First");
                                        System.out.println("1.Add Monthly Budget\n2.Exit");
                                        int ch=sc.nextInt();
                                        sc.nextLine();
                                        if(ch==1)
                                        {
                                            setMonthlyBudget();
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
    public void updateMonthlyBudget()
    {
        double fetchedBudget=0.0;
        double updateAmount=0.0;
        double finalBudget=0.0;
        try 
        {
            String fetch="select budget from monthlyBudget where user_name=?";
            PreparedStatement pp=conn.prepareStatement(fetch);
            pp.setString(1, userName);
            ResultSet rr=pp.executeQuery();
            if(rr.next())
            fetchedBudget=rr.getDouble("budget");

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        while (true) {
            
        
        try {
            System.out.println("Enter the Amount:");
            updateAmount=sc.nextDouble();
        } catch (Exception e) {
            System.out.println("Pls enter the valid amount only");
         continue;
         }
          if(updateAmount<=0)
          {
            System.out.println("Enter the valid amount");
           continue;
          }
          finalBudget=fetchedBudget+updateAmount;
          break;
    }
        try 
        {
            String query="update monthlyBudget set budget=? where user_name=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setDouble(1, finalBudget);
            ps.setString(2, userName);
            int rows=ps.executeUpdate();
            if(rows>0)
            {
                System.out.println("Monthly budget updated successfuly from "+fetchedBudget+"to "+finalBudget);
                return;
            }else
            {
                System.out.println("Unable to update the monthly Budget");
                return;
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void setMonthlyBudget()
    {
             double budget=0.0;
                 String month=null;
                        int currentYear=0;
 
        while (true) {
            
        
        System.out.println("Enter the month to set the Budget:");
    
       

        try 
        {
            
            month=sc.nextLine();
             currentYear = Year.now().getValue();


        }catch(Exception e)
        {
            System.out.println("Invalid input");
        }
        String regex = "(?i)^(january|february|march|april|may|june|july|august|september|october|november|december)$";
        if(!month.matches(regex))
        {
            System.out.println("Pls enter the valid month [eg:January]");
            continue;

        }
        System.out.println("Enter the Budget for "+month+": ");
   
        try
{        budget=sc.nextDouble();

     
    }catch(Exception e)
    {
        System.out.println("Invalid budget entered");
        continue;
    }
    if(budget<=0)
    {
        System.out.println("Pls enter the valid budget");
        continue;
    }
    break;
}
try 
{
       double validateBudget=0.0;
        String validateMonth=null;
          int validateYear=0;
    String search="select month,budget,year from monthlyBudget where user_name=?";
    PreparedStatement preparedStatement1=conn.prepareStatement(search);
    preparedStatement1.setString(1, userName);
    ResultSet rs1=preparedStatement1.executeQuery();
    if(rs1.next())
    {
   validateMonth =rs1.getString("month");
     validateYear=rs1.getInt("year");

    validateBudget=rs1.getDouble("budget");
    }
    if(month.equals(validateMonth)&&currentYear==validateYear)
    {
        System.out.println("Budget for "+month+" is already set");
        System.out.println("Budget is: "+validateBudget);
        System.out.println("1.Update Monthly Budget\n2.Exit");
        int choice=sc.nextInt();
        if(choice==1)
        {
            updateMonthlyBudget();
            viewDashboard(userName);
          return;
        }else 
        {
            return;
        }
    }
    String query="insert into monthlyBudget(month,budget,year,user_name,actualBudget)values(?,?,?,?,?)";
    PreparedStatement preparedStatement=conn.prepareStatement(query);
    preparedStatement.setString(1, month);
    preparedStatement.setDouble(2, budget);
    preparedStatement.setInt(3, currentYear);
    preparedStatement.setString(4, userName);
    preparedStatement.setDouble(5, budget);
    int rows=preparedStatement.executeUpdate();
    if(rows>0)
    {
        System.out.println("Budget set Successfully");
        viewDashboard(userName);
    }else 
    {
        System.out.println("Unable to add the budget");
        setMonthlyBudget();
    }
}catch(Exception e)
{
    e.printStackTrace();
}
    }
    public void viewBalance()
    {
        double fetchedBudget=0.0;
        try
        {
            String query="select budget from monthlyBudget where user_name=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, userName);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                fetchedBudget=rs.getDouble("budget");
                System.out.println("Total Wallet Balance: "+fetchedBudget);
                viewDashboard(userName);

            }else 
            {
                System.out.println("Pls Set the monthly budget First");
                System.out.println("1.Set Budget\n2.Exit");
                int choice=sc.nextInt();
                if(choice==1)
                {
                    setMonthlyBudget();
                }else
                {
                    return;
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void viewIncomeHis()
    {
        int found=0;
        try 
        {
            String query="select * from users where user_name=?";
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet rs=preparedStatement.executeQuery();
            System.out.printf("%-12s %-12s %-12s %-15s%n",
        "user_name", "amount", "source", "salary");

System.out.println("-----------------------------------------------------");
            while (rs.next()) {
                found=1;
                 System.out.printf("%-12s %10.2f %-12s %15.2f%n",
            rs.getString("user_name"),
            rs.getDouble("amount"),
            rs.getString("source"),
            rs.getDouble("salary"));

                
            }
            
            if(found==0)
            {
                System.out.println("NO Income History");
                 viewDashboard(userName);
            }
            viewDashboard(userName);
            return;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void viewExpenseHis()
    {
        try 
        {
            String query="select * from expenses where user_name=?";
            PreparedStatement ps=conn.prepareStatement(query);
           
            ps.setString(1, userName);
            System.out.println("DEBUG userName = [" + userName + "]");
            ResultSet rs=ps.executeQuery();
            int found=0;
            System.out.printf("+------------+----------+--------+-------------+%n");
System.out.printf("| %-10s | %-8s | %6s | %-11s |%n",
        "expense_id", "category", "amount", "description");
System.out.printf("+------------+----------+--------+-------------+%n");


            while (rs.next()) {
                found=1;
 System.out.printf("| %10d | %-8s | %6.2f | %-11s |%n",
            rs.getInt("expense_id"),
            rs.getString("category"),
            rs.getDouble("amount"),
            rs.getString("description"));
                
            }

            System.out.printf("+------------+----------+--------+-------------+%n");
      
            if(found==0)
            {
                System.out.println("NO Expense History");
                viewDashboard(userName);
            }
                  viewDashboard(userName);
        
    }
    catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void viewReport()
    {
        int found=0;
        try {
            String query1="select amount from users where user_name=?";
            PreparedStatement ps1=conn.prepareStatement(query1);
            ps1.setString(1, userName);
            ResultSet rs1=ps1.executeQuery();
            double totalIncome=0.0;
            double totalExpense=0.0;
            if(rs1.next())
                {
                    totalIncome=rs1.getDouble("amount");
                    found=1;
                    
                }
                   if(found!=1)
     {
        System.out.println("Pls add  the income field to generate the report");
        return;
     }  
            String query2="select amount from expenses where user_name=?";
            PreparedStatement ps2=conn.prepareStatement(query2);
            ps2.setString(1, userName);
            ResultSet rs2=ps2.executeQuery();
            if(rs2.next())
                {
                    totalExpense=rs2.getDouble("amount");
                    found=2;
                    
                }
                double fetchedBudget=0.0;
             String query3="select budget from monthlyBudget where user_name=?";
            PreparedStatement ps3=conn.prepareStatement(query3);
            ps3.setString(1, userName);
            ResultSet rs3=ps3.executeQuery();
            if(rs3.next())
            {

                fetchedBudget=rs3.getDouble("budget");
            
                System.out.println("Total Wallet Balance: "+fetchedBudget);

            }
            double maxExpense=0.0;
            String query4="SELECT MAX(amount) AS maxExpense FROM expenses WHERE user_name=?";
            PreparedStatement ps4=conn.prepareStatement(query4);
            ps4.setString(1, userName);
            ResultSet rs4=ps4.executeQuery();
            if(rs4.next())
            {
                maxExpense=rs4.getDouble("maxExpense");
      

            }
            double highestIncome=0.0;
            String query5="select max(amount) as r from users where user_name=?";
            PreparedStatement ps5=conn.prepareStatement(query5);
            ps5.setString(1, userName);
        ResultSet rs5=ps5.executeQuery();
        if(rs5.next())
        {
            highestIncome=rs5.getDouble("r");
            
        }
        double actualBudget=0.0;
        String query6="select total from total_income where user_name=?";
        PreparedStatement ps6=conn.prepareStatement(query6);
        ps6.setString(1, userName);
        ResultSet rs6=ps6.executeQuery();
        if(rs6.next())
        {
            actualBudget=rs6.getDouble("total");
     
        }
    double budgetUsed=0.0;
    budgetUsed=actualBudget-fetchedBudget;
   System.out.println("========================================");
System.out.println("           FINANCIAL REPORT             ");
System.out.println("========================================");

System.out.println("Total Income        : ₹ " + totalIncome);
System.out.println("Total Expense       : ₹ " + totalExpense);
System.out.println("Current Balance     : ₹ " + fetchedBudget);
System.out.println("Highest Expense     : ₹ " + maxExpense);
System.out.println("Highest Income      : ₹ " + highestIncome);

System.out.println("----------------------------------------");

System.out.println("Budget              : ₹ " + actualBudget);
System.out.println("Budget Used         : ₹ " + budgetUsed);
System.out.println("Remaining Budget    : ₹ " + fetchedBudget);

System.out.println("----------------------------------------");



System.out.println("========================================");
viewDashboard(userName);
  

        
                       
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewDashboard(String userName)
    {
        
            this.userName=userName;
        int ch=0;
        try 
        {
            System.out.println("1.Add Income\n2.Add Expense\n3.Set Monthly Budget\n4.View Balance\n5.View Income History\n6.View Expense History\n7.View Reports\n8.Exit");
      
            System.out.println("Enter your choice[1-8]:");
            
            ch=sc.nextInt();
            sc.nextLine();
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
            case 3:
                setMonthlyBudget();
                break;
            case 4:
                viewBalance();
                break;
            case 5:
                viewIncomeHis();
                break;
            case 6:
                viewExpenseHis();
                break;
            case 7:
                viewReport();
                break;
            case 8:
                return;
            default:
                System.out.println("Pls enter the valid choice between 1-8");
                break;
        }
    }
    
}
