package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
public class RolesAllocation {
    Scanner sc;
    Connection conn;
    CustomerVerification cusObj;
    
    public RolesAllocation(Scanner sc,Connection conn)
    {
        this.conn=conn;
        this.sc=sc;
    cusObj=new CustomerVerification(sc, conn);
    }
    public void restaurantRegistration()
    {
        int resId=0;
           ArrayList<String> storedUserName=new ArrayList<>();
             ArrayList <String> fetchedMobNo=new ArrayList<>();
          ArrayList<String>fetchedEmailId=new ArrayList<>();
        
    try {
             String fetch="select * from restaurant_registration";
            PreparedStatement pp=conn.prepareStatement(fetch);
            ResultSet rr=pp.executeQuery();
           
            while (rr.next()) {
                 storedUserName.add(rr.getString("res_name"));
                 fetchedMobNo.add(rr.getString("mob"));
                 fetchedEmailId.add(rr.getString("email"));
                 resId=rr.getInt("res_id");
                      
                
            }
    } catch (Exception e) {
       e.printStackTrace();
    }
             
        String resName=null,owner=null,email=null,mob=null,add=null,cuisine=null,pass=null;
        System.out.println("----------Restaurant Registration----------");
        
        System.out.print("Restaurant name: ");
        resName=sc.nextLine();
        
        System.out.print("Owner name: ");
        owner=sc.nextLine();
        while(true)
        {
        System.out.print("Email: ");
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
            
        
        
        System.out.print("Mobile: ");
        mob=sc.nextLine();
          if(fetchedMobNo.contains(mob))
            {
               
                System.out.println("This Mobile Number is already registered");
                
                continue;

            }

if (!mob.matches("[7-9][0-9]{9}")) {
    
    System.out.println("Invalid mobile number");
    continue;
}

cusObj.generateOtp();
        
break;}

        while(true)
        {
        System.out.print("Address: ");
        add=sc.nextLine().trim();
        String regex = "^[A-Za-z0-9\\s,./#()'-]{5,100}$";
    if( !add.matches(regex))
    {
        System.out.println("Invalid address pls enter the valid address");
        continue;
    }
    break;
}
int count=1;
    while (true) {
    
    
        System.out.print("Cuisine type "+count+": ");
        cuisine=sc.nextLine();
         
    
        try {
            String query="insert into cuisine_type (res_id,cuisine_type )values(?,?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,resId );
            
            ps.setString(2, cuisine);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n1.Add more        2.Next");
        int ch=0;
        try 
        {
            ch=sc.nextInt();
            sc.nextLine();
            
        }catch(Exception e)
        {
            System.out.println("Pls choose the valid option [eg:2 for Next]");
            continue;
        }
        if(ch==1)
        {
            count++;
         
            continue;
        }else if(ch==2)
        {
            break;
        }else 
        {
            System.out.println("Pls enter the valid option [eg:2 for Next]");
            continue;
        }
    }

        System.out.print("Password: ");
        pass=sc.nextLine();
        try {
            String query="insert into restaurant_registration (res_name,owner_name,email,mob,address,password )values(?,?,?,?,?,?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, resName);
            ps.setString(2, owner);
            ps.setString(3, email);
            ps.setString(4, mob);
            ps.setString(5, add);
            ps.setString(6, pass);
       int rows= ps.executeUpdate();
       if(rows>0)
       {
        System.out.println("Restaurant registered Successfully");
       }
           
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void deliveryPartnerRegistration()
    {
        
             ArrayList <String> fetchedMobNo=new ArrayList<>();
          ArrayList<String>fetchedEmailId=new ArrayList<>();
          ArrayList<String>vehNo=new ArrayList<>();
            String email=null,name=null,mob=null,vehNum=null,vehicle=null;
            String regex="^[A-Z]{2}[ -]?[0-9]{1,2}[ -]?[A-Z]{1,3}[ -]?[0-9]{4}$";
            try {
                String query="select * from delivery_partners";
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                while(rs.next())
                {
                    
                 fetchedMobNo.add(rs.getString("mob_no"));
                 fetchedEmailId.add(rs.getString("email"));
                 vehNo.add(rs.getString("veh_no"));
                
                      

                }

                
            } catch (Exception e) {
              e.printStackTrace();
            }
                                
        System.out.println("----------Delivery Partner Registration----------");
        System.out.print("\nName: ");
        name=sc.nextLine();
          while (true) {
        System.out.println("Mobile no: ");
         mob=sc.nextLine();
       
            if(fetchedMobNo.contains(mob))
            {
               
                System.out.println("This Mobile Number is already registered");
                
                continue;

            }

if (!mob.matches("[7-9][0-9]{9}")) {
    
    System.out.println("Invalid mobile number");
    continue;
}
break;
            
         }
         while(true)
         {
        System.out.println("Email: ");
        email=sc.nextLine();
        
                    String emailRegex="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if(email.matches(emailRegex))
            {
               

                if(fetchedEmailId.contains(email))
            {
               
                System.out.println("This email is already registered");
                continue;

            }

        }
        break;

    }

      
        while(true)
        {
        System.out.println("Vehicle Number: ");
        vehNum=sc.nextLine();
        if(vehNum.matches(regex))
        {
            if(vehNo.contains(vehNum))
            {
                System.out.println("Vehicle with this number is already registered");
                continue;
            }
            break;
        }
    }
    try {
        String query="insert into delivery_partners (name,email,veh_no,)values(?,?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, vehNum);
        int rows=ps.executeUpdate();
        if(rows>0)
        {
            System.out.println("Delivery Partner registered successfully");
        }else 
        {
            System.out.println("Unable to register the deilvery partner");
        }
    } catch (Exception e) {
    e.printStackTrace();
    }
    


    }

    public void allocateRoles()
    {
        while(true)
        {
        

        System.out.println("\n1.Customer        2.Restaurant Owner        3.Delivery Partner        4.Exit");
                System.out.print("Register as: ");
        int choice=0;
        try 
        {
            choice=sc.nextInt();
            sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("Pls choose the valid option only [eg:1 Customer]");
            continue;
        }
        if(choice==1)
        {
                return ;
        }else if(choice==2)
        {
             restaurantRegistration();

        }else if(choice==3)
        {
            deliveryPartnerRegistration();

        }else if(choice==4)
        {
         
            cusObj.menu();
        }
        else 
        {
            System.out.println("Pls choose the valid option only [eg:1 Customer]");
            continue;
        }
    }

    }
}
    

