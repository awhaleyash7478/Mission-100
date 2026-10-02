package threads;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class deliveryPartnerAssignmentThread extends Thread{
    Connection conn;
    int order_id;
    public deliveryPartnerAssignmentThread(Connection conn,int order_id)
    {
        this.conn=conn;
        this.order_id=order_id;
    }
    public void run()
    {
          int delivery_partner_id=0;
          while(true)
          {
         try {
            System.out.println("entered the thread");
        String status="free";
        String query="select delivery_partner_id from delivery_partners where status=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, status);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
            {
                delivery_partner_id=rs.getInt("delivery_partner_id");
                System.out.println("id: "+delivery_partner_id);

            }else 
                {
                    System.out.println("Currently no delivery partner available");
                       try {
    
         Thread.sleep(5000);
     

    } catch (Exception e) {
       e.printStackTrace();
    }
    
                   
                  
                }    
    } catch (Exception e) {
       e.printStackTrace();
    }
    try {
        String orderStatus="Food Prepared";
        String query="update appOrders set delivery_partner_id =? , order_status =? where order_id=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, delivery_partner_id);
        ps.setString(2, orderStatus);
        ps.setInt(3, order_id);
        int rows=ps.executeUpdate();
        if(rows>0)
        {
            System.out.println("Assigning Delivery Partner");
        }
       
   
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      
         Thread.sleep(5000);
     

    } catch (Exception e) {
       e.printStackTrace();
    }
    
              }    }
    
}
