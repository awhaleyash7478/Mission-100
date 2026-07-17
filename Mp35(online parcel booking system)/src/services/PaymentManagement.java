package services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import threads.*;

import java.util.Random;
import java.util.Scanner;

import javax.xml.transform.Result;

public class PaymentManagement {
    ParcelDetails parObj;
    ParcelBooking parObj2;
    Connection conn;
    CustomerVerification cusObj;
    Scanner sc;
     int tempParcelID=0;
    public PaymentManagement(ParcelDetails parobj,ParcelBooking parObj2,Connection conn,Scanner sc,CustomerVerification cusObj)
    {
        this.parObj=parobj;
        this.parObj2=parObj2;
         this.conn=conn;
         this.sc=sc;
         this.cusObj=cusObj;
    }

     final double baseCharge=50;
    double weightCharge,distanceCharge;
    double GST;
    double finalAmount;
    double subTotal;
    public void calculatePrice()
    {
      weightCharge=20*parObj.weight;



distanceCharge =parObj2.distance*5;

subTotal=distanceCharge+weightCharge+baseCharge;
GST=(subTotal*18)/100;
finalAmount=subTotal+GST;
System.out.println("\t\t---------------------------");
System.out.println("\t\tFinal Amount: "+finalAmount);
System.out.println("\t\t---------------------------");
payment();



    }
    
    // CustomerVerification cusObj=new CustomerVerification(sc, conn, parObj2);
    public void payment()
    {
       double amount=0.0;
        try 
        {
            System.out.println("Enter the Total Charge: "+finalAmount);
            amount=sc.nextDouble();
        }catch(Exception e)
        {
            System.out.println("pls enter the valid amount only");
        }
        if(amount!=finalAmount)
        {
            System.out.println("Invalid amount final amount is: "+finalAmount);
            return;
        } 
        PaymentProcessingThread p=new PaymentProcessingThread();
        p.start();
        try 
        {
            p.join();
            int fetchedId=0;
            int fetchedId2=0;
                  String fetchId="SELECT * FROM sender ORDER BY sender_id DESC LIMIT 1;";
              PreparedStatement ps1=conn.prepareStatement(fetchId);
        ResultSet rs1=ps1.executeQuery();
        if(rs1.next())
        {
         fetchedId=rs1.getInt("sender_id");
        
       
        }
              String fetchId2="SELECT * FROM receiver ORDER BY receiver_id DESC LIMIT 1;";
              PreparedStatement ps2=conn.prepareStatement(fetchId2);
        ResultSet rs2=ps2.executeQuery();
        if(rs2.next())
        {
         fetchedId2=rs2.getInt("receiver_id");
        
      
        }
        
            String query="insert into parcelDetails(weight,charges,sender_id,receiver_id)values(?,?,?,?)";
            
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setDouble(1,parObj.weight);
            ps.setDouble(2, finalAmount);
            ps.setInt(3, fetchedId);
            ps.setInt(4, fetchedId2);
           int rows=ps.executeUpdate();
           if(rows<=0)
           {
            System.out.println("Unable to insert parcel details in parcel details table");
        return;   
        }
        String parcelid="select parcel_id from parcelDetails order by parcel_id desc limit 1";
        
        PreparedStatement ppp=conn.prepareStatement(parcelid);
        ResultSet rsss=ppp.executeQuery();
       
        if(rsss.next())
        {
tempParcelID=rsss.getInt("parcel_id");
System.out.println("parcel id from parcel details: "+tempParcelID);
        }
                String fetchUserName="select * from login order by login_id desc limit 1";
            PreparedStatement preparedStatement=conn.prepareStatement(fetchUserName);
            String username=null;
            ResultSet rr=preparedStatement.executeQuery();
            if(rr.next())
            {
username=rr.getString("user_name");
            
            }
        String query2="insert into parcel_history(parcel_id,sender_name,receiver_name,sender_add,receiver_add,status,user_name)values(?,?,?,?,?,?,?)";
        PreparedStatement pss=conn.prepareStatement(query2);
        pss.setInt(1,tempParcelID);
        pss.setString(2, parObj2.senderName);
        pss.setString(3, parObj2.receiverName);
        pss.setString(4, parObj2.sender_add);
        pss.setString(5, parObj2.receiver_add);
        String tempStatus="Booked";
        pss.setString(6,tempStatus);
        pss.setString(7,username);
        int rowss=pss.executeUpdate();
        if(rowss<=0)
        {
            System.out.println("unable to insert into the table parcel history");
        return;
        }
        String parcel="insert into parcel_tracking(parcel_id,status,username)values(?,?,?)";
        PreparedStatement ps3=conn.prepareStatement(parcel);
        ps3.setInt(1, tempParcelID);
        ps3.setString(2, tempStatus);
        ps3.setString(3, username);
        int rows3=ps3.executeUpdate();
        if(rows3<=0)
        {
            System.out.println("unable to insert the values in parcel_tracking table");
            return;
        }

        

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        DriverAssignmentThread d=new DriverAssignmentThread(conn);
        d.setParcelId(tempParcelID);
        d.start();

        try 
        {
            d.join();
        }catch(Exception e)
        {
            e.printStackTrace();
        }


    
        DeliveryThread dd=new DeliveryThread(cusObj,conn);
       Random random = new Random();
 int otpgenerated = 1000 + random.nextInt(9000);


System.out.println("\t\t--------------------------------");
System.out.println("\t\tOTP sent to registered mob no.");
System.out.println("\t\tOTP: " + otpgenerated);
System.out.println("\t\tOTP valid until 5 minutes");
System.out.println("\t\t--------------------------------");

OtpValidationThread t=new OtpValidationThread(cusObj);

t.setDaemon(true);
t.start();
int OTP;
System.out.println("Enter the otp:");
OTP=sc.nextInt();
int flag=0;
if(OTP==otpgenerated)
{
    flag=1;
    System.out.println("Valid otp");
   dd.setParcelId(tempParcelID);
     dd.start();
    
}else if(flag==0) 
{
    System.out.println("Invalid otp");
    return;

}
       
      


          
        
    }

}
