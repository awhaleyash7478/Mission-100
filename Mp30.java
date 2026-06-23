import java.security.DrbgParameters.Reseed;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.management.Query;
import javax.naming.spi.DirStateFactory.Result;
class AttendanceVerificationThread extends Thread
{
    Employee obj;
    Connection conn;

    AttendanceVerificationThread(Employee obj,Connection conn)
    {
        this.obj=obj;
        this.conn=conn;
    }
    public void run()
    {
       try 
       {
        int found=0;
        String query="select * from employee3 where emp_id=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, obj.attEmpid);
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            found=1;
            
        }
        if(found==0)
            System.out.println("Employee not exists");

       }catch(Exception e)
       {
        e.printStackTrace();
       }

        try 
       {
       
        int found=0;
        String query1="select att_day from attendance  where emp_id=?";
        PreparedStatement ps=conn.prepareStatement(query1);
        ps.setInt(1, obj.attEmpid);
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
           obj.attDays = rs.getInt("att_day");
            found=1;
            System.out.println("Attendance days:"+obj.attDays);
            
        }
        if(found==0)
            System.out.println("attendance not exists");

       }catch(Exception e)
       {
        e.printStackTrace();
       }

    }
}
class SalaryCalculationThread extends Thread
{
    Employee obj;
    double finalSalary=0.0;
    SalaryCalculationThread(Employee obj)
    {
        this.obj=obj;
    }
    public void run()
    {
     
     

      finalSalary = (obj.empSalary / 30) * obj.attDays;
        
    }
}
class PayrollGenerationThread extends Thread
{
    
    String status;
    Connection conn;
    Employee obj;
    PayrollGenerationThread(Employee obj,Connection conn)
    {
        this.obj=obj;
        this.conn=conn;
    
    }
    public void run()
    {

        try 
        {
            status="generated";
        String query="insert into payroll(emp_id,att_days,final_salary,status)values(?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        

        SalaryCalculationThread ss=new SalaryCalculationThread(obj);
        ps.setInt(1,obj.tempid );
       ps.setInt(2, obj.attDays);
        ps.setDouble(3,ss.finalSalary);
        ps.setString(4, status);

        int rows=ps.executeUpdate();
        if(rows>0)
        {
            System.out.println("Payroll generated successfully");
        }else 
        {
            System.out.println("something went wrong");
        }
        String query2="insert into history(emp_id,att_days,salary)values(?,?,?)";
        PreparedStatement ps1=conn.prepareStatement(query2);
        ps1.setInt(1, obj.tempid);
    ps1.setInt(2, obj.attDays);
        ps1.setDouble(3, ss.finalSalary);
        int rows1=ps1.executeUpdate();
        if(rows1>0)
        {
            System.out.println("History updated");
    }else
    {
        System.out.println("cannot update history");
    }
}catch(Exception e)
    {
        e.printStackTrace();
    }
}
}
class PayslipNotificationThread extends Thread
{
    public void run()
    {
        try 
        {
            System.out.println("Generating payslip...");
            Thread.sleep(2000);
            System.out.println("Payslip sent successfully");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

class Employee
{
    Connection conn;
    Employee(Connection conn)
    {
        this.conn=conn;
    }
    int empID;
      int tempid=0;
      int attDays;
      int attEmpid=0;
    String empName;
    String empDep;
    double empSalary;
    Scanner sc=new Scanner(System.in);
void addEmployee()
{
    try 
    {
        System.out.println("Enter the employee id:");
        empID=sc.nextInt();
         sc.nextLine();
        System.out.println("Enter the employee name:");
        empName=sc.nextLine();
        System.out.println("Enter the employee department:");
        empDep=sc.nextLine();
        System.out.println("Enter the employee salary:");
        empSalary=sc.nextDouble();

    }catch(Exception e)
    {
        System.out.println("Invalid entry");
        sc.nextLine();
    }
    try 
    {
        String query="insert into employee3(emp_id,emp_name,department,emp_salary)values(?,?,?,?)";
           PreparedStatement ps=conn.prepareStatement(query);
           ps.setInt(1, empID);
           ps.setString(2, empName);
           ps.setString(3, empDep);
           ps.setDouble(4, empSalary);
           int rows=ps.executeUpdate();
           if(rows>0)
        
        {    System.out.println("Employee added successfully");
        }else
        {
            System.out.println("Something went wrong");
        }
           

    }catch(SQLException e)
    {
        e.printStackTrace();
    }
}
void viewEmployee()
{
    try 
    {
        int found=0;
        String query="select * from employee3 ";
        PreparedStatement ps=conn.prepareStatement(query);
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            
      System.out.printf("%-8d %-15s %-15s %.2f%n",
                        rs.getInt("emp_id"),
                        rs.getString("emp_name"),
                        rs.getString("department"),
                        rs.getDouble("emp_salary"));
                        found=1;

        }
        if(found==0)
            System.out.println("Employee not found");
    }catch(Exception e )
    {
        System.out.println("invalid entry");
    }
}
void searchEmployees()
{
    int searchId=0;
    try 
    {
        System.out.println("Enter the search id:");
        searchId=sc.nextInt();

    }catch(Exception e)
    {
        System.out.println("Invalid id");
    }
    try 
    {
        int found=0;
        String query="select * from employee3 where emp_id=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, searchId);
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
                 System.out.printf("%-8d %-15s %-15s %.2f%n",
                        rs.getInt("emp_id"),
                        rs.getString("emp_name"),
                        rs.getString("department"),
                        rs.getDouble("emp_salary"));
                        found=1;


        }
        if(found==0)
            System.out.println("Employee not found");
    }catch(Exception e)
    {
        e.printStackTrace();
    }
}
void markAttendance()
{
  
    int days=0;
    String month=null;
    try 
    {
        System.out.println("Enter the employee id:");
        attEmpid=sc.nextInt();
        System.out.println("Enter the employee attendance days:");
        days=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the attendance month:");
        month=sc.nextLine();

    }catch(Exception e)
    {
        System.out.println("Invalid entry");
    }
    try 
    {
        String query="insert into attendance(emp_id,att_day,month)values(?,?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, attEmpid);
        ps.setInt(2, days);
        ps.setString(3, month);
        int rows=ps.executeUpdate();
        if(rows>0)
        {
            System.out.println("Attendance marked successfully");
        }else
        {
            System.out.println("Cannot mark attendance");
        }
    }catch(Exception e)
    {
        e.printStackTrace();
    }
}

void generatePayroll()
{
  
    try{
        System.out.println("Enter the empid:");
        tempid=sc.nextInt();
    }
    catch(Exception e)
    {
        System.out.println("Invalid entry");
    }
    try 
    {
    AttendanceVerificationThread a=new AttendanceVerificationThread(this,conn);
a.start(); 
a.join();
SalaryCalculationThread s=new SalaryCalculationThread(this);
s.start();
s.join();
PayrollGenerationThread ps=new PayrollGenerationThread(this,conn);
ps.start();
ps.join();
PayslipNotificationThread pp=new PayslipNotificationThread();
pp.start();

}catch(Exception e)
{
    e.printStackTrace();
}
}
void viewPayrollHistory()
{
    try 
    {
        String query="select * from history";
    
        int found=0;
        PreparedStatement ps=conn.prepareStatement(query);
       ResultSet rs= ps.executeQuery();
       while (rs.next()) {
            System.out.printf("%-8d %-15s %-15s %.2f%n",
                        rs.getInt("payroll_id"),
                        rs.getInt("emp_id"),
                        rs.getInt("att_days"),
                        rs.getDouble("salary"),
                    rs.getString("status"));
                    
                    found=1;

        
       }
       if(found==0)
        System.out.println("No history found");
    }catch(Exception e)
    {
        e.printStackTrace();
    }

}
    void menu()
    {
        int ch=0;
        while (true) {
            try 
            {
                System.out.println("1.Add Employee\n" + //
                                        "2.View Employees\n" + //
                                        "3.Search Employee\n"+
                                    "4.Mark Attendance\n"+
                                "5.Generate Payroll\n"+
                            "6.View Payroll history\n"+
                        "7.Exit");
                                ch=sc.nextInt();
            }catch(Exception e)
            {
                System.out.println("Invalid entry");
                sc.nextLine();
            }
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
                    markAttendance();
                    break;   
                case 5:
                    generatePayroll();
                    break; 
                case 6:
                    viewPayrollHistory();
                    break;
                    
                case 7:
                    return;
                default:
                    System.out.println("Invalid entry");

            
                    break;
            }
            
        }
    }
}
class Mp30 
{
        public static void main(String[] args) {

             try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localHost:3306/demodatabase",
                    "root",
                    "Yash@7478");
            System.out.println("connection established successfully");
            Employee e=new Employee(conn);
 
    e.menu();
        } catch (Exception s) {
            s.printStackTrace();
        }
    }
}