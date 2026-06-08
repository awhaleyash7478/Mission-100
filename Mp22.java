import java.util.Scanner;
class PaymentProcessor extends Thread
{
    Warehouse obj;
int storeIndex;
String status;
int hisIndex;
    PaymentProcessor(Warehouse obj,int storeIndex,int hisIndex)

    {
         this.obj=obj;
         this.hisIndex=hisIndex;
         this.storeIndex=storeIndex;
    }
    public void run()
    {

 double payment=0.0;

        try 
        {
          obj.finalprice[hisIndex]=obj.quantity[storeIndex]*obj.price[storeIndex];
            System.out.println("Processing Payment");
            Thread.sleep(2000);
            System.out.println("Payment done Succesfully");
            obj.status[storeIndex]="Placed";


        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
    class InventoryThread extends Thread{
        int storeIndex;

        int storeQuantity;
        Warehouse obj;
        InventoryThread(Warehouse obj,int storeIndex,int storeQuantity)
        {
            this.obj=obj;
            this.storeIndex=storeIndex;
            this.storeQuantity=storeQuantity;
        }
        public void run()
        {

            obj.quantity[storeIndex]-=storeQuantity;
            try 
            {
                System.out.println("reducing stock....");
                Thread.sleep(2000);
                System.out.println("stock reduced....");
            }catch(InterruptedException e)
            {
                System.out.println("thread interrupted");
            }
        }
    }
    class ShipmentThread extends Thread
    {
        Warehouse obj;
        int storeIndex;
        ShipmentThread(Warehouse obj,int storeIndex)
        { this.obj=obj;
            this.storeIndex=storeIndex;

        }
        public void run()
        {
            obj.status[storeIndex]="Shipped";

System.out.println("shipped");
        }
    }
    class DeliveryThread extends Thread
    {
        String status;
        int storeIndex;
        Warehouse obj;
        DeliveryThread(Warehouse obj,int storeIndex)

    {
        this.obj=obj;
        this.storeIndex=storeIndex;
    }
        public void run()
        {
            obj.status[storeIndex]="Delivered";
            System.out.println("delivered");
        }
    }
class Warehouse
{

    Scanner sc=new Scanner(System.in);
    final int max=100;
    double finalprice[]=new double[max];
    String status[]=new String[max];
    int storeQuantity;
    int storeIndex;
    int hisQuantity[]=new int[max];
    String hisName[]=new String[max];
    int hisId[]=new int[max];
    int hisIndex;
    double amount[]=new double[max];
    int proId[]=new int[max];
    String pronName[]=new String[max];
    int quantity[]=new int[max];
    double price[]=new double[max];
    void addProduct()
    {
        String tempName=null;
        int tempId=0;
        double tempPrice=0.0;
        int tempQuantity=0;

        try 
        {
            System.out.println("Enter the product id:");
             tempId=sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the product name:");
         tempName=sc.nextLine();
         System.out.println("Enter the product price:");
         tempPrice=sc.nextDouble();
         System.out.println("Enter the product Quantity:");
          tempQuantity=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("invalid entry");
        }
        for(int i=0;i<max;i++)
        {
        
            if(proId[i]==0)
            {
                
                proId[i]=tempId;
                pronName[i]=tempName;
                quantity[i]=tempQuantity;
                price[i]=tempPrice;
                System.out.println("Product added successfully");
                break;
            }
        } 
    }

    void viewProduct()
    {
        for(int i=0;i<max;i++)
        {
            if(i==0)
            {
                if(proId[i]==0)
                {
                    System.out.println("No product exists");
                    break;
                }
            }
            if(proId[i]==0)
                break;
            System.out.println("Product Name: "+pronName[i]+"\nProduct id: "+proId[i]+"\nProduct quantity: "+quantity[i]+"\nProduct price: "+price[i]);

        }
    }
    void searchProduct()
    {
        int found=0;
        int searchID=0;
        try 
        {
            System.out.println("Enter the search id:");
            searchID=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("invalid exception ");
            sc.nextLine();
        }
        for(int i=0;i<max;i++)
        {
            if(searchID==proId[i])
            {
                found=1;
                        System.out.println("Product Name: "+pronName[i]+"\nProduct id: "+proId[i]+"\nProduct quantity: "+quantity[i]+"\nProduct price: "+price[i]);
                break;
            }
        }
        if(found==0)
        {
            System.out.println("Product not found");
        }
    }
    void placeOrder()
    {
        int orderId=0,orderQunantity=0,found=0;
        for(int i=0;i<max;i++)
        {
            if(proId[i]==0)
                break;
            System.out.println("Product Name: "+pronName[i]+"\nProduct id: "+proId[i]);

        }

        try 
        {
            
            System.out.println("Enter the product id to place the order:");
            
        orderId=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("invalid input");
            sc.nextLine();
        }
        for(int i=0;i<max;i++)
        {
            if(orderId==proId[i])
            {
                found=1;
                System.out.println("Enter the quantity:");
                orderQunantity=sc.nextInt();
                storeIndex=i;
                storeQuantity=orderQunantity;
                if(orderQunantity<0)
                {
                    System.out.println("Order cant be negative");
                    break;
                }else if(orderQunantity>quantity[i])
                {
                    System.out.println("outof stock");
                    break;
                }
                hisQuantity[hisIndex]=orderQunantity;
                hisId[hisIndex]=orderId;
                hisName[hisIndex]=pronName[i];
               int currentIndex=hisIndex;
                hisIndex++;
               
                PaymentProcessor p=new PaymentProcessor(this,storeIndex,currentIndex);
                 p.start();

                 InventoryThread ii=new InventoryThread(this,storeIndex, storeQuantity);
                 ii.start();
                 try{
                 p.join();
                 }catch(InterruptedException e)
                 {}
                 ShipmentThread s=new ShipmentThread(this,storeIndex);
                 s.start();
                 try 
                 {
                 s.sleep(5000);
                 s.join();
                 }catch(InterruptedException e)
                 {}
                 DeliveryThread d=new DeliveryThread(this,storeIndex);
                 d.start();
                 
                 
            }
        }
    }
    void viewHistory()
    {

            for(int i=0;i<max;i++)
        {
            if(hisId[i]==0)
                break;
System.out.println("Name: "+hisName[i]+"\nId: "+hisId[i]+"\nAmount: "+finalprice[i]+"\nStatus: "+status[i]+"\nQuantity: "+hisQuantity[i]);
        }
    }
    void menu()
    {
        int ch=0;
        while (true) {
            System.out.println("1.Add Product\n2.View Product\n3.Search Product\n4.Place Order\n5.View Historyr\n6.Exit");
            try 
            {
            ch=sc.nextInt();
            }catch(Exception e)
            {
                System.out.println("Invalid entry");
                sc.nextLine();
            }
            switch (ch) {
                case 1:
                    addProduct();
                    
                    break;
                case 2:
                    viewProduct();
                    break;
                case 3:
                    searchProduct();
                    break;
                case 4:
                    placeOrder();
                    break;
                case 5:
                    viewHistory();
                    break;
                    
            
                case 7:
                    return;
            
                default:
                    System.out.println("invalid entry");
                    break;
            }
            
        }

    }
}
class Mp22
{
    public static void main(String[] args) {
        Warehouse w=new Warehouse();
        w.menu();
    }
}