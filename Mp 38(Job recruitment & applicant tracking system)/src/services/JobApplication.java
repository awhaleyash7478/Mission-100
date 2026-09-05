package services;
import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.sql.*;
import java.sql.Date;

public class JobApplication {
    Connection conn;
    Scanner sc;
    public JobApplication(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
    
    public void searchJobs()
    {
        String jobTitle=null;
        String location=null;
        
         System.out.println("Enter the Job title: ");
         jobTitle=sc.nextLine().toLowerCase();
        System.out.println("Enter the Location: ");
         location=sc.nextLine().toLowerCase();
         
        int found=0;
           
     

        try {
         String query="select *from jobs where position like ? and location like ?";
         

         
         PreparedStatement ps=conn.prepareStatement(query);
         ps.setString(1, "%"+jobTitle+"%");
         ps.setString(2, location+"%");

         ResultSet rs=ps.executeQuery();
         System.out.printf("%-10s %-30s %-30s %-30s%n",
        "Job ID", "Position", "Company", "Location");

System.out.println("-------------------------------------------------------------------------------------");

     
         while (rs.next()) {
            found=1;
            
           
            System.out.printf("%-10d %-30s %-30s %-30s%n",
        rs.getInt("job_id"),
        rs.getString("position"),
        rs.getString("company"),
        rs.getString("location"));


          

            

            
            
         }
         System.out.println("-------------------------------------------------------------------------------------");
        }catch(Exception e)
        {
            e.printStackTrace();
        }

         
         if(found==0)
         { System.out.println();
            System.out.println("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
            System.out.println("No Such Jobs or not in given Location");
            System.out.println("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
            System.out.println();
            System.out.println("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
            System.out.println("Other Similar Job matches");
           System.out.println("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
            try {
                  String query2="select *from jobs where position like ?";
         

         
         PreparedStatement pp=conn.prepareStatement(query2);
         pp.setString(1, "%"+jobTitle+"%");
 

         ResultSet rr=pp.executeQuery();
         System.out.printf("%-10s %-30s %-30s %-30s%n",
        "Job ID", "Position", "Company", "Location");

System.out.println("-------------------------------------------------------------------------------------");
            

    
     
         while (rr.next()) {
            found=1;
            
           
            System.out.printf("%-10d %-30s %-30s %-30s%n",
        rr.getInt("job_id"),
        rr.getString("position"),
        rr.getString("company"),
        rr.getString("location"));


          

            

            
            
         }
         System.out.println("-------------------------------------------------------------------------------------");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    while (true) {
        
    
         System.out.println("1.View Job Details        2.Exit");
         int choice=0;
      
            
         
         try 
         {
            choice=sc.nextInt();
            sc.nextLine();
         }catch(Exception e)
         {
            System.out.println("Pls enter the valid choice[For eg:2 for Exit]");
            sc.nextLine();
            continue;
         }
        
        switch (choice) {
            case 1:
                viewJobDetails();
                
                break;
            case 2:
                return ;
        
            default:
                System.out.println("Invalid Choice pls enter the valid choice");
                break;
        }
    }
     
      
        
       
              
            
                

       
        
        
    

         
    
    

    
}
public  void viewJobDetails()
{
    int tempjobId=0;
    while (true) {
        
    
    System.out.println("Enter the Job Id: ");
    try 
    {
        tempjobId=sc.nextInt();
    }catch(Exception e)
    {
        System.out.println("Pls enter the valid Job id");
        sc.nextLine();
    continue;
    }
    break;
}
String position=null,company=null,location=null,experience=null,employment=null,salary=null,requiredSkills=null,description=null;
Date postedOn=null,applicationDeadline=null;
int openings=0;
try {
    String query ="select * from jobs where job_id=?";
    PreparedStatement ps=conn.prepareStatement(query);
    ps.setInt(1, tempjobId);
    ResultSet rs=ps.executeQuery();
    int jobId=0;
    if(rs.next())
    {
        jobId = rs.getInt("job_id");
    position = rs.getString("position");
    company = rs.getString("company");
    location = rs.getString("location");
    experience = rs.getString("experience");
    employment = rs.getString("employment");
    salary = rs.getString("salary");
    requiredSkills = rs.getString("required_skills");
    description = rs.getString("description");
    postedOn = rs.getDate("posted_on");
    applicationDeadline = rs.getDate("application_deadline");
    openings = rs.getInt("openings");
    }
    System.out.println("\n========== JOB DETAILS ==========");
System.out.println("Job ID              : " + jobId);
System.out.println("Position            : " + position);
System.out.println("Company             : " + company);
System.out.println("Location            : " + location);
System.out.println("Experience          : " + experience);
System.out.println("Employment          : " + employment);
System.out.println("Salary              : " + salary);
System.out.println("Required Skills     : " + requiredSkills);
System.out.println("Description         : " + description);
System.out.println("Posted On           : " + postedOn);
System.out.println("Application Deadline: " + applicationDeadline);
System.out.println("Openings            : " + openings);
System.out.println("================================");
} catch (Exception e) {
e.printStackTrace();
}
while(true)
{
System.out.println("\n1.Apply      2.Save      3.Exit");
int choice=0;

try{
    choice=sc.nextInt();
}catch(Exception e)
{
    System.out.println("Pls enter the valid option[Eg:1 for Apply]");
    sc.nextLine();
    continue;
}


switch (choice) {
    case 1:
     applyJob(tempjobId);
        
        break;
    case 2:
        saveJob(tempjobId);
    case 3:
        return ;

    default:
        System.out.println("Invalid entry ");
        break;
}
}



}
public void applyJob(int tempjobId)
{
    int jobId=tempjobId;
   
     try 
{
    String query="insert into applyJob(job_id,username)values(?,?)";
    PreparedStatement ps=conn.prepareStatement(query);
    ps.setInt(1, jobId);
    ps.setString(2, CustomerVerification.userName);
  int rows=  ps.executeUpdate();
  if(rows>0)
  {
    System.out.println("Applied for Job successfully");
    
  }else 
  {
    System.out.println("Unable to apply for Job");
  }

}catch(Exception e)
{
    e.printStackTrace();
}
    


}
public void saveJob(int tempjobId)
{
     int jobId=tempjobId;
   
     try 
{
    String query="insert into saveJob(job_id,username)values(?,?)";
    PreparedStatement ps=conn.prepareStatement(query);
    ps.setInt(1, jobId);
    ps.setString(2, CustomerVerification.userName);
  int rows=  ps.executeUpdate();
  if(rows>0)
  {
    System.out.println("Job  Saved successfully");
    
  }else 
  {
    System.out.println("Unable to Save Job");
  }

}catch(Exception e)
{
    e.printStackTrace();
}
}
}

    
