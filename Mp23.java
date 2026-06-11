import java.util.Scanner;

class TransactionThread extends Thread
{
    Bank obj;
    Scanner sc;
    TransactionThread(Bank obj,Scanner sc)
    
    {
        this.sc=sc;
        this.obj=obj;
        

    }
    int sourceAccNo=0;
    int desAccNo=0;
    double balance=0.0;
    public void run()
    {
        try 
        {
          System.out.println("Enter the  sender's account number:");
          sourceAccNo=sc.nextInt();

        }catch(Exception e)
        {
            System.out.println("invalid entry");
        }
        for(int i=0;i<obj.max;i++)
        {
            if(sourceAccNo==obj.accNo[i])
            {
                
                System.out.println("Enter the receiver's account number:");
                desAccNo=sc.nextInt();
                for(int j=0;j<obj.max;j++)
                {
                if(desAccNo==obj.accNo[j])
                {
                    System.out.println("Enter the amount to transfer:");
                    balance=sc.nextDouble();
                    if(balance<0)
                    {
                        System.out.println("Amount cannot be negative");
                        break;
                    }else if(balance>obj.balance[i])
                    {
                        System.out.println("insufficient balance");
                        break;
                    }
                    obj.balance[j]+=balance;
 System.out.println("Amount transferred successfully");
                }
            }
                
                obj.balance[i]-=balance;
                break;

            }
        }
    }
}

class Bank
{
    final  int max=100;
    Scanner sc=new Scanner(System.in);
    int accNo[]=new int[max];
    String cusName[]=new String[max];
    double balance[]=new double[max];
    void createAccount()
    {
        int tempAccNo=0;
        String tempCusName=null;
        double tempAccBal=0.0;
        try 
        {
        System.out.println("Enter the account number:");
        tempAccNo=sc.nextInt();
        sc.nextLine();
       System.out.println("Enter the Customer name:");
       tempCusName=sc.nextLine();    
       System.out.println("Enter the Account balance:");
       tempAccBal=sc.nextDouble();
    }catch(Exception e)
    {
        System.out.println("invalid entry");
        sc.nextLine();
    }
    for(int i=0;i<max;i++)
    {
        if(accNo[i]==0)
        {
        accNo[i]=tempAccNo;
        balance[i]=tempAccBal;
        cusName[i]=tempCusName;
        System.out.println("Account added successfully");

break;}
    }


    }
    void viewAccount()
    {
        for(int i=0;i<max;i++)
        {
            if(i==0)
            {
                if(accNo[i]==0)
                {
                    System.out.println("Pls add account");
                    break;
                }
            }

            if(accNo[i]==0)
                break;
            System.out.println("Account holder Name: "+cusName[i]+"\nAccount number: "+accNo[i]+"\nAccount balance: "+balance[i]);

        }
    }
    void searchAccount()
    {
        int found=0;
        int searchId=0;
        try 
        {
            System.out.println("Enter the account number:");
            searchId=sc.nextInt();
            
        }catch(Exception e)
        {
            System.out.println("Invalid entry");
            sc.nextLine();
        }
        for(int i=0;i<max;i++)
        {
            if(searchId==accNo[i])
            {
                found=1;
                    System.out.println("Account holder Name: "+cusName[i]+"\nAccount number: "+accNo[i]+"\nAccount balance: "+balance[i]);
break;
            }
        }
        if(found==0)
        {
            System.out.println("Account not found");
        }

    }
    void depositMoney()
    {
        int tempAccNo=0;
        double tempDepo=0.0;
        try{
            System.out.println("Enter the account number:");
            tempAccNo=sc.nextInt();
            
        }catch(Exception e)
        {
            System.out.println("Invalid entry");
            sc.nextLine();
        }
        int found=0;
        for(int i=0;i<max;i++)
        {
            if(tempAccNo==accNo[i])
            {
                found=1;
                System.out.println("Enter the amount:");
                tempDepo=sc.nextDouble();
                if(tempDepo<0)
                {
                    System.out.println("Amount cant be negative");
                    break;
                }
                balance[i]+=tempDepo;
                System.out.println("Amount deposited successfully");
                break;
            }
        }
        if(found==0)
        System.out.println("Account not found");
    }
    void withdrawMoney()
    {
        double tempWith=0.0;
        int tempAccNo=0;
        try 
        {
            System.out.println("Enter the account number:");
            tempAccNo=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("Invalid entry");
            sc.nextLine();
        }
        int found=0;
        for(int i=0;i<max;i++)
        {
            if(tempAccNo==accNo[i])
            {
                found=1;
               
                System.out.println("Enter the amount:");
                tempWith=sc.nextDouble();
                if(tempWith<0)
                {
                    System.out.println("Amount cant be negative");
                    break;

                }else if(tempWith>balance[i])
                {
                    System.out.println("insufficient balance"+"Available balance: "+balance[i]);
                    break;
                }
                balance[i]-=tempWith;
                System.out.println("Amount withdrawn successfully");
                break;
            }
        }
        if(found==0)
            System.out.println("Account not found");
    }



    void menu()
    {
        int ch=0;
        while (true) {
        
        
        System.out.println("1.Create Account\n2.View Account\n3.Search Account\n4.Deposit Money\n5.Withdraw Money\n6.Transfer Money\nExit");
    try
{
    ch=sc.nextInt();
    sc.nextLine();
}catch(Exception e)
{
System.out.println("Invalid entry");

sc.nextLine();}   
 
switch (ch) {
    case 1:
        createAccount();
        
        break;
    case 2:
        viewAccount();
        break;
    case 3:
        searchAccount();
        break;
    case 4:
        depositMoney();
        break;
    case 5:
        withdrawMoney();
        break;
    case 6:
        TransactionThread t=new TransactionThread(this,sc);
        t.run();
        break;
case 7:
    return;
            default:
                System.out.println("Invalid input");
        break;
}
}
}
}class Mp23
{
    public static void main(String[] args) {
        Bank b=new Bank();
        b.menu();
    }
}