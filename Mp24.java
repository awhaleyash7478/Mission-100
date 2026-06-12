import java.util.Scanner;

class Library
{
    final int max=100;
    Scanner sc=new Scanner(System.in);
    int bookId[]=new int[max];
    String bookName[]=new String[max];
    String author[]=new String[max];
    int quantity[]=new int[max];
    int memberId[]=new int[max];
    String memberName[]=new String[max];
    String memberType[]=new String[max];
    String issueStatus[]=new String[max];
    int hisIssueBookId[]=new int[max];
    String hisIssueStatus[]=new String[max];
    int hisissueMemberId[]=new int[max];
    void addBook()
    {
      
        int tempBookId=0,tempQuantity=0;
        String tempAuthorName=null,tempBookName=null;
        try{
        System.out.println("Enter the book id:");
        tempBookId=sc.nextInt();
        sc.nextLine();
  System.out.println("Enter the book name:");
  tempBookName=sc.nextLine();
  System.out.println("Enter the book author name:");
  tempAuthorName=sc.nextLine();
  System.out.println("Enter the quantity:");
  tempQuantity=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("invalid entry");
            sc.nextLine();
        }
        for(int i=0;i<max;i++)
        {
            if(bookId[i]==0)
            {
            bookId[i]=tempBookId;
            bookName[i]=tempBookName;
            author[i]=tempAuthorName;
            quantity[i]=tempQuantity;
            System.out.println("Book added successfully");
break;
            }
        }
    }
    void viewBook()
    {

        for(int i=0;i<max;i++)
        {
            if(i==0)
            {
            if(bookId[i]==0)
               
               { System.out.println("Pls add the book");

                break;
}

}else if(bookId[i]==0) 
{
break;
}

            System.out.println("Book name: "+bookName[i]+"\n Author: "+author[i]+"\nBook id:"+bookId[i]+"\nQuantity: "+quantity[i]);

        }
    }
    void registerMembers()
    {
        int tempMemberId=0;
        String tempName=null,tempType=null;
        
        try 
        {
            System.out.println("Enter the member id:");
            tempMemberId=sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the Member name:");
            tempName=sc.nextLine();
            System.out.println("Enter the member type:");
            tempType=sc.nextLine();
        
        }catch(Exception e)
        {
            System.out.println("Invalid entry");
            sc.nextLine();
        }  
        for(int i=0;i<max;i++)
        {
            if(memberId[i]==0)
            {
                memberId[i]=tempMemberId;
                memberName[i]=tempName;
                memberType[i]=tempType;
                System.out.println("Member added successfully");
                break;
            }
        }}
        void viewMembers()
        {
            for(int i=0;i<max;i++)
            {
                if(i==0)
                    if(memberId[i]==0)
                    {
                        System.out.println("pls add the member");
                        break;
                    }
            
            if(memberId[i]==0)
            break;
    
        System.out.println("Member name: "+memberName[i]+"\nMember type: "+memberType[i]+"\nMember id: "+memberId[i]);
    

    
        }
            
        }
        void issueBook()
        {
            int tempBookId=0,found=0,bookFound=0;
            int tempid=0;
            int tempQuantity=0;

            try 
            {
                System.out.println("Enter the member id to issue book:");
                tempid=sc.nextInt();
            }catch(Exception e)
            {
                System.out.println("Invalid entry");
                sc.nextLine();
            }
            for(int i=0;i<max;i++)
            {
                if(tempid==memberId[i])
                {
                    found=1;

                    System.out.println("Enter the book id:");
                    tempBookId=sc.nextInt();
                    System.out.println("Enter the quantity:");
                    tempQuantity=sc.nextInt();
                    if(tempQuantity<0)
                    {
                        System.out.println("Quantity cannot be negative:");
                        break;
                    }
                    else if(tempQuantity>quantity[i])
                    {
                        System.out.println("outoff stock"+"\navailable quantity: "+quantity[i]);
                        break;
                    }
                    for(int j=0;j<max;j++)
                    {
                    if(tempBookId==bookId[j])
                    {
                       
                        
                        
                          bookFound=1;
                          quantity[j]-=tempQuantity;
                   
                         
                          hisIssueStatus[j]="Issued";
                          hisIssueBookId[j]=bookId[j];
                                  if(hisIssueBookId[j]==tempBookId){
                            System.out.println("U cannot issue same book twice");
                            return;
                           
                        }
                          System.out.println("Book issued successfully");
                          break;  
                               
                          
                    }

                }
                
                if(bookFound==0)
                {
                    System.out.println("Book not found");
                    break;
                }
                hisissueMemberId[i]=memberId[i];
                }
            }
            if(found==0)
            {
                System.out.println("Member not found");
            }
        }
        void returnBook()
        {
            int tempBookId=0,found=0;
          try 
          {
            System.out.println("Enter the book id:");
            tempBookId=sc.nextInt();

          }catch(Exception e)
          {
            System.out.println("Invalid entry");
            sc.nextLine();
          }
          for(int i=0;i<max;i++)
          {
            if(tempBookId==hisIssueBookId[i])
            {
                System.out.println("Enter the quantity:");
                int temoQuantity=sc.nextInt();
                if(temoQuantity<0)
                {
                    System.out.println("quantity can't be negative ");
                    break;
                }
                found=1;
                quantity[i]+=temoQuantity;
                hisIssueStatus[i]="Returned";
                System.out.println("Book returned successfully");
                break;
            }
          }
          if(found==0)
          {
            System.out.println("Book must be issued");
          }
        }
        void searchBook()
        {
            int searchID=0,found=0;
            try 
            {
                System.out.println("Enter the search id:");
                searchID=sc.nextInt();
            }catch(Exception e)
            {
                System.out.println("invalid entry");
                sc.nextLine();
            }
            for(int i=0;i<max;i++)
            {
                if(searchID==bookId[i])
                {
                    found=1;
                    
            System.out.println("Book name: "+bookName[i]+"\n Author: "+author[i]+"\nBook id:"+bookId[i]+"\nQuantity: "+quantity[i]);
            break;
                }
            }
            if(found==0)
            {
                System.out.println("book not found");
            }

        }
        void viewIssueHistory()
        {
            for(int i=0;i<max;i++)
            {
                if(i==0)
                {
                    if(hisIssueBookId[i]==0)
                    {
                        System.out.println("no books issued");
                        break;
                    }
                }
                System.out.println("Book id: "+hisIssueBookId[i]+"\nBook status: "+hisIssueStatus[i]+"\nMember id: "+hisissueMemberId[i]);
                break;
            }

        }
    
    void menu()
    {
        int ch=0;
        while (true) {
            
        
        System.out.println("1.Add Book\n2.View Book\n3.Register Memebers\n4.View Members\n5.Issue Book\n6.Return Book\n7.Search book\n8.View Issue history\n9.Exit");
        try 
        {
        ch=sc.nextInt();
    }catch(Exception e)
    {
        System.out.println("Invalid entry");
        sc.nextLine();
        continue;
    }

  switch (ch) {
    case 1:
        addBook();
        
        break;
    case 2:
        viewBook();
        break;
    case 3:
        registerMembers();
        break;
    case 4:
        viewMembers();
        break;
    
        case 5:
            issueBook();
            break;
        case 6:
            returnBook();
            break;
        case 7:searchBook();
        break;
        case 8:
            viewIssueHistory();
            break;
        case 9:
            return;
  
    default:
        System.out.println("Invalid choice");
        break;
  }

    }
}}
class Mp24
{
    public static void main(String[] args) {
        Library l=new Library();
        l.menu();
    }
}