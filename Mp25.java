import java.util.Scanner;

import java.util.regex.*;

class Student
{
        Scanner sc=new Scanner(System.in);
    final int  max=100;
    int stuId[]=new int[max];
    String stuName[]=new String[max];
    String stuDep[]=new String[max];
    int sem[]=new int[max];
    void addStudent()
    {
        int tempId=0,tempSem=0;
        String tempDep=null,tempName=null;


        try 
        {
        System.out.println("Enter the student id:");
        tempId=sc.nextInt();
        sc.nextLine();
        
        
        
           System.out.println("Enter the Student name:");
           tempName=sc.nextLine();
        
            
        
        System.out.println("Enter the Student department:");
        tempDep=sc.nextLine();
        System.out.println("Enter the semester:");
        tempSem=sc.nextInt();
         }catch(Exception e)
         {
            System.out.println("Invalid input");
            return;
            
         } for(int i=0;i<max;i++)
        {
            if(stuId[i]==0)
            {
                stuId[i]=tempId;
                stuDep[i]=tempDep;
                stuName[i]=tempName;
                sem[i]=tempSem;
                System.out.println("Student added successfully");
                break;

            }
        }


    }
    void viewStudent()
    {
        for(int i=0;i<max;i++)
        {
            if(i==0)
            {
                if(stuId[i]==0)
                {
                    System.out.println("first add the student");
                    break;
                }
            }
            if(stuId[i]==0)
            {
                break;
            }
            System.out.println("Student name: "+stuName[i]+"Student id: "+stuId[i]+"Student department: "+stuDep[i]+"Student Semester: "+sem[i]);

        }
    }
    void searchStudent()
    {
        int searchId=0;
     try 
     {
        System.out.println("Enter the search id:");
        searchId=sc.nextInt();
     }catch(Exception e)
     {
        System.out.println("Invalid entry");
        sc.nextLine();
     }
     int found=0;
     for(int i=0;i<max;i++)
     {
        if(searchId==stuId[i])
        {
            found=1;
             System.out.println("Student name: "+stuName[i]+"Student id: "+stuId[i]+"Student department: "+stuDep[i]+"Student Semester: "+sem[i]);
             break;
        }
     }
     if(found==0)
     {
        System.out.println("Student not found");
     }
    }

    void menu()
    {
        while(true)
        {
            int ch=0;
            System.out.println("1.Add Student\r\n" + //
                                "2.View Students\r\n" + //
                                "3.Search Student\r\n");
                                try 
                                {
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
                                    default:
                                        System.out.println("Invalid input");
                                        break;
                                }
        }
    }
}
class Mp25 
{
    public static void main(String[] args) {
        Student s=new Student();
        s.menu();
    }
}