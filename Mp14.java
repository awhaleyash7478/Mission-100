import java.util.Scanner;
class BillingThread extends Thread
{
    public void run(){
        System.out.println("Processing Payment...");
        try 
        {
            Thread.sleep(2000);
        }catch(InterruptedException e)
        {

        }
         System.out.println("Generating Invoice...");
        try 
        {
            Thread.sleep(2000);
        }catch(InterruptedException e)
        {
            
        }
         System.out.println("Purchase Successful...");
        try 
        {
            Thread.sleep(2000);
        }catch(InterruptedException e)
        {
            
        }
    }
}

class Inventory {
    final int max = 1000;
    Scanner sc = new Scanner(System.in);

    String name[] = new String[max];
    String cat[] = new String[max];
    int id[] = new int[max];
    double quantity[] = new double[max];
    double price[] = new double[max];

    void addProduct() {
        System.out.println("Enter the product name:");
        String name1 = sc.nextLine();
        System.out.println("Enter the product id:");
        int id1 = sc.nextInt();
        System.out.println("Enter the product quantity:");
        double quantity1 = sc.nextDouble();
        System.out.println("Enter the product price:");
        double price1 = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter the product category:");
        String catg = sc.nextLine();

        for (int i = 0; i < max; i++) {
            if (id[i] == 0) {
                name[i] = name1;
                id[i] = id1;
                quantity[i] = quantity1;
                price[i] = price1;
                cat[i] = catg;
                break;
            }

        }

    }

    void searchProduct() {
        int found = 0;
        int searchid;
        System.out.println("Enter the id to search:");
        searchid = sc.nextInt();
        for (int i = 0; i < max; i++) {
            if (searchid == id[i]) {
                found = 1;
                System.out.println("Product Name:" + name[i] + "\n Product id: " + id[i] + "\nProduct Quantity: "
                        + quantity[i] + "\nProduct Category: " + cat[i] + "\n Product Price: " + price[i]);

                break;
            }
        }
        if (found == 0) {
            System.out.println("Product does not Exist");
        }
    }

    void viewProduct() {
        for (int i = 0; i < max; i++) {
            if (id[i] == 0) {
                System.out.println("Product does not Exist");
                break;
            }
            System.out.println("Product Name:" + name[i] + "\n Product id: " + id[i] + "\nProduct Quantity: "
                    + quantity[i] + "\nProduct Category: " + cat[i] + "\n Product Price: " + price[i]);

        }
    }

    void updateQuantity() {
        int searchid = 0;
        int found = 0;
        System.out.println("Enter the id to update the product:");
        try {
            searchid = sc.nextInt();
        } catch (Exception e) {
            System.err.println("Invalid input");
        }
        for (int i = 0; i < max; i++) {
            if (searchid == id[i]) {
                found = 1;
                System.out.println("Enter the Quantity:");
                try {
                    int tempquantity = sc.nextInt();
                    if (tempquantity < 0)
                        System.out.println("Quantity cant be negative");
                    quantity[i] += tempquantity;
                } catch (Exception e) {
                    System.out.println("Error");
                }
                break;
            }
        }
        if (found == 0) {
            System.out.println("Product does not exist");
        }
    }

    void purchaseProduct() {
        int i, found = 0;
        for (i = 0; i < max; i++) {
            int tempid = 0;
            for (int j = 0; j < max; j++) {
                if (id[j] == 0)
                    break;
                System.out.println("Product:" + name[i] + " " + id[i] + " " + price[i]);
            }
            System.out.println("Enter the product id for purchase:");
            try {
                tempid = sc.nextInt();

            } catch (Exception e) {
                System.out.println("Invalid id");
            }

            if (tempid == id[i]) {
                found = 1;
                int tempquantity = 0;
                System.out.println("Enter the quantity:");
                try {
                    tempquantity = sc.nextInt();
                    if (tempquantity < 0) {
                        System.out.println("Quantity cant be negative");
                    }
                } catch (Exception e) {
                    System.out.println("Error");
                }
                quantity[i] -= tempquantity;
                double bill = tempquantity * price[i];
                double finalbill = bill * 1.02;
                BillingThread t=new BillingThread();
                t.start();
                try 
                {
                t.join();
                }catch(Exception e)
                {
                    
                }
                System.out.println("-----A.Empires-----");
                System.out.println("Product name: " + name[i]);
                System.out.println("Product price: " + price[i]);
                System.out.println("Product Quantity: " + quantity[i]);
                System.out.println("Total bill: " + finalbill);
                System.out.println("-----Thank-You-----");

            }
        }
        if (found == 0)
            System.out.println("Product not found");
    }
    void deleteQuantity()
    {
        int tempid=0;
        System.out.println("Enter the id for deletion:");
        try 
        {
            tempid=sc.nextInt();
        }
        catch(Exception e)
        {
            System.out.println("Invalid id");
        }
        int i,found=0;
        for(i=0;i<max;i++)
        {
        
            if(tempid==id[i])
            {
                found=1;
                name[i]=name[i+1];
                id[i]=id[i+1];
                quantity[i]=quantity[i+1];
             cat[i]=cat[i+1];
             price[i]=price[i+1];
             System.out.println("deleted successfully");
            break;
             
            }
            
        }
        if(found==0)
        {
            System.out.println("Product does not exist");
        }

    }

    void menu() {
        int ch = 0;
        while (true) {

            System.out.println(
                    "1.Add Product\n2.View Products\n3.Search Product\n4.Update Quantity\n5.Purchase Product\n6.Delete Product\n7.Exit");
            try {
                ch = sc.nextInt();
                sc.nextLine();

            } catch (Exception e) {
                System.out.println("Invalid input");
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
                    updateQuantity();
                    break;
                case 5:
                    purchaseProduct();
                    break;
                case 6:
                    deleteQuantity();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Inavlid input");
                    break;
            }
        }
    }
}

public class Mp14 {
    public static void main(String[] args) {
        Inventory i = new Inventory();
        i.menu();
    }
}