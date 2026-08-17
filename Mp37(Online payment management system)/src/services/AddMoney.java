package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;

import java.time.LocalDate;
import java.time.LocalTime;


import threads.AttemptValidationThread;

public class AddMoney {
    Scanner sc;
    AttemptValidationThread a;
    Connection conn;
    public AddMoney(Scanner sc,Connection conn)
    {
        this.sc=sc;
        this.conn=conn;
        a  =new AttemptValidationThread();

    }
      public void addMoney()
      {
        
        if(AttemptValidationThread.sleep==1)
        {
          System.out.println("Pls Wait Your Attempt limit is Exceeded");
          return;

        }

        int countattempt=3;
        while (true) {
            
        
        double amount=0.0;
        String password=null;
        try 
        {
            System.out.println("Enter the Amount: ");
            amount=sc.nextDouble();
            

        }catch(Exception e)
        {
            System.out.println("Invalid amount");
             continue;
        }
        if(amount<=0)
        {
            System.out.println("Pls enter the valid amount");
            sc.nextLine();
            continue;
        }
        System.out.println("Enter the Password:");
        sc.nextLine();
        
        password=sc.nextLine();
   
        CustomerVerification cusObj=new CustomerVerification(sc, conn);

      Double fetchedbalance=0.0;
         String username=CustomerVerification.userName;
      try {
        String query="select totalbalance from usertotalbalance where user_name=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
          fetchedbalance=rs.getDouble("totalbalance");
          System.out.println("previous balance :"+fetchedbalance);
        }

      } catch (Exception e) {
        e.printStackTrace();      }
        Double totalbalance=amount+fetchedbalance;
        String fetchedpass=cusObj.password;

   
    
       if(password.equals(fetchedpass))
       {
        try {
          LocalDate currentDate=LocalDate.now();
          LocalTime currentTime=LocalTime.now();
       


          String query="insert into usersdata(user_name,balance,date,time)values(?,?,?,?)";
          PreparedStatement ps=conn.prepareStatement(query);
          ps.setString(1,username );
          ps.setDouble(2, amount);
          ps.setObject(3, currentDate);
          ps.setObject(4, currentTime);
          ps.executeUpdate();
          String sql="select user_name from usertotalbalance where user_name=?";
          PreparedStatement preparedStatement=conn.prepareStatement(sql);
          preparedStatement.setString(1, username);
          ResultSet resultSet=preparedStatement.executeQuery();
          if(resultSet.next())
          {
            String update="update usertotalbalance set totalbalance=? where user_name=?";
            PreparedStatement pp=conn.prepareStatement(update);
            pp.setDouble(1, totalbalance);
            pp.setString(2, username);
            int rows=pp.executeUpdate();
            if(rows>0)
            {
              System.out.println("Balance Updated Successfully");
              System.out.println("Current balance: "+totalbalance);
               break;  
            }else 
            {
              System.out.println("Unable to Update the Balance");
            break;
            }


          }
          
          String query2="insert into usertotalbalance (user_name ,totalbalance)values(?,?)";
          PreparedStatement ps2=conn.prepareStatement(query2);
          ps2.setString(1, username);
          ps2.setDouble(2, totalbalance);
          int rows=ps2.executeUpdate();
          System.out.println("added balance: "+totalbalance);
          
          if(rows>0)
          {
            System.out.println("Amount added successfully");
            System.out.println("Current balance: "+totalbalance);
            break;
          }else 
          {
            System.out.println("Unable to add the amount");
            break;
          }

        } catch (Exception e) {
          e.printStackTrace();
        }


      
       }else
       {
        System.out.println("Invalid password attempts left "+countattempt);
          
         
          countattempt--;
        
          if(countattempt>=0)
      {
        continue;
      }else 
      {
        System.out.println("Attempt limit Exceeded Pls try again after 30 sec");
    
        a.start();
        break;
      }
         
       }
        

      }
    }
    
}
