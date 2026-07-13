package threads;

public class PaymentProcessingThread extends Thread {
    public void run()
    {
        try {
            System.out.println("Processing payment.....");
            Thread.sleep(2000);
            System.out.println("Payment Successfull.....");
            System.out.println("\t\t---------------------------------------");
            System.out.println("\t\tThankyou for using our Services..!");
             System.out.println("\t\t---------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
