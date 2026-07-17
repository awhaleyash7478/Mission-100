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
    ParcelBooking parObj;
    public int otpgenerated;
   public CustomerVerification(Scanner sc,Connection conn,ParcelBooking parObj)
       {
           
           this.sc=sc;
           this.conn=conn;
           this.parObj=parObj;
    }
    
        String  mobNo=null;
        String password=null;
        String userName;
        String address=null;
    public void  login()
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
                    while(true) {
                        
                    
                    System.out.println("Login Successfull");
                    System.out.println("1.View Parcel History\n2.Track Parcel\n3.Book Parcel\n4.Exit");
                    int choice=0;
                    try 
                    {
                        choice=sc.nextInt();
                        sc.nextLine();
                    }catch(Exception e)
                    {
                        System.out.println("pls enter the valid input");
                        login();
                    }
                    if(choice==1)
                    {
                        ParcelHistory p=new ParcelHistory(conn);
                        p.viewHistory();
                    }else if (choice==2)
                    {
                        ParcelTracking p=new ParcelTracking(conn);
                        p.trakParcel();

                    }else if(choice==3)
                    { parObj.senderDetails();

                        
                        
                    }
                    else if(choice==4)
                    {
                        return;
                    }
                        else 
                    {
                        System.out.println("invalid option selected");
                        return;
                    }
                }
                   
                    
                //return;
                    


                } else 
                    {
                        System.out.println("Unable to Login");
                          return;
                    }  
    


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
   public  void register()
    {
        
        
          try 
        {
            System.out.println("Enter the UserName:");
            userName=sc.nextLine();
            ParcelTracking p=new ParcelTracking(conn);
            p.getUserName(userName);
            String fetch="select user_name from register";
            PreparedStatement pp=conn.prepareStatement(fetch);
            ResultSet rr=pp.executeQuery();
            while (rr.next()) {
                if(rr.getString("user_name").equals(userName))
                {
                    System.out.println("The user already exists");
                    return;
                }
            }
         
        
            System.out.println("Enter the password:");
            password=sc.nextLine();
            System.out.println("1.Confirm Password\n2.Re-enter Password");
            int choice=0;
            try 
            {
                choice=sc.nextInt();
                sc.nextLine();
            }catch(Exception e)
            {
                System.out.println("Invalid choice");
                return ;
            }
            
            if(choice==2)
            {
                System.out.println("Enter the password:");
            password=sc.nextLine();
                
            }

           System.out.println("Enter the mobile number:");
            mobNo = sc.next();
            sc.nextLine();

if (!mobNo.matches("[7-9][0-9]{9}")) {
    
    System.out.println("Invalid mobile number");
    return ;
}
System.out.println("Enter the address:");

    address=sc.nextLine().trim();
   String regex = "^[A-Za-z0-9\\s,./#()'-]{5,100}$";
    if( !address.matches(regex))
    {
        System.out.println("Invalid address pls enter the valid address");
        return;
    }

 try {
             String search="select * from register where user_name=? and password=?";
            PreparedStatement pp2=conn.prepareStatement(search);
            pp2.setString(1, userName);
            pp2.setString(2, password);
            ResultSet rs=pp2.executeQuery();
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
            if(rows<0)
                {
                    System.out.println("Unable to register");
                    return;

                } 
                generateOtp();
            
           }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
            
        }
        catch(Exception e)
        {
            System.out.println("Invalid input pls enter the valid input only");
            sc.nextLine();
            menu();

        }
       
    }
     public void generateOtp()
        {
         
             
          Random random = new Random();
otpgenerated = 1000 + random.nextInt(9000);
System.out.println("\t\t--------------------------------");
System.out.println("\t\tOTP sent to mobile no."+mobNo);
System.out.println("\t\tOTP: " + otpgenerated);
System.out.println("\t\tOTP valid until 5 minutes");
System.out.println("\t\t--------------------------------");
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
    System.out.println("Account registered Successfully");
   
    return;

}else if(flag==0) 
{
    System.out.println("Invalid otp");
    return;

}
        }
    public void  menu()
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
               return ; 
            default:
                System.out.println("Invalid entry");
                break;
        }
    }
}
    

}
