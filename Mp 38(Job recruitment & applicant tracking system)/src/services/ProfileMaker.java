package services;


import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProfileMaker {
    Connection conn;
    Scanner sc;
    public ProfileMaker(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
     String name,address,email,institue,qualification,role,mobNo,experience,company;
        int passYear=0,exYear=0;
        double percentage=0.0;
   String skill;
        ArrayList <String>skills =new ArrayList<String>();
              int applicant_no=0;
        public void createProfile()
    {

       
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
        int count=1;
        while(true)
        {
        
    System.out.print("\nSkill no."+count+": ");
    
    skill=sc.nextLine();
    //   try {
    //     String query="insert into applicant_skills (applicant_no,skills)values(?,?)";
    //     PreparedStatement ps1=conn.prepareStatement(query);
    //     ps1.setInt(1, applicant_no);
    //     ps1.setString(2, skill);
    //     ps1.executeUpdate();

        


    //    } catch (Exception e) {
    //     e.printStackTrace();
    //    }

    skills.add(skill);
      count++;
          int choice=0;
    System.out.println(skills);
    int flag=0;
    while(true)
    {

    System.out.println("1.Add     2.Exit");
        

    try 
    {
choice=sc.nextInt();
sc.nextLine();
    }catch(Exception e)
    {
        System.out.println("Invalid entry pls enter the option no. only");
        sc.nextLine();
        continue;
    }

 if(choice==1)
 {
    flag=1;
    break;
 }
     else if(choice==2)
    {
       break;
    }else
    {
        System.out.println("Invalid Option allowed is 1-2");
        sc.nextLine();
        continue;

        
    }

}
if(flag==1)
{
    continue;
}
break;

    }
        int choice=0;
        while (true) {
            
        
            
        System.out.println("\n-----Experience-----");
        System.out.println("1.Fresher   2.Experienced: ");
        try 
        {
        choice=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("Invalid input pls enter the option no. only");
            continue;
        }

        break;
    }
    if(choice==1)
    {
        experience="Fresher";

    }else if(choice==2)
    {
        experience="experienced";
    
        
        System.out.print("\nCompany: ");
        company=sc.nextLine();
        System.out.print("\nRole: ");
        role=sc.nextLine();
        while (true) {
            
        
        System.out.print("\nYears: ");
        try 
        {
        exYear=sc.nextInt();
    }catch(Exception e)
    {
        System.out.println("Invalid entry pls enter the number of years only");
        continue;
    }
    break;
}
}
        int ch=0;
        while (true) {
            
        
        System.out.println("\n1.Generate Profile    2.Make Changes        3.Exit");
        try 
        {
            ch=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("Invalid entry pls enter the valid option no.");
            sc.nextLine();
      continue;
        }
        if(ch==1)
        {
            genereateProfile();
        }else if(ch==2)
        {
            //make changes
        }else
        {
            return;
        }
   
    }                                  
    }
    public void genereateProfile()
    {
        try {
            String query="insert into applicant_profile (username,email,mob_no,location,qualification,institute,passingYear,percentage,experience,company,role,years)values(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps=conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,name);
            ps.setString(2, email);
            ps.setString(3, mobNo);
            ps.setString(4, address);
            ps.setString(5, qualification);
            ps.setString(6, institue);
            ps.setInt(7, passYear);
            ps.setDouble(8, percentage);
            
            ps.setString(9, experience);
            ps.setString(10, company);
            ps.setString(11, role);
            ps.setInt(12, exYear);
         ps.executeUpdate();
            ResultSet rr=ps.getGeneratedKeys();
            if(rr.next())
                applicant_no=rr.getInt(1);
            int rows= 0;
            System.out.println("applicant no: "+applicant_no);
            for(String nums:skills)
            {
                  System.out.println("nums: "+nums);
                  System.out.println("----------");
            String insert="insert into applicant_skills (applicant_no ,skills)values(?,?)";
            PreparedStatement pp=conn.prepareStatement(insert);
            pp.setInt(1, applicant_no);
            
            pp.setString(2, nums);
      
         rows=  pp.executeUpdate();
            }
           
            if(rows>0)
            {
                System.out.println("Profile Generated Successfully");

            }else 
            {
                System.out.println("Unable to Generate the Profile");
                return;
            }
        } catch (Exception e) {
           e.printStackTrace();
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
