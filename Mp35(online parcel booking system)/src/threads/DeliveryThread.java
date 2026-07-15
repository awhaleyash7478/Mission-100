package threads;

import java.sql.Connection;

import services.CustomerVerification;
import services.ParcelHistory;

public class DeliveryThread extends Thread {

CustomerVerification cusObj;
Connection conn;
public DeliveryThread(CustomerVerification cusObj,Connection conn)
{
    this.cusObj=cusObj;
    this.conn=conn;
   
}



    public void run()
    {
        
        OtpValidationThread o=new OtpValidationThread(cusObj);
        o.start();
        System.out.println("\t\t---------------------------------");
        System.out.println("\t\tPackage successfully delivered");
        System.out.println("\t\t---------------------------------");
        ParcelHistory hisObj=new ParcelHistory(conn);
        hisObj.viewHistory();
      
    
    

    }
}
