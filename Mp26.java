import java.security.spec.ECFieldF2m;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Student {
    Scanner sc = new Scanner(System.in);
    int stuId, sem;
    String name, dep;
    Connection conn;

    Student(Connection conn) {
        this.conn = conn;
    }

    void addStudent() {
        try {
            System.out.println("Enter the student name:");
            name = sc.nextLine();
            System.out.println("Enter the student id:");
            stuId = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the student department:");
            dep = sc.nextLine();
            System.out.println("Enter the student semester:");
            sem = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid entry");
            sc.nextLine();
        }
        try {
            String query = "insert into Student (stu_id,name,department,semester)values(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, stuId);
            ps.setString(2, name);
            ps.setString(3, dep);
            ps.setInt(4, sem);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student inserted successfully");
            } else {
                System.out.println("Student not inserted");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void viewStudent() {
        try {
            String query = "select * from Student";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int found = 0;
            while (rs.next()) {

                System.out.printf(
                        "%-8d %-15s %-15s %.2f%n",
                        rs.getInt("stu_Id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("semester"));
                found = 1;
            }
            if (found == 0)
                System.out.println("No student found");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void searchStudent() {
        int searchID = 0;
        try {
            System.out.println("Enter the search id:");
            searchID = sc.nextInt();

        } catch (Exception e) {
            System.out.println("Invalid entry");
        }
        try {
            String query = "select * from Student where stu_Id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, searchID);
            ResultSet rs = ps.executeQuery();
            int found = 0;
            while (rs.next()) {

                System.out.printf(
                        "%-8d %-15s %-15s %.2f%n",
                        rs.getInt("stu_Id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("semester"));
                found = 1;
            }
            if (found == 0)
                System.out.println("No student found");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateStudent() {
        int tempId = 0;
        String tempDep = null;
        try {
            System.out.println("Enter the student id:");
            tempId = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the student department:");
            tempDep = sc.nextLine();
        } catch (Exception e) {
            System.out.println("invalid entry");
            sc.nextLine();
        }

        try {
            String query = "update  Student set department=? where stu_Id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, tempDep);
            ps.setInt(2, tempId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student id: " + tempId + "departement updated: " + tempDep);
            } else {
                System.out.println("Student not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleteStudent() {
        int tempID = 0;
        try {
            System.out.println("Enter the id:");
            tempID = sc.nextInt();
        } catch (Exception e) {
            System.out.println("invalid entry");
        }
        try {
            String query = "delete from Student where stu_Id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, tempID);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student  id: " + tempID + "deleted successfully");
            } else {
                System.out.println("student not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void menu() {
        int ch = 0;
        while (true) {
            try {
                System.out.println(
                        "1.Add Student\n2.View Student\n3.Search Student\n4.Update Student\n5.Delete Student\n6.Exit");
                ch = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
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
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid entry");
                    break;
            }

        }
    }
}

class Mp26 {
    public static void main(String[] args) {
  
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localHost:3306/demodatabase",
                    "root",
                    "Yash@7478");
            System.out.println("connection established successfully");
                  Student s = new Student(conn);
            s.menu();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }
}