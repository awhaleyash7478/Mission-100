package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DriverAssignmentThread extends Thread{
    int assignDriId;
    Connection conn;
    Customers obj;
    int assignRideID;
    DriverAssignmentThread(Connection conn,Customers obj)
    {
        this.obj=obj;
        this.conn=conn;

    }

public void run()
{
    
    try
    {
        String tempStatus="free";
        String query="select * from drivers where status=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, tempStatus);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
        assignDriId=rs.getInt("driver_id");
        
        }else 
        {
            System.out.println("Sorry all drivers are busy");
        System.exit(0);
        }
       

        
    }catch(Exception e)
    {
        e.printStackTrace();
    }
}
    
}
