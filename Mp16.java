import java.util.Scanner;
import java.util.concurrent.ExecutionException;
class ReturnThread extends Thread 
{
    public void run()
    {
        try{
        System.out.println("Verifying book...");
        Thread.sleep(2000);
        System.out.println("Updating records...");
        Thread.sleep(2000);
        System.out.println("Generating receipt...");
        
    }catch(InterruptedException e)
    {
        System.out.println("Thread interrupted");
    }
}
}
class library 
{
    final int max=100;
    Scanner sc=new Scanner(System.in);
    int hisBookId[]=new int[max];
    String hisBookName[]=new String[max];
    int id[]=new int[max];
    String bookName[]=new String[max];
    String authorName[]=new String[max];
    int total[]=new int[max];
    int available[]=new int[max];
    void add_Book()
    {
        System.out.println("Enter the book id:");
        int tempId=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the book name:");
        String tempBookName=sc.nextLine();
        System.out.println("Enter the author name:");
        String tempAuthorName=sc.nextLine();
        System.out.println("Enter the total copies:");
        int temptotal=sc.nextInt();
        System.out.println("Enter the available copies:");
        int tempavailable=sc.nextInt();

        for(int i=0;i<max;i++)
        {
            if(id[i]==0)
                {
            id[i]=tempId;
            bookName[i]=tempBookName;
            authorName[i]=tempAuthorName;
            total[i]=temptotal;
            available[i]=tempavailable;
            break;
        }
    }
    }
    void displayBook()
    {

        int found=0;
        for(int i=0;i<max;i++)
        {
            if(id[i]==0)
                break;
            found=1;
            System.out.println("Book name: "+bookName[i]+"\nAuthor name: "+authorName[i]+"\nBook id: "+id[i]+"Total Books: "+total[i]+"\n available books: "+available[i]);
        }
        if(found==0)
        {
            System.out.println("no books exist");
        }
    }
    void searchBook()
    {
        int found=0;
        int searchid=0;
        try{
            System.out.println("Enter the id to search:");
            searchid=sc.nextInt();
            sc.nextLine();
        }
        catch(Exception e)
        {
            System.out.println("invalid input");
        }
        for(int i=0;i<max;i++)
        {
            
            if(searchid==id[i])
            {
              found=1;
                          System.out.println("Book name: "+bookName[i]+"\nAuthor name: "+authorName[i]+"\nBook id: "+id[i]+"Total Books: "+total[i]+"\n available books: "+available[i]);
                      
                          break;

            }
        }
        if(found==0)
        {
            System.out.println("book not found");
        }
    }
    void issueBook()
    {
        int tempid=0;
        try{
        System.out.println("Enter the book id:");
        tempid=sc.nextInt();
        sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("Invalid input");
        }
        int found=0;
        for(int i=0;i<max;i++)
        {

            if(tempid==id[i])
            {
                 System.out.println("Enter the number of copies:");
                 try{
        int tempcopy=sc.nextInt();

        if(tempcopy<0)
        {
       System.out.println("Cant be negative");
        }else if(tempcopy>available[i])
        {
            System.out.println("Not in stock\n Available stock: "+available[i]);
            break;
        }
                 }catch(Exception e)
                 {
                    System.out.println("Error: invalid input");
                 }
                found=1;
                System.out.println("Book name: "+bookName[i]+"\n Author name: "+authorName[i]);
                hisBookName[i]=bookName[i];
                hisBookId[i]=id[i];
                System.out.println("Book issued");
                break;

            }
        }
        if(found==0)
            System.out.println("Book not found");
       
    }
    void deleteBook()
    {
        int tempid=0;
        try{
        System.out.println("Enter the book id:");
        tempid=sc.nextInt();
        sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("Invalid input");
        }
        int found=0;
        for(int i=0;i<max;i++)
       {
        if(tempid==id[i])
        {
            found=1;
            for(int j=i;j<max;j++)
            {
                id[j]=id[j+1];
                bookName[j]=bookName[j+1];
                authorName[j]=authorName[j+1];
                total[j]=total[j+1];
                available[j]=available[j+1];
            }
            id[max-1]=0;
            authorName[max-1]=null;
            bookName[max-1]=null;
            total[max-1]=0;
            available[max-1]=0;
            System.out.println("Order deleted successfully");
            break;
        }
       }
       if(found==0)
        System.out.println("order not found"); 
        
    }
    void returnBook()
    {

        double fine;
        int tempid=0,tempcopy=0,found=0;
        try{
        System.out.println("Enter the book id:");
        tempid=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the number of copies:");
        tempcopy=sc.nextInt();
        if(tempcopy<0)
        {
            System.out.println("Number of copies cant be negative");
            
        }
        }catch(Exception e)
        {
            System.out.println("Invalid input");
        }
        for(int i=0;i<max;i++)
        {
            if(tempid==id[i])
                    found=1;
                    available[i]+=tempcopy;
                    System.out.println("Enter the number of days: ");
                    int days=sc.nextInt();
                    if(days<5)
                    {
                        fine=days*10;
                    }
                    else 
                    {
                        fine=days*20;
                    }
                   ReturnThread t=new ReturnThread();
                   t.start();
                   try{
                   t.join();
                   }catch(InterruptedException e)
                   {
                    System.out.println("Thread interrupted");
                   }
                   System.out.println("A.empires Library");
                   System.out.println("Book name: "+bookName[i]);
                   System.out.println("Number of days late: "+days);
                   System.out.println("Total fine: "+fine);
                   break;

                }
            
        
        if(found==0)
            System.out.println("book not exist");
        

    }
    void viewHistory()
    {
        for(int i=0;i<max;i++)
        {
            if(id[i]==0)
            {
                System.out.println("No history");
                break;
            }
                System.out.println("-----History-----");
                System.out.println("Book name: "+bookName[i]+"\n Book id: "+id[i]);
                
            
        }
    }
    void menu()
    {
        while(true)
        {
            int ch=0;
            System.out.println("1 Add Book\n2.View Book\n3.Search Book\n4.Issue Book\n5.Delete Book\n.6.Return Book\n7.view History\n8.Exit");
            try{
                ch=sc.nextInt();
            }catch(Exception e)
            {
                System.out.println("Invalid input");
            }
            switch (ch) {
                case 1:
                    add_Book();
                    
                    break;
                case 2:
                    displayBook();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    issueBook();
                    break;
                case 5:
                    deleteBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    viewHistory();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }
}
class Mp16
{
    public static void main(String[] args) {
        library l=new library();
        l.menu();
    }
}