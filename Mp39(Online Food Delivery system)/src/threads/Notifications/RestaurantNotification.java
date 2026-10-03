package threads.Notifications;
import java.sql.*;
import services.*;
public class RestaurantNotification extends Thread {
  Connection conn;
  public RestaurantNotification(Connection conn)
  {
    this.conn=conn;

  }
  public void run()
  {
    System.out.println("thread started");
    String itemName=null;
int res_id=0;
    try {

     
      String query="select res_id from restaurant_registration where mob_no=?";
      PreparedStatement ps=conn.prepareStatement(query);
      ps.setString(1, CustomerVerification.mob);
      // System.out.println("mb: "+CustomerVerification.mob);
      ResultSet rs=ps.executeQuery();

      if(rs.next())
      {
       res_id=rs.getInt("res_id");
      //  System.out.println("res id:"+res_id);
      }
    } catch (Exception e) {
     e.printStackTrace();
    }
    try {
      int defaultValue=1;
      while(true)
      {
      String query="select * from appOrders where res_id=? and notification_id  <>? and notification_id is not null";
      PreparedStatement ps=conn.prepareStatement(query);
      ps.setInt(1,res_id );
      ps.setInt(2, defaultValue);
      ResultSet rs=ps.executeQuery();
      while (rs.next()) {
        int notificationId=rs.getInt("notification_id");
        // System.out.println("notification id: "+notificationId);
        int order_id=rs.getInt("order_id");
        // System.out.println("order id: "+order_id);
    
          
        
        
        int item_id=rs.getInt("item_id");
        // System.out.println("item id: "+item_id);
        String item="select menu_item from restaurant_menu where item_id=?";
        PreparedStatement pr=conn.prepareStatement(item);
        pr.setInt(1, item_id);
        ResultSet rr=pr.executeQuery();
        if(rr.next())
        {
          itemName=rr.getString("menu_item");
            System.out.println("\n╔══════════════════════════════════════╗");
    System.out.println("║          🔔 ORDER NOTIFICATION       ║");
    System.out.println("╠══════════════════════════════════════╣");
    
    System.out.println("║ Item     : " + itemName);

    System.out.println("║ Mobile  : " + rs.getString("cus_mobNo"));
    System.out.println("║  Address : " + rs.getString("address"));
    System.out.println("║ Payment : " + rs.getString("payment_status"));
    System.out.println("║ Price   : ₹" + rs.getString("prize"));
    System.out.println("║ Quantity   : ₹" + rs.getInt("quantity"));
     
    

    System.out.println("╚══════════════════════════════════════╝");
    
    String setNofication="update appOrders set notification_id=null where notification_id=? and order_id=?";
    // int defaultNotificationValue=0;
    PreparedStatement ppp=conn.prepareStatement(setNofication);
 
    ppp.setInt(1, notificationId);
    ppp.setInt(2, order_id);
    ppp.executeUpdate();
     
    // String setNofication="delete from where notification_id=?";
    // int defaultNotificationValue=0;
    // PreparedStatement ppp=conn.prepareStatement(setNofication);
    // ppp.setInt(1, notificationId);
    
    // ppp.executeUpdate();


        }
     
     
      
      }
        //  System.out.println("entering sleep");
        try {
          Thread.sleep(5000);
          // System.out.println("sleep over");
        } catch (Exception e) {
          e.printStackTrace();
        }





        
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
    
}
