package services;
import java.util.*;
import java.sql.*;

public class RestaurantDashboard {
    Scanner sc;
    Connection conn;
    public RestaurantDashboard(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
    int flag=0;
    ArrayList <Integer>fetchedItemId=new ArrayList<>();
    String column;
    public void update()
    {
        int itemID=0;
        while (true) {
            System.out.print("Item id:");
            try {
                itemID=sc.nextInt();
            } catch (Exception e) {
                System.out.println("Pls enter the valid id");
                continue;
            }
            String updatedText=null;
            Double prize=0.0;
            if(fetchedItemId.contains(itemID))
            {

                System.out.print("Enter the new value to update the selected item: ");
                updatedText=sc.nextLine();
                try {
                    String query="update restaurant_menu set"+column+"=? where item_id=?";
                    PreparedStatement ps=conn.prepareStatement(query);
                    if(flag==0)
                    {
                    ps.setString(1, updatedText);
                    ps.setInt(2, itemID);
                    }else 
                    {
                        prize=Double.parseDouble(updatedText);
                        ps.setDouble(1, prize);
                        ps.setInt(2, itemID);
                    }
                    int rows=ps.executeUpdate();
                    if(rows>0)
                    {
                        System.out.println("Item Name Updated Successfully");
                    }else
                    {
                        System.out.println("Unable to add the item Name");
                    }

                } catch (Exception e) {
                   e.printStackTrace();
                }


            }
            

            
        }
    }
    public void updateItem()
    {
        int resId=0;
          try {
                String query="select res_id from restaurant_registration where mob_no=?";
                PreparedStatement ps=conn.prepareStatement(query);
                ps.setString(1, CustomerVerification.mob);
                ResultSet rs=ps.executeQuery();
                if(rs.next())
                {
                    resId=rs.getInt("res_id");
                }
                
            } catch (Exception e) {
              e.printStackTrace();
            }
        
        while (true) {
          
try {
     String query="select * from restaurant_menu where res_id=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, resId);
            ResultSet rs=ps.executeQuery();
            System.out.printf("%-6s %-25s %-20s %10s%n",
        "ID", "ITEM NAME", "CUISINE", "PRICE");

System.out.println("----------------------------------------------------------");
while (rs.next()) {
fetchedItemId.add(rs.getInt("item_id"));

    int itemId = rs.getInt("item_id");
    String itemName = rs.getString("menu_item");
    String cuisine = rs.getString("cuisine");
    double price = rs.getDouble("item_price");

    System.out.printf("%-6d %-25s %-20s ₹%9.2f%n",
            itemId, itemName, cuisine, price);
}

System.out.println("==========================================================");

} catch (Exception e) {
    e.printStackTrace();
}
           
            System.out.println("1.Update name        2.Update Prize        3.Update Cuisine        4.Exit");
            int choice=0;
            try {
                choice=sc.nextInt();
            } catch (Exception e) {
                 System.out.println("Pls choose the valid option [eg:4 for Exit]");
                 continue;
            }
           if(choice==1)
           {

            column="menu_item";
            update();
            
          
           }else if(choice==2) 
           {
            flag=1;
            column="item_prize";
            update();
            
           }else if(choice==3)
           {
            column="cuisine";
           
           }else
           {
             System.out.println("Invalid option selected");
            continue;
           }
            
        }
    }
    public void removeItem()
    {
        while(true)
        {
        System.out.print("Item id: ");
        int itemId=0;
        try {
            itemId=sc.nextInt();
        } catch (Exception e) {
            System.out.println("Pls enter the valid item id");
            continue;
        }
        try {
            String query="delete from restaurant_menu where item_id=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, itemId);
            int rows=ps.executeUpdate();
            if(rows>0)
            {
                System.out.println("Item removed Successfully");

            }else 
        {
            System.out.println("Unable to remove the item");
        }
        } catch (Exception e) {
        e.printStackTrace();
        }
    }
    }
    public void addItem()
    {
        System.out.print("Item name: ");
        String itemName=null;
        double prize=0.0;
        
        itemName=sc.nextLine();
        System.out.print("Cuisine type: ");
        String cuisine=sc.nextLine();
        while(true)
        {
        try 
        {
        System.out.print("Prize: ");
        prize=sc.nextDouble();
        }catch(Exception e)
        {
            System.out.println("Pls enter the valid amount");
            continue;
        }
        if(prize<=0)
        {
            System.out.println("Prize can't be negative or zero");
            continue;
        }
        break;
    }
    int resId=0;
    try {

        String query="select res_id from restaurant_registration where mob_no=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, CustomerVerification.mob);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
            resId=rs.getInt("res_id");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    try {
        String query="insert into restaurant_menu(restaurant_id,menu_item,item_prize,cuisine)values(?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, resId);
        ps.setString(2, itemName);
        ps.setDouble(3, prize);
        ps.setString(4, cuisine);
        int rows=ps.executeUpdate();
        if(rows>0)
        {
            System.out.println("Item added Successfully");
        }else 
        {
            System.out.println("Unable to add the item");
        }

    } catch (Exception e) {
       e.printStackTrace();
    }

        
    }
    public void manageMenu()
{
    while(true)
    {
    System.out.println("1.View Menu          2.Exit");
    int choice=0;
    try {
        choice=sc.nextInt();
    } catch (Exception e) {
        System.out.println("Pls enter the valid option [eg:2 for Exit]");
        continue;
    }
    
if(choice==1)
{
    while(true)
    {
    System.out.println("1.Add Item        2.Remove Item        3.Update item        4.Exit");
    int  ch=0;
    try {
        ch=sc.nextInt();
        sc.nextLine();
    } catch (Exception e) {
        System.out.println("Pls choose the valid option [eg:4 for Exit]");
        continue;
    }
    switch (ch) {
        case 1:
            addItem();
            
            break;
        case 2:
            removeItem();
            break;
        case 3:
            updateItem();
            break;
    
        default:
            break;
    }
}
}else if(choice==2){
    return ;
}else 
{
    System.out.println("Pls enter the valid option [eg:2 for Exit]");
    continue;
}

}
}
    public void restaurantMenu()
    {
        while(true)
        {
        System.out.println("===== RESTAURANT DASHBOARD =====\r\n" + //
                        "\r\n" + //
                        "1. Manage Menu\r\n" + //
                        "2. New Orders\r\n" + //
                        "3. Current Orders\r\n" + //
                        "4. Order History\r\n" + //
                        "5. Update Restaurant\r\n" + //
                        "6. Notifications\r\n" + //
                        "7. Logout");
                        int choice=0;
                        try {
                            choice=sc.nextInt();
                        } catch (Exception e) {
                            System.out.println("Pls choose the valid option [eg:7 for Logout]");
                            continue;
                        }
           switch (choice) {
            case 1:
                manageMenu();
                
                break;
           
            default:
                break;
           }
                    }
    }
    
}
