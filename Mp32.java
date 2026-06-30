import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
class RestaurantAcceptanceThread extends Thread
{
    public void run()
    {
        System.out.println("Preparing");
    }
}
class DeliveryAssignmentThread extends Thread
{
    
    public void run()
    {

    }
}
class  PaymentProcessingThread  extends Thread 
{
   
    Restaurant obj;
    PaymentProcessingThread(Restaurant obj)
    {
        this.obj=obj;
    }
    public void run()
    {
        try 
    {
        System.out.println("Processing payment...");
        Thread.sleep(2000);
        System.out.println("Payment Successful");
        Thread.sleep(2000);
        obj.status = "Paid";
    }catch(InterruptedException e)
    {
        e.printStackTrace();
    }

    }
}
class  RestaurantAcceptanceThread extends Thread 
{


}

class Restaurant 
{
    Connection conn;
    Restaurant(Connection conn)
    {
        this.conn=conn;
    }
    Scanner sc=new Scanner(System.in);

  String status;
    void registerCustomer()
    {
        int cusId=0;
        String cusName=null;
        int mobNo=0;
        String cusAdd=null;

     
    try
    {
        System.out.println("Enter the customer id:");
        cusId=sc.nextInt();
        System.out.println("Enter the customer name:");
        cusName=sc.nextLine();
        System.out.println("Enter the customer mobile number:");
        mobNo=sc.nextInt();
        System.out.println("Enter the customer address:");
        cusAdd=sc.nextLine();
    }catch(Exception e)
    {
        System.out.println("Invalid entry");
    }
    try 
    {
        String query="insert into customers(customer_id,customer_name,phone,address)values(?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, cusId);
        ps.setString(2, cusName);
        ps.setInt(3, mobNo);
        ps.setString(4, cusAdd);
        int rows=ps.executeUpdate();
        if(rows>0)
        {
            System.out.println("Customer registered successfully");
        }
        else 
        {
            System.out.println("Unable to register the customer");
        }
    }catch(Exception e)
    {
      e.printStackTrace();
    }
    }
    void viewCustomers()
    {
        try 
        {
            int found=0;
            String query="select * from customers where customers";
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                                   System.out.printf(
                        "%-8d %-15s %-8d %-15s%n",
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getInt("phone"),
                        rs.getString("address"));
                found = 1;

                
            }
            if(found==0)
                System.out.println("No customers found");


        }catch(Exception e)
        {
        e.printStackTrace();
        }

    }
    void placeOrder()
    {
        String exit=null;
        int order_item_id=0;
        
int quantity=0;
double subtotal=0.0,tempSubTotal=0.0;
double tempPrice=0.0;
int cusId=0;
int tempResID=0;
int tempQuantity=0;
try 
        {
            
            while (true) {
System.out.println("Type 'exit' for exiting:");
exit=sc.nextLine();
if(exit.equals("exit"))
{
    System.out.println("Thankyou for ordering");

    return;
}
                String query="select * from menu";
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                while (rs.next()) {
                    System.out.println("-----Food Items-----");
                                  System.out.printf(
    "%-8d %-15d %-15s %-10.2f %-10d%n",
    rs.getInt("item_id"),
    rs.getInt("restaurant_id"),

    rs.getString("item_name"),
    rs.getDouble("price"),
    rs.getInt("stock"));
tempResID=rs.getInt("restaurant_id");
tempQuantity=rs.getInt("stock");
    tempPrice=rs.getInt("price");
                        System.out.println("----xxxxx----");


                    
                }
                
            
int tempFound=0;
            System.out.println("Enter the item id:");
            order_item_id=sc.nextInt();
            
                String  checkQuery="select * from menu where item_id=?";
                PreparedStatement ps1=conn.prepareStatement(checkQuery);
                ps1.setInt(1, order_item_id);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next())
                {
                    
                
              



            
          
            System.out.println("Enter the quantity:");
            quantity=sc.nextInt();
            if(quantity>tempQuantity)
            {
                System.out.println("out of stock");
                break;
            }
            tempSubTotal=quantity*tempPrice;
            System.out.println("Total Amount:"+subtotal);
            System.out.println("Enter the the subtotal:");
            subtotal=sc.nextDouble();
            System.out.println("Enter the customer id:");
            cusId=sc.nextInt();
            status="Ordered";
                }else 
                {
                    System.out.println("Item not exists");
return;
                }
            }
         
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        try 
        {
            String query="insert into orders(customer_id,restaurant_id,total_amount,status)values(?,?,?,?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, cusId);
            ps.setInt(2, tempResID);
            ps.setDouble(3, subtotal);
            ps.setString(4, status);


            int rows=ps.executeUpdate();
           if(rows>0)
           {
            System.out.println("Order placed successfully");
           }else 
           {
            System.out.println("Something went wrong");
           }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    void trackOrder()
    {


    }

    void menu()
    {
        final int pass=7478;
        while(true){
            System.out.println("1.Admin Panel\n2.Customer Panel\n3.Exit");
            int ch=sc.nextInt();
            if(ch==1)
            {
                try 
                {
                System.out.println("Enter the password:");
                int tempPass=sc.nextInt();
                if(tempPass==pass)
                {
                     adminMenu();
                }
                else 
                {
                    System.out.println("Invalid password");
                    return;
                }
            }catch(Exception e)
            {
                System.out.println("invalid entry");
            }
        }
        else if (ch==2)
        {
            int choice=0;
            System.out.println("1.Register Customers\n2.Place Order\n3.Track Order\n4.View Order History\n5.View Customers\n6..Exit");
            try {
            choice=sc.nextInt();
            }catch(Exception e)
            {
                System.out.println("Invalid entry");
            }
            switch (choice) {
                case 1:
                    registerCustomer();
                    
                    break;
                case 2:
                    placeOrder();
                    break;
                case 3:
                    trackOrder();
                    break;
                case 4:
                    //viewOrderHistory();
                    break;
                case 5:
                    viewCustomers();
                    break;
                case 6:
                    return;

            
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
        else if(ch==3)
        {
            return;
        }
        else 
        {
            System.out.println("Invalid entry");
        }
        }
    }
    void addRestaurant()
    {
             int resId=0;
    String resName=null;
    String  resLocation=null;
        try 
        {
            System.out.println("Enter the Restaurant id:");
            resId=sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the Restaurant Name:");
            resName=sc.nextLine();
            System.out.println("Enter the Restaurant Location:");
            resLocation=sc.nextLine();
            
        }
        catch(Exception e)
        {
            System.out.println("Invalid entry");
        }
        try 
        {
            String query="insert into restaurants (restaurant_id ,restaurant_name,location)values(?,?,?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, resId);
            ps.setString(2, resName);
            ps.setString(3, resLocation);
            int rows=ps.executeUpdate();
            if(rows>0)
            {
                System.out.println("Restaurant registered successfully");
            }else{
                System.out.println("Something went wrong");
            }
            
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    void viewRestaurant()
    {
        try 
        {
            int found=0;
            String query="select * from restaurants";
          PreparedStatement ps=conn.prepareStatement(query);
          ResultSet rs=ps.executeQuery();
          
          while (rs.next()) {
                 System.out.printf(
                        "%-8d %-15s %-15s %.2f%n",
                        rs.getInt("restaurant_id"),
                        rs.getString("restaurant_name"),
                        rs.getString("location"));
                        found=1;
                

            

            
          }
          if(found==0)
          {
            System.out.println("No restaurants registered");
          }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    void addFoodItem()
    {
        int itemID=0;
        int tempResID=0;
        String itemName=null;
        double itemPrice=0.0;
        int stock=0;
        try 
        {
            System.out.println("Enter the Item id:");
             itemID=sc.nextInt();
             System.out.println("Enter the restaurant id:");
             tempResID=sc.nextInt();
             sc.nextLine();
             System.out.println("Enter the item name:");
             itemName=sc.nextLine();
             System.out.println("Enter the item price:");
             itemPrice=sc.nextDouble();
             System.out.println("Enter the amount of stock:");
             stock=sc.nextInt();
            }
            catch(Exception e)
            {
                System.out.println("invalid entry for Food item");
            }
            try 
            {
                String query="insert into menu (item_id,restaurant_id,item_name,price,stock)values(?,?,?,?,?)";
                PreparedStatement ps=conn.prepareStatement(query);
                ps.setInt(1, itemID);
                ps.setInt(2, tempResID);
                ps.setString(3, itemName);
                ps.setDouble(4, itemPrice);
                ps.setInt(5, stock);
                int rows=ps.executeUpdate();
                if(rows>0)
                {
                    System.out.println("Item added successfully");
                }else 
                {
                    System.out.println("Unable to add item");
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
    }
    void ViewFoodItem()
    {
       
        try 
        {
             System.out.println("-----Food Items-----");
        String query="select * from menu";
                PreparedStatement ps=conn.prepareStatement(query);
        ResultSet rs=ps.executeQuery();
        
        int found=0;
        while (rs.next()) {
            

                   System.out.printf(
    "%-8d %-15d %-15s %-10.2f %-10d%n",
    rs.getInt("item_id"),
    rs.getInt("restaurant_id"),
    rs.getString("item_name"),
    rs.getDouble("price"),
    rs.getInt("stock"));
                        System.out.println("----xxxxx----");
                found = 1;
            
            
        }
        if(found==0)
        {
            System.err.println("no items found");
        }
    }catch(SQLException e)
    {
        e.printStackTrace();
    }
    }
    void searchFoodItem()
    {
        int searchID=0,found=0;
        try 
        {
        
            System.out.println("Enter the search id:");
            searchID=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("Invalid entry");
        }
        try{
            String query="select * from menu where item_id=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, searchID);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                System.out.printf(
                        "%-8d %-15d %-15s %.2f %2f%n",
                        rs.getInt("item_id"),
                        rs.getInt("restaurant_id"),
                        rs.getString("item_name"),
                        rs.getDouble("price"),
                    
                    
                        rs.getDouble("stock"));

                found=1;
            }
            if(found==0)
                System.out.println("No  Food item found");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    void addDeliveryPartner()
    {
        int partId=0;
        String partName=null;
        String partStatus="Free";
    
        try 
        {
            System.out.println("Enter the delivery partner id:");
            partId=sc.nextInt();
            System.out.println("Enter the delivery partner name:");
            partName=sc.nextLine();
        }catch(Exception e)

        {
            System.out.println("Invalid entry");

        }
        try 
        {
        String query="insert into delivery_partner(partner_id,partner_name,status)values(?,?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, partId);
        ps.setString(2, partName);
        ps.setString(3, partStatus);
        int rows=ps.executeUpdate();
        if(rows>0)
        {
            System.out.println("Delivery partner added successfully");
        }else 
        {
            System.out.println("Unable to add the partner");
        }
    }catch(SQLException e)
    {
        e.printStackTrace();
    }
}
    void adminMenu()
    {
        while (true) {
            
        
        System.out.println("Welcome to admin panel...");
        int ch=0;
        System.out.println("1.Add Restaurant\n2.View Restaurant\n3.Add Food item\n4.View Food Item\n5.Search food item\n6.Track Order\n7.View Order History\n8.Add Delivery Partner\n9.Exit");
        try 
        {
            ch=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("invalid entry!!!");
        }
        switch (ch) {
            case 1:
                addRestaurant();
                
                break;
            case 2:
                viewRestaurant();
                break;
            case 3:
                addFoodItem();
                break;
            case 4:
                ViewFoodItem();
                break;
            case 5:
                searchFoodItem();
                break;
            case 6:
               // trackOrder();
                break;
            case 7:
                //viewAllOrdersHistory();
                break;
            case 8:
                addDeliveryPartner();
                break;
            case 9:
                return;
            
        
            default:
                System.out.println("Invalid entry...");
                break;
        }
    }
    }
}

public class Mp32 {
        public static void main(String[] args) {
      try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localHost:3306/demodatabase",
                    "root",
                    "Yash@7478");
            System.out.println("connection established successfully");
      Restaurant s=new Restaurant(conn);
      s.menu();
        } catch (Exception s) {
            s.printStackTrace();
        }
    }
    
}
