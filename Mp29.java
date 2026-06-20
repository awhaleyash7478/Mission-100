import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
class LeaveValidationThread extends Thread 
{
    leaveManagement obj;
    Connection conn;
    
    LeaveValidationThread(leaveManagement obj,Connection conn)
    {
        this.conn=conn;
        this.obj=obj;
    }
    public void run()
    {
        try 
        {
            int found=0;
            String query="select * from leave_requests where emp_id=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, obj.leaveEmpId);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
               found=1;

               if(found==0)
                System.out.println("employee not found");

        }catch(Exception e)
        {e.printStackTrace();}
    }
}
class LeaveApprovalThread extends Thread 
{
    leaveManagement obj;
    Connection conn;
    LeaveApprovalThread(leaveManagement obj,Connection conn)
    {
        this.conn=conn;
         this.obj=obj;
    }
    public void run()
    {
        
            Scanner sc2=new Scanner(System.in);
       
        try 
        {
            System.out.println("1.Approved \n2.Reject");
            obj.status=sc2.nextLine();

            
            
            String query="update  leave_requests set status=? where emp_id=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, obj.status);
            ps.setInt(2, obj.leaveEmpId);
            ps.executeUpdate();


        }catch(Exception e)
        {
            System.out.println("Employee alreadu exists");
        }
        
    }
}
class LeaveHistoryThread extends Thread
{
    
 leaveManagement obj;
 Connection conn;
 LeaveHistoryThread(leaveManagement obj,Connection conn)
 { this.conn=conn;
    this.obj=obj;
 }
  public void run()
{
    try
    {
        String query1 = "select emp_id, leave_days from leave_requests where emp_id=?";
        PreparedStatement ps1 = conn.prepareStatement(query1);
        ps1.setInt(1, obj.leaveEmpId);

        ResultSet rs = ps1.executeQuery();

        if(rs.next())
        {
            String query2 = "insert into leave_history1(emp_id,leave_days,status) values(?,?,?)";
            PreparedStatement ps2 = conn.prepareStatement(query2);

            ps2.setInt(1, rs.getInt("emp_id"));
            ps2.setInt(2, rs.getInt("leave_days"));
            ps2.setString(3, obj.status);

            ps2.executeUpdate();
        }
    }
    catch(Exception e)
    {
        System.out.println("employee already exists");
    }
}
}

class EmployeeManagement 
{
    Connection conn;
    EmployeeManagement()
    {

    }
    EmployeeManagement(Connection conn)
    {
        this.conn=conn;
    }
    int empId;
    String empDep;
    String empName;
    Scanner sc=new Scanner(System.in);
    void addEmployee()
    {
        try {
            System.out.println("----Employee Registration----");
            System.out.println("Enter the emp Name:");
            empName=sc.nextLine();
            System.out.println("Enter the emp id:");
            empId=sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the emp dep:");
            empDep=sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("Invalid entry");
                sc.nextLine();
        }
        try 
        {
            String query="insert into emp2(empID,empName,empDep)values(?,?,?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, empId);
            ps.setString(2, empName);
            ps.setString(3, empDep);
            int rows=ps.executeUpdate();
            if(rows>0)
            {
                System.out.println("Employee added successfully");
            }else 
            {
                System.out.println("Something goes wrong....");
            }
        }catch(Exception e)
        {
            System.out.println("Employee with id:"+empId+" already exists");
        }
        
    }
    void viewEmployee()
    {
        try 
        {
            int found=0;
            String query="select * from emp2 ";
         PreparedStatement ps=conn.prepareStatement(query);
         ResultSet rs=ps.executeQuery();
         while(rs.next())
         {
            System.out.printf("%-15s %-8d %-15s%n",
        rs.getString("empName"),
        rs.getInt("empID"),
        rs.getString("empDep"));  
                        
                        found=1;                 
                    
         }
         if(found==0)
         {
            System.out.println("No record found");
         }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    void searchEmployees()
    {
        int tempEmpId=0;
        try 
        {
            System.out.println("Enter the empid to search:");
            tempEmpId=sc.nextInt();
        }catch(Exception e)
        {
            System.out.println("invalid entry");
            sc.nextLine();
        }
        try 
{
    String query="select * from emp2 where empId=?";
    PreparedStatement ps=conn.prepareStatement(query);
    ps.setInt(1, tempEmpId);
    ResultSet rs=ps.executeQuery();
    int found=0;   
    while(rs.next())
         {
             System.out.printf("%-15s %-8d %-15s%n",
        rs.getString("empName"),
        rs.getInt("empID"),
        rs.getString("empDep"));
                        
                        found=1;                 
                    
         }
         if(found==0)
         {
            System.out.println("No record found");
         }
    

}catch(Exception e)
{
    e.printStackTrace();
}
    }
 
}
class leaveManagement extends EmployeeManagement
{ 
    
    leaveManagement(Connection conn)
{
    super(conn);
}
    int  tempLeaveDays=0;
    
    String status=null;

    int leaveEmpId=0;
    int tempEmpId=0;
    void applyForLeave()
    {
      
        String tempReason=null;

        try 
        {

            System.out.println("----Leave Management----");
            System.out.println("Enter the empid:");
            tempEmpId=sc.nextInt();
            System.out.println("Enter the number of days:");
            tempLeaveDays=sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the reason for leave:");
            tempReason=sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("Invalid entry");
        }
        try 
        {
            String query ="insert into leave_requests(emp_id,leave_days,reason)values(?,?,?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, tempEmpId);
            ps.setInt(2, tempLeaveDays);
            ps.setString(3, tempReason);
            int rows=ps.executeUpdate();
            if(rows>0)
            {
                System.out.println("Leave approved");
            }else 
            {
                System.out.println("Record not found");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    void viewLeaves()
    {
          
        try 
        {
            String query="select * from leave_requests";
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            int found=0;
             while(rs.next())
         {
         System.out.printf("%-8d %-8d %-10d %-20s %-10s%n",
        rs.getInt("leave_id"),
        rs.getInt("emp_id"),
        rs.getInt("leave_days"),
        rs.getString("reason"),
        rs.getString("status")); 

            found=1;                 
                    
         }
         if(found==0)
         {
            System.out.println("No record found");
         }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    void approveLeave()
    {
      
        try 
        {
            System.out.println("Enter the emp id:");
            leaveEmpId=sc.nextInt();
            sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("invalid entry");
        }
        try 
        {
        LeaveApprovalThread l=new LeaveApprovalThread(this,conn);
        LeaveHistoryThread h=new LeaveHistoryThread(this,conn);
        LeaveValidationThread l1=new LeaveValidationThread(this,conn);
    l1.start();
    l1.join();
        l.start();
        l.join();
        h.start();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
     
    }
    void viewHistory()
    {
        try 
        {
        String query="select * from leave_history1";
        PreparedStatement ps=conn.prepareStatement(query);
        ResultSet rs=ps.executeQuery();
        int found=0;
        while (rs.next()) {
System.out.printf("%-8d %-10d %-10s%n",
        rs.getInt("emp_id"),
        rs.getInt("leave_days"),
        rs.getString("status"));
                        found=1;  
        }
        if(found==0)
            System.out.println("no record found");
    }catch(Exception e)
{e.printStackTrace();}}
    void menu()
    {
        int ch=0;
        while (true) {
            System.out.println("1.Add Employee\n" + //
                                "2.View Employees\n" + //
                                "3.Search Employee\n"+
                               "4. Apply Leave\n"+
"5.View Leave Requests\n"+
"6.Approve Leave\n"+

"7.View Leave History\n"+
"8.Exit");
ch=sc.nextInt();
sc.nextLine();
switch (ch) {
    case 1:
        addEmployee();
        
        break;
    case 2:
        viewEmployee();
        break;
    case 3:
        searchEmployees();
        break;
    case 4:
        applyForLeave();
        break;
    case 5:
        viewLeaves();
        break;
    case 6:
        approveLeave();
        break;
    case 7:
        viewHistory();
        break;
    case 8:
        return;

    default:
        System.out.println("Invalid choice");
        break;
}
            
        }
    }
       
}
class Mp29
{
    public static void main(String[] args) {

             try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localHost:3306/demodatabase",
                    "root",
                    "Yash@7478");
            System.out.println("connection established successfully");
            EmployeeManagement e=new EmployeeManagement(conn);
                leaveManagement l=new leaveManagement(conn);
    l.menu();
        } catch (Exception s) {
            s.printStackTrace();
        }
    }
}