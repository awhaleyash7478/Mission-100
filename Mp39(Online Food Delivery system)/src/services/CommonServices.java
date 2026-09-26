package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

public class CommonServices {
    Connection conn;
    Scanner sc;
    public  CommonServices(Connection conn,Scanner sc)
    {
         this.conn=conn;
         this.sc=sc;
    }
       String tableName=null;
     public void myProfile()
    {
        
     
        String selectedRole=CustomerVerification.selectedRole;
        System.out.println("selected role: "+CustomerVerification.selectedRole);
        if(selectedRole.equals("Customer"))
        {
             tableName="register";
        }else if(selectedRole.equals("Delivery Partner"))
        {
            tableName="delivery_partners";
        }else 
        {
            tableName="restaurant_registration";
        }
        try {
            String query="select * from "+tableName+" where mob_no=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, CustomerVerification.mob);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
            if(tableName.equals("register"))
            {
                System.out.println("================================");
System.out.println("          USER PROFILE          ");
System.out.println("================================");
System.out.println("Name      : " + rs.getString("user_name"));
System.out.println("Mobile No : " + rs.getString("mob_no"));
System.out.println("Password  : " + rs.getString("password"));
System.out.println("Address   : " + rs.getString("address"));
System.out.println("Email     : " + rs.getString("email_id"));
System.out.println("================================");

                
            } else if(tableName.equals("delivery_partners"))
            {
                System.out.println("======================================");
System.out.println("        DELIVERY PARTNER PROFILE       ");
System.out.println("======================================");
System.out.println("Name       : " + rs.getString("name"));
System.out.println("Mobile No  : " + rs.getString("mob_no"));
System.out.println("Email      : " + rs.getString("email"));
System.out.println("Vehicle No : " + rs.getString("veh_no"));
System.out.println("======================================");

            }else             {
                System.out.println("======================================");
System.out.println("          RESTAURANT PROFILE          ");
System.out.println("======================================");
System.out.println("Restaurant : " + rs.getString("res_name"));
System.out.println("Owner      : " + rs.getString("owner_name"));
System.out.println("Email      : " + rs.getString("email"));
System.out.println("Mobile No  : " + rs.getString("mob_no"));
System.out.println("Address    : " + rs.getString("address"));
System.out.println("Password   : " + rs.getString("password"));
System.out.println("======================================");

            }
        }
            
            
        } catch (Exception e) {
          e.printStackTrace();
        }
        while(true)
        {

        System.out.println("1.Update Profile          2.Exit");
        int ch=0;
        try {
            ch=sc.nextInt();
        } catch (Exception e) {
        System.out.println("Invalid option allowed is 1 and 2");
        continue;
        }
        if(ch==1)
        {
            updateProfile();
        }else if(ch==2)
        {
            return ;
        }else
        {
            System.out.println("Invalid option allowed is 1 and 2");
            continue;
        }
    }

    }
    public void updateProfile()
    {
          int columnCount=0;
        HashMap<Integer,String>columnName=new HashMap<>();
      try {
          String query="select * from "+tableName;
          PreparedStatement ps=conn.prepareStatement(query);
          ResultSet rs=ps.executeQuery();
          ResultSetMetaData rr=rs.getMetaData();
      columnCount  =rr.getColumnCount();
          for(int i=1;i<columnCount;i++)
          {
            columnName.put(i,rr.getColumnName(i));
            
        System.out.println("column name: "+columnName);  
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
      
      int i=0;
      for(i=1;i<columnCount;i++)
      {
        System.out.println(i+"."+columnName.get(i));
      }
     
   while(true)
   {
      System.out.println("Enter the Number to update the field: ");
      int num=0;
   try {
         num=sc.nextInt();
         sc.nextLine();
   } catch (Exception e) {
    System.out.println("Pls enter the valid number [eg:"+i+".for Exit]");
    continue;
   }
   if(num>columnCount||num<=0)
   {
     System.out.println("Pls enter the valid number [eg:"+i+".for Exit]");
     continue;

   }

        String updateColName=columnName.get(num);
        if(updateColName.endsWith("id")||updateColName.endsWith("no"))
        {
            System.out.println("This field is not allowed to be updated");
            continue;
        }
        System.out.println("update : "+updateColName);
        System.out.print("Updated Field: ");
        String field=sc.nextLine();
        try {
            String update="update "+tableName+" set "+updateColName+" =? where mob_no =?";
            PreparedStatement ps=conn.prepareStatement(update);
            ps.setString(1, field);
            ps.setString(2, CustomerVerification.mob);
            int rows=ps.executeUpdate();
            if(rows>0)
            {
                System.out.println(updateColName+" updated Succesfully");
                return ;
            }else 
            {
                System.out.println("Unable to update "+updateColName);
                return ;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

      

    }
    
}
}
