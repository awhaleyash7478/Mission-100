package services;

import java.sql.Connection;
import java.util.Scanner;

public class ProfileMaker {
    Connection conn;
    Scanner sc;
    public ProfileMaker(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
    public void createProfile()
    {
        String name,address,email,institue,qualification,role,mobNo,experience,company;
        int passYear=0,exYear=0;
        double percentage=0.0;

        String skills=null;
       System.out.println("-----Personal Information-----");
       System.out.print("Name: ");
       name=sc.nextLine();
while (true) {
    

        System.out.print("\nEmail: ");
        email=sc.nextLine();
           
            String regex="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if(email.matches(regex))
            {
                break;
            }else 
            {
                System.out.println("Invalid email id");
                continue;
            }

}
while (true) {
    

        System.out.print("\nMobile: "); 
        mobNo=sc.nextLine();


if (!mobNo.matches("[7-9][0-9]{9}")) {
    
    System.out.println("Invalid mobile number");
    continue;
}
break;    }
        while (true) {
            
        
        System.out.print("\nLocation: ");
       
         address=sc.nextLine().trim();
   String regex = "^[A-Za-z0-9\\s,./#()'-]{5,100}$";
    if( !address.matches(regex))
    {
        System.out.println("Invalid address pls enter the valid address");
        continue;
    }
    break;
}
        System.out.println("\n-----Education-----");
        System.out.print("\nQualification: ");
        qualification=sc.nextLine();
        System.out.print("\nInstitute: ");
        institue=sc.nextLine();
        System.out.print("\nPassing Year: ");
        passYear=sc.nextInt();
        System.out.print("\nPercentage/CGPA: ");
        percentage=sc.nextDouble();
        sc.nextLine();
        System.out.println("\n-----Skills-----");
        System.out.print("Skills: ");
        skills=sc.nextLine();
        System.out.println("\n-----Experience-----");
        System.out.print("Fresher/Experienced: ");
        experience=sc.nextLine();

        System.out.print("\nCompany: ");
        company=sc.nextLine();
        System.out.print("\nRole: ");
        role=sc.nextLine();
        System.out.print("\nYears: ");
        exYear=sc.nextInt();
        int choice=0;
        while (true) {
            
        
        System.out.println("\n1.Generate Profile    2.Make Changes        3.Exit");
        try 
        {
            choice=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("Invalid entry pls enter the valid option no.");
            sc.nextLine();
      continue;
        }
        if(choice==1)
        {
            //generate the profile
        }else if(choice==2)
        {
            //make changes
        }else
        {
            return;
        }
    }                                  
    }
    public void menu()
    {
        int ch=0;
        while (true) {
            
        
        System.out.println("1.Create Profile   2.Exit");
        try 
        {
            ch=sc.nextInt();
            sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("Pls enter the valid choice only [eg:1 for Create Profle]");
            sc.nextLine();
            continue;
        }
        if(ch==1)
        {
          createProfile();
        }else if(ch==2)
        {
            return;
        }else 
        {
            System.out.println("Allowed choices are 1 and 2");
            sc.nextLine();
            continue;
        }
    }
}
    
}
