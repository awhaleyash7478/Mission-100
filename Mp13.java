import java.util.Scanner;

class Transactionthread extends Thread
{
    public void run()
    {
        try{
        System.out.println("Processing Deposit...");
        Thread.sleep(2000);
    }catch(InterruptedException e)
    {}
     try{
        System.out.println("Processing Transfer...");
        Thread.sleep(2000);
    }catch(InterruptedException e)
    {}
        System.out.println("Transfer Succesfull...");
}
}

class BankManaer extends Transactionthread
{
    Scanner sc=new Scanner(System.in);
    
  
        String accHoldName[]=new String[1000];
        int accNo[]=new int[1000];
        double balance[]=new double[1000];
        String accType[]=new String[1000];
    void create_Account()
    {
        
        int i;
          int no=0;
        System.out.println("Enter the Account holder name:");
        String name=sc.nextLine();
        System.out.println("Enter the Account Number:");
         no=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the balance:");
        double bal=sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter the Account Type:");
        String type=sc.nextLine();
        for(i=0;i<1000;i++)
        {
            if(accHoldName[i]==null)
            {
        accHoldName[i]=name;
        accNo[i]=no;
        balance[i]=bal;
        accType[i]=type;
        break;
            }
        
    }
}
void view_Account()
{
    
      int i;
    

             for(i=0;i<1000;i++)
    {
          if(accNo[i]==0)
          {
            System.out.println("No accounts registered");
            break;
          }   
         System.out.println("Account holder name: "+accHoldName[i]+"\nAccount number: "+accNo[i]+"\nAccount balance: "+balance[i]+"\nAccount type: "+accType[i]);

    }
        
           
   
   
}
void deposit(){
 double depositamount=0;
    int i;
    int searaccno;

    System.out.println("Enter the account number:");
    searaccno=sc.nextInt();

    System.out.println("Enter the amount to deposit:");
    try{
    depositamount=sc.nextDouble();
    if(depositamount<0)
        System.out.println("amount cant be negative");
}catch(Exception e)
{
    System.out.println("error");
}
int found=0;

    for(i=0;i<1000;i++)
    {
        found=1;

        if(searaccno==accNo[i])
        {
            balance[i]+=depositamount;
            break;
        }
    }
    if(found==0)
    {
        System.out.println("account does not exist");
    }
}
void withdraw()
{
    double withdrawamount=0;
    int i;
    int searaccno;

    System.out.println("Enter the account number:");
    searaccno=sc.nextInt();

    System.out.println("Enter the amount to deposit:");
    try{
    withdrawamount=sc.nextDouble();
    if(withdrawamount<0)
        System.out.println("amount cant be negative");
}catch(Exception e)
{
    System.out.println("error");
}
int found=1;

    for(i=0;i<1000;i++)
    {
        found=0;

        if(searaccno==accNo[i])
        {
            balance[i]-=withdrawamount;
            break;
        }
    }
    if(found==0)
    {
        System.out.println("account does not exist");
    }
}
void transfer()
{
    int found1=0,i;
    double amount=0;
    System.out.println("Enter the senders account number:");
    int sender=sc.nextInt();
    for(i=0;i<1000;i++)
    {
    if(sender==accNo[i])
    {found1=1;
        break;
    }
}
    if(found1==0)
    {
        System.out.println("Account not found");
       
    
    }


    System.out.println("Enter the receiver account number:");
    int receiver=sc.nextInt();
    int found=0;
    for( i=0;i<1000;i++)
    {

        if(receiver==accNo[i])
        {
            
            found =1;
            System.out.println("Enter the amount:");

           
            try 
            {
            amount=sc.nextDouble();
             
            if(amount<0)
                System.out.println("amount cant be negative");

        }catch(Exception e)
        {
            System.out.println("error");
        }
            balance[i]+=amount;
             run();
            break;
            
        }
       
    }
    if(found==0)
    {
        System.out.println("Account not found");
    }
}
void search()
{
    int accountno=0,found=0;
    System.out.println("Enter the account Number:");
    try 
    {
        accountno=sc.nextInt();
        
    }catch(Exception e)
    {
        System.out.println("enter the valid account number");
    }
    for(int i=0;i<1000;i++)
    {
        if(accountno==accNo[i]){

            found=1;
                        System.out.println("Account holder name: "+accHoldName[i]+"\nAccount number: "+accNo[i]+"\nAccount balance: "+balance[i]+"\nAccount type: "+accType[i]);

        }
                    
 
        }
        if(found==0)
        {
            System.out.println("account not found");
        }
    }
    void delete()
    {
        int i,found=0;
        System.out.println("Enter the account number:");
        int account=sc.nextInt();
        for(i=0;i<1000;i++)
        {
            if(account==accNo[i])
            {
                found =1;
                //break;
                for(int j=0;j<1000-1;j++)
                {
                    accNo[j]=accNo[j+1];

                }
            }
        }
        if(found==0)
            System.out.println("account not found");
    }
    

    void menu()
    {
       
            while (true) {

                 System.out.println("1. Create Account\r\n" + //
                        "2. View Accounts\r\n" + //
                        "3. Deposit Money\r\n" + //
                        "4. Withdraw Money\r\n" + //
                        "5. Transfer Money\r\n" + //
                        "6. Search Account\r\n" + //
                        "7. Delete Account\r\n" + //
                        "8. Exit");
                        int ch=0;
                        System.out.println("Enter the choice:");
                        try{
                            ch=sc.nextInt();
                              sc.nextLine();
                        }
                        catch(Exception e)
                        {
                            System.out.println("Invalid choice");
                        }
                        switch (ch) {
                            case 1:create_Account();
                            break;
                            
                            case 2:view_Account();
                            break;
                            
                            case 3:deposit();
                       
                            break;

                            case 4:withdraw();
                            
                            break;
                            
                            case 5:transfer(); 
                                                     
                            break;
                            
                            case 6:search();
                            break;

                            case 7:delete();
                            break;
                            case 8:
                                return;
                            
                            default:
                                System.out.println("invalid input");
                                break;
                        }

                
                

            }
    }
}
class Mp13
{
    public static void main(String[] args) {
        BankManaer m=new BankManaer();
        m.menu();
    }
}
