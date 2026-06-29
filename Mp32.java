import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Restaurant 
{
    Connection conn;
    Restaurant(Connection conn)
    {
        this.conn=conn;
    }
    Scanner sc=new Scanner(System.in);

    int resId;
    String resName;
    String  resLocation;

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
                    //registerCustomer();
                    
                    break;
                case 2:
                    //placeOrder();
                    break;
                case 3:
                    //trackOrder();
                    break;
                case 4:
                    //viewOrderHistory();
                    break;
                case 5:
                    //viewCustomers();
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
    void adminMenu()
    {
        while (true) {
            
        
        System.out.println("Welcome to admin panel...");
        int ch=0;
        System.out.println("1.Add Restaurant\n2.View Restaurant\n3.Add Food item\n4.View Food Item\n5.Search food item\n6.Track Order\n7.View Order History\n8.Exit");
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
