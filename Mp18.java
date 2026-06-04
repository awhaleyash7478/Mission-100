import java.util.Scanner;
class PaymentThread extends Thread
{
    public void run()
    {
        System.out.println("Processing Payment...");
        try 
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
System.out.println("thread interrupted");
        }
        System.out.println("Payment Successful...");
    }
}
class InventoryThread extends Thread 
{
    public void run()
    {
        System.out.println("Updating Stock...");
         try 
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
System.out.println("thread interrupted");
        }
        System.out.println("Stock Updated...");
    }
}
class NotificationThread extends Thread 
{
    public void run()
    {
        System.out.println("Sending SMS...");
           try 
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
System.out.println("thread interrupted");
        }
        System.out.println("Sending Email...");
            try 
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
System.out.println("thread interrupted");
        }
        System.out.println("Notification send...");
        
    }
}
class OnlineShopping 
{
    
    int hisIndex;
    final int max=100;
    int quantity[]=new int[max];
    Scanner sc=new Scanner(System.in);
    int id[]=new int[max];
    String name[]=new String[max];
    String category[]=new String[max];
    int stock[]=new int[max];
    double price[]=new double[max];
    String hisName[]=new String[max];
    double hisBill[]=new double[max];
    String hisStatus[]=new String[max];
    String status[]=new String[max];
    


    void addProduct()
    {
        String tempname=null;
        int tempid=0;
        double tempPrice=0.0;
        String tempCategory=null;
        int tempstock=0;
try 
{
        System.out.println("Enter the Product name:");
         tempname=sc.nextLine();
        System.out.println("Enter the Product id:");
         tempid=sc.nextInt();
        System.out.println("Enter the Product Price:");
         tempPrice=sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter ther Product Category:");
         tempCategory=sc.nextLine();
        System.out.println("Enter the Product Quantity:");
         tempstock=sc.nextInt();
    }catch(Exception e)
    {
        System.out.println("Invalid entry");
    }
    for(int i=0;i<max;i++)
    {
        if(id[i]==0)
        {
            name[i]=tempname;
            id[i]=tempid;
            stock[i]=tempstock;
            category[i]=tempCategory;
            price[i]=tempPrice;
            System.out.println("Product added Successfully");
            break;

        }
    }
}
void viewProduct()
{
    for(int i=0;i<max;i++)
    {
        if(id[i]!=0)
        {
            System.out.println("Product name: "+name[i]+"\n Product Price: "+price[i]+"\nProduct Id: "+id[i]+"\nProduct Stock: "+stock[i]+"\nProduct Category: "+category[i]);
        }
        else 
        {
            break;
        }
    }
}
void placeOrder()
{
    int tempid=0;
    int tempQuantity=0,found=0;
    double bill=0.0;
    double discount=0.0;
    double finalBill=0.0;
    System.out.println("Enter the id to place order:");
    try
    {
        tempid=sc.nextInt();
        
    }catch(Exception e)
    {
        System.out.println("invalid entry");
    }
    for(int i=0;i<max;i++)
    {
        if(tempid==id[i])
        {
            found=1;
            System.out.println("Enter the quantity:");
            tempQuantity=sc.nextInt();
            if(tempQuantity>stock[i])
            {
                System.out.println("Out of stock\n Available stock: "+stock[i]);
                break;
            }
            else if(tempQuantity<0)
            {
                System.out.println("Order can't be placed for quantity 0");
                break;

            }
            quantity[i]=tempQuantity;
            stock[i]-=tempQuantity;
            bill= tempQuantity*price[i];
            hisName[hisIndex]=name[i];
                status[i]="placed";
            
            if(bill>5000)
            {
 discount=bill*0.9;
            }
            else if(bill>20000)
            {
                discount=bill*0.8;

            }
            finalBill=bill-discount;
            hisBill[hisIndex]=finalBill;
            hisIndex++;
            PaymentThread p=new PaymentThread();
            p.start();
            InventoryThread ii=new InventoryThread();
            ii.start();
            NotificationThread n=new NotificationThread();
       try 
       {
            p.join();
       }catch(InterruptedException e)
       {
        System.out.println("thread interrupted");
       }
            n.start();
            try{
                n.join();
                ii.join();
            }catch(InterruptedException e)
            {
                System.out.println("Thread interrupted");
            }
        

            System.out.println("Order placed Successfully");
            System.out.println("\t\t\t\t\t-----A.Empires-----");
            System.out.println("\t\t\t\t\tProduct Name: "+name[i]);
            System.out.println("\t\t\t\t\tProduct Quantity: "+tempQuantity);
            System.out.println("\t\t\t\t\tFinal Bill: "+finalBill);
        System.out.println("\t\t\t\t\t-----Thank-You-----");  
        break;     
        }
    }
    if(found==1)
        System.out.println("Order not found");
}
void cancelOrder()
{
    int cancelQuantity=0;
    int found=0;
    int cancelId=0;
    System.out.println("Enter the id to cancel the order:");
    try{
        cancelId=sc.nextInt();
    }
    catch(Exception e)
    {
        System.out.println("invalid entry");
    }
    for(int i=0;i<max;i++)
    {
        if(cancelId==id[i])
        {
            found=1;
            System.out.println("Enter the quantity to cancel:");
            cancelQuantity=sc.nextInt();
            if(cancelQuantity==quantity[i])
            {
            for(int j=i;i<max;j++)
            {
         name[j]=name[j+1];
         id[j]=id[j+1];
         price[j]=price[j+1];
                     System.out.println("Order Cancelled Successfully");


         break;       

            }
        }
        else
        {
            stock[i]+=cancelQuantity;
            quantity[i]-=cancelQuantity;
            System.out.println("Order Cancelled Successfully");
            break;
        }
        }
    }
    if(found==0)
    {
        System.out.println("Order not found");
    }
}
void viewHistory()
{

    int found=0;
  

    for(int i=0;i<max;i++)
    {

        if(hisName[i]==null)
            break;
            
            found=1;
            System.out.println("Product Name: "+hisName[i]+"\nProduct Quantity: "+quantity[i]+"\nProduct Status: "+status[i]+"\nProduct Bill: "+price[i]);
           

    }
    if(found==0)
    {
        System.out.println("Order not found");
    }
}
void searchProduct()
{
    int searchId=0,found=0;
    System.out.println("Enter the id to search:");
    try
    {
        searchId=sc.nextInt();
    }
    catch(Exception e)
    {
        System.out.println("Invalid choice");
    }
    for(int i=0;i<max;i++)
    {
        if(id[i]==searchId)
        {
            found=1;
              System.out.println("Product Name: "+name[i]+"\nProduct Quantity: "+stock[i]+"\nProduct Status: "+status[i]+"\nProduct Bill: "+price[i]);
              break;

        }
    }
    if(found==0)
    {
        System.out.println("Order not found");
    }
}
void updateStatus()
{
    int updateID=0,found=0;
    String newStatus;
    System.out.println("Enter the id to update:");
    try{
        updateID=sc.nextInt();
        sc.nextLine();
    }catch(Exception e)
    {
        System.out.println("Invalid entry");
    }
    for(int i=0;i<max;i++)
    {
        found=1;
        if(id[i]==updateID)
        {
            System.out.println("Enter the status to update:");
            newStatus=sc.nextLine();
            if(status[i].equals("placed"))
            {
                if(newStatus.equals("Shipped"))
                {
                  status[i]=newStatus;
            System.out.println("Status Updated");
break;
                }
                System.out.println("Invalid entry");
                break;
            }else if(status[i].equals("Shipped"))
            {
                if(newStatus.equals("Delivered"))
                {
                     status[i]=newStatus;
            System.out.println("Status Updated");
            break;


                }
                System.out.println("Invalid entry");
                break;

            }else 
            {
                if(status[i].equals("Delivered"))
                {
                    if(newStatus.equals("Shipped")||newStatus.equals("PLaced"))
                    {
                        System.out.println("Invalid entry");
                        break;
                    }
                }
                
            }
           
        }
    }
    if(found==0)
    {
        System.out.println("Order not found");
    }

}

    void menu()
    {
        while(true)
        {
        int ch=0;
        System.out.println("1.Add Product\n2.View Product\n3.Place Order\n4.Cancel Order\n5.Order History\n6.Search Product\n7.Update Status\n8.Exit");
        System.out.println("Enter your choice:");
       try{ 
        ch=sc.nextInt();
        sc.nextLine();
    }catch(Exception e){
        System.out.println("Invalid choice");
    }
    switch (ch) {
        case 1:
            addProduct();
            
            break;
        case 2:
            viewProduct();
            break;
        case 3:
            placeOrder();
            break;
        case 4:
            cancelOrder();
            break;
        case 5:
            viewHistory();
            break;
        case 6:
            searchProduct();
            break;
        case 7:
            updateStatus();
            break;
        case 8:
            return;
        default:
            System.out.println("invalid entry");
            break;
    }
}
}
}
class Mp18
{
    public static void main(String[] args) {
        OnlineShopping o=new OnlineShopping();
        o.menu();
    }
}