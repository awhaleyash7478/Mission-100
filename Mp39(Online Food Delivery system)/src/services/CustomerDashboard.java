package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerDashboard {
    Scanner sc;
    Connection conn;
    CustomerServices cusObj;
    CommonServices commObj;
    public CustomerDashboard(Scanner sc,Connection conn)
    {
        this.sc=sc;
        this.conn=conn;
        cusObj=new CustomerServices(conn, sc);
        commObj=new CommonServices(conn, sc);
    }
    public void searchRestaurant()
    {
          ArrayList<Double>storedFinalAmount=new ArrayList<>();
         String itemName=null;
         double price=0.0;
            String address=null;
            int quantity=0;

        ArrayList<Integer>selectedItemIds=new ArrayList<>();

         Double finalAmount=0.0;
        ArrayList<Integer> itemIds=new ArrayList<>();
        int resId=0;
        System.out.println("Restaurnat name: ");
        String search=sc.nextLine();
      
            System.out.println("hey the control is here");
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
          System.out.println("\nRestaurant ID : " + rs2.getInt("res_id"));
           resId= rs2.getInt("res_id");
                 System.out.println("Restaurant: " + rs2.getString("res_name"));
    System.out.println("Location  : " + rs2.getString("address"));
    System.out.println("----------------------------");
                

    }
        
        }
    }catch(Exception e)
    {
        e.printStackTrace();
    }
 
        int ch=0;
        while(true)
        {
        System.out.println("1.View Menu          2.Exit");
    
        try {
            ch=sc.nextInt();
        } catch (Exception e) {
        System.out.println("Pls select the valid option");
        continue;
        } 
          
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
                      System.out.printf("| %-10s | %-10s | %-8s | %-7d |%n",
            rr.getString("menu_item"),
            rr.getString("item_prize"),
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
                System.out.println("selected item id: "+selectedItemIds);
                if(!selectedItemIds.isEmpty())
                selectedItemIds.remove(0);
                System.out.println("after removing: "+selectedItemIds);
            System.out.println("Enter the item Id to place Order: ");
        
            
            try
            {
            itemId=sc.nextInt();

            }catch(Exception e)
            {
                System.out.println("Pls enter the valid id only");
                continue;
            }
                selectedItemIds.add(itemId);
                System.out.println("after entering itemid: "+selectedItemIds);
                for(int itemIdss:selectedItemIds)
                {
                    itemId=itemIdss;
                    System.out.println("itemidss: "+itemIdss);

                }
            
          
        
        if(itemIds.contains(itemId))
        {
           
            
            try {
                String order="select * from restaurant_menu where item_id=?";
                PreparedStatement pr=conn.prepareStatement(order);
                pr.setInt(1, itemId);
                ResultSet rrs=pr.executeQuery();
                if(rrs.next())
                {
                    
                    System.out.println("-----Selected Dish-------");
                    System.out.println("Item Name: "+rrs.getString("menu_item"));
                    System.out.println("Cuisine: "+rrs.getString("cuisine"));
                    System.out.println("Price: "+rrs.getString("item_prize"));
                    System.out.println("-------------------------");
                     price=Double.parseDouble(rrs.getString("item_prize"));
                     itemName=rrs.getString("menu_item");
                }
              
            } catch (Exception e) {
                e.printStackTrace();
            }
        
            while(true)
            {
            System.out.print("Quantity: ");
            
            try {
                quantity=sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid quantity");
                continue;
            }
            if(quantity<=0)
            {
                System.out.println("Quantity can't be negative or zero");
                continue;
            }
            break;
        }
            finalAmount =price*quantity;
            storedFinalAmount.add(finalAmount);
                     System.out.println("final amount: "+finalAmount);
         System.out.println(storedFinalAmount);
       
            
              int selection=0;
            while(true)
            {
            System.out.println("1.Add          2.Next");
          
            try
            {
            selection=sc.nextInt();
            }catch(Exception e)
            {
                System.out.println("Invalid option");
                continue;
            }
            break;
        }
        
            if(selection==1)
            {
                continue;
             

            }else if(selection==2)
            {
                System.out.println("breaking");
                break;
                
            }else 
            {
                System.out.println("Invalid option allowed is 1 and 2");

                

            }
        

        }
        
        else 
        {
            System.out.println("No such item id");
            continue;
        }
            }
            
        sc.nextLine();
            System.out.println("Address: ");
            address=sc.nextLine();
Double finalBill=0.0;

          for(Double d:storedFinalAmount)

          { 
        
                    finalBill+=d;
        
           
          }
     
       

        
while(true)
{
        System.out.println("=====PAYMENT=====");
        System.out.println("Amount: "+finalBill);
        System.out.println("1.UPI\n2.Card\n3.Cash on Delivery");
        int paymentMode=0;
        try {
            paymentMode=sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid option selected");
            continue;
        }
        String paymentStatus="COD(Unpaid)";
        
     if(paymentMode==1||paymentMode==2||paymentMode==3)
     {
        if(paymentMode==1||paymentMode==2)
            paymentStatus="Online(Paid)";
        try {
            String order="insert into appOrders(res_id,cus_mobno,item_id,address,payment_status,prize)values(?,?,?,?,?,?)";
            PreparedStatement ps1=conn.prepareStatement(order);
            ps1.setInt(1, resId);
            ps1.setString(2,CustomerVerification.mob);
            ps1.setInt(3,itemId);
            ps1.setString(4,address);
            ps1.setString(5,paymentStatus);
            ps1.setDouble(6, finalAmount);
           ps1. executeUpdate();

            String history="insert into appHistory(item_name,amount,paymentstatus,mob_no)values(?,?,?,?)";
            PreparedStatement ps2=conn.prepareStatement(history);
            ps2.setString(1, itemName);
            ps2.setDouble(2, finalAmount);
            ps2.setString(3, paymentStatus);
            ps2.setString(4, CustomerVerification.mob);
            int rows=ps2.executeUpdate();

            
            if(rows>0)
            {
                System.out.println("Order Placed Successfully");
                return ;
            }else 
            {
                System.out.println("Unable to place the order");
                return ;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    


     }else 
     {
        System.out.println("Invalid option selected");
        continue;
     }
    }


    
        
        }else if(ch==2)
            {
                return ;
            }else  
            {
                System.out.println("Invalid entry allowed is 1 and 2");
                continue;
            }
            


            
        }
    }
    
 
    


//     public  void searchFood()
//     {
//         System.out.print("Name of Food item/Cuisine: ");
//         String foodName=sc.nextLine();
// int item_id=0;
// int res_id=0;
// String item_name=null,item_prize=null,res_name=null;
//         try {
//             String query="select * from restaurant_menu where menu_item like ? or cuisine like ?";
//             PreparedStatement ps=conn.prepareStatement(query);
//             ps.setString(1, "%"+foodName+"%");
//             ps.setString(2, "%"+foodName+"%");
//             ResultSet rs=ps.executeQuery();
//             if(rs.next())
//             {
//                 item_id=rs.getInt("item_id");
//                 item_prize=rs.getString("item_prize");
//                 res_id=rs.getInt("res_id");
//                 String resName="select res_name from restaurant_registration where res_id=?";
//                 PreparedStatement pp=conn.prepareStatement(resName);
//                 pp.setInt(1, res_id);
//                 ResultSet rr=pp.executeQuery();
//                 if(rr.next())
//             {
//                 resName=rr.getString("res_name");
//             }
//                 item_name=rs.getString("menu_item");
//                 System.out.println("-----Tasty Dishes-------");
//                 System.out.println("Dish Name: "+item_name);
//                 System.out.println("Prize: "+item_prize);
//                 System.out.println("Restaurant Name: "+res_name);
//                 System.out.println("Item Id: "+item_id);
//                 System.out.println("-----------------------");

//             }else 
//             {
//                 System.out.println("No such food item found");
//                 return;
//             }
            
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
//     public void viewFoodMenu()
//     {

//         try {
//             int found=0;
//             String query="select * from restaurant_registration";
//             PreparedStatement ps=conn.prepareStatement(query);
//             ResultSet rs=ps.executeQuery();
//             System.out.println("+------------+------------+----------+---------+");
// System.out.println("| menu_item  | item_prize | cuisine  | item_id |");
// System.out.println("+------------+------------+----------+---------+");
//             while (rs.next()) {
//                 found=1;
//                  System.out.printf("| %-10s | %-10.1f | %-8s | %-7d |%n",
//             rs.getString("menu_item"),
//             rs.getDouble("item_prize"),
//             rs.getString("cuisine"),
//             rs.getInt("item_id"));

                
//             }
//             System.out.println("+------------+------------+----------+---------+");
//             if(found==0)
//             {
//                 System.out.println("No Food items yet");
//                 return ;
//             }
//         } catch (Exception e) {
//            e.printStackTrace();
//         }
//     }
//     public void placeOrder()
//     {
        
//         int choice=0;
//         while(true)
//         {
//         System.out.println("1.Search          2.Menu          3.Exit ");

//         try {
//             choice=sc.nextInt();
//         } catch (Exception e) {
//             System.out.println("Invalid choice");
//             continue;
//         }

        
//         break;
        
//     }
//        if(choice==1)
//        {
//         searchFood();

//        }else if(choice==2)
//        {
//         viewFoodMenu();
//        }else 
//        {
//         return;
//        }

//     }

    public void cusMenu()
    {
        while(true)
      {
        System.out.println("\n=================================\r\n" + //
                        "        CUSTOMER DASHBOARD\r\n" + //
                        "=================================\r\n" + //
                        "\r\n" + //
                        "1. Search Restaurants\r\n" + //
                        "2. View Nearby Restaurants\r\n" + //
                        "3. View Menu\r\n" + //
                        "4. My Cart\r\n" + //
                       
                        "5. Track Current Order\r\n" + //
                        "6. My Orders\r\n" + //
                        
                        "7. Notifications\r\n" + //
                        "8. My Profile\r\n" + //
                        "9. Logout");
      int choice=0;
      
      try {
        choice=sc.nextInt();
        sc.nextLine();
      } catch (Exception e) {
       System.out.println("Pls choose the valid option [eg:11 for Logout]");
       continue;
      }
   switch (choice) {
    case 1:
        searchRestaurant();


        
        break;
    case 2:
        cusObj.viewnearbyRes();
        break;
    
    case 3:
        cusObj.viewMenu();
        break;
     
    case 4:
        cusObj.viewCart();
        break;

    case 5:
        cusObj.trackOrder();
        break;
    
    case 6:
        cusObj.myOrders();
        break;
    
    case 7:
        cusObj.notications();
        break;
    case 8:
        commObj.myProfile();
     
        break;
        
    case 9:
        return ;
    
   
    default:
        System.out.println("Invalid choice");
        break;
   }
    }
}
}
