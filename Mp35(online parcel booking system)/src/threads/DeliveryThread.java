package threads;

import java.sql.Connection;
import java.sql.PreparedStatement;

import services.CustomerVerification;
import services.ParcelHistory;

public class DeliveryThread extends Thread {

CustomerVerification cusObj;
Connection conn;
public DeliveryThread(CustomerVerification cusObj,Connection conn)
{
    this.cusObj=cusObj;
    this.conn=conn;
   
}
int parcelId;
public void setParcelId(int parcelId)
{
    this.parcelId=parcelId;
}



    public void run()
    {
        
        OtpValidationThread o=new OtpValidationThread(cusObj);
        o.start();
        System.out.println("\t\t---------------------------------");
        System.out.println("\t\tPackage successfully delivered");
        System.out.println("\t\t---------------------------------");
        ParcelHistory hisObj=new ParcelHistory(conn);
        try 
        {
            
            String query="update parcel_tracking set status='Delivered' where parcel_id=?";
            PreparedStatement ps=conn.prepareCall(query);
            ps.setInt(1, parcelId);
            int rows=ps.executeUpdate();
            if(rows<=0)
            {
                System.out.println("Unable to update the table package_tracking");
                return;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
       // hisObj.viewHistory();
      
    
    

    }
}
