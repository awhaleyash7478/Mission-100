package services;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import threads.AttemptValidationThread;

public class RequestMoney {
    Connection conn;
    Scanner sc;
    public RequestMoney(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
    String mobno;
    String fetchedName;
    int attempts=3;
    String currUser=CustomerVerification
    .userName;
    public void requestMoney()
    {
        int choice=0;

        while (true) {
            
        
    
        System.out.println("Enter the Mobile number to Request for money: ");
        mobno=sc.nextLine();
        if (!mobno.matches("[7-9][0-9]{9}")) {
    
    System.out.println("Invalid mobile number");
  
    continue;
            }
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
                    System.out.println("You can't Request Money from Yourself");
                    // sc.nextLine();
                    continue;
                  }
                  System.out.println("--------------------------------");

                    System.out.println("User Found" + //
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
                     while (true) {
                            if(attempts<0)
                            {
                                System.out.println("Attempt limit exceeded pls try again after 30 sec");
                                 AttemptValidationThread a=new AttemptValidationThread();
                                 a.start();
                                 return;

                            }
                    String password=CustomerVerification.password;
                        String pass=null;
                        System.out.println("Enter the Password:");
                     
                        pass=sc.nextLine();

                      
                            
                        
                        if(!pass.equals(password))
                        {
                            System.out.println("Invalid password attempts left "+attempts);
                            attempts--;
                           
                            continue;
                        }
                        LocalDate currdate=LocalDate.now();
                        LocalTime currtime=LocalTime.now();
                        System.out.println("Money Requested Successfully");
                         int notification_id=2;
                        String notify="insert into notification (notification_type,sender,receiver,amount,user_name,date,time)values(?,?,?,?,?,?,?)";
                        PreparedStatement pn=conn.prepareStatement(notify);
                        pn.setInt(1, notification_id);
                        pn.setString(2, currUser);
                        pn.setString(3, fetchedName);
                        pn.setDouble(4, amount);
                        pn.setString(5, fetchedName);
                        pn.setObject(6, currdate);
                        pn.setObject(7, currtime);
                        pn.executeUpdate();
                        String request="insert into paymentrequest (user_name,sender,amount,date,time)values(?,?,?,?,?)";
                        PreparedStatement pn1=conn.prepareStatement(request);
                        pn1.setString(1, fetchedName);
                        pn1.setString(2, currUser);
                        
                        pn1.setDouble(3, amount);
                     
                        pn1.setObject(4, currdate);
                        pn1.setObject(5, currtime);
                        pn1.executeUpdate();


                        return;
                    }
                    
                
                }
            }
        }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        }
    
    


    

