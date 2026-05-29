import java.util.Scanner;
import java.util.concurrent.ExecutionException;

class Simulation extends Thread
{
 public void run() 
 {
    try{
    System.out.println("Preparing Food...");
    Thread.sleep(2000);
    System.out.println("Packing Order..");
    Thread.sleep(2000);
    System.out.println("Out For Delivery...");
    Thread.sleep(2000);
    System.out.println("Delivered Successfully...");
    
    }catch(Exception e)
    {
        System.out.println("Interrupted exception");    }

 }
}
class FoodDelivery
{
    
    final int max=100;
    String hisname[]=new String[max];
    double hisFinalBill[]=new double[max];
     int hisquantity[]=new int[max];
     String name[]=new String[max];
        double price[]=new double[max];
        int id[]=new int[max];
        int available[]=new int[max];

    Scanner sc=new Scanner(System.in);
    void addFoodItem()
    {
       System.out.println("Enter the name:");
       String name1=sc.nextLine();
       System.out.println("Enter the id:");
       int id1=sc.nextInt();
       System.out.println("Enter the Price:");
       double price1=sc.nextDouble();
       sc.nextLine();
       System.out.println("Enter the availability:");
       int  avail1=sc.nextInt();
       for(int i=0;i<max;i++)
       {
        if(id[i]==0)
        {
        
        name[i]=name1;
        id[i]=id1;
        price[i]=price1;
        available[i]=avail1;
        break;

       }
    }
    }
    void  viewFoodItem()
    {
        for(int i=0;i<max;i++)
        {
            if(id[i]==0)
                break;
            System.out.println("Name: "+name[i]+"\nID: "+id[i]+"\n Availability: "+available[i]+"\nPrice: "+price[i]);
        }

    }
    void search()
    {
        int searchid;
        System.out.println("Enter the id to search:");
        searchid=sc.nextInt();
        int found=0;
        for(int i=0;i<max;i++)
        {
            if(searchid==id[i])
            {
                System.out.println("Name: "+name[i]+"\nID: "+id[i]+"\n Availability: "+available[i]+"\nPrice: "+price[i]);
                 break;
            }
           
        }
        if(found==0)
            System.out.println("Product not found");
    }
    void placeOrder()
    {
        double finalbill,discount;
        int tempid=0,tempquantity=0,found=0;

        System.out.println("Enter the id:");
        try 
        {
            tempid=sc.nextInt();
        }
        catch(Exception e)
        {
            System.out.println("Invalid input");
        }
        
        for(int i=0;i<max;i++)
        {
            if(tempid==id[i])
            {
                found=1;
                hisname[i]=name[i];
             
                

                System.out.println("Enter the quantity:");
                   hisquantity[i]=available[i];
                try 
                {
        tempquantity=sc.nextInt();
        if(tempquantity>available[i])
        {
            System.out.println("Not in Stock");
            break;
        }
        else if(tempquantity<0)
        {
            System.out.println("Quantity cant be negative");
        }
 
                }catch(Exception e)
                {
                    System.out.println("error");
                }
             
             double bill=price[i]*tempquantity;
             available[i]-=tempquantity;
             if(bill>1500)
             {
                discount=0.10*bill;
                finalbill=bill-discount;
             }
             else
             {
             discount=0.20*bill;
             
                            finalbill=bill-discount;
}
hisFinalBill[i]=finalbill;
             
             Simulation s=new Simulation();
            if(finalbill!=0)          
             s.start();
             try{
                s.join();
                System.out.println("-----A.Empires-----");
                System.out.println("Product name: " + name[i]);
                System.out.println("Product price: " + price[i]);
                System.out.println("Product Quantity: "+tempquantity);
                System.out.println("Total bill: " +finalbill);
                System.out.println("-----Thank-You-----");
                break;

             }catch(Exception e)
             {

             }
            }
        }
        if(found==0)
            System.out.println("Product not found");
    }
    void deleteOrder()
    {
        int found=0;
        System.out.println("Enter the id:");
        int searchid=sc.nextInt();
        for(int i=0;i<max;i++)
        {
            found=1;
            if(searchid==id[i])
            {
                found=1;
                id[i]=id[i+1];
                name[i]=name[i+1];
                price[i]=price[i+1];
                available[i]=available[i+1];
                System.out.println("Order deleted");

            }
            break;

        }
        if(found==0)
            System.out.println("Order not found");
    }
    void viewHistory()
    {
        int i;
        for(i=0;i<max;i++)
        {
           if(hisFinalBill[i]==0)
            {
                System.out.println("No history yet");
                  break;
            }
              
    System.out.println("-----Order History-----");
    System.out.println("Product name: "+hisname[i]);
    System.out.println("Product price: "+hisFinalBill[i]);
    System.out.println("Product quantity: "+hisquantity[i]);
    break;
        }        
    }
    void menu()
    {
        while(true)
        {
        System.out.println("1.Add Food Item\n2.View Menu\n3.Search Food Item\n4.Place Order\n5.Delete Item\n6.View History\n7.return");
        int ch=0;
        try 
        {
            System.out.println("Enter the choice:");
            ch=sc.nextInt();
            sc.nextLine();
        }
        catch(Exception e)
        {
            System.out.println("Invalid input");
        } 
        switch (ch) {
            case 1:
                addFoodItem();
                break;
            case 2:
                viewFoodItem();
                break;
            case 3:search();
            break;
            case 4:
                placeOrder();
                break;
            case 5:
                deleteOrder();
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
class Mp15
{
    public static void main(String[] args) {
        FoodDelivery d=new FoodDelivery();
        d.menu();
    }
}