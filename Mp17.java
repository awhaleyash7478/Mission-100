import java.util.Scanner;

class NegativePriceException extends Exception{
    
}
class BillSimulationThread extends Thread
{
    public void run()
    {
        try{
        System.out.println("Booking seats.....");
        Thread.sleep(2000); 
        System.out.println("Calculating Price.....");
        Thread.sleep(2000); 
        System.out.println("Tickets Booked Succesfully.....");

        }catch(InterruptedException e)
        {
            System.out.println("Thread Interrupted");
        }
    }
}
class NotificationThread extends Thread
{
    public void run()
    {
        try{
 System.out.println("Sending SMS...");
 Thread.sleep(2000);
 System.out.println("Sending Email...");
 Thread.sleep(2000);
 System.out.println("Notification sent...");
        }catch(InterruptedException e)
        {
            System.out.println("Thread interrupted");
        }
    }
}
class Movie
{
    int historyIndex;
    final int max=100;
    Scanner sc=new Scanner(System.in);
    int movieId[]=new int[max];
    String movieName[]=new String[max];
    int totalSeats[]=new int[max];
    int availableSeats[]=new int[max];
    double ticketPrice[]=new double[max];
    String hisName[]=new String[max];
        int hisSeats[]=new int[max];
        double hisPrice[]=new double[max];
        int hisId[]=new int[max];
        int i=0,found=0;
    void addMovie()

    {
        int tempId=0,tempAvailable=0,tempSeats=0;
        String tempName=null;
        double tempPrice=0.0;
        try
        {
        System.out.println("Enter the movie id:");
         tempId=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the Movie Name:");
        tempName=sc.nextLine();
        System.out.println("Enter the Total seats:");
        tempSeats=sc.nextInt();
        System.out.println("Enter the Avaiable seats:");
        tempAvailable=sc.nextInt();
        System.out.println("Enter the Ticket Price:");
        tempPrice=sc.nextDouble();
        if(tempPrice<0)
            throw new NegativePriceException();
        }
        catch(NegativePriceException e)
        {
            System.out.println("ticket price cant be negative");
        }catch(Exception e)
        {
            System.out.println("Invalid input"+e);
        } 
        
        for(int i=0;i<max;i++)
            {
                if(movieId[i]==0)
                {
                    movieId[i]=tempId;
                    availableSeats[i]=tempAvailable;
                    ticketPrice[i]=tempPrice;
                    movieName[i]=tempName;
                    totalSeats[i]=tempSeats;
                    System.out.println("Movie added Successfully");
                    break;
                }
            }       
    }
    void viewMovie()
    {
        for(int i=0;i<max;i++)
        {
            if(movieId[i]==0)
                break;
            System.out.println("Movie name: "+movieName[i]+"\nMovie id: "+movieId[i]+"\nTotal Seats: "+totalSeats[i]+"\nAvailable Seats: "+availableSeats[i]+"\n Ticket Price: "+ticketPrice[i]);
        }
    }
    void searchMovie()
    {
        int found=0;
        int searchId=0;
        try
        {
        System.out.println("Enter the Search id:");
       searchId=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("Invalid input");
        }
        for(int i=0;i<max;i++){
            if(movieId[i]==searchId)
            {
                found=1;
                 System.out.println("Movie name: "+movieName[i]+"\nMovie id: "+movieId[i]+"\nTotal Seats: "+totalSeats[i]+"\nAvailable Seats: "+availableSeats[i]+"\n Ticket Price: "+ticketPrice[i]);
                 break;

            }
        }
        if(found==0)
        {
            System.out.println("Movie not found");
        }
    }
   synchronized void bookTickets()
    {
        int tempid=0,found=0;
        double tempPrice=0.0;
        System.out.println("Enter the Movie id:");
        try 
        {
            tempid=sc.nextInt();
         
        }catch(Exception e)
        {
            System.out.println("Invalid input"+e);
        }
        for(int i=0;i<max;i++)
        {
            if(tempid==movieId[i])
            {
                found=1;
                System.out.println("Enter the nuumber of seats:");
                int tempseats=sc.nextInt();
                
              
                
                if(tempseats<0)
                {
                    System.out.println("Seats Can't be negative");
     break;
                }else if(tempseats>availableSeats[i])
                {
                    System.out.println("Seats not available");
                    break;
                }
                else 
                {
                    availableSeats[i]-=tempseats;
                      tempPrice=ticketPrice[i]*tempseats;
                
                    BillSimulationThread b=new BillSimulationThread();
                    b.start();
                    
                    
                    hisName[historyIndex]=movieName[i];
                    hisPrice[historyIndex]=tempPrice;
                    hisSeats[historyIndex]=tempseats;
                    hisId[historyIndex]=tempid;
                    historyIndex++;
                    try 
                    {b.join();
                    }catch(Exception e)
                    {}


                    System.out.println("Seats : "+tempseats+" Booked Successfully");
                
                System.out.println("\t\t\t\t\t-----A.empires Movie Ticket Booking System-----");
                System.out.println("\t\t\t\t\tMovie Name: "+movieName[i]);
                System.out.println("\t\t\t\t\tSeats Booked: "+tempseats);
                System.out.println("\t\t\t\t\tTotal Price: "+tempPrice);
                System.out.println("\t\t\t\t\t-----Thank-You-----");
                NotificationThread n=new NotificationThread();
                
                n.start();
                try
                {
                n.join();
                }catch(Exception e)
                {}
                break;
            }
        }
        }
    }
    void cancelTickets()
    {
         int found=0;
        int tempid=0;
        System.out.println("Enter the id:");
        try{
            tempid=sc.nextInt();
            
        }catch(Exception e)
        {
            System.out.println("Invalid input");
        }
        for(int i=0;i<max;i++)
        {
           
            if(tempid==movieId[i])
            {
                found=1;
                System.out.println("Enter the number od seats to be canceled");
                int tempSeats=sc.nextInt();
                if(tempSeats<0)
                {
                    System.out.println("Seats can't be negative");
                    break;
                }
                
                availableSeats[i]-=tempSeats;

                for(int j=0;j<max;j++)
                {
                    movieName[j]=movieName[j+1];
                    ticketPrice[j]=ticketPrice[j+1];
                    availableSeats[j]=availableSeats[j+1];
                    totalSeats[j]=totalSeats[j+1];
                    break;
                }
                System.out.println("Seats Cancelled Successfully");
                break;
            }
        }
        if(found==0)
        {
            System.out.println("Seats not found");
        }
    }
    void viewHistory()
    {
        
        
        for(i=0;i<max;i++)
        {
            if(movieId[i]==0)
                break;
            found=1;
            System.out.println("Movie name: "+hisName[i]+"\nSeats Booked: "+hisSeats[i]+"\nTickets Price: "+hisPrice[i]);

        }
        if(found==0)
        {
            System.out.println("No more history Exists");
        }
    }

    void menu()
    {
        while(true)
        {
    System.out.println("1.Add Movie\n2.View Movie\n3.Search Movie\n4.Book Tickets\n5.Cancel Tickets\n6.Booking history\n7.Exit");
    System.out.println("Enter your choice:");
    int ch=0;
    try{
        ch=sc.nextInt();
        sc.nextLine();
    }catch(Exception e)
    {
         System.out.println("Invalid input");
    }
    switch (ch) {
        case 1:addMovie();
            
            break;
        case 2:
            viewMovie();
            break;
        case 3:
            searchMovie();
            break;
        case 4:bookTickets();
        break;
        case 5:
            cancelTickets();
            break;
        case 6:
            viewHistory();
            break;
        case 7:
            return;
        default:
            System.out.println("Invalid input");
            break;
    }
    }
}
}
public class Mp17
{
    public static void main(String[] args) {
        Movie m=new Movie();
        m.menu();
    }
}
