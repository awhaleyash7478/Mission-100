package threads;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import services.CustomerVerification;

public class DriverAssignmentThread  extends Thread{
    int assignDriId=0;
    Connection conn;
    CustomerVerification cusObj;
   public DriverAssignmentThread(Connection conn)
    {
        this.conn=conn;
        
    }
    int parcelId;
     public void setParcelId(int parcelId) {
        this.parcelId = parcelId;
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
        String query1="update drivers set status='busy' where driver_id=?";
        PreparedStatement ps1=conn.prepareStatement(query1);
        ps1.setInt(1, assignDriId);
        int rows=ps1.executeUpdate();
        if(rows<=0)
        {
            System.out.println("unable to update status");
            return;
        }
        String updatestatus="update  parcel_tracking set status='Out For Delivery' where parcel_id=?";
        PreparedStatement ps2=conn.prepareStatement(updatestatus);
        ps2.setInt(1, parcelId);
        int rows2=ps2.executeUpdate();
        if(rows2<=0)
        {
            System.out.println("unable to update the values for the parcel_tracking table");
            return;
        }

        
        
      
        }else 
        {
            System.out.println("\t\t\t|Sorry all drivers are busy|");
        System.exit(0);
        }
       

        
    }catch(Exception e)
    {
        e.printStackTrace();
    }
}

}
