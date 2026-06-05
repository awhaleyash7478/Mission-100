import java.util.Scanner;

class PaymentThread extends Thread{
    public void run()
    {
        try 
        {
        System.out.println("Processing Payment...");
        
            Thread.sleep(2000);
        System.out.println("Payment Successful...");
            
        }catch(InterruptedException e)
        {
           System.out.println("thread interrupted");
        }
    }
}
class HousekeepingThread extends Thread{
    public void run()
    {
        try 
        {
            System.out.println("Cleaning Room...");
            Thread.sleep(2000);
            System.out.println("Room Ready...");
        }catch(InterruptedException e)
        {
            System.out.println("Thread interrupted");
        }
    }
}
class NotificationThread extends Thread 
{
    public void run()
    {try
    {
        System.out.println("Sending SMS...");
        Thread.sleep(2000);
        System.out.println("Sending Email...");
    }catch(Exception e)
    {
        System.out.println("Thread interrupted");
    }
}
}

class Hotel {
    Scanner sc = new Scanner(System.in);
    final int max = 100;
    int roomId[] = new int[max];
    String roomType[] = new String[max];
    double roomPrice[] = new double[max];
    String roomStatus[] = new String[max];
    int bookId[]=new int[max];
    String cusName[]=new String[max];
    int bookDays[]=new int[max];
    int bookRoomId[]=new int[max];
    int hisBookDays[]=new int[max];
    int hisBookId[]=new int[max];
    int hisBookRoomID[]=new int[max];
    String hisCusName[]=new String[max];
    int hisIndex=0;

    void addRoom() {
        int tempId = 0;
        String tempStatus = null, temproomType = null;
        double tempPrice = 0.0;
        try {
            System.out.println("Enter the room id:");
            tempId = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the room type:");
            temproomType = sc.nextLine();
            System.out.println("Enter the room one night price:");
            tempPrice = sc.nextDouble();
            sc.nextLine();
            System.out.println("Enter the room status:");
            tempStatus = sc.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid entry");
        }
        for (int i = 0; i < max; i++) {
            if (roomId[i] == 0) {
                roomId[i] = tempId;
                roomPrice[i] = tempPrice;
                roomStatus[i] = tempStatus;
                roomType[i] = temproomType;
                
                System.out.println("Room added successfully");
                break;

            }
        }

    }

    void viewRooms() {

        for (int i = 0; i < max; i++) {

            if (roomId[i] == 0) {
                System.out.println("NO rooms added...!");
                break;
            }
            System.out.println("Room id: " + roomId[i] + "\nRoom type: " + roomType[i] + "\nRoom price: " + roomPrice[i]
                    + "\nRoom status: " + roomStatus[i]);

        }
    }

    void searchRooms() {
        int searchId = 0;
        try {
            System.out.println("Enter the search id:");
            searchId = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid entry");
        }

        int found = 0;

        for (int i = 0; i < max; i++) {

            if (searchId == roomId[i]) {
                found = 1;
                System.out.println("Room id: " + roomId[i] + "\nRoom type: " + roomType[i] + "\nRoom price: " + roomPrice[i]+ "\nRoom status: " + roomStatus[i]);
                break;
            }
        }
        if (found == 0) {
            System.out.println("room not found");
        }
    }
    void bookRoom()
    {
        int found=0;
        String tempcusName=null;
        int tempbookDays=0,tempbookId=0,tempbookroomId=0;
try 
{
    System.out.println("Enter the Customer name:");
    tempcusName=sc.nextLine();
    System.out.println("Enter the room id:");
    tempbookId=sc.nextInt();
    System.out.println("Enter the number of days:");
    tempbookDays=sc.nextInt();
    if(tempbookDays>0)
    {
    System.out.println("Enter the room id:");
    tempbookroomId=sc.nextInt();
    for(int i=0;i<max;i++)
    {
    if(tempbookId==roomId[i])
    {
        
     if(roomStatus[i].equals("available"))
    {
        found=1;
        bookId[i]=tempbookId;
        bookDays[i]=tempbookDays;
        cusName[i]=tempcusName;
        bookRoomId[i]=tempbookroomId;
        hisBookId[hisIndex]=tempbookId;
        hisBookDays[hisIndex]=tempbookDays;
        hisCusName[hisIndex]=tempcusName;
        hisBookRoomID[hisIndex]=tempbookroomId;
        hisIndex++;
  roomStatus[i]="Booked";
        System.out.println("Room booked successfully");
        break;
    }
    
    
    }
}
if(found==0)
    System.out.println("rooms not available");
    }

else 
{
    System.out.println("Booking days cant be zero...");

}
    
    
    

}catch(Exception e)
{
    System.out.println("Invalid input");
}
}
    

void checkIn()
{
    int checkInId=0,found=0;
    try 
    {
        System.out.println("Enter the book id:");
        checkInId=sc.nextInt();
    }catch(Exception e)
    {
        System.out.println("Invalid entry");
    }
    for(int i=0;i<max;i++)
    {
        if(checkInId==bookId[i])
        {
            found=1;
            roomStatus[i]="occupied";
        System.out.println("Check-in successfully done");
        break;
        }
    }
    if(found==0)
    {
        System.out.println("room not avaialble");
    }
}
void checkOut()
{
    int checkOutId=0;
    try 
    {
        System.out.println("Enter the book id :");
        checkOutId=sc.nextInt();
    }catch(Exception e)
    {
        System.out.println("Invalid entry");
    }
    int found=0;
    for(int i=0;i<max;i++)
    {

        if(checkOutId==bookId[i])
        {
            if(roomStatus[i].equals("occupied"))
            {
                found=1;
                roomStatus[i]="available";
                PaymentThread p=new PaymentThread();
                p.start();
                HousekeepingThread h=new HousekeepingThread();
                h.start();
                try 
                {
                    p.join();
                    h.join();
                }catch(InterruptedException e)
                {
                    System.out.println("Thread interrupted");
                }
                NotificationThread n=new NotificationThread();
                n.start();
                try 
                {
                    n.join();
                }catch(Exception e)
                {
                    System.out.println("Thread Interrupted");
                }
            System.out.println("Checkout done successfully");
            break;
            }
        }
    }
    if(found==0)
    {
        System.out.println("checkin is not done");
    }
}
void cancelRooms()
{
    int found=0;
    int searchid=0;
    try 
    {
        System.out.println("Enter the book id:");
        searchid=sc.nextInt();
    }catch(Exception e)
    {
        System.out.println("Invalid entry");
    }
    for(int i=0;i<max;i++)
    {
        if(searchid==bookId[i])
        {
            found=1;
            for(int j=i;j<max;j++)
            {
                bookId[j]=bookId[j+1];
                bookRoomId[j]=bookRoomId[j+1];
                cusName[j]=cusName[j+1];
                bookDays[j]=bookDays[j+1];
                break;
            }
            System.out.println("room cancelled successfully");
            roomStatus[i]="available";
            break;
        }
    }
    if(found==0)
    {
        System.out.println("booking not found");
    }
}
void viewHistory()
{
    for(int i=0;i<max;i++)
    {
        if(hisBookId[i]==0)
        {
            
            break;
        }
        System.out.println("Customer name: "+hisCusName[i]+"\nBooking id: "+hisBookId[i]+"\nRoom id: "+hisBookRoomID[i]+"\nBooking days: "+hisBookDays[i]);
        break;
    }
}

    void menu() {
        int ch = 0;
        while(true)
        {
        System.out.println(
                "1.Add Room\n2.View Rooms\n3.Search Rooms\n4.Book Room\n5.Check-in\n6.Check-out\n7.Cancel Booking\n8.View History\n9.Exit");

        try {
            System.out.println("Enter your choice:");
            ch = sc.nextInt();
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid entry");
            sc.nextLine();
        }
        switch (ch) {
            case 1:
                addRoom();
                break;
            case 2:
                viewRooms();
                break;
            case 3:
                searchRooms();
                break;
            case 4:
                bookRoom();
                break;
            case 5:
                checkIn();
                break;
            case 6:
                checkOut();
                break;
            case 7:
                cancelRooms();
                break;
            case 8:
            viewHistory();
            break;
            case 9:
                return;

            default:
                System.out.println("Invalid entry");
                break;
        }
    }
    }
}
class Mp20
{
    public static void main(String[] args) {
        Hotel h=new Hotel();
        h.menu();
    }
}