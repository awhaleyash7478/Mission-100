package services;
import java.sql.*;
public class PaymentHistory {
    Connection conn;
    public PaymentHistory(Connection conn)
    {
        this.conn=conn;
    }
    public void viewHistory()
    {
        try {
            String query="select * from paymenthistory where  user_name=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, CustomerVerification.userName);
            ResultSet rs=ps.executeQuery();
System.out.println("+--------+------------+----------------------+--------------+------------+----------------------+");
System.out.printf("| %-6s | %-10s | %-20s | %-12s | %-10s | %-20s |%n",
        "ID", "Amount", "Sender", "Date", "Time", "Transaction");
System.out.println("+--------+------------+----------------------+--------------+------------+----------------------+");



            while (rs.next()) {
                 System.out.printf("| %-6d | %-10.2f | %-20s | %-12s | %-10s | %-20s |%n",
            rs.getInt("his_id"),
            rs.getDouble("amount"),
            rs.getString("sender"),
            rs.getDate("date"),
            rs.getTime("time"),
            rs.getString("transaction"));
                
            }
            System.out.println("+--------+------------+----------------------+--------------+------------+----------------------+");
    
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
