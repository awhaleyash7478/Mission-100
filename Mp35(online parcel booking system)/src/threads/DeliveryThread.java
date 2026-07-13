package threads;

import java.sql.Connection;

import services.CustomerVerification;

public class DeliveryThread extends Thread {

CustomerVerification cusObj;
public DeliveryThread(CustomerVerification cusObj)
{
    this.cusObj=cusObj;
}



    public void run()
    {
        
        OtpValidationThread o=new OtpValidationThread(null);
        o.start();
        System.out.println("\t\t---------------------------------");
        System.out.println("\t\tPackage successfully delivered");
        System.out.println("\t\t---------------------------------");
    
    

    }
}
