package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import threads.PaymentThread;

public class BillCalculation {
    ShowSelection showObj;
    Connection conn;
    Scanner sc;
    MovieSelection movObj;
    public BillCalculation(ShowSelection showObj,Connection conn,Scanner sc,MovieSelection movObj)
    {
        this.showObj=showObj;
        this.conn=conn;
        this.sc=sc;
       this.movObj=movObj;
    }
    ArrayList<Integer> Vip=new ArrayList<>();
    ArrayList<Integer>Premium=new ArrayList<>();
    ArrayList<Integer>standard=new ArrayList<>();
    int vipLength,preLength,stdLength;
    double billVipSection,billPremiumSection,billStandardSection;
    ArrayList<Integer> storedseatNo=new ArrayList<>();
    double totalBill;
    int totalSeats;
    double gst;
    double finalTicketPrice;

    public void calculateBill()
    {
// MovieSelection movObj=new MovieSelection(sc, conn, showObj);
       
        try 

        {
            String query="select seat_no from bookSeats where cus_id=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1, movObj.generated_cus_id);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                storedseatNo.add(rs.getInt("seat_no"));
            }
            System.out.println("before sorting:"+storedseatNo);
            storedseatNo.sort(null);
            String seatnum=storedseatNo.toString();
            String storeseat="update bookingdetails set seat_no=? where cus_id=?";
            PreparedStatement pp=conn.prepareStatement(storeseat);
            pp.setString(1,seatnum);
            pp.setInt(2, movObj.generated_cus_id);
            int rr=pp.executeUpdate();
            if(rr>0)
            {
                System.out.println("yes we have doneit: "+seatnum);
            }else 
            {
                System.out.println("haar nhi manege");
            }
          

        }catch(SQLException e)
        {
            e.printStackTrace();

        }
        System.out.println("stored seat number:"+storedseatNo);
        for(int i=0;i<storedseatNo.size();i++){
            System.out.println("stored seat nos:"+storedseatNo);
            if(storedseatNo.get(i)<=20)
            {
                Vip.add(storedseatNo.get(i));
                vipLength=Vip.size();
                billVipSection=vipLength*400;
                System.out.println("bill vip:"+billVipSection);
                System.out.println("vip seats:"+Vip);
                
            }else if(storedseatNo.get(i)<=40)
            {
                Premium.add(storedseatNo.get(i));
                preLength=Premium.size();
                billPremiumSection=preLength*300;
                
            }else 
            {
                standard.add(storedseatNo.get(i));
                stdLength=standard.size();
                billStandardSection=stdLength*200;
            }
        }
        totalBill=billVipSection+billPremiumSection+billStandardSection;
        totalSeats=vipLength+preLength+stdLength;
        
        
        gst=totalBill*0.10;
        finalTicketPrice=totalBill+gst;
        System.out.println("total bill:"+finalTicketPrice+"seats:"+totalSeats);
        payment();

try 
{
    String query="update bookingdetails set amount=? ,status=? where cus_id=?";
    PreparedStatement ps=conn.prepareStatement(query);
    ps.setDouble(1, finalTicketPrice);
    ps.setString(2, showObj.status);
    ps.setInt(3,movObj.generated_cus_id );
    int u=ps.executeUpdate();
    if(u>0)
    {
        System.out.println("yash finally finally done");

    }else 
    {
        System.out.println("wait a little");
    }

}catch(Exception e)
{
    e.printStackTrace();
}
    }
    
    public void payment()
    {
        Double amount=0.0;
        try
        {
            System.out.println("Enter the amount:");
            amount=sc.nextDouble();
        }catch(Exception e)
        {
            System.out.println("pls enter the valid amount");
            sc.nextLine();
            return;
        }
       if(amount!=finalTicketPrice)
       {
        System.out.println("Total Amount is: "+finalTicketPrice+"not: "+amount);
        return;
       }else
       {
        PaymentThread payObj=new PaymentThread();
        payObj.start();
        try 
        {
            payObj.join();
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        showObj.status="Booked";

       }
        
    }

}
