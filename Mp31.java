import java.rmi.server.ExportException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import javax.swing.tree.ExpandVetoException;
class ExamValidationThread extends Thread
{
    Connection conn;
    StudentManagement obj;
    ExamValidationThread(Connection conn,StudentManagement obj)
    {
        this.conn=conn;
        this.obj=obj;
    }
    public void run()
    {
        int found=0;
        try 
        {
            String query="select * from student101 where stuID=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, obj.examStuID);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
             found=1;
                        }            else if(found==0) 
            {
                System.out.println("student not exist");
            return;
            }
            String query2="select *from exams101 where examId=?";
            PreparedStatement ps1=conn.prepareStatement(query2);
            ps1.setInt(1, obj.examExamID);
            ResultSet rs1=ps1.executeQuery();
            if(rs1.next())
            {
                found=1;
            }
            else if(found==0)
            {
                System.out.println("exam not found");
                return;
            }


        
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
    }
}
class ResultCalculationThread extends Thread
{
    Connection conn;
    StudentManagement obj;
    ResultCalculationThread(StudentManagement obj)
    {
        
        this.obj=obj;
    }
    public void run()
    {
         try 
         {
            if(obj.obtMarks>=90)
            {
                obj.grade="A";
            }else if(obj.obtMarks>=75)
            {
                obj.grade="B";
            }else if(obj.obtMarks>=60)
            {
                obj.grade="c";
            }else 
            {
                obj.grade="fail";
            }
         }catch(Exception e)
         {
            e.printStackTrace();
         }
    }
}

class StudentManagement 
{
    Connection conn;
    StudentManagement(Connection conn)
    {
        this.conn=conn;
    }
    int stuID;
    String stuName;
    String dep;
    Double sem;
    Scanner sc=new Scanner(System.in);
    String examName;
    int examID;
    int marks;
    int duration;
    int examinationID;
    int regID;
    int studentID;
    String status;
    int examStuID;
    int examExamID;
    int obtMarks;
    int subExamId;
    int subStuId;
   
    String grade;
    int resultID;
    void addStudent()

{
    try{
        System.out.println("Enter the student id:");
        stuID=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the student name:");
        stuName=sc.nextLine();
        System.out.println("Enter the student semester:");
        sem=sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter the student department:");
        dep=sc.nextLine();
    }catch(Exception e){
        System.out.println("Invalid entry");
    }
    try 
    {
        String query="insert into student101 (stuId,stuName,department,semester)values(?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, stuID);
        ps.setString(2, stuName);
        ps.setString(3, dep);
        ps.setDouble(4,sem);
        int rows=ps.executeUpdate();
        if(rows>0)
            System.out.println("Student added");
        else 
             System.out.println("Cannot add student");
        
        


    }catch(Exception e){
        e.printStackTrace();
    }
}
void viewStudent()
{
    try 
    {
        int found=0;
        String query="select * from student101 ";
        
        PreparedStatement ps=conn.prepareStatement(query);
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
              System.out.printf(
                        "%-8d %-15s %-15s %.2f%n",
                        rs.getInt("stuId"),
                        rs.getString("stuName"),
                        rs.getString("department"),
                        rs.getDouble("semester"));
                found = 1;
            
        }
        if(found==0)
            System.out.println("Students not found");
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
}
void searchStudent()
{
    int searchID=0;
    try 
    {
        System.out.println("Enter the student id to search:");
        searchID=sc.nextInt();
        sc.nextLine();
    }catch(Exception e)
    {
        System.out.println("Invalid entry");
    }
    try 
    {
        int found=0;
        String query="select * from student101 where stuID=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, searchID);

        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
                  System.out.printf(
                        "%-8d %-15s %-15s %.2f%n",
                        rs.getInt("stuId"),
                        rs.getString("stuName"),
                        rs.getString("department"),
                        rs.getDouble("semester"));
                found = 1;
            
        }
        if(found==0)
            System.out.println("Student not found");


    }catch(Exception e)
    {
        e.printStackTrace();
    }
}
void createExam()
{
    try
    {

        System.out.println("Enter the exam name:");
        examName=sc.nextLine();
        System.out.println("Enter the exam id:");
        examID=sc.nextInt();
        System.out.println("Enter the total marks:");
        marks=sc.nextInt();
        System.out.println("Enter the duration:");
        duration=sc.nextInt();
    }catch(Exception e)
    {
        System.out.println("invalid entry");
    }
    try 
    {
        String query="insert into exams101(examID ,examName ,marks  ,duration )values(?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, examID);
        ps.setString(2, examName);
        ps.setInt(3, marks);
        ps.setInt(4, duration);
        int rows=ps.executeUpdate();
        if(rows>0)
            System.out.println("Exam added successfully");
        else 
            System.out.println("Unable to add");

    }catch(Exception e)
    {
        e.printStackTrace();
    }
}
void viewExam()
{
    int found=0;
    try 
    {
        
        String query="select * from exams101";
        PreparedStatement ps=conn.prepareStatement(query);
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
                              System.out.printf(
                        "%-8d %-15s %-15s%n",
                        rs.getInt("examId"),
                        rs.getString("examName"),
                        rs.getInt("Marks"));
                found = 1;


            
        }
        if(found==0)
            System.out.println("Cannnot find exam");
    }catch(Exception e)
    {
        e.printStackTrace();
    }

}
void deleteExam()
{
    String tempExamName=null;
    try 
    {
        int tempexamID=0;
        System.out.println("Enter the exam id to delete:");
tempexamID=sc.nextInt();
        
        String query="delete from exams101 where examID=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, tempexamID);
        int rows=ps.executeUpdate();
         
        if(rows>0)
            System.out.println("Exam deleted sucessfully");
        else 
            System.out.println("Unable to delete exam");
    }catch(Exception e)
    {
        e.printStackTrace();
    }
}
void registerForExam()
{
    try{
        System.out.println("Enter the registration id:");
        regID=sc.nextInt();
       
        System.out.println("Enter the Student id:");
        studentID=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the Exam id:");
        examinationID=sc.nextInt();
         sc.nextLine();
    }
    catch(Exception e)
    {
        System.out.println("Invalid entry");
    }
    try 
    {
        String query="insert into registrations101(regID,stuID,examID,status)values(?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, regID);
        ps.setInt(2, studentID);
        ps.setInt(3, examinationID);
        ps.setString(4,status);
        int rows=ps.executeUpdate();
        if(rows>0)
            System.out.println("Regustered for exam successfully");
        else
            System.out.println("Unable to register for exam");

    }catch(Exception e)
    {
        e.printStackTrace();
    }
}
void startExam()
{
    try 
    {
        System.out.println("Enter the student id:");
        examStuID=sc.nextInt();
        System.out.println("Enter the exam id:");
        examExamID=sc.nextInt();
        ExamValidationThread e=new ExamValidationThread(conn,this);
        e.start();
        

    }catch(Exception e)
    {
        System.out.println("Invalid entry");
    }
    
}
void submitMarks()
{
    int tempResId=0;
    try 
    {
        System.out.println("Enter the result id:");
        tempResId=sc.nextInt();
        System.out.println("Enter the student id:");
        subStuId=sc.nextInt();
        System.out.println("Enter the exam id:");
        subExamId=sc.nextInt();
        sc.nextLine();
  

        System.out.println("Enter the obtained marks:");
obtMarks=sc.nextInt();
ResultCalculationThread r=new ResultCalculationThread(this);
r.start();
    }catch(Exception e)
    {
        System.out.println("Invalid entry");
    }
    try 
    {
        String query="insert into result101(resultID,stuId,examID,score,grade)values(?,?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1, tempResId );
        ps.setInt(2, subStuId);
        ps.setInt(3,subExamId );
        ps.setInt(4, obtMarks);
        ps.setString(5, grade);
        int rows=ps.executeUpdate();
        if(rows>0)
            System.out.println("Marks submitted");
        else 
           System.out.println("Unable to submit or student not exist");
    }catch(Exception e)
    {
        e.printStackTrace();
    }

    
}
void viewResult()
{
    int found=0;
    try 
    {
    String query="select *from result101";
PreparedStatement ps=conn.prepareStatement(query);
ResultSet rs=ps.executeQuery();

           while (rs.next()) {
                              System.out.printf(
                        "%-8d %-15s %-15s %.2f%n%-15s",
                        rs.getInt("resultID"),
                        rs.getString("stuID"),
                        rs.getInt("examID"),
                        rs.getDouble("score"),
                    rs.getString("grade"));
                found = 1;


    
}
if(found==0)
    System.out.println("no results");    


}catch(Exception e){
    e.printStackTrace();
}
}
    void menu()
    {
        while (true) {
            System.out.println("1.Add student\n2.View Student\n3.Search Student\n4.Create Exam\n" + //
                                "5.View Exams\n" + //
                                "6.Delete Exam\n7.Register for Exam\n8.Start Exam\n9.Submit Marks\n10.View Result\n11.Exit");
                    int ch=0;
                    try 
                    {
                        System.out.println("Enter the choice:");
                        ch=sc.nextInt();
                    }catch(Exception e)
                    {
                        System.out.println("Invalid entry");
                        sc.nextLine();
                    }
                    switch (ch) {
                        case 1:
                            addStudent();
                            
                            break;
                        case 2:
                            viewStudent();
                            break;
                        case 3:
                            searchStudent();
                            break;
                        case 4:
                            createExam();
                            break;
                        case 5:
                            viewExam();
                            break;
                        case 6:
                            deleteExam();
                            break;
                        case 7:
                            registerForExam();
                            break;
                        case 8:
                            startExam();
                            break;
                        case 9:
                            submitMarks();
                            break;
                        case 10:
                            viewResult();
                            break;
                        case 11:
                            return;
                        default:
                            System.out.println("Invalid entry");
                            break;
                    }
            
        }
    }
}
public class Mp31
{
    public static void main(String[] args) {
      try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localHost:3306/demodatabase",
                    "root",
                    "Yash@7478");
            System.out.println("connection established successfully");
      StudentManagement s=new StudentManagement(conn);
      s.menu();
        } catch (Exception s) {
            s.printStackTrace();
        }
    }
    }

