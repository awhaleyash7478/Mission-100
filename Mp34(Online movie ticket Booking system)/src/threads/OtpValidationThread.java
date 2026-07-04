
package threads;
import services.*;
public class OtpValidationThread extends Thread {
  CustomerVerification obj;
   public OtpValidationThread(CustomerVerification obj)
  {
    this.obj=obj;
  }
    public void run()
  {

    try 
    {
      
        Thread.sleep(30000);
        obj.otpgenerated=0;
    

    }catch(InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}
