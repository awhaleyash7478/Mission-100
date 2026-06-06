import java.util.Scanner;

class NegativeSalaryException extends Exception
{
}
class PayrollThread extends Thread
{
    public void run()
    {
        try 
        {
System.out.println("Calculating Salary...");
Thread.sleep(2000);
System.out.println("Verifying Attendance...");
Thread.sleep(2000);
System.out.println("Payroll Generated...");
Thread.sleep(2000);

        }catch(InterruptedException e)
        {
            System.out.println("Thread interrupted ");
        }
    }
}
class AttendanceThread extends Thread 
{
    public void run()
    {
                try 
        {
System.out.println("Updating Attendance...");
Thread.sleep(2000);
System.out.println("Attendance Updated...");


        }catch(InterruptedException e)
        {
            System.out.println("Thread interrupted ");
        }
    }
}
class NotificationThread extends Thread{
    public void run()
    {
                   try 
        {
System.out.println("Sending Salary Slip...");
Thread.sleep(2000);
System.out.println("Sending Email...");
Thread.sleep(2000);
System.out.println("Notification Sent...");


        }catch(InterruptedException e)
        {
            System.out.println("Thread interrupted ");
        }
    }
}
class Employee
{
    Scanner sc=new Scanner(System.in);
    final int  max=100;
      int empId[]=new int[max];
        String empName[]=new String[max];
        double empSalary[]=new double[max];
        String department[]=new String[max];
        int attDays[]=new int[max];
        String project[]=new String[max];
        String status[]=new String[max];
        int hisId[]=new int[max];
        double hisPayroll[]=new double[max];
        String hisEmpName[]=new String[max];
        String hisProject[]=new String[max];
               int hisIndex;
    
    void addEmployee()
    {
        String tempName=null,tempStatus=null,tempdep=null,tempproj=null;
        int tempId=0,tempDays=0;
        double tempSalary=0.0;

        try 
        {
        System.out.println("Enter the employee name:");
        tempName=sc.nextLine();
        System.out.println("Enter the employee id:");
        tempId=sc.nextInt();
        System.out.println("Enter the employee Salary:");
        try 
        {
            
        tempSalary=sc.nextDouble();
        if(tempSalary<0)
            throw new NegativeSalaryException();
    }catch(NegativeSalaryException e)
    {
        System.out.println("salary cant be negative");
    }
        sc.nextLine();
        System.out.println("Enter the employee department:");
        tempdep=sc.nextLine();
        System.out.println("Enter the employee project name:");
        tempproj=sc.nextLine();
        System.out.println("Enter the number of daya:");
        tempDays=sc.nextInt();
        tempStatus="Active";
    }catch(Exception e)
    {
        System.out.println("Invalid entry");
        sc.nextLine();
    }
    for(int i=0;i<max;i++)
    {
        if(empId[i]==0)
        {
            empId[i]=tempId;
            empName[i]=tempName;
            empSalary[i]=tempSalary;
            department[i]=tempdep;
            project[i]=tempproj;
            status[i]=tempStatus;
            attDays[i]=tempDays;
            System.out.println("Employee added sucessfully....");
            break;
        }
    }
    }
    void viewEmployee()
    {
        for(int i=0;i<max;i++)
        {
            if(i==0)
            {
            if(empId[i]==0)
            {
                System.out.println("No employee exists");
                break;
            }
        }else if(empId[i]==0)
        {
            break;
        }
        
            System.out.println("Employee Name: "+empName[i]+"\nEmployee id: "+empId[i]+"\nEmployee Status: "+status[i]+"\nAttendance days: "+attDays[i]+"\nEmployee Salary: "+empSalary[i]+"\nEmployee department: "+department[i]+"\nEmployee Project: "+project[i]);
        }
    }
    void searchEmployee()
    {
        int searchID=0;
        try 
        {
            System.out.println("Enter the id to search Employee:");
            searchID=sc.nextInt();

        }catch(Exception e)
        {
            System.out.println("invalid id");
        }
        int found=0;

        for(int i=0;i<max;i++)
        {
                        if(searchID==empId[i])
            {
                found=1;
                 System.out.println("Employee Name: "+empName[i]+"\nEmployee id: "+empId[i]+"\nEmployee Status: "+status[i]+"\nAttendance days: "+attDays[i]+"\nEmployee Salary: "+empSalary[i]+"\nEmployee department: "+department[i]+"\nEmployee Project: "+project[i]);
                 break;
                
            }
        }
        if(found==0)
        {
            System.out.println("Employee not found");
        }
    }
    void assignProject()
    {
        int found=0,tempID=0;
        try 
        {
        System.out.println("Enter the Employee id:");
        tempID=sc.nextInt();
            sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("invalid entry");
        }
    
        for(int i=0;i<max;i++)
        {
        if(tempID==empId[i])
        {
           found=1;
        System.out.println("Enter the project Name:");
        project[i]=sc.nextLine();
        System.out.println("Project assigned Successfully");
        break;
        }
    }
        if(found==0)
            System.out.println("Employee not found");
    }
    void markAttendance()
    {
        int tempID=0,tempAttDays=0;
        try 
        {
            System.out.println("Enter the Employee id:");
            tempID=sc.nextInt();
            sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("Invalid entry");
        }
        int found=0;
        for(int i=0;i<max;i++)
        {
            if(tempID==empId[i])
            {
                found=1;
                System.out.println("Enter the attendance:");
                tempAttDays=sc.nextInt();
                if(tempAttDays<0)
                {
                    System.out.println("Attendance cant be negative");
                    break;
                }
                attDays[i]+=tempAttDays;
                System.out.println("Attendance MArked successfully");
                break;

            }
        }
        if(found==0)
            System.out.println("Employe not found");
    }
      void generatePayroll()
      {
 
                int tempID=0;
                double salary;
                double salaryPerDay;
                int attendanceDays;
                double finalSalary;
                int found=0;

        try 
        {
            System.out.println("Enter the Employee id:");
            tempID=sc.nextInt();
            sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("Invalid entry");
        }
        for(int i=0;i<max;i++)
        {
            if(tempID==empId[i])
            {
                found=1;
                salary=empSalary[i];
                attendanceDays=attDays[i];
                salaryPerDay = salary / 30;

finalSalary = salaryPerDay * attendanceDays;
hisId[hisIndex]=tempID;
hisPayroll[hisIndex]=finalSalary;
hisEmpName[hisIndex]=empName[i];
hisProject[hisIndex]=project[i];
hisIndex++;
PayrollThread p=new PayrollThread();
p.start();
AttendanceThread a=new AttendanceThread();
a.start();
try 
{
    p.join();
    a.join();
}catch(InterruptedException e)
{
    System.out.println("Thread interrupted");
}
NotificationThread n=new NotificationThread();
n.start();
try 
{
    n.join();
}catch(InterruptedException e)
{
    System.out.println("Thread Interrupted");
}
System.out.println("Payroll generated successfully");
break;            }
        }
        if(found==0)
            System.out.println("Employee not found");
      }
      void viewPayrollHistory()
      {
        for(int i=0;i<max;i++)
        {
            if(i==0)
            {
            if(hisId[i]==0)
         
         {
            System.out.println("no history yet");
              break;
        }
    }
    if(hisId[i]==0)
        break;
            System.out.println("Emp Name: "+hisEmpName[i]+"\nEmp id: "+hisId[i]+"\nPayroll: "+hisPayroll[i]+"\nProject: "+hisProject[i]);

        }
      }
      void deleteEmployee()
      {
        int tempID=0;
         try 
        {
            System.out.println("Enter the Employee id:");
            tempID=sc.nextInt();
            sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("Invalid entry");
        }
        int found=0;
        for(int i=0;i<max;i++)
        {
            if(tempID==empId[i])
            {
                found=1;
                for(int j=i;j<max-1;j++){
                    empId[j]=empId[j+1];
                    empName[j]=empName[j+1];
                    empSalary[j]=empSalary[j+1];
                    department[j]=department[j+1];
                    attDays[j]=attDays[j+1];
                    project[j]=project[j+1];
                    status[j]=status[j+1];
                }
                System.out.println("Employee deleted");
                break;
            }
        }
        if(found==0)
            System.out.println("Employee not found");
      }
    void menu()
    {
        while (true) {
            
        
        System.out.println("1.Add Employee\n2.View Employee\n3.Search Employee\n4.Assign Project\n5.Mark Attendance\n6.Generate Payroll\n7. View Payroll History\n8.Delete Employee\n9.Exit");
        System.out.println("Enter the choice:");
        int ch=0;
        try 
        {
            ch=sc.nextInt();
        sc.nextLine();
        }catch(Exception e)
        {
            System.out.println("Invalid  input");    
        }
        switch (ch) {
            case 1:
                addEmployee();
                
                break;
            case 2:
                viewEmployee();
                break;
            case 3:
                searchEmployee();
                break;
            case 4:assignProject();
            break;
            case 5:markAttendance();
            break;
            case 6:
                generatePayroll();
                break;
            case 7:
                viewPayrollHistory();
                break;
            case 8:
                deleteEmployee();
                break;
            case 9:
                return;
            default:
                System.out.println("invalid choice");
                break;
        }
    }
}
}
class Mp21 
{
    public static void main(String[] args) {
        Employee e=new Employee();
        e.menu();
    }
}