package threads;

public class AttemptValidationThread extends Thread
{

    public  static int sleep;
    public void run()
    {
        
        try 
        {
            sleep=1;
        
           
            Thread.sleep(30000);
            sleep=2;
     
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}