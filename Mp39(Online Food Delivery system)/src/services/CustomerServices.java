
package services;
import java.util.*;

import java.sql.*;

public class CustomerServices {

Connection conn;
Scanner sc;    
    public CustomerServices(Connection conn ,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
    public void viewnearbyRes()
    {
          ArrayList<Double>storedFinalAmount=new ArrayList<>();
          int quantity=0;
         String itemName=null;
         double price=0.0;
         String address=null;
          Double finalAmount=0.0;
           ArrayList<Integer>selectedItemIds=new ArrayList<>();
        ArrayList <Integer>itemIds=new ArrayList<>();
    
        int found=0;
        String fetchedAddress=null;
        try {
            String query="select address from register where mob_no=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, CustomerVerification.mob);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
           
                fetchedAddress=rs.getString("address");
                System.out.println("fetched address: "+fetchedAddress );
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        int resId=0;
        try {
            String query="select * from restaurant_registration where address like ?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, fetchedAddress+"%");
           ResultSet rs=ps.executeQuery();
           while(rs.next())
            {
                found=1;
                resId= rs.getInt("res_id");
                System.out.println("hey :"+resId);
                
                  System.out.println("Restaurant ID : " +resId);
                 System.out.println("Restaurant: " + rs.getString("res_name"));
    System.out.println("Location  : " + rs.getString("address"));
    System.out.println("----------------------------");
                
            } if(found==0)
            {
                System.out.println("Unable to find the Restaurant");
               
                 String query2="select * from restaurant_registration where res_name like ? ";

            PreparedStatement ps2=conn.prepareCall(query2);
            ps2.setString(1,"%"+fetchedAddress+"%");
            ResultSet rs2=ps2.executeQuery();
while(rs2.next())
    {
         System.out.println("-----Similar Results-----");
          System.out.println("\nRestaurant ID : " + rs2.getInt("res_id"));
           resId= rs2.getInt("res_id");
                 System.out.println("Restaurant: " + rs2.getString("res_name"));
    System.out.println("Location  : " + rs2.getString("address"));
    System.out.println("----------------------------");
                

    }
        
        }
    }catch(Exception e)
    {
        e.printStackTrace();
    }
         int ch=0;
        while(true)
        {
        System.out.println("1.View Menu          3.Exit");
    
        try {
            ch=sc.nextInt();
        } catch (Exception e) {
        System.out.println("Pls select the valid option");
        continue;
        } 
      
    if(ch==1)
        {
            while(true)
            {
            System.out.println("Enter the restaurant id: ");
            try
            {
            resId=sc.nextInt();
            }catch(Exception e)
            {
                System.out.println("Pls enter the valid id");
                continue;
            }
            break;
        }
            try {
                String fetch="select * from restaurant_menu where res_id=?";
                PreparedStatement pp=conn.prepareStatement(fetch);
                pp.setInt(1,resId);
                ResultSet rr=pp.executeQuery();
                  System.out.println("+------------+------------+----------+---------+");
System.out.println("| menu_item  | item_prize | cuisine  | item_id |");
System.out.println("+------------+------------+----------+---------+");
                if(rr.next()) {
                    itemIds.add(rr.getInt("item_id"));
                      System.out.printf("| %-10s | %-10s | %-8s | %-7d |%n",
            rr.getString("menu_item"),
            rr.getString("item_prize"),
            rr.getString("cuisine"),
            rr.getInt("item_id"));

                }
                System.out.println("+------------+------------+----------+---------+");
            } catch (Exception e) {
               e.printStackTrace();
            }
             int itemId=0;
            while(true)
            {
                System.out.println("selected item id: "+selectedItemIds);
                if(!selectedItemIds.isEmpty())
                selectedItemIds.remove(0);
                System.out.println("after removing: "+selectedItemIds);
            System.out.println("Enter the item Id to place Order: ");
        
            
            try
            {
            itemId=sc.nextInt();

            }catch(Exception e)
            {
                System.out.println("Pls enter the valid id only");
                continue;
            }
                selectedItemIds.add(itemId);
                System.out.println("after entering itemid: "+selectedItemIds);
                for(int itemIdss:selectedItemIds)
                {
                    itemId=itemIdss;
                    System.out.println("itemidss: "+itemIdss);

                }
            
          
        
        if(itemIds.contains(itemId))
        {
           
            
            try {
                String order="select * from restaurant_menu where item_id=?";
                PreparedStatement pr=conn.prepareStatement(order);
                pr.setInt(1, itemId);
                ResultSet rrs=pr.executeQuery();
                if(rrs.next())
                {
                    
                    System.out.println("-----Selected Dish-------");
                    System.out.println("Item Name: "+rrs.getString("menu_item"));
                    System.out.println("Cuisine: "+rrs.getString("cuisine"));
                    System.out.println("Price: "+rrs.getString("item_prize"));
                    System.out.println("-------------------------");
                     price=Double.parseDouble(rrs.getString("item_prize"));
                     itemName=rrs.getString("menu_item");
                }
              
            } catch (Exception e) {
                e.printStackTrace();
            }
        
            while(true)
            {
            System.out.print("Quantity: ");
            
            try {
                quantity=sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid quantity");
                continue;
            }
            if(quantity<=0)
            {
                System.out.println("Quantity can't be negative or zero");
                continue;
            }
            break;
        }
            finalAmount =price*quantity;
            storedFinalAmount.add(finalAmount);
                     System.out.println("final amount: "+finalAmount);
         System.out.println(storedFinalAmount);
       
            
              int selection=0;
            while(true)
            {
            System.out.println("1.Add          2.Next");
          
            try
            {
            selection=sc.nextInt();
            }catch(Exception e)
            {
                System.out.println("Invalid option");
                continue;
            }
            break;
        }
        
            if(selection==1)
            {
                continue;
             

            }else if(selection==2)
            {
                System.out.println("breaking");
                break;
                
            }else 
            {
                System.out.println("Invalid option allowed is 1 and 2");

                

            }
        

        }
        
        else 
        {
            System.out.println("No such item id");
            continue;
        }
            }
            
        sc.nextLine();
            System.out.println("Address: ");
            address=sc.nextLine();
Double finalBill=0.0;

          for(Double d:storedFinalAmount)

          { 
        
                    finalBill+=d;
        
           
          }
     
       

        
while(true)
{
        System.out.println("=====PAYMENT=====");
        System.out.println("Amount: "+finalBill);
        System.out.println("1.UPI\n2.Card\n3.Cash on Delivery");
        int paymentMode=0;
        try {
            paymentMode=sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid option selected");
            continue;
        }
        String paymentStatus="COD(Unpaid)";
        
     if(paymentMode==1||paymentMode==2||paymentMode==3)
     {
        if(paymentMode==1||paymentMode==2)
            paymentStatus="Online(Paid)";
        try {
            String order="insert into appOrders(res_id,cus_mobno,item_id,address,payment_status,prize)values(?,?,?,?,?,?)";
            PreparedStatement ps1=conn.prepareStatement(order);
            ps1.setInt(1, resId);
            ps1.setString(2,CustomerVerification.mob);
            ps1.setInt(3,itemId);
            ps1.setString(4,address);
            ps1.setString(5,paymentStatus);
            ps1.setDouble(6, finalAmount);
           ps1. executeUpdate();

            String history="insert into appHistory(item_name,amount,paymentstatus)values(?,?,?)";
            PreparedStatement ps2=conn.prepareStatement(history);
            ps2.setString(1, itemName);
            ps2.setDouble(2, finalAmount);
            ps2.setString(3, paymentStatus);
            int rows=ps2.executeUpdate();

            
            if(rows>0)
            {
                System.out.println("Order Placed Successfully");
                return ;
            }else 
            {
                System.out.println("Unable to place the order");
                return ;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    


     }else 
     {
        System.out.println("Invalid option selected");
        continue;
     }
    }

            
       
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
     public void viewMenu()
    {
        ArrayList<Integer> storedItemIds=new ArrayList<>();
        int found=0;
        double price=0.0;
        String itemName=null;
       try {
                String order="select * from restaurant_menu ";
                PreparedStatement pr=conn.prepareStatement(order);
        
                ResultSet rrs=pr.executeQuery();
            while(rrs.next())
                {
                    storedItemIds.add(rrs.getInt("item_id"));
                    found=1;
                    
                    System.out.println("---------Dish------------");
                    System.out.println("Item Name: "+rrs.getString("menu_item"));
                    System.out.println("Item id: "+rrs.getInt("item_id"));
                    System.out.println("Cuisine: "+rrs.getString("cuisine"));
                    System.out.println("Price: "+rrs.getString("item_prize"));
                    System.out.println("-------------------------");
                     price=Double.parseDouble(rrs.getString("item_prize"));
                     itemName=rrs.getString("menu_item");
                }
              
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(found==0)
            {
                System.out.println("Unable to load the menu");
                return ;
            }
                  int choice=0;
            while(true)
            {
        System.out.println("1.Order          2.More options");
  
        try {
            choice=sc.nextInt();
        } catch (Exception e) {
        System.out.println("Invalid choice allowed is 1 and 2");
        continue;
        }
        break;
    }
      if(choice==1)
      {
        
      }else if(choice==2)
      {
         int ch=0;
        while(true)
        {
        System.out.println("1.Add to cart          2.Exit");
       
        try {
            ch=sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid choice allowed is 1 and 2");
            continue;
        }
    
    
    if(ch==1)
        {
            int itemID=0;
            while(true)
            {
            System.out.print("Item id: ");
            
            try {
                itemID=sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid itemID");
                continue;
            }
            if(!storedItemIds.contains(itemID))
            {
                System.out.println("No such item id");
                continue;
            }
            break;

            
        } 
        try {
            String query="insert into cartItems (item_id,mob_no)values(?,?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, itemID);
            ps.setString(2, CustomerVerification.mob);
            int rows=ps.executeUpdate();
            if(rows>0)
            {
                System.out.println("Added to cart successfully");
            return ;
            }else 
            {
                System.out.println("Unable to add to cart");
                return ;
            }
        } catch (Exception e) {
          e.printStackTrace();
        } 
    }else if(ch==2)
    {
        return ;
    }else 
    {
        System.out.println("Invalid option selected allowed is 1 and 2");
        continue;
    }
    
      
    }
}
    

        
        
    }
    public void viewCart()
    {
        int itemId=0;
        int resId=0;
        double finalAmount=0.0;
    ArrayList <Double>storedFinalAmount=new ArrayList<>();
        int quantity=0;
        ArrayList <Integer>selectItemIds=new ArrayList<>();
        try {
        String query="select item_id from cartItems where mob_no=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, CustomerVerification.mob);
        ResultSet rs=ps.executeQuery();
        int found=0;
        while (rs.next()) {
            found=1;
            int itemIds=rs.getInt("item_id");
            String query2="select * from restaurant_menu where item_id=?";
            PreparedStatement ps2=conn.prepareStatement(query2);
            ps2.setInt(1, itemIds);
            ResultSet rs2=ps2.executeQuery();
            if(rs2.next())
            {
                selectItemIds.add(rs2.getInt("item_id"));
                 System.out.println("-----------Dish-------------");
                    System.out.println("Item Name: "+rs2.getString("menu_item"));
                    System.out.println("Item id: "+rs2.getInt("item_id"));
                    System.out.println("Cuisine: "+rs2.getString("cuisine"));
                    System.out.println("Price: "+rs2.getString("item_prize"));
                    System.out.println("-------------------------");
                
            }
            
            
        }
        
        if(found==0)
        {
            System.out.println("\n--------------");
            System.out.println("Cart is Empty");
            System.out.println("--------------");
            return;
        }
        while(true)
        {
        System.out.println("1.Order          2.Exit");
        int option=0;
        try {
            option=sc.nextInt();
        } catch (Exception e) {
        System.out.println("Invalid option allowed is 1 and 2");
        continue;
       }
       if(option==1)
       {
        
                double price=0.0;
              String itemName=null;
                while(true)
        {
        System.out.print("Item id: ");

        try {
            itemId=sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid item id");
            continue;
        }
        if(!selectItemIds.contains(itemId))
        {
            System.out.println("No such item id");
            continue;
        }
     
    try 
    {
    
         String order="select * from restaurant_menu where item_id=?";
                PreparedStatement pr=conn.prepareStatement(order);
                pr.setInt(1, itemId);
                ResultSet rrs=pr.executeQuery();
                if(rrs.next())
                {
                    
                    System.out.println("-----Selected Dish-------");
                    System.out.println("Item Name: "+rrs.getString("menu_item"));
                    System.out.println("Cuisine: "+rrs.getString("cuisine"));
                    System.out.println("Price: "+rrs.getString("item_prize"));
                    System.out.println("-------------------------");
                     price=Double.parseDouble(rrs.getString("item_prize"));
                     itemName=rrs.getString("menu_item");
                }
              
            } catch (Exception e) {
                e.printStackTrace();
            }
              while(true)
            {
            System.out.print("Quantity: ");
            
            try {
                quantity=sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid quantity");
                continue;
            }
            if(quantity<=0)
            {
                System.out.println("Quantity can't be negative or zero");
                continue;
            }
            break;
        }
            finalAmount =price*quantity;
            storedFinalAmount.add(finalAmount);
                     System.out.println("final amount: "+finalAmount);
         System.out.println(storedFinalAmount);
       
            
              int selection=0;
            while(true)
            {
            System.out.println("1.Add          2.Next");
          
            try
            {
            selection=sc.nextInt();
            }catch(Exception e)
            {
                System.out.println("Invalid option");
                continue;
            }
            break;
        }
        
            if(selection==1)
            {
                continue;
             

            }else if(selection==2)
            {
                System.out.println("breaking");
                break;
                
            }else 
            {
                System.out.println("Invalid option allowed is 1 and 2");

                
            }
        }
        sc.nextLine();
              System.out.println("Address: ");
         String address=null;
            address=sc.nextLine();
Double finalBill=0.0;

          for(Double d:storedFinalAmount)

          { 
        
                    finalBill+=d;
        
           
          }
     
          
       

        
while(true)
{
        System.out.println("=====PAYMENT=====");
        System.out.println("Amount: "+finalBill);
        System.out.println("1.UPI\n2.Card\n3.Cash on Delivery");
        int paymentMode=0;
        try {
            paymentMode=sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid option selected");
            continue;
        }
        String paymentStatus="COD(Unpaid)";
        
     if(paymentMode==1||paymentMode==2||paymentMode==3)
     {
        if(paymentMode==1||paymentMode==2)
            paymentStatus="Online(Paid)";
        try {
            String order="insert into appOrders(res_id,cus_mobno,item_id,address,payment_status,prize)values(?,?,?,?,?,?)";
            PreparedStatement ps1=conn.prepareStatement(order);
            ps1.setInt(1, resId);
            ps1.setString(2,CustomerVerification.mob);
            ps1.setInt(3,itemId);
            ps1.setString(4,address);
            ps1.setString(5,paymentStatus);
            ps1.setDouble(6, finalAmount);
           ps1. executeUpdate();

            String history="insert into appHistory(item_name,amount,paymentstatus)values(?,?,?)";
            PreparedStatement ps2=conn.prepareStatement(history);
            ps2.setString(1, itemName);
            ps2.setDouble(2, finalAmount);
            ps2.setString(3, paymentStatus);
            int rows=ps2.executeUpdate();

            
            if(rows>0)
            {
                System.out.println("Order Placed Successfully");
                return ;
            }else 
            {
                System.out.println("Unable to place the order");
                return ;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    


     }else 
     {
        System.out.println("Invalid option selected");
        continue;
     }
    }


    



        


       }else if(option==2)
       {
        return ;
       }else 
       {
        System.out.println("Invalid option allowed is 1 and 2");
        continue;
       }
    }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
            
    }
   
}


