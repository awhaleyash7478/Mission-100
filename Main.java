import java.sql.Connection;
import java.sql.DriverManager;

class Main {
    public static void main(String[] args) {

        try {

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/demo",
                "root",
                "Yash@7478"
            );

            System.out.println("Connected Successfully");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}