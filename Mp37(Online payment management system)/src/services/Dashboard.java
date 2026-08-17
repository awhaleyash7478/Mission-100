package services;

import java.sql.Connection;
import java.util.Scanner;

public class Dashboard {
    Scanner sc;
    Connection conn;
    public Dashboard(Scanner sc,Connection conn)
    {
        this.sc=sc;
        this.conn=conn;
    }
    public void viewDashboard()
    {
        while (true) {
            
        
        System.out.println("========================================\n" + //
                        "          WELCOME TO A.EMPIRES\n" + //
                        "========================================"
                        );
                        System.out.println(
                                                        "1. Add Money\n" + //
                                                        "2. Send Money\n" + //
                                                        "3. Pay Merchant\n" + //
                                                        "4. Request Money\n" + //
                                                        "5. Payment Requests\n" + //
                                                        "6. Transaction History\n" + //
                                "7. Logout");
                                     int choice=0;
                                try
                                {
                 choice  =sc.nextInt();
                                }catch(Exception e)
                                {
                                    System.out.println("Pls enter the valid input[eg:-1 for Add Money]");
                                }
                                switch (choice) {
                                    case 1:
                                        AddMoney addObj=new AddMoney(sc,conn);
                                        addObj.addMoney();
                                        
                                        
                                        break;
                                
                                    default:
                                        System.out.println("Invalid choice [allowed is 1-6]");
                                        break;
                                }
    }
}
    
}
