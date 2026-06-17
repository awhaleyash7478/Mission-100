import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class PaymentThread extends Thread {
    Order o;

    PaymentThread(Order obj) {
        o = obj;

    }

    public void run() {

        System.out.println("Processing payment");
        o.status = "Paid";
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Payment successfull");

    }
}

class Order {

    Connection conn;
    Order(Connection conn)
    {
        
        this.conn=conn;

    }
    final int max = 100;
    Scanner sc = new Scanner(System.in);
    int productId[] = new int[max];
    double productPrice[] = new double[max];
    int proStock[] = new int[max];
    String status;
    int hisOrderID[] = new int[max];

    void addProduct() {
        int proId = 0, stock = 0;
        double price = 0.0;
        String proName = null;

        try {
            System.out.println("Enter the product id:");
            proId = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the  product name:");
            proName = sc.nextLine();
            System.out.println("Enter the product price:");
            price = sc.nextDouble();
            System.out.println("Enter the product stock:");
            stock = sc.nextInt();
            int i = 0;
            while (proId > 0) {

                productId[i] = proId;
                productPrice[i] = price;
                proStock[i] = stock;
                break;

            }
        } catch (Exception e) {
            System.out.println("Invalid entry");

        }
        try {
            String query = "insert into ordertable(proId,proName,stock,price)values(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, proId);
            ps.setString(2, proName);
            ps.setInt(3, stock);
            ps.setDouble(4, price);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Product added successfully");
            } else {
                System.out.println("Unable to add product");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void viewProducts() {
        try {
            String query = "select*from ordertable ";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            int found = 0;
            while (rs.next()) {

                System.out.printf(
                        "%-8d %-15s %-15s %.2f%n",
                        rs.getInt("proID"),
                        rs.getString("proName"),
                        rs.getString("stock"),
                        rs.getDouble("price"));
                found = 1;
            }
            if (found == 0)
                System.out.println("No product found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void searchProduct() {
        int searchID = 0;
        try {
            System.out.println("Enter the product id to search:");
            searchID = sc.nextInt();
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid entry");
        }
        try {
            String query = "select * from ordertable where proId=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, searchID);
            ResultSet rs = ps.executeQuery();
            int found = 0;
            while (rs.next()) {
                System.out.printf(
                        "%-8d %-15s %-15s %.2f%n",
                        rs.getInt("proID"),
                        rs.getString("proName"),
                        rs.getInt("stock"),
                        rs.getDouble("price"));
                found = 1;

            }
            if (found == 0)
                System.out.println("No product found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void placeOrder() {
        int tempproId = 0;
        int quantity = 0;
        double totalPrice = 0.0;

        try {
            System.out.println("Enter the product id:");
            tempproId = sc.nextInt();

        } catch (Exception e) {
            System.out.println("Invalid entry");
        }
 
        try {
            int tempOrderID = 100;

            for (int i = 0; i < max; i++) {
                 
                  
                    System.out.println("Enter the product quantity:");
                    quantity = sc.nextInt();
                    String query = "SELECT stock FROM ordertable WHERE proID=? AND stock>=?";
PreparedStatement ps = conn.prepareStatement(query);

ps.setInt(1, tempproId);
ps.setInt(2, quantity);

ResultSet rs = ps.executeQuery();

if (rs.next()) {
    System.out.println("Order can be placed");
} else {
    System.out.println("Insufficient stock");
    break;
}
                    
                    proStock[i] -= quantity;
                    totalPrice = productPrice[i] * quantity;
                    String query1="update ordertable set stock =? where proId=?";
                    PreparedStatement ps1=conn.prepareStatement(query1);
                    ps1.setInt(1, proStock[i]);
                    ps1.setInt(2, tempproId);
                    ps1.executeUpdate();
  
                    String query2 = "insert into placeOrder (proID,quantity,totalAmount,status)values(?,?,?,?)";
                    PreparedStatement ps2 = conn.prepareStatement(query2);
                    
                    ps2.setInt(1, tempproId);
                    ps2.setInt(2, quantity);
                    ps2.setDouble(3, totalPrice);
                  
                
                    PaymentThread p = new PaymentThread(this);
                    p.start();
                    p.join();

                    ps2.setString(4, status);
                   int rows2=ps2.executeUpdate();
if(rows2>0)
{
    System.out.println("Order placed");
    break;
}else 
{
    System.out.println("cant place order");
    break;
}

                
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void viewOrders() {

        try {
            String query = "select*from placeOrder ";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            int found = 0;
            while (rs.next()) {

                System.out.printf(
                        "%-8d %-15s %-15s %.2f%n",
                        rs.getInt("OrderID"),
                        rs.getInt("proId"),
                        rs.getInt("quantity"),
                        rs.getDouble("totalAmount"),
                        rs.getString("status"));
                found = 1;
            }
            if (found == 0)
                System.out.println("No product found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cancelOrders() {
        int cancelID = 0;
        try {
            System.out.println("Enter the order id:");
            cancelID = sc.nextInt();

        } catch (Exception e) {
            System.out.println("Invalid entry");
        }
        try {
            
      
             
                   
                    String query = "delete from placeOrder where orderID=?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setInt(1, cancelID);
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        System.out.println("ordere cancelled successfully");
                        
                    }else
                    {
                        System.out.println("Order not found");
                    }
                
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void menu() {
        int ch = 0;
        while (true) {
            try {
                System.out.println(
                        "1.Add product\n2.View Product\n3.Search Product\n4.Place Order\n5.View Orders\n6.Cancel Orders\n7.Exit");
                ch = sc.nextInt();
                sc.nextLine();

            } catch (Exception e) {
                System.out.println("Invalid entry");
            }
            switch (ch) {
                case 1:
                    addProduct();

                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    searchProduct();
                    break;
                case 4:
                    placeOrder();
                    break;
                case 5:
                    viewOrders();
                    break;
                case 6:
                    cancelOrders();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("invalid input");
                    break;
            }
        }
    }
}
class Mp27
{public static void main(String[] args) {

         try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localHost:3306/demodatabase",
                    "root",
                    "Yash@7478");
            System.out.println("connection established successfully");
                Order obj=new Order(conn);
    obj.menu();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }
}
