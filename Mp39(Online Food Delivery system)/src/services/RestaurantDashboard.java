package services;
import java.util.*;

import threads.deliveryPartnerAssignmentThread;
import threads.Notifications.RestaurantNotification;

import java.sql.*;

public class RestaurantDashboard {
    Scanner sc;
    Connection conn;
    CommonServices commObj;
    public RestaurantDashboard(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
        commObj=new CommonServices(conn, sc);
    }
    int flag=0;
    ArrayList <Integer>fetchedItemId=new ArrayList<>();
    String column;
    public void viewMenu()
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
                    System.out.println("resid: "+resId);
                }
                
            } catch (Exception e) {
                    
              e.printStackTrace();
        
            }
        
          
try {
     String query="select * from restaurant_menu where res_id=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, resId);
            ResultSet rs=ps.executeQuery();
            System.out.printf("%-6s %-25s %-20s %10s%n",
        "ID", "ITEM NAME", "CUISINE", "PRICE");

System.out.println("-------------------------------------------------------------------------");
while (rs.next()) {
fetchedItemId.add(rs.getInt("item_id"));

    int itemId = rs.getInt("item_id");
    String itemName = rs.getString("menu_item");
    String cuisine = rs.getString("cuisine");
    double price = rs.getDouble("item_prize");

    System.out.printf("%-6d %-25s %-20s ₹%9.2f%n",
            itemId, itemName, cuisine, price);
}

System.out.println("==========================================================================");

} catch (Exception e) {
    e.printStackTrace();
}
    
}
    public void update()
    {
        int itemID=0;
        while (true) {
            System.out.print("Item id:");
            try {
                itemID=sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Pls enter the valid id");
                continue;
            }
            break;
        }
            String updatedText=null;
            Double prize=0.0;
            if(fetchedItemId.contains(itemID))
            {

                System.out.print("Enter the new value to update the selected item: ");
                updatedText=sc.nextLine();
                try {
                    String query="update restaurant_menu set "+column+"=? where item_id=?";
                  
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
                        System.out.println("Selected Field Updated Successfully");
                    }else
                    {
                        System.out.println("Unable to add the item Name");
                    }

                } catch (Exception e) {
                   e.printStackTrace();
                   sc.nextLine();
            
                }


            }else 
            {
                System.out.println("No Such Item Id");
                
            }
            

            
        
    }
    public void updateItem()
    {
       
           while(true)
           {
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
            update();
           
           }else if(choice==4)
            {
                return;
            }else
           {
             System.out.println("Invalid option selected");
            continue;
           }
            
        }
    }
    public void removeItem()
    {
         int itemId=0;
        while(true)
        {
        System.out.print("Item id: ");
       
        try {
            itemId=sc.nextInt();
        } catch (Exception e) {
            System.out.println("Pls enter the valid item id");
            continue;
        }
        break;
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
        String query="insert into restaurant_menu(res_id,menu_item,item_prize,cuisine)values(?,?,?,?)";
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
        sc.nextLine();
    } catch (Exception e) {
        System.out.println("Pls enter the valid option [eg:2 for Exit]");
        continue;
    }
    
if(choice==1)
{
     viewMenu();
   
    
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
             viewMenu();
            addItem();
            
            break;
        case 2:
             viewMenu();
            removeItem();
            break;
        case 3:
             viewMenu();
            updateItem();
            break;
        case 4:
            return;
    
        default:
            System.out.println("Invalid choice Pls choose the Valid Option");
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
public void viewOrders()
{
    String fetcheditemName=null;
    int flag=0;
    ArrayList<Integer>orderId=new ArrayList<>();
    int res_id=0;
    try {
        String query="select res_id from restaurant_registration where mob_no=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, CustomerVerification.mob);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
            res_id=rs.getInt("res_id");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
double final_amount=0.0;
    int quantity=0;
    try {
        String defaultStatus="ordered";
        String query="select * from appOrders where res_id=? and order_status =?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, res_id);
        ps.setString(2,defaultStatus);
        ResultSet rs=ps.executeQuery();
    
     
        
System.out.printf("%-15s %-15s %-15s %-20s %-10s %-10s %-10s%n",
        "Item Name", "Mobile", "Address", "Payment", "Price", "Quantity", "Order ID");

System.out.println("----------------------------------------------------------------------------------------------------------");

int found=0;
int fetchedItemId=0;

        while (rs.next()) {
            found=1;
            quantity=rs.getInt("quantity");
            final_amount=rs.getDouble("prize");

            fetchedItemId=rs.getInt("item_id");
            
            try {
                String query2="select menu_item from restaurant_menu where item_id=?";
                PreparedStatement ps2=conn.prepareStatement(query2);
                ps2.setInt(1, fetchedItemId);
                ResultSet rs2=ps2.executeQuery();
                if(rs2.next())
                {
                    fetcheditemName=rs2.getString("menu_item");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
                orderId.add(rs.getInt("order_id"));
            System.out.printf("%-15s %-15s %-15s %-20s %-10.2f %-10d %-10d%n",
            fetcheditemName,
            rs.getString("cus_mobNo"),
            rs.getString("address"),
            rs.getString("payment_status"),
            rs.getDouble("prize"),
            rs.getInt("quantity"),
            rs.getInt("order_id"));

            
        }
        if(found==0)
        {
            System.out.println("No orders yet");
            return;
        }

    } catch (Exception e) {
    e.printStackTrace();
    }
    while( true)
    {
    System.out.println("\n1.Accept Order          2.Reject        3.Exit");
    int option=0;
     try {
        option=sc.nextInt();
     } catch (Exception e) {
        System.out.println("Pls enter the valid option no.");
        continue;
     
    }
    if(option==1)
        {
            acceptOrder(orderId,flag,res_id,fetcheditemName,quantity,final_amount);

        }else if(option==2)
        {
            flag=1;
            acceptOrder(orderId, flag, res_id, fetcheditemName, quantity, final_amount);
        }else if(option==3)
        {
            return;
        }
        else  
        {
            System.out.println("Pls enter the valid option no. allowed is 1 and 2");
            continue;
        }
    }
}
public void acceptOrder(ArrayList<Integer> orderId,int flag,int res_id,String itemName,int quantity,double final_amount)
{
    // int orderID;
    // orderID=orderId;
       int enteredOrderId=0;
          String status=null;
 while(true)
 {
    System.out.print("Order id: ");
 
    try {
        enteredOrderId=sc.nextInt();
    } catch (Exception e) {
     System.out.println("Pls enter the valid order id");
     continue;
    }
    if(orderId.contains(enteredOrderId))
    {
     
        try{
        String query="update appOrders set order_status=? where order_id=? ";
     PreparedStatement ps=conn.prepareStatement(query);
     if(flag==0)
     {
            status="accepted";
     ps.setString(1, status);
     ps.setInt(2, enteredOrderId);
     }else 
     {
        status="rejected";
        ps.setString(1, status);
        ps.setInt(2, enteredOrderId);
     }

     int rows=ps.executeUpdate();
     if(rows>0 && status.equals("accepted"))
        {
          
           System.out.println("Order accepted Successfully");
      break;
        }else if(rows>0 && status.equals("rejected")) 
            {
                System.out.println("Order rejected Successfully");
                
              break;
            } else 
                {
                    System.out.println("Something went wrong");
                    break;
                }  
    }catch(Exception e)
        {
            e.printStackTrace();
        }
       

    }else 
    {
        System.out.println("No such Order id");
        return;
    }
}
try {
    String query="insert into appOrderHistory(order_id,status,res_id,item_name,quantity,final_amount)values(?,?,?,?,?,?)";
    PreparedStatement ps=conn.prepareStatement(query);
    ps.setInt(1, enteredOrderId);
    ps.setString(2, status);
    ps.setInt(3, res_id);
    ps.setString(4,itemName );
    ps.setInt(5, quantity);
    ps.setDouble(6, final_amount);

    ps.executeUpdate();
    return;


} catch (Exception e) {
    e.printStackTrace();


}
}

public void viewOrderHistory()
{
    int res_id=0;
    try {
        String query="select res_id from restaurant_registration where mob_no=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, CustomerVerification.mob);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
            res_id=rs.getInt("res_id");
        }
    } catch (Exception e) {
e.printStackTrace();
    }
    try {
        String query="select * from appOrderHistory where res_id=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, res_id);
        ResultSet rs=ps.executeQuery();
   System.out.printf(
    "%-10s %-15s %-20s %-10s %-15s%n",
    "order_id", "status", "item_name", "quantity", "final_amount"
);
String result = "-".repeat(75);
System.out.println(result);

   int found=0;
        while (rs.next()) {
            found=1;
         System.out.printf(
        "%-10d %-15s %-20s %-10d %-15.2f%n",
        rs.getInt("order_id"),
        rs.getString("status"),
        rs.getString("item_name"),
        rs.getInt("quantity"),
        rs.getDouble("final_amount")
    );

            
        }
        if(found==0)
        {
            System.out.println("No Order History yet");
            return;
        }

    } catch (Exception e) {
     e.printStackTrace();
    }
}
public void assignDeliveryPartner(ArrayList orderId)
{
    int order_id=0;
   while(true)
   {
    System.out.print("Order Id: ");
    try {
        order_id=sc.nextInt();
        
    } catch (Exception e) {
      System.out.println("Invalid order id");
      continue;
    }
    if(!orderId.contains(order_id))
    {
        System.out.println("No Such Order id");
    }else {
      
    
    deliveryPartnerAssignmentThread delObj=new deliveryPartnerAssignmentThread(conn,order_id);
    System.out.println("starting thread");
    
    delObj.setDaemon(true);
    delObj.start();
    
    }
    
}
    
  
   
}
public void acceptedOrders()
{
    ArrayList <Integer>orderIds=new ArrayList<>();
    String status="accepted";
    try {
        String query="select * from appOrders where order_status=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, status);
        ResultSet rs=ps.executeQuery();
               
System.out.printf("%-15s %-15s %-15s %-20s %-10s %-10s %-10s%n",
        "Item Name", "Mobile", "Address", "Payment", "Price", "Quantity", "Order ID");

System.out.println("---------------------------------------------------------------------------------------------------------");
int found=0;
String item_name=null;
        while (rs.next()) {
             
              found=1;
              orderIds.add(rs.getInt("order_id"));
              
             int item_id=   rs.getInt("item_id");
                try {
                String query2="select menu_item from restaurant_menu where item_id=?";
                PreparedStatement ps2=conn.prepareStatement(query2);
                ps2.setInt(1, item_id);
                ResultSet rs2=ps2.executeQuery();
                if(rs2.next())
                {
                    item_name=rs2.getString("menu_item");

                }
             } catch (Exception e) {
                e.printStackTrace();
             }
            System.out.printf("%-15s %-15s %-15s %-20s %-10.2f %-10d %-10d%n",
            item_name,
            rs.getString("cus_mobNo"),
            rs.getString("address"),
            rs.getString("payment_status"),
            rs.getDouble("prize"),
            rs.getInt("quantity"),
            rs.getInt("order_id"));

            
        }
        if(found==0)
        {
            System.out.println("No Accepted Orders Yet");
            return;
        }
           int choice=0;
        while (true) {
            
        
        System.out.println("\n1.Assign Delivery Partner          2.Exit");
     
        try {
            choice=sc.nextInt();
        } catch (Exception e) {
            System.out.println("Pls enter the valid option");
            continue;
        }
        if(choice==1)
        {
            assignDeliveryPartner(orderIds);
        }else if(choice==2)
        {
            return ;
        }else
        {
            System.out.println("Invalid option selected");
            continue;
        }
    }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public void restaurantMenu()
    {
        RestaurantNotification resObj=new RestaurantNotification(conn);
                  resObj.setDaemon(true);
                     resObj.start();
        while(true)
        {
        System.out.println("===== RESTAURANT DASHBOARD =====\r\n" + //
                        "\r\n" + //
                        "1. Manage Menu\r\n" + //
                        "2. View Orders\r\n" + //
                        "3.Accepted Orders\n"+
                        "4. Order History\r\n" + //
                        
                    
                        "5.View Profile\n"+
                        "6. Logout");
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
            case 2:
                viewOrders();
                break;
            
            case 3:
                acceptedOrders();
                break;
            case 4:
                viewOrderHistory();
                break;
            case 5:
                commObj.myProfile();
                break;
            case 6:
                return ;

                           
            default:
                break;
           }
                    }
    }
    
}
