package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ParcelTracking  {
    Connection conn;
    public ParcelTracking(Connection conn)
    {
        this.conn=conn;
    }
    String userName;
    public  void getUserName(String UserName)
    {
        this.userName=userName;
    }
    public void trakParcel()
    {
        try {
            String query="select * from parcel_tracking where username=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, userName);
            ResultSet rs=ps.executeQuery();
System.out.println("================================================================================");
System.out.printf("| %-10s | %-20s | %-20s |%n",
        "Parcel ID",
        "Status",
        "User Name");
System.out.println("================================================================================");

while(rs.next()) {
    System.out.printf("| %-10d | %-20s | %-20s |%n",
            rs.getInt("parcel_id"),
            rs.getString("status"),
            rs.getString("username"));
}

System.out.println("================================================================================");
            
        } catch (Exception e) {
    e.printStackTrace();
        }
    }


}
