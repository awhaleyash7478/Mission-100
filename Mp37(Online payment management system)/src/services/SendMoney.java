package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SendMoney {
    Connection conn;
    Scanner sc;
    public SendMoney(Scanner sc,Connection conn)
    {
        this.conn=conn;
        this.sc=sc;

    }
    public void sendMoney()
    {
        String mobno=null;
         String fetchedName=null;
        while (true) {
            
        
            System.out.println("Enter the Mobile Number: ");
       
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
                  if(fetchedName.equals(CustomerVerification.userName))
                  {
                    System.out.println("Sender and Receiver Must be different");
                    // sc.nextLine();
                    continue;
                  }

                    System.out.println("Receiver Found\r\n" + //
                                                "\r\n" + //
                                                "Name: "+fetchedName+"\n"+
                                                "Mobile: "+mobno +"\n"+
                                                
                                                "Continue?\n" + 
                                                "1. Yes\n2. No");
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
                        preparedStatement.setString(1,fetchedName);
                        ResultSet resultSet=preparedStatement.executeQuery();
                  
                        if(resultSet.next())
                        {
                         fetchedamount=resultSet.getDouble("totalbalance");
                         System.out.println("fetched amount: "+fetchedamount);

                        }
                        if(amount>fetchedamount)
                        {
                            System.out.println("Insufficient balance in the account\nTransaction Failed...\nThank-You");
                            break;
                        }
                        

                    } catch (Exception e) {
                        e.printStackTrace();
                        
                    }
                    while (true) {
                        int option=0;
                        
                    
                    System.out.println("--------------------------------\nPAYMENT CONFIRMATION\n--------------------------------");
                    System.out.println("From: "+CustomerVerification.userName);
                    System.out.println("To: "+fetchedName);
                    System.out.println("Amount: "+amount);
                    System.out.println("Confirm Payment?");
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
                        double finalamount=fetchedamount+amount;
                        String pay="update usertotalbalance set totalbalance=? where user_name=?";
                        PreparedStatement pp=conn.prepareStatement(pay);
                        pp.setDouble(1, finalamount);
                        pp.setString(2, fetchedName);
                        int rows=pp.executeUpdate();
                        if(rows>0)
                        {
                            System.out.println("Transaction Done Succesfully");
                            System.out.println("final amount: "+finalamount);
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
