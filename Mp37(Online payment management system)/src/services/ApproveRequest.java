package services;

import java.util.Scanner;

import threads.AttemptValidationThread;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ApproveRequest {
    Connection conn;
    Scanner sc;
    public ApproveRequest(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
    double amount;
    String sender;
    Object date,time;
    String currUser=CustomerVerification.userName;
    int requestid;
    public void viewpaymentRequest()
    {
       
        try 
        {
            String query="select * from paymentrequest where user_name=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, currUser);
            ResultSet rs=ps.executeQuery();
         int found=0;

         
            while(rs.next())
            {
                
                found=1;
                amount=rs.getDouble("amount");
                sender=rs.getString("sender");
                date=rs.getObject("date");
                time=rs.getObject("time");
                requestid=rs.getInt("requestid");
              
            System.out.println("-----------------------------");
            System.out.println("Money Requested: "+amount);
            System.out.println("From: "+sender);
            System.out.println("Date: "+date);
            System.out.println("Time: "+time);
            System.out.println("-----------------------------");
            approveRequest();
            


            }
            if(found==0)
            {
                System.out.println("No Payment Request");
                return;
            }
            


        }catch(Exception e)
        {
            e.printStackTrace();
            
        }
    }
    public void approveRequest()
    {
        int choice=0;
 while (true) {
    
 
        try 
        {
            System.out.println("1.Approve      2.Reject       3.Skip      4.Exit");
            choice=sc.nextInt();
            sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("Invalid choice");
            continue;
        }
        if(choice==1)
        {
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
                                 AttemptValidationThread a=new AttemptValidationThread();
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
                        preparedStatement.setString(1,sender);
                        ResultSet resultSet=preparedStatement.executeQuery();
                        
                        if(resultSet.next())
                        {
                            receiverbal=resultSet.getDouble("totalbalance");
                          

                        }
                          double finalamount=receiverbal+amount;
                        double senderbal=fetchedamount-amount;
                        String pay="update usertotalbalance set totalbalance=? where user_name=?";
                        PreparedStatement pp=conn.prepareStatement(pay);
                        pp.setDouble(1, finalamount);
                        pp.setString(2, sender);
                        pp.executeUpdate();
                        String minus="update usertotalbalance set totalbalance=? where user_name=?";
                        PreparedStatement p=conn.prepareStatement(minus);
                        p.setDouble(1, senderbal);
                        
                        p.setString(2 ,currUser);
                        int rows=p.executeUpdate();
                        LocalDate currDate=LocalDate.now();
                        LocalTime currtime=LocalTime.now();
                        String transaction="Money Request(A)";
                          if(rows>0)
                        {
                            System.out.println("Transaction Done Succesfully");
                            String notify="insert into notification (notification_type ,user_name)values(?,?)";
                            int approveid=3;
                            PreparedStatement notification=conn.prepareStatement(notify);
                            notification.setInt(1, approveid);
                            notification.setString(2, sender);
                            notification.executeUpdate();


                            String history="insert into paymenthistory (amount,sender,receiver,date,time,transaction,user_name)values(?,?,?,?,?,?,?)";
                            PreparedStatement pr=conn.prepareStatement(history);
                            pr.setDouble(1, amount);
                            pr.setString(2,currUser );
                            pr.setString(3, sender);
                            pr.setObject(4, currDate);
                            pr.setObject(5, currtime);
                            pr.setString(6, transaction);
                            pr.setString(7,currUser);
                            pr.executeUpdate();
                            
                                String query="delete from paymentrequest where requestid=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, requestid);
            
            ps.executeUpdate();
           
            break;
           

                            
                           
                            
                        }else 
                        {
                            System.out.println("Transaction Failed");
                            break;
                          
                        }

                  
                    } catch (Exception e) {
                     e.printStackTrace();
                    }

        }else if(choice==2)
        {
            try {
                String query="delete from paymentrequest where requestid=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, requestid);
            int rows=ps.executeUpdate();
            if(rows>0)
            {
                System.out.println("Payment Request Rejected successfully");
                return;
            }else 
            {
                System.out.println("Unable to delete the request");
                return;
            }
        
            } catch (Exception e) {
               e.printStackTrace();
            }

        }
        else if(choice==3)
        {
            System.out.println("breaking it");
            break;

        }else if(choice==4) 
        {
            
            return;
        }else 
        {
            System.out.println("Invalid choice pls select from 1-3");
            continue;
        }
    }
}
    
}
