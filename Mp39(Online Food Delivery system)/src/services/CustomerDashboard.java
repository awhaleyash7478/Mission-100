package services;

import java.sql.Connection;
import java.util.Scanner;

public class CustomerDashboard {
    Scanner sc;
    Connection conn;
    public CustomerDashboard(Scanner sc,Connection conn)
    {
        this.sc=sc;
        this.conn=conn;
    }
    public void searchRestaurant()
    {
        System.out.println("Restaurnat name or cuisine name: ");
        String search=sc.nextLine();
        try {
            String query="select menu from restaurant registration where menu=?";
            
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void cusMenu()
    {
        System.out.println("=================================\r\n" + //
                        "        CUSTOMER DASHBOARD\r\n" + //
                        "=================================\r\n" + //
                        "\r\n" + //
                        "1. Search Restaurants\r\n" + //
                        "2. View Nearby Restaurants\r\n" + //
                        "3. View Menu\r\n" + //
                        "4. My Cart\r\n" + //
                        "5. Place Order\r\n" + //
                        "6. Track Current Order\r\n" + //
                        "7. My Orders\r\n" + //
                        "8. Saved/Favourite Restaurants\r\n" + //
                        "9. Notifications\r\n" + //
                        "10. My Profile\r\n" + //
                        "11. Logout");
      int choice=0;
      while(true)
      {
      try {
        choice=sc.nextInt();
      } catch (Exception e) {
       System.out.println("Pls choose the valid option [eg:11 for Logout]");
       continue;
      }
   switch (choice) {
    case 1:
        searchRestaurant();

        
        break;
   
    default:
        break;
   }
    }
}
}
