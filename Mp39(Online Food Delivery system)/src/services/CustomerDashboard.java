package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerDashboard {
    Scanner sc;
    Connection conn;
    public CustomerDashboard(Scanner sc,Connection conn)
    {
        this.sc=sc;
        this.conn=conn;
    }
    public void searchRestaurant()
    {
        ArrayList<Integer> itemIds=new ArrayList<>();
        int resId=0;
        System.out.println("Restaurnat name: ");
        String search=sc.nextLine();
        try {
            String query="select * from restaurant_registration where res_name like ? ";

            PreparedStatement ps=conn.prepareCall(query);
            ps.setString(1,search+"%");
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                resId= rs.getInt("res_id");
                
                  System.out.println("Restaurant ID : " +resId);
                 System.out.println("Restaurant: " + rs.getString("res_name"));
    System.out.println("Location  : " + rs.getString("address"));
    System.out.println("----------------------------");
                
            }else 
            {
                System.out.println("Unable to find the Restaurant");
               
                 String query2="select * from restaurant_registration where res_name like ? ";

            PreparedStatement ps2=conn.prepareCall(query2);
            ps2.setString(1,"%"+search+"%");
            ResultSet rs2=ps2.executeQuery();
if(rs2.next())
    {
         System.out.println("-----Similar Results-----");
          System.out.println("\nRestaurant ID : " + rs.getInt("res_id"));
                 System.out.println("Restaurant: " + rs.getString("res_name"));
    System.out.println("Location  : " + rs.getString("address"));
    System.out.println("----------------------------");
                

    }
        
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
    break;       }   
    if(ch==1)
        {
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
                      System.out.printf("| %-10s | %-10.1f | %-8s | %-7d |%n",
            rr.getString("menu_item"),
            rr.getDouble("item_prize"),
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
            System.out.println("Enter the item Id to place Order: ");
            try
            {
            itemId=sc.nextInt();
            }catch(Exception e)
            {
                System.out.println("Pls enter the valid id only");
                continue;
            }
          
        
        if(itemIds.contains(itemId))
        {
            //further process;
        }else 
        {
            System.out.println("No such item id");
            continue;
        }
    }
        
        }     
            


            
        } catch (Exception e) {
         e.printStackTrace();
        }

    }
    public  void searchFood()
    {
        System.out.print("Name of Food item/Cuisine: ");
        String foodName=sc.nextLine();
int item_id=0;
int res_id=0;
String item_name=null,item_prize=null,res_name=null;
        try {
            String query="select * from restaurant_menu where menu_item like ? or cuisine like ?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, "%"+foodName+"%");
            ps.setString(2, "%"+foodName+"%");
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                item_id=rs.getInt("item_id");
                item_prize=rs.getString("item_prize");
                res_id=rs.getInt("res_id");
                String resName="select res_name from restaurant_registration where res_id=?";
                PreparedStatement pp=conn.prepareStatement(resName);
                pp.setInt(1, res_id);
                ResultSet rr=pp.executeQuery();
                if(rr.next())
            {
                resName=rr.getString("res_name");
            }
                item_name=rs.getString("menu_item");
                System.out.println("-----Tasty Dishes-------");
                System.out.println("Dish Name: "+item_name);
                System.out.println("Prize: "+item_prize);
                System.out.println("Restaurant Name: "+res_name);
                System.out.println("Item Id: "+item_id);
                System.out.println("-----------------------");

            }else 
            {
                System.out.println("No such food item found");
                return;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void viewFoodMenu()
    {
        try {
            int found=0;
            String query="select * from restaurant_registration";
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            System.out.println("+------------+------------+----------+---------+");
System.out.println("| menu_item  | item_prize | cuisine  | item_id |");
System.out.println("+------------+------------+----------+---------+");
            while (rs.next()) {
                found=1;
                 System.out.printf("| %-10s | %-10.1f | %-8s | %-7d |%n",
            rs.getString("menu_item"),
            rs.getDouble("item_prize"),
            rs.getString("cuisine"),
            rs.getInt("item_id"));

                
            }
            System.out.println("+------------+------------+----------+---------+");
            if(found==0)
            {
                System.out.println("No Food items yet");
                return ;
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    public void placeOrder()
    {
        
        int choice=0;
        while(true)
        {
        System.out.println("1.Search          2.Menu          3.Exit ");

        try {
            choice=sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid choice");
            continue;
        }

        
        break;
        
    }
       if(choice==1)
       {
        searchFood();

       }else if(choice==2)
       {
        viewFoodMenu();
       }else 
       {
        return;
       }

    }

    public void cusMenu()
    {
        System.out.println("=================================\r\n" + //
                        "        CUSTOMER DASHBOARD\r\n" + //
                        "=================================\r\n" + //
                        "\r\n" + //
                        "1. Search Restaurants\r\n" + //
                        "2. View Nearby Restaurants\r\n" + //
                        "3. View Menu\r\n" + //
                        "4. My Cart\r\n" + //
                        "5. Place Order\r\n" + //
                        "6. Track Current Order\r\n" + //
                        "7. My Orders\r\n" + //
                        "8. Saved/Favourite Restaurants\r\n" + //
                        "9. Notifications\r\n" + //
                        "10. My Profile\r\n" + //
                        "11. Logout");
      int choice=0;
      while(true)
      {
      try {
        choice=sc.nextInt();
      } catch (Exception e) {
       System.out.println("Pls choose the valid option [eg:11 for Logout]");
       continue;
      }
   switch (choice) {
    case 1:
        searchRestaurant();

        
        break;
   
    default:
        break;
   }
    }
}
}
