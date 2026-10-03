package threads.Notifications;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import services.CustomerVerification;

public class DeliveryPartnerNotification extends Thread {
    Connection conn;
    public DeliveryPartnerNotification(Connection conn)
    {
        this.conn=conn;

    }
    public void run()
    {
        
           int defaultId=1;
        int delivery_partner_id=0;
        while(true)
        {
        try {
            String query="select delivery_partner_id from delivery_partners where mob_no=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, CustomerVerification.mob);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                delivery_partner_id=rs.getInt("delivery_partner_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String res_add=null,cus_add=null;
        int order_id=0;
        try {
            String query="select a.address as cus,a.order_id as ord, r.address as res from appOrders a join restaurant_registration r on a.res_id=r.res_id  where delivery_partner_id=? and a.notification_id is null";
            PreparedStatement ps=conn.prepareStatement(query);
                 ps.setInt(1, delivery_partner_id);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                res_add=rs.getString("res");
                cus_add=rs.getString("cus");
                order_id=rs.getInt("ord");
      System.out.println(
    "\n+------------------------------------------+" +
    "\n|              NEW ORDER #" + order_id + "              |" +
    "\n+------------------------------------------+" +
    "\n| Pickup     : " + res_add +
    "\n| Deliver To : " + cus_add +
    "\n+------------------------------------------+\n"
);
try {
 
    String updateNotification="update appOrders set notification_id=? where order_id=? ";
    PreparedStatement pr=conn.prepareStatement(updateNotification);
    pr.setInt(1, defaultId);
    pr.setInt(2, order_id);
    int rows=pr.executeUpdate();
} catch (Exception e) {
    e.printStackTrace();
}


            
            }

       
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        e.printStackTrace();
        }
    

    }
    
}
}
