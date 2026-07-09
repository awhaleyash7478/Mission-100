package threads;

public class PaymentThread extends Thread {
public void run()
{
    try {
        System.out.println("Processing payment.....");
        Thread.sleep(2000);
        System.out.println("Fetching account details.....");
        Thread.sleep(2000);
        System.out.println("Payment successfull.....");
        Thread.sleep(2000);
    } catch (Exception e) {
        System.out.println("Payment thread interrupted");
        e.printStackTrace();
    }
}
                                                     
}
