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
        while(true)
        {
        System.out.print("\nPercentage/CGPA: ");
        try 
        {
        percentage=sc.nextDouble();
         sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("Pls enter the valid Input");
            sc.nextLine();
            continue;
        }
        break;
         
        }
       
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
            
        
        System.out.println("\n1.Generate Profile    2.Update Profile       3.Exit");
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
          updateProfile();
        }else
        {
            return;
        }
   
    }                                  
    }
    public void updateProfile()
    {
        String column=null;
        String field=null;
        
        while (true) {
            
        
        System.out.println("{Note:Enter the Number to update the field[for eg:1 for Name]}");
        System.out.println("1.Name     2.Email     3.MobNo     4.Location     5.Qualification");
        System.out.println("6.Institute     7.Passing Year     8.Percentage/CGPA");
        System.out.println("9.Experience     10.Company     11.Role     12.Years     13.Exit");
        
   int choice=0;
   System.out.println("Enter the choice: ");
   try 
   {
    choice=sc.nextInt();
   }catch(Exception e)
   {
    System.out.println("{Note:Enter the Number to column the field[for eg:1 for Name]}");
    sc.nextLine();
    continue;
    
   }
   switch (choice) {
    case 1:
        System.out.println(" Old Name: "+name);
        System.out.print("Updated Name: ");
        name=sc.nextLine();
        column="username";
        field=name;
   

        
        break;
    case 2:
        System.out.println("Old Email: "+email);
        column="email";
      
             
while (true) {
    

    System.out.print("Updated Email: ");
        email=sc.nextLine();
         field=email;

       

           
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

break;
     case 3:
        System.out.println("Old MobNo: "+mobNo);
         column="mob_no";
while (true) {
    

        System.out.print("Updated MobNo: "); 
        mobNo=sc.nextLine();
        field=mobNo;


if (!mobNo.matches("[7-9][0-9]{9}")) {
    
    System.out.println("Invalid mobile number");
    continue;
}
break;}
break;
     case 4:
        System.out.println("Old Location: "+address);
        column="location";
        while (true) {
            
        
        System.out.print("Updated Location: ");
       
         address=sc.nextLine().trim();
         field=address;
   String regex = "^[A-Za-z0-9\\s,./#()'-]{5,100}$";
    if( !address.matches(regex))
    {
        System.out.println("Invalid address pls enter the valid address");
        continue;
    }
    break;
}
break;
    case 5:
        column="qualification";
        System.out.println("Old Qualification: "+qualification);
        System.out.println("Updated Qualification: ");
        qualification=sc.nextLine();
        field=qualification;

        break;
    case 6:
        column="institute";
        System.out.println("Old Institute: "+institue);
        System.out.print("Updated Institute: ");
        institue=sc.nextLine();
        field=institue;
   
        break;
    case 7:
        column="passingYear";
        System.out.println("Old Passing Year: "+passYear);
        while (true) {
            System.out.print("New Passing Year: ");
            try 
            {
            passYear=sc.nextInt();
            field=String.valueOf(passYear);
            
            }catch(Exception e)
            {
                System.out.println("Pls enter the valid year only");
                sc.nextLine();
                continue;
            }
            break;


            
        }
        break;
    case 8:
        column="percentage";
        System.out.println("Old Percentage/CGPA: "+percentage);
        System.out.println("Updated Percentage/CGPA: ");
        while (true) {
            
        
        try {
        percentage=sc.nextDouble();
        field=String.valueOf(percentage);
        }catch(Exception e)
        {
            System.out.println("Pls enter the valid Percentage/CGPA only");
            continue;
        }
        break;
    }

        break;
    case 9:
        column="experience";
        System.out.println("Old Profile: "+experience);
        System.out.println("New Profile: ");
        experience=sc.nextLine();
        field=experience;

        break;
    case 10:
        column="company";
        System.out.println("Old Company: "+company);
        System.out.print("New Company: ");
        company=sc.nextLine();
        field=company;
      
        break;
    case 11:
        column="role";
        System.out.println("Old role: "+role);
        System.out.print("New role: ");
        role=sc.nextLine();
        field=role;
 
        break;
    case 12:
        column="years";
        System.out.println("Old Experience Years: "+exYear);
        while (true) {
            
        
        System.out.print("New Experience Years: ");
        try 
        {
        exYear=sc.nextInt();
field=String.valueOf(exYear);
    }catch(Exception e)
        {
            System.out.println("Pls enter the valid Year Only");
            continue;
        }
        break;
        }
        break;
    case 13:


        return;
           
    default:
        System.out.println("Pls enter the valid option Only[1-12]");
        break;
    }
    System.out.println("Hey out from Switch");
try {
    System.out.println("hey the column is:"+column);
        String query="update applicant_profile set "+column+"= ? where registername=?";

    PreparedStatement preparedStatement=conn.prepareStatement(query);
    preparedStatement.setString(1, field);
    preparedStatement.setString(2, CustomerVerification.userName);
   int rows= preparedStatement.executeUpdate();
     if(rows>0)
     {
      
        System.out.println(column+" updated successfully");
    
    }else 
     {
        System.out.println("Unable to update "+column);
     }
} catch (Exception e) {
    e.printStackTrace();
}

   
    

   
    

        


        
        
        

       
    
}
}
    public void genereateProfile()
    {
        try {
            String query="insert into applicant_profile (username,email,mob_no,location,qualification,institute,passingYear,percentage,experience,company,role,years,registername)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            ps.setString(13,CustomerVerification.userName);
            System.out.println("username: "+CustomerVerification.userName);
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
