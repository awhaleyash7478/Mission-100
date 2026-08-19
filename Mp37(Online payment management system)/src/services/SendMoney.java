package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import threads.AttemptValidationThread;

public class SendMoney {
  
    String currUser=CustomerVerification.userName;
      AttemptValidationThread a;
    Connection conn;
    Scanner sc;
    public SendMoney(Scanner sc,Connection conn)
    {
        this.conn=conn;
        this.sc=sc;
a=new AttemptValidationThread();
    }
    public void sendMoney()
    {
        if(AttemptValidationThread.sleep==1)
        {
            System.out.println("Pls wait Your attempt limit is exceeded ");
            return;
        }
        
        String mobno=null;
         String fetchedName=null;
        while (true) {
            
        
            System.out.println("Enter the Receiver's Mobile Number: ");
       
            mobno=sc.nextLine();
            if (!mobno.matches("[7-9][0-9]{9}")) {
    
    System.out.println("Invalid mobile number");
  
    continue;
            }
            int choice=0;
            try 
            {
                String query="select user_name from register where mob_no=?";
                PreparedStatement ps=conn.prepareStatement(query);
                ps.setString(1, mobno);
                ResultSet rs=ps.executeQuery();
                if(rs.next())
                {
                    
                  fetchedName =rs.getString("user_name");
                  if(fetchedName.equals(currUser))
                  {
                    System.out.println("Sender and Receiver Must be different");
                    // sc.nextLine();
                    continue;
                  }
                  System.out.println("--------------------------------");

                    System.out.println("Receiver Found" + //
                                             "\nName: "+fetchedName+"\n"+
                                                "Mobile: "+mobno +"\n"+
                                                
                                                "Continue?\n" + 
                                                "1. Yes\n2. No");
                  System.out.println("--------------------------------");
                                                choice=sc.nextInt();
                

                }else 
                {
                    int ch=0;
                    System.out.println("User Not found");
                    System.out.println("1.Try Again\n2.Exit");
                    try 
                    {
                   ch =sc.nextInt();
                   
                        sc.nextLine();
                    }catch(Exception e)
                    {
                        System.out.println("Pls choose the valid option only");
                        sc.nextLine();
                        ch=sc.nextInt();
                    }
                    if(ch==1)
                    {
                        continue;
                    }else  
                    {
                        return;
                        
                    }
                }
                double amount=0.0;
                while (true) {
                    
                
                if(choice==1)
                {
                    System.out.println("Enter the amount: ");
                    try {
                        amount=sc.nextDouble();
                           sc.nextLine();
                    
                    } catch (Exception e) {
                        System.out.println("Invalid amount");
                        continue;
                    }
                    if(amount<=0)
                    {
                        System.out.println("Pls enter the valid amount");
                        continue;
                    }
                          double fetchedamount=0.0;
                    try {
                        String fetch="select totalbalance from usertotalbalance where user_name=?";
                        PreparedStatement preparedStatement=conn.prepareStatement(fetch);
                        preparedStatement.setString(1,currUser);
                        ResultSet resultSet=preparedStatement.executeQuery();
                  
                        if(resultSet.next())
                        {
                         fetchedamount=resultSet.getDouble("totalbalance");
                        

                        }
                        if(amount>fetchedamount)
                        {
                        System.out.println("--------------------------------");
                            System.out.println("Insufficient balance in the account\nTransaction Failed...\nThank-You");
                            System.out.println("--------------------------------");
                            return;
                        }
                        String pass=null;

                        String password=CustomerVerification.password;
                        int attempts=3;
                          while (true) {
                            if(attempts<0)
                            {
                                System.out.println("Attempt limit exceeded pls try again after 30 sec");
                                 
                                 a.start();
                                 return;

                            }
                        System.out.println("Enter the Password:");
                     
                        pass=sc.nextLine();
                      
                            
                        
                        if(!pass.equals(password))
                        {
                            System.out.println("Invalid password attempts left "+attempts);
                            attempts--;
                           
                            continue;
                        }else 
                        {
                            break;
                        }
                    }
                        

                        

                    } catch (Exception e) {
                        e.printStackTrace();
                        
                    }
                    double receiverbal=0.0;
                    try {
                         String fetch="select totalbalance from usertotalbalance where user_name=?";
                        PreparedStatement preparedStatement=conn.prepareStatement(fetch);
                        preparedStatement.setString(1,fetchedName);
                        ResultSet resultSet=preparedStatement.executeQuery();
                        
                        if(resultSet.next())
                        {
                            receiverbal=resultSet.getDouble("totalbalance");
                          

                        }

                  
                    } catch (Exception e) {
                     e.printStackTrace();
                    }
                    while (true) {
                        int option=0;
                        
                    
                    System.out.println("--------------------------------\nPAYMENT CONFIRMATION\n--------------------------------");
                    System.out.println("From: "+currUser);
                    System.out.println("To: "+fetchedName);
                    System.out.println("Amount: "+amount);
                    System.out.println("\nConfirm Payment?");
                    System.out.println("1.Confirm\n2.Cancel");
                    System.out.println("--------------------------------");
                    try 
                    {
                        option=sc.nextInt();
                    }catch(Exception e)
                    {
                        System.out.println("Invalid option ");
                        continue;
                    }
                    if(option==1)
                    {
                        
                        double finalamount=receiverbal+amount;
                        double senderbal=fetchedamount-amount;
                        String pay="update usertotalbalance set totalbalance=? where user_name=?";
                        PreparedStatement pp=conn.prepareStatement(pay);
                        pp.setDouble(1, finalamount);
                        pp.setString(2, fetchedName);
                        pp.executeUpdate();
                        String minus="update usertotalbalance set totalbalance=? where user_name=?";
                        PreparedStatement p=conn.prepareStatement(minus);
                        p.setDouble(1, senderbal);
                        
                        p.setString(2 ,currUser);
                        int rows=p.executeUpdate();
                        LocalDate currDate=LocalDate.now();
                        LocalTime currtime=LocalTime.now();
                        String transaction="send";
                        

                        if(rows>0)
                        {
                            System.out.println("Transaction Done Succesfully");
                            String history="insert into paymenthistory (amount,sender,receiver,date,time,transaction)values(?,?,?,?,?,?)";
                            PreparedStatement pr=conn.prepareStatement(history);
                            pr.setDouble(1, amount);
                            pr.setString(2,currUser );
                            pr.setString(3, fetchedName);
                            pr.setObject(4, currDate);
                            pr.setObject(5, currtime);
                            pr.setString(6, transaction);
                            pr.executeUpdate();
                                  

                            
                           
                            return;
                        }else 
                        {
                            System.out.println("Transaction Failed");
                            return;
                        }

                    }

                    }

                }
            }
            }catch(SQLException e)
            {
                e.printStackTrace();
            }

       
    }
    
}
}
