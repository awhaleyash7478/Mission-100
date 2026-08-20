package threads;
import services.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.*;

public class NotificationThread extends Thread{
    Connection conn;
    public NotificationThread(Connection conn)
    {
        this.conn=conn;
    }
    Object currDate;
    Object currTime;
    public void run()
    {
        while(true)
        {
            try 
            {
                checkNotification();
                Thread.sleep(5000);
            }catch(Exception e)
            {
                e.printStackTrace();
                break;

            }
        }
    }
    public void checkNotification()
    {
        // System.out.println("notification thread started");
        String sender=null,receiver=null;
        int notification_id=0;
        double amount=0.0;

    
        try{
            String userName=CustomerVerification.userName;
            String query="select * from notification where user_name=?";
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            preparedStatement.setString(1,userName );
        ResultSet resultSet=preparedStatement.executeQuery();
        if((resultSet.next()))
        {
            sender=resultSet.getString("sender");
            
            amount=resultSet.getDouble("amount");
            notification_id=resultSet.getInt("notification_type");
            currTime=resultSet.getObject("time");
            currDate=resultSet.getObject("date");
        }
        if(notification_id==1)
        {
            System.out.println("----------------------------");
            System.out.println("Money received: "+amount);
            System.out.println("From: "+sender);
            System.out.println("Time: "+currTime);
            System.out.println("Date: "+currDate);
             System.out.println("----------------------------");

        }else if(notification_id==2)  
        {
               System.out.println("----------------------------");
            System.out.println("Money requested: "+amount);
            System.out.println("From: "+sender);
            System.out.println(" At Time: "+currTime);
            System.out.println(" At Date: "+currDate);
             System.out.println("----------------------------");


        }
        String delete="delete from notification where user_name=?";
    PreparedStatement ds=conn.prepareStatement(delete);
    ds.setString(1, userName);
    ds.executeUpdate();
        // System.out.println("Thread ended");

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
