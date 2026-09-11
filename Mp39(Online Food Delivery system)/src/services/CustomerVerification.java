package services;

import java.lang.classfile.instruction.ArrayLoadInstruction;
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


   
   public CustomerVerification(Scanner sc,Connection conn)
       {
     
           
           this.sc=sc;
           this.conn=conn;
                    
        
         
    }
   
   
    
    
        String  mobNo=null;
       static String password=null;
      public  static String userName ;
      static String mob;
        String address=null;
    public void  login()
    {
        try 
        {
            System.out.println("Enter the Mobile No:");
            mob=sc.nextLine();
            // d.addIncome(userName);
        
         
        
            System.out.println("Enter the password:");
            password=sc.nextLine();
            
        try {
            String query="select role from usersRoles where mob_no=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1,mob );
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                String role=rs.getString("role");
                System.out.println("roles: "+role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }catch(Exception e)
        {
            System.out.println("Invalid input pls enter the valid input only");
            sc.nextLine();
            menu();

        }
                 int found=0;
             try {
            String search="select * from register where mob_no=? and password=?";
            PreparedStatement pp=conn.prepareStatement(search);
         
            pp.setString(1, mob);
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
       
        ArrayList <String> fetchedMobNo=new ArrayList<>();
          ArrayList<String>fetchedEmailId=new ArrayList<>();
        
          try 
        {
            int found=0;
          
                 
                   
                   
                found=0;
            

            System.out.println("Enter the UserName:");
            userName=sc.nextLine();
          
            String fetch="select * from register";
            PreparedStatement pp=conn.prepareStatement(fetch);
            ResultSet rr=pp.executeQuery();
           
            while (rr.next()) {
               
                 fetchedMobNo.add(rr.getString("mob_no"));
                 fetchedEmailId.add(rr.getString("email_id"));
                      
                
            }
            
             
        
             
               
               
        

      
       
    }
catch(Exception e)
{
    e.printStackTrace();
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
            if(fetchedMobNo.contains(mobNo))
            {
               
                System.out.println("This Number is already registered");
                
                continue;

            }

if (!mobNo.matches("[7-9][0-9]{9}")) {
    
    System.out.println("Invalid mobile number");
    continue;
}
generateOtp();
break;
            }
             String email=null;
        while(true)
        {
           


            System.out.println("Enter the email id:");
            email=sc.nextLine();
            String regex="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if(email.matches(regex))
            {
              

                if(fetchedEmailId.contains(email))
            {
               
                System.out.println("This email is already registered");
                continue;

            }
            break;
            } 
            else
            {
                System.out.println("Invalid email id");
                continue;
            }
                                

            
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

 
        //      String search="select * from register where mob=? and password=?";
        //     PreparedStatement pp2=conn.prepareStatement(search);
        //     pp2.setString(1, userName);
        //     pp2.setString(2, password);
        //     ResultSet rs=pp2.executeQuery();
        //    if(rs.next())
        //    {
        //     System.out.println("This account already exists");
        //     return;
        //    }else 
           
            try 
            {
                String query="insert into register(user_name,mob_no,password,address,email_id)values(?,?,?,?,?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, userName);
            ps.setString(2, mobNo);
            
            
            ps.setString(3, password);
            ps.setString(4,address);
            ps.setString(5, email);
            ps.executeUpdate();
            String role="Customer";
            
            String roles="insert into usersRoles (role,mob_no)values(?,?)";
            PreparedStatement preparedStatement=conn.prepareStatement(roles);
            preparedStatement.setString(1, role);
            preparedStatement.setString(2, mobNo);
            int rows=preparedStatement.executeUpdate();
             
            if(rows>0)
                {
                    System.out.println("Account registered Successfully");
                    return;

                } 
            }catch(Exception e)
            {
                e.printStackTrace();
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
sc.nextLine();
int flag=0;
if(OTP==otpgenerated)
{
    flag=1;

   
    
    


   
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
                 RolesAllocation roleObj=new RolesAllocation(sc, conn);
                           roleObj.allocateRoles();
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

