package services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TicketGeneration
{
    Connection conn;
    MovieSelection movObj;
    public TicketGeneration(Connection conn,MovieSelection movObj)
    {
        this.conn=conn;
        this.movObj=movObj;
      
    }
    int cusId,movId,theaId,showId;
    double amount;
    String seatno,status,theatreName;
 public void generateTicket()
 {
    try 
    {

        String query="select * from bookingdetails where cus_id=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1,movObj.generated_cus_id);
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
            cusId=rs.getInt("cus_id");
            movId=rs.getInt("movie_id");
            theaId=rs.getInt("theatre_id");
            showId=rs.getInt("show_id");
            seatno=rs.getString("seat_no");
            amount=rs.getDouble("amount");
            status=rs.getString("status");
           
            
        }
        String query2="select theatre_name from theatres where theatre_id=?";
        PreparedStatement pss=conn.prepareStatement(query2);
        pss.setInt(1, theaId);
        ResultSet rss=pss.executeQuery();
        if(rss.next())
        {
            theatreName=rss.getString("theatre_name");
        }
        
        viewTicket();
        
    }catch(Exception e)
    {
        e.printStackTrace();
    }
 }
 public void viewTicket()
 {
    System.out.println("\n==================================================");
System.out.println("                 🎬 MOVIE TICKET 🎬");
System.out.printf("              %s\n", theatreName);
System.out.println("==================================================");

System.out.printf("| Customer ID : %-33d|\n", cusId);
System.out.printf("| Movie ID    : %-33d|\n", movId);
System.out.printf("| Theatre ID  : %-33d|\n", theaId);
System.out.printf("| Show ID     : %-33d|\n", showId);

System.out.println("--------------------------------------------------");

System.out.printf("| Seat Number : %-33s|\n", seatno);
System.out.printf("| Amount Paid : ₹%-32.2f|\n", amount);
System.out.printf("| Status      : %-33s|\n", status);

System.out.println("--------------------------------------------------");
System.out.println("|              Enjoy Your Movie! 🍿              |");
System.out.println("|        Thank You For Choosing Us              |");
System.out.println("==================================================");
System.exit(0);
}
 
    
}