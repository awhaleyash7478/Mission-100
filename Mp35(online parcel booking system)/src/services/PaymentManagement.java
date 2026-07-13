package services;
import java.sql.Connection;
import java.sql.PreparedStatement;

import threads.*;

import java.util.Random;
import java.util.Scanner;

public class PaymentManagement {
    ParcelDetails parObj;
    ParcelBooking parObj2;
    Connection conn;
    Scanner sc;
    public PaymentManagement(ParcelDetails parobj,ParcelBooking parObj2,Connection conn,Scanner sc)
    {
        this.parObj=parobj;
        this.parObj2=parObj2;
         this.conn=conn;
         this.sc=sc;
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



    }CustomerVerification cusObj=new CustomerVerification(sc, conn, parObj2);
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
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        DriverAssignmentThread d=new DriverAssignmentThread(conn);
        d.start();
        try 
        {
            d.join();
        }catch(Exception e)
        {
            e.printStackTrace();
        }


    
        DeliveryThread dd=new DeliveryThread(cusObj);
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
   
     dd.start();

}else if(flag==0) 
{
    System.out.println("Invalid otp");
    return;

}
       
      


          
        
    }

}
