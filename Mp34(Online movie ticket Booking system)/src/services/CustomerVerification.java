package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
import threads.*;

public class CustomerVerification {
    Scanner sc;
    Connection conn;
    public int otpgenerated;
   public CustomerVerification(Scanner sc,Connection conn)
    {
        this.sc=sc;
        this.conn=conn;
    }
    
        String  mobNo=null;
        String password=null;
        String userName=null;
    public void login()
    {
        try 
        {
            System.out.println("Enter the UserName:");
            userName=sc.nextLine();
         
        
            System.out.println("Enter the password:");
            password=sc.nextLine();
            
        }
        catch(Exception e)
        {
            System.out.println("Invalid input pls enter the valid input only");
            sc.nextLine();
            menu();

        }
        try {
            String search="select * from register where user_name=? and password=?";
            PreparedStatement pp=conn.prepareStatement(search);
            pp.setString(1, userName);
            pp.setString(2, password);
            ResultSet rs=pp.executeQuery();
            if(rs.next())
            {
                 String query="insert into Login(user_name,password)values(?,?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, userName);
            
            ps.setString(2, password);
            int rows=ps.executeUpdate();
            if(rows>0)
                {
                    System.out.println("Login Successfull");

                } else 
                    {
                        System.out.println("Unable to Login");
                          return;
                    }  
                
//Todo: process after login


            }
            else 
            {
                System.out.println("Account not exists pls register first");
                    return;
            }
                
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   public void register()
    {
        
          try 
        {
            System.out.println("Enter the UserName:");
            userName=sc.nextLine();
         
        
            System.out.println("Enter the password:");
            password=sc.nextLine();

           System.out.println("Enter the mobile number:");
            mobNo = sc.next();

if (!mobNo.matches("[7-9][0-9]{9}")) {
    
    System.out.println("Invalid mobile number");
    return;
}
 try {
             String search="select * from register where user_name=? and password=?";
            PreparedStatement pp=conn.prepareStatement(search);
            pp.setString(1, userName);
            pp.setString(2, password);
            ResultSet rs=pp.executeQuery();
           if(rs.next())
           {
            System.out.println("This account already exists");
            return;
           }else 
           {
                String query="insert into register(user_name,mob_no,password)values(?,?,?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, userName);
            ps.setString(2, mobNo);
            
            ps.setString(3, password);
            int rows=ps.executeUpdate();
            if(rows>0)
                {
                    System.out.println("Acoount Registered Successfully");

                } else 
                    {
                        System.out.println("Unable to Register");
                          return;
                    }  
            
           }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
             
          Random random = new Random();
otpgenerated = 1000 + random.nextInt(9000);
System.out.println("\t\t\t--------------------------------");
System.out.println("\t\tOTP sent to mobile no."+mobNo);
System.out.println("\t\tOTP: " + otpgenerated);
System.out.println("\t\t\t--------------------------------");
OtpValidationThread t=new OtpValidationThread(this);

t.setDaemon(true);
t.start();
int OTP;
System.out.println("Enter the otp:");
OTP=sc.nextInt();
int flag=0;
if(OTP==otpgenerated)
{
    flag=1;

}else if(flag==0) 
{
    System.out.println("Invalid otp");
    return;

}
            
        }
        catch(Exception e)
        {
            System.out.println("Invalid input pls enter the valid input only");
            sc.nextLine();
            menu();

        }
       
    }
    public void menu()
    {
        int ch=0;
        while (true) {
            
        
        
        System.out.println("1.Login\n2.Register\n3.Exit");
        System.out.println("Enter the choice:");
        try {
            ch=sc.nextInt();
            sc.nextLine();
            
        } catch (Exception e) {
        System.out.println("Invalid input pls choose enter the digit only");
        sc.nextLine();
        
        }
        switch (ch) {
            case 1:
                login();
                
                break;
            case 2:
                register();
                break;
            case 3:
               return; 
            default:
                System.out.println("Invalid entry");
                break;
        }
    }
}
    

}
