package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import threads.*;

public class CustomerVerification {
    Scanner sc;
    

    Connection conn;
   
    public int otpgenerated;
     Dashboard dasObj;
   
   public CustomerVerification(Scanner sc,Connection conn)
       {
           
           this.sc=sc;
           this.conn=conn;
                  dasObj  =new Dashboard(sc, conn);
        
         
    }
   
   
    
    
        String  mobNo=null;
       static String password=null;
        String userName;
        String address=null;
    public void  login()
    {
        try 
        {
            System.out.println("Enter the UserName:");
            userName=sc.nextLine();
            // d.addIncome(userName);
        
         
        
            System.out.println("Enter the password:");
            password=sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("Invalid input pls enter the valid input only");
            sc.nextLine();
            menu();

        }
                 int found=0;
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
                    
                        found=1;
                    
                    System.out.println("Login Successfull");
                    
                    dasObj.viewDashboard();
                   
                    }
                }
                    if(found==0) 
                    {
                        System.out.println("Account not exists Pls register the account first..!");
                        System.out.println("1.Try again\n2.Register\n3.Exit");
                        System.out.println("Enter the choice[1-3]:");
                        
                        int subChoice=sc.nextInt();
                         sc.nextLine();
                        if(subChoice==1)
                        {
                           
                            login();
                        }else if(subChoice==2)
                        {
                            register();
                        }else if(subChoice==3)
                        {
                            return;
                        }else 
                        {
                            System.out.println("Pls enter the valid input");
                            return;
                        }
                    }
            
        
    }catch(SQLException e)
    {
        e.printStackTrace();
    }

       
    }
   public  void register()
    {
        
        
          try 
        {
            int found=0;
            while (true) {
                     ArrayList<String> storedUserName=new ArrayList<>();
                   
                found=0;
            

            System.out.println("Enter the UserName:");
            userName=sc.nextLine();
          
            String fetch="select user_name from register";
            PreparedStatement pp=conn.prepareStatement(fetch);
            ResultSet rr=pp.executeQuery();
           
            while (rr.next()) {
                 storedUserName.add(rr.getString("user_name"));
                      
                
            }
             
            for(String user:storedUserName){
             
      
     

                if(user.equals(userName))
                {
                    found=1;
                    Random ranObj=new Random();
                    System.out.println("This username is already taken");
                    //  String randomName[]=new String[5];
                     int randomNumber=0;
                     int i;
                      HashSet<String>  h=null;
                      String randomName[]=new String[5];
                     for( i=0;i<5;i++)
                    {
                        
                    randomNumber=ranObj.nextInt(1,100);

                     randomName[i]=userName+randomNumber;
                     if(storedUserName.contains(randomName[i]))
                     {
                        
                       
                        
                        randomName[i]=randomName[i+1];
                     }
                                   h=new HashSet<>(Arrays.asList(randomName));

                  
                    }
                 
                    System.out.print("Suggestions: ");
                    
                    System.out.println(h);
                     
                     System.out.println();
                  break;

                }
             
               
               
        }

        if(found==1)
        continue;
        else 
            break;
       
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
            while (true) {
                
            
           System.out.println("Enter the mobile number:");
            mobNo = sc.next();
            sc.nextLine();

if (!mobNo.matches("[7-9][0-9]{9}")) {
    
    System.out.println("Invalid mobile number");
    continue;
}
break;
            }
while (true) {
    
System.out.println("Enter the address:");

    address=sc.nextLine().trim();
   String regex = "^[A-Za-z0-9\\s,./#()'-]{5,100}$";
    if( !address.matches(regex))
    {
        System.out.println("Invalid address pls enter the valid address");
        continue;
    }
    break;
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
while(true)
{
System.out.println("Enter the otp:");
OTP=sc.nextInt();
int flag=0;
if(OTP==otpgenerated)
{
    flag=1;
    System.out.println("Account registered Successfully");
    dasObj.viewDashboard();


   
  break;

}else if(otpgenerated==2)
{
   
    while (true) {
        
    
    System.out.println("OTP Timeout\n1.Resend OTP\n2.Exit");
    System.out.println("Enter your choice: ");
    int choice=sc.nextInt();
    if(choice==1)
    {
        generateOtp();
        break;

    }else if(choice==2)
    {
        return;
    }else
    {
        System.out.println("Pls select the valid option only[eg:1 for resend]");
        continue;

    }
}

}
else if(flag==0) 
{
    System.out.println("Invalid otp");
    continue;

}
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
