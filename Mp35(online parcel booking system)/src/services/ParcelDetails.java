package services;

import java.sql.Connection;
import java.util.Scanner;

public class ParcelDetails {
    Connection conn;
    Scanner sc;
   public  ParcelDetails(Scanner sc,Connection conn)
    {
        this.sc=sc;
        this.conn=conn;
    
    }
       double weight=0.0;

    public void getParcelDetails()
    {
        int ch=0;
     
        double declaredValue=0;
        System.out.println("1.Document\n2.Electronics\n3.Clothes\n4.Food\n5.Fragile");
 try 
 {
    System.out.println("Enter the parcel type(eg 1 for Document):");
    ch=sc.nextInt();
    
    System.out.println("Enter the parcel weight:");
 weight=sc.nextDouble(); 
 if(weight<=0)
 {
    System.out.println("Weight can't be negative or zero");
 }else if(weight>20) 
 {
    System.out.println("\t\t------------------------------------------------------");
    System.out.println("\t\tWeight exceeded the limit allowed weight is upto 20 kg");
    System.out.println("\t\tPls contact the cargo services for more details");
     System.out.println("\t\t------------------------------------------------------");
     return;
 }

 System.out.println("Enter the declared value of the parcel:");
 declaredValue=sc.nextDouble();
 if(declaredValue<=0)
 {
    System.out.println("Declared value can't be negative or zero");
    return;
 }

 }catch(Exception e)
 {
    System.out.println("invalid entry pls enters the digits only...");
    return;
 }
 
 
    }

}
