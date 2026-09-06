package services;
import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.sql.*;
import java.sql.Date;
import java.time.*;
public class JobApplication {
    Connection conn;
    Scanner sc;
    public JobApplication(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
    ArrayList <Integer>list=new ArrayList<>();
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
            int fetchedJobID=rs.getInt("job_id");
            found=1;
            
           
            System.out.printf("%-10d %-30s %-30s %-30s%n",
   rs.getInt("job_id"),
        rs.getString("position"),
        rs.getString("company"),
        rs.getString("location"));
              list.add(fetchedJobID);


          

            

            
            
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
            int fetch=rr.getInt("job_id");
           
            System.out.printf("%-10d %-30s %-30s %-30s%n",
        rr.getInt("job_id"),
        rr.getString("position"),
        rr.getString("company"),
        rr.getString("location"));
        list.add(fetch);


          

            

            
            
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
    if(list.contains(tempjobId))
    {
        break;
    }else
    {
        System.out.println("No job with this id exist");
        sc.nextLine();
        continue;
    }
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
    }else 
    {
        System.out.println("No Job with this ID exist");
        return;
    }
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
        break;
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
    LocalDate currDate=LocalDate.now();

    Date deadLine=null;
    try {
String query="select username from applicant_profile where registername=?";
PreparedStatement ps=conn.prepareStatement(query);
ps.setString(1, CustomerVerification.userName);
ResultSet rs=ps.executeQuery();
if(!rs.next())
{
    System.out.println("Pls Generate Your Profile First");
    return ;
}
    } catch (Exception e) {
        e.printStackTrace();
    }
      int openings=0;
    try {
      

        String query="select * from jobs where job_id=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, jobId);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
            deadLine=rs.getDate("application_deadline");
            openings=rs.getInt("openings");
            
          
            


        }
               if(currDate.isAfter(deadLine.toLocalDate()))
        {
            System.out.println("Application Deadline has passesd");
            return ;

        }
    } catch (Exception e) {
       e.printStackTrace();}
   
     try 
{
    String query="insert into applyJob(job_id,username)values(?,?)";
    PreparedStatement ps=conn.prepareStatement(query);
    ps.setInt(1, jobId);
    ps.setString(2, CustomerVerification.userName);
  int rows=  ps.executeUpdate();
  if(rows>0)
  {
     
          
            if(openings!=0)
            {
                 openings--;
            String set="update jobs set openings=? where job_id=?";
            PreparedStatement pp=conn.prepareStatement(set);
            pp.setInt(1, openings);
            pp.setInt(2, jobId);
            pp.executeUpdate();
            }else 
            {
                System.out.println("No Vacancies Available");
                return ;
            }
           
              
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
public void viewSavedJobs()
{
    try {
        
        String query="select job_id from saveJob where username=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, CustomerVerification.userName);
        ResultSet rs=ps.executeQuery();
          int jobId=0,job_Id=0;
          String position=null,company=null,location=null,experience=null,employment=null,salary=null,requiredSkills=null,description=null;
Date postedOn=null,applicationDeadline=null;
int openings=0;
int flag=0;
        while(rs.next())
        {
            flag=1;
      jobId=rs.getInt("job_id");
       
        String fetch="select * from jobs where job_id=?";
        PreparedStatement pp=conn.prepareStatement(fetch);
        pp.setInt(1, jobId);
        ResultSet rr=pp.executeQuery();
        while (rr.next()) {

            job_Id = rr.getInt("job_id");
    position = rr.getString("position");
    company = rr.getString("company");
    location = rr.getString("location");
    experience = rr.getString("experience");
    employment = rr.getString("employment");
    salary = rr.getString("salary");
    requiredSkills = rr.getString("required_skills");
    description = rr.getString("description");
    postedOn = rr.getDate("posted_on");
    applicationDeadline = rr.getDate("application_deadline");
    openings = rr.getInt("openings");

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
    

            
        }
        }
        if(flag==0)
        {
            System.out.println("No Saved Jobs");
            return;
        }


    } catch (Exception e) {
       e.printStackTrace();
    }
}
public void viewAppliedJobs()
{
    try {
        String query="select job_id from applyJob where username=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, CustomerVerification.userName);
        ResultSet rs=ps.executeQuery();
         int jobId=0,job_Id=0;
          String position=null,company=null,location=null,experience=null,employment=null,salary=null,requiredSkills=null,description=null;
Date postedOn=null,applicationDeadline=null;
        int flag=0;
int openings=0;
        while (rs.next()) {
             flag=1;
            jobId=rs.getInt("job_id");
              String fetch="select * from jobs where job_id=?";
        PreparedStatement pp=conn.prepareStatement(fetch);
        pp.setInt(1, jobId);
        ResultSet rr=pp.executeQuery();

        while (rr.next()) {
           

            job_Id = rr.getInt("job_id");
    position = rr.getString("position");
    company = rr.getString("company");
    location = rr.getString("location");
    experience = rr.getString("experience");
    employment = rr.getString("employment");
    salary = rr.getString("salary");
    requiredSkills = rr.getString("required_skills");
    description = rr.getString("description");
    postedOn = rr.getDate("posted_on");
    applicationDeadline = rr.getDate("application_deadline");
    openings = rr.getInt("openings");

     System.out.println("\n========== JOB DETAILS ==========");
System.out.println("Job ID              : " + job_Id);
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

            
        }
    }
    if(flag==0)
    {
        System.out.println("NO Jobs Applied");
        return;
    }
 } catch (Exception e) {
       e.printStackTrace();
    }
}

}



    
