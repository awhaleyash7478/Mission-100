package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import threads.Notifications.DeliveryPartnerNotification;

public class DeliveryPartnerDashboard {
    Connection conn;
    Scanner sc;
    public DeliveryPartnerDashboard(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
    public void availableDeliveries()
    {
        ArrayList<Integer>orderId=new ArrayList<>();
        String res_add=null,cus_add=null;
        int order_id=0;
        int delivery_partner_id=0;
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
        try {
            
       String query="select a.address as cus,a.order_id as ord, r.address as res from appOrders a join restaurant_registration r on a.res_id=r.res_id  where delivery_partner_id=? and a.notification_id is null";
            PreparedStatement ps=conn.prepareStatement(query);
                 ps.setInt(1, delivery_partner_id);
            ResultSet rs=ps.executeQuery();
            int found=0;
            while(rs.next())
            {
                found=1;
                res_add=rs.getString("cus");
                cus_add=rs.getString("res");
                order_id=rs.getInt("ord");


                System.out.println("Order id: "+order_id);
                System.out.println("Pickup Location: "+res_add);
                System.out.println("Delivery Location: "+cus_add);
                orderId.add(order_id);
                System.out.println(orderId);

     
  

        } 
        if(found==0)
        System.out.println("No avaiable deliveries");
    return ;
        }catch (Exception e) {
            e.printStackTrace();
        }
        while(true)
        {
        System.out.println("1.Accept Delivery          2.Exit");
        int choice=0;
        try {
            choice=sc.nextInt();
        } catch (Exception e) {
            System.out.println("Pls enter the valid option no.[eg: 2 for Exit]");
            continue;
        }
        if(choice==1)
        {
            while(true)
            {
            System.out.print("Order id: ");
            int enteredOrderId=0;
            if(!orderId.contains(enteredOrderId))
            {
                System.out.println("No such Order id");
                continue;
            }else 
            {
                System.out.println("Order accepted successfully");
                 try {
        String orderStatus="Out for delivery";
        String query="update appOrders set delivery_partner_id =? , order_status =? where order_id=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, delivery_partner_id);
        ps.setString(2, orderStatus);
        ps.setInt(3, order_id);
        int rows=ps.executeUpdate();
        
        String status="busy";
            String update="update delivery_partners set status=? where delivery_partner_id=?";
            PreparedStatement pp=conn.prepareStatement(update);
            pp.setString(1, status);
            pp.setInt(2, delivery_partner_id);
            int r=pp.executeUpdate();
            
   
    } catch (Exception e) {
      e.printStackTrace();
    }
                return ;
            }

        }
        }
    }

    }
    public void currentDeliveries()
    {
       String res_add=null,cus_add=null;
        int order_id=0;
        int delivery_partner_id=0;
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
        String value="out for delivery";
        try {
       String query="select a.address as cus,a.order_id as ord, r.address as res from appOrders a join restaurant_registration r on a.res_id=r.res_id  where delivery_partner_id=? and a.order_status=?";
            PreparedStatement ps=conn.prepareStatement(query);
                 ps.setInt(1, delivery_partner_id);
                 ps.setString(2, value);
            ResultSet rs=ps.executeQuery();
int found=0;
            while(rs.next())
            {
                 found=1;
                res_add=rs.getString("cus");
                cus_add=rs.getString("res");
                order_id=rs.getInt("ord");

                System.out.println("\nOrder id: "+order_id);
                System.out.println("Pickup Location: "+res_add);
                System.out.println("Delivery Location: "+cus_add);

     
  

        }
        if(found==0)
        {
            System.out.println("No Current Deliveries");
            return ;
        }
        while(true)
            { 
        System.out.println("\n1.Delivered    2.Exit");
        int ch=0;
        try {
            ch=sc.nextInt();
        } catch (Exception e) {
          System.out.println("Pls enter the valid option no.");
          continue;

        }
        if(ch==1)
        {
             try {
        String orderStatus="Delivered";
        String query2="update appOrders set delivery_partner_id =? , order_status =? where order_id=?";
        PreparedStatement pp=conn.prepareStatement(query2);
        pp.setInt(1, delivery_partner_id);
        pp.setString(2, orderStatus);
        pp.setInt(3, order_id);
        int rows=pp.executeUpdate();
        
        String status="free";
            String update="update delivery_partners set status=? where delivery_partner_id=?";
            PreparedStatement pp1=conn.prepareStatement(update);
            pp1.setString(1, status);
            pp1.setInt(2, delivery_partner_id);
            int r=pp1.executeUpdate();
            if(r>0)
            {
                System.out.println("Ordered Delivered Successfully");
                return ;
            }else 
            {
                System.out.println("Unable to deliver the Order");
                return ;
            }
            
   
    } catch (Exception e) {
      e.printStackTrace();
    }

        }
        else if(ch==2)
        {
            return;
        }else 
        {
            System.out.println("Pls enter the valid option allowed is 1 and 2");
            continue;
        }
    }
    }catch (Exception e) {
            e.printStackTrace();
        }


    }
    public  void updateStatus()
    {
          while (true) {
                                
                            
                            System.out.println("\n1.Active        2.Inactive       3.Exit");
                            int choice=0;
                            try {
                                choice=sc.nextInt();
                                sc.nextLine();
                            } catch (Exception e) {
                                System.out.println("Invalid option");
                                continue;
                            }
                            int delivery_partner_id=0;
                            String status=null;
                            if(choice==1)
                            {status="free";
                            
                               
            
            
       

                            }else if(choice==2)
                                {
                                    status="busy";
                                }
                                else if(choice==3)
                                    {
                                        return;
                                    }else
                                {
                                    System.out.println("Invalid option allowed is 1 and 2");
                                    continue;
                                }
                                 try {
            String query="select delivery_partner_id from delivery_partners where mob_no=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, CustomerVerification.mob);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                delivery_partner_id=rs.getInt("delivery_partner_id");
                
            String update="update delivery_partners set status=? where delivery_partner_id=?";
            PreparedStatement pp=conn.prepareStatement(update);
            pp.setString(1, status);
            pp.setInt(2, delivery_partner_id);
            int r=pp.executeUpdate();
            if(r>0)
            {
                System.out.println("Status updated Successfully");
                break;
            }else 
            {
                System.out.println("Unable to Update the status");
                break;
            }
                            
        }
    }catch(Exception e)
    {
        e.printStackTrace();
    }
          }}

    
    public void menu()
    {
                         DeliveryPartnerNotification delObj=new DeliveryPartnerNotification(conn);
                delObj.setDaemon(true);
                 delObj.start();
        int ch=0;
        while(true)
        {
        System.out.println("===== DELIVERY PARTNER =====\r\n" + //
                        "\r\n" + //
                        "1. Available Deliveries\r\n" + //
                        "2. Current Delivery\r\n" + //
                        
                        "3. Profile\r\n" + //
                        "4. Change Availability\r\n" + //
                        "5. Logout");
                        try {
                            ch=sc.nextInt();
                        } catch (Exception e) {
                         
                        System.out.println("Pls enter the valid option no.");
                    continue;}
                    switch (ch) {
                        case 1:
                            availableDeliveries();

                            
                            break;
                        case 2:
                            currentDeliveries();
                            break;

                        case 3:
                            CommonServices commObj=new CommonServices(conn, sc);
                            commObj.myProfile();
                            break;
                        case 4:
                            updateStatus();
                          
                    break;
                    case 5:
                        return ;
                            
                    
                        default:
                            System.out.println("Inavalid option pls enter the valid option");
                            continue;
                            
                    }
                        }
    }
    
}
