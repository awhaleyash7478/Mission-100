import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
class PayrollThread extends Thread 
{
    public void run()
    {
        System.out.println("Calculating salary...");

    }
}
class AttendenceVerificationThread extends Thread

{
    public void run()
    {
        System.out.println("Verifying attendance...");
    }
}
class NotificationThread extends Thread
{
    public void run()

    {
        System.out.println("Sending salary slip...");
    }
}

class Employee {
    Connection conn;
    String status;
    int empid = 0;
    String empName = null;
    double empSalary = 0.0;
    String empDep = null;
    Scanner sc = new Scanner(System.in);
    Employee(Connection conn){
        this.conn=conn;
    }

    void addEmployees()
    {

        try 
        {
        System.out.println("Enter the empid:");
        empid=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the emp name:");
        empName=sc.nextLine();
        System.out.println("Enter the emp salary:");
        empSalary=sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter the empDep:");
        empDep=sc.nextLine();
        }catch(Exception e)
    {
        System.out.println("invalid entry");
    }
    try 
    {
    String query="insert into employee2(emp_id,name,department,salary)values(?,?,?,?)";
    PreparedStatement ps=conn.prepareStatement(query);
    ps.setInt(1, empid);
    ps.setString(2, empName);
    ps.setString(3, empDep);
    ps.setDouble(4, empSalary);
    int rows=ps.executeUpdate();
    if(rows>0)
    {
        System.out.println("Employee added successfully");
    }else
        
        {
            System.out.println("Something went wrong");
        }
    }catch(Exception e)
    {
        System.out.println("Invalid input");
    }
    }

    void viewEmployees() {
        try {
            String query = "select*from employee2 ";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            int found = 0;
            while (rs.next()) {

                System.out.printf(
                        "%-8d %-15s %-15s %.2f%n",
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("salary"));

                found = 1;
            }
            if (found == 0)
                System.out.println("No product found");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    void searchEmployees()
    {
        int tempSearchid=0;
        try 
        {
            System.out.println("Enter the id to search:");
            tempSearchid=sc.nextInt();

    }catch(Exception e)
        {
            System.out.println("Invalid input");
        }
        int found=0;
        try 
        {
            String query="select * from employee2 where emp_id=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, tempSearchid);
            ResultSet rs=ps.executeQuery();
            
 while(rs.next())
 {
    found=1;
      System.out.printf("%-8d %-15s %-15s %.2f%n",
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("salary"));

 }
            
            if(found==0)
            {
                System.out.println("No employee found");
            }
        }catch(Exception e)
        {
           e.printStackTrace();
        }
    }
    void generatePayroll()
    {
        double tempempSalary=0;
        int tempEmpid=0;
         int tempAttDays=0;
        try 
        {
            System.out.println("Enter the employee id:");
            tempEmpid=sc.nextInt();
            System.out.println("Enter the attendance:");
            tempAttDays=sc.nextInt();

            String search="select salary from employee2 where emp_id=?";
            PreparedStatement ps=conn.prepareStatement(search);
            ps.setInt(1, tempEmpid);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
            tempempSalary=rs.getDouble("salary");
            System.out.println("salary:"+tempempSalary);
            }else 
            {
                System.out.println("employee not found");
                return;
            }
        }catch(Exception e)
        {
            System.out.println("invalid input");
        }
        try 
        {
       
       double salaryPerDay = tempempSalary / 30;
double finalSalary = salaryPerDay * tempAttDays;
String query="insert into payroll(emp_id,attendance_days,final_salary,status)values(?,?,?,?)";


   
PreparedStatement ps=conn.prepareStatement(query);
ps.setInt(1, empid);
ps.setInt(2, tempAttDays);
ps.setDouble(3, finalSalary);
ps.setString(4, status);
int rows=ps.executeUpdate();
if(rows>0){
    PayrollThread p=new PayrollThread();
    p.start();
    AttendenceVerificationThread a=new AttendenceVerificationThread();
    a.start();
    p.join();
    a.join();
    NotificationThread n=new NotificationThread();
    n.start();
    n.join();
    System.out.println("payroll generated successfully");
    
    
}else 
{
    System.out.println("employee not found");
}


    }catch(Exception e)
    {
        e.printStackTrace();
    }
}
void viewPayrollHistory()
{
    try 
    {
    int found=0;
    String query="select*from payroll";
    PreparedStatement ps=conn.prepareStatement(query);
    status="regular";
    ResultSet rs=ps.executeQuery();
    while(rs.next())
    {
          System.out.printf("%-8d %-15s %-15s %.2f%n",
                        rs.getInt("payroll_id"),
                        rs.getInt("emp_id"),
                        rs.getInt("attendance_days"),
                        rs.getDouble("final_salary"),
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
void deleteEmployee()
{
    int tempdeleteId=0;
    try 
    {
        System.out.println("Enter the id to delete Employee:");
        tempdeleteId=sc.nextInt();
    }catch(Exception e)
    {
        System.out.println("Invalid input");
    }
    try 
    {
    String query="delete from employee2 where emp_id=?";
    PreparedStatement ps=conn.prepareStatement(query);
    ps.setInt(1, tempdeleteId);
    
    int rows=ps.executeUpdate();
    if(rows>0)
    {
        System.out.println("Record deleted Successfully");
    }else
    {
        System.out.println("not record found "); }

}catch(Exception e)
{
    e.printStackTrace();
}
}

    void menu() {
        int ch = 0;
        while (true) {
            System.out.println(
                    "1.Add employee\n2.View employee\n3.Search employee\n4.Generate Payroll\n5.View Payroll history\n6.Delete employee\n7.Exit");
            try {
                ch = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Enter a valid number");
            }
            switch (ch) {
                case 1:
                    addEmployees();

                    break;
                case 2:
                    viewEmployees();
                    break;
                
                 case 3:
                    searchEmployees();
                    break;
                
                    case 4:
                        generatePayroll();
                        break;
                    case 5:
                        viewPayrollHistory();
                        break;
                    case 6:
                        deleteEmployee();
                        break;
                    case 7:
                        return;
                default:
                    System.out.println("Invalid input");
                    break;
            }

        }

    }
}
class Mp28
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
