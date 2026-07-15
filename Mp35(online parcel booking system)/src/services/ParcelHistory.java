package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParcelHistory {
    Connection conn;
    
    public ParcelHistory(Connection conn)
    {
        this.conn=conn;
        
    }
    public void viewHistory()
    {
        try 
        {
            String fetchUserName="select * from login order by login_id desc limit 1";
            PreparedStatement preparedStatement=conn.prepareStatement(fetchUserName);
            String username=null;
            ResultSet rr=preparedStatement.executeQuery();
            if(rr.next())
            {
username=rr.getString("user_name");
//TODO: fetch details by using password as well

                System.out.println("username from login: "+username);
            }
            String query="select * from parcel_history where user_name =?";
            PreparedStatement ps=conn.prepareStatement(query);
            System.out.println("username: "+username);
            ps.setString(1, username);

            ResultSet rs=ps.executeQuery();
           System.out.println("===============================================================================================================");
System.out.printf("| %-9s | %-15s | %-18s | %-18s | %-20s | %-20s | %-12s |%n",
        "Parcel ID", "User Name", "Sender Name", "Receiver Name",
        "Sender Address", "Receiver Address", "Status");
System.out.println("===============================================================================================================");

while (rs.next()) {
    System.out.printf("| %-9d | %-15s | %-18s | %-18s | %-20s | %-20s | %-12s |%n",
            rs.getInt("parcel_id"),
            rs.getString("user_name"),
            rs.getString("sender_name"),
            rs.getString("receiver_name"),
            rs.getString("sender_add"),
            rs.getString("receiver_add"),
            rs.getString("status"));
}

System.out.println("===============================================================================================================");

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

}
