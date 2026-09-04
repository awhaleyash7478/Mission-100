package services;
import java.util.*;
import java.sql.*;

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
        
        // System.out.println("Enter the Job title: ");
        // jobTitle=sc.nextLine();
        // System.out.println("Enter the Location: ");
        // location=sc.nextLine();
           
        LinkedHashMap <String,String>h=new LinkedHashMap<>();

        try {
         String query="select *from jobs";
         PreparedStatement ps=conn.prepareStatement(query);
         
         ResultSet rs=ps.executeQuery();
     
         while (rs.next()) {
           
            String positions=rs.getString("position");
            String locations=rs.getString("location");
            h.put(positions, locations);
          

            

            
            
         }
          String ss=null;
        //   System.out.println(h.entrySet());
         // System.out.print(entry.getKey());
            // System.out.print("        ");
            // System.out.print(entry.getValue());
            // System.out.println();
            //                
            // entry.getKey(),
            jobTitle="java";

  for(String s:h.keySet())
  {
    if(s.toLowerCase().contains(jobTitle.toLowerCase()))
    {
        System.out.println("hi");
        System.out.println("key: "+s);
       System.out.println("value: "+h.get(s)); 
      

    }
  }
        
        // for(String key:h.keySet())
        // {
        //     System.out.println(key);
        //     // for(String value:h.values())
        //     // {
        //     //     System.out.println(value);
        //     //     break;
        //     // }
        // }
        //  for(String s:h.keySet())
        //  {
        //     System.out.print("\n"+s);
           
        //           for(String sss:h.values())
        //  {

        //     System.out.println(" ss: "+ss);
        //     ss=sss;
        //     System.out.println("sss: "+sss);
      
          
        //     break;
            
        //  }
        //    h.values().remove(ss);
            
            

        //  }
        //  System.out.print("    ");
      
      
        
       
              
            
                

       
        
        } catch (Exception e) {
         e.printStackTrace();
        }

         
    }
    
}
