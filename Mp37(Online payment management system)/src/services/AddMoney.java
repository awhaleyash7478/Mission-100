package services;

import java.sql.Connection;
import java.util.Scanner;


import threads.AttemptValidationThread;

public class AddMoney {
    Scanner sc;
    AttemptValidationThread a;
    Connection conn;
    public AddMoney(Scanner sc,Connection conn)
    {
        this.sc=sc;
        this.conn=conn;
        a  =new AttemptValidationThread();

    }
      public void addMoney()
      {
        
        if(AttemptValidationThread.sleep==1)
        {
          System.out.println("Pls Wait Your Attempt limit is Exceeded");
          return;

        }

        int countattempt=3;
        while (true) {
            
        
        double amount=0.0;
        String password=null;
        try 
        {
            System.out.println("Enter the Amount: ");
            amount=sc.nextDouble();
            

        }catch(Exception e)
        {
            System.out.println("Invalid amount");
             continue;
        }
        if(amount<=0)
        {
            System.out.println("Pls enter the valid amount");
            sc.nextLine();
            continue;
        }
        System.out.println("Enter the Password:");
        sc.nextLine();
        
        password=sc.nextLine();
   
        CustomerVerification cusObj=new CustomerVerification(sc, conn);
      
        
        String fetchedpass=cusObj.password;

   
    
       if(password.equals(fetchedpass))
       {
        System.out.println("Money added successfully");
        break;
       }else
       {
        System.out.println("Invalid password attempts left "+countattempt);
          
         
          countattempt--;
        
          if(countattempt>=0)
      {
        continue;
      }else 
      {
        System.out.println("Attempt limit Exceeded Pls try again after 30 sec");
    
        a.start();
        break;
      }
         
       }
        

      }
    }
    
}
