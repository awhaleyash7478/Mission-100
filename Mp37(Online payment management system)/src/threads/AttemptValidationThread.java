package threads;

public class AttemptValidationThread extends Thread
{

    public  static int sleep;
    public void run()
    {
        
        try 
        {
            sleep=1;
            System.out.println("sleep :"+sleep);
           
            Thread.sleep(30000);
            sleep=2;
            System.out.println("sleep after completion: "+sleep);
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}