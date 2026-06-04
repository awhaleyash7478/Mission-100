
import java.util.Scanner;
class TreatmentThread extends Thread{
    public void run()
    {
        try {

            System.out.println("Doctor Assigned...");
            Thread.sleep(2000);
            System.out.println("Treatment Started...");
                 Thread.sleep(2000);
                 System.out.println("Treatment Completed...");
        }catch(InterruptedException e)
        {
            System.out.println("THread interrupted");
        }
    }
}
class BillingThread extends Thread{
    public void run()
    {
        try {

            System.out.println("Generating Bill...");
            Thread.sleep(2000);
            System.out.println("Updating Records...");
                 Thread.sleep(2000);
                 System.out.println("Bill Generated...");
        }catch(InterruptedException e)
        {
            System.out.println("THread interrupted");
        }
    }
}
class NotificationThread extends Thread{
    public void run()
    {
        try {

            System.out.println("Sending SMS...");
            Thread.sleep(2000);
            System.out.println("Sending Email...");
                 Thread.sleep(2000);
                 System.out.println("Notification Sent...");
        }catch(InterruptedException e)
        {
            System.out.println("THread interrupted");
        }
    }
}
class Hospital {
    Scanner sc = new Scanner(System.in);
    final int max = 100;
    int id[] = new int[max];
    String name[] = new String[max];
    double age[] = new double[max];
    String disease[] = new String[max];
    int room[] = new int[max];
    String status[] = new String[max];
    int hisIndex = 0;
    String hisName[] = new String[max];
    int hisID[] = new int[max];
    double hisBill[] = new double[max];
    String hisDisease[] = new String[max];
  String hisStatus[]=new String[max];
    void addPatient() {
        String tempName = null, tempDisease = null, tempStatus = null;
        int tempID = 0, tempRoom = 0;
        double tempAge = 0.0;
        try {
            System.out.println("Enter the Patient name:");
            tempName = sc.nextLine();
            System.out.println("Enter the patient id:");
            tempID = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the disease:");
            tempDisease = sc.nextLine();
            System.out.println("Enter the room number:");
            tempRoom = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the status:");
            System.out.println("Admitted\r\n" + //
                    "Under Treatment\r\n" + //
                    "Discharged");
            tempStatus = sc.nextLine();
            System.out.println("Enter the age:");
            tempAge = sc.nextDouble();
        } catch (Exception e) {
            System.out.println("Invalid entry");
        }
        for (int i = 0; i < max; i++) {
            if (id[i] == 0) {
                id[i] = tempID;
                name[i] = tempName;
                disease[i] = tempDisease;
                room[i] = tempRoom;
                age[i] = tempAge;
                status[i] = tempStatus;
                System.out.println("Patient added successfully");

                break;
            }

        }
    }

    void viewPatient() {
        for (int i = 0; i < max; i++) {
            if (id[i]== 0)
                break; 
                System.out.println("Patient name: " + name[i] + "Patient id: " + id[i] + "\nPatient age: " + age[i]
                        + "\nPatient Disease: " + disease[i] + "\nPatient status: " + status[i] + "\n Patient room: "
                        + room[i]);

            
            
        }
    }

    void searchPatient() {
        int searchId = 0;
             int found = 0;
        try {
            System.out.println("Enter the Search id:");
            searchId = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid entry");
        }
        for (int i = 0; i < max; i++) {
       
            if (searchId == id[i]) {
                found = 1;
                System.out.println("Patient name: " + name[i] + "Patient id: " + id[i] + "\nPatient age: " + age[i]
                        + "\nPatient Disease: " + disease[i] + "\nPatient status: " + status[i] + "\n Patient room: "
                        + room[i]);
                break;

            }
        }
            if (found == 0) {
                System.out.println("Patient not found");
            }
        
    }

    void deletePatient() {
        int found = 0;
        int deleteID = 0;
        try {
            System.out.println("Enter the id:");
            deleteID = sc.nextInt();
        } catch (Exception e) {
            System.out.println("invalid entry");
        }
        for (int i = 0; i < max; i++) {
            if (deleteID == id[i]) {
                found = 1;
                for (int j = i; j < max; j++) {
                    hisID[j] = hisID[j + 1];
                    hisName[j] = hisName[j + 1];
                    hisDisease[j] = hisDisease[j + 1];
                 
                    hisStatus[j] = hisStatus[j + 1];
                    hisBill[j]=hisBill[j+1];
                    break;
                }

            }
        }
        if (found == 0) {
            System.out.println("Patient not found");
        }
    }

    void startTreatment() {
        int treatmentid = 0;
        
        int found = 0;
        try {
            System.out.println("Enter the id:");
            treatmentid = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid entry");
        }
        for (int i = 0; i < max; i++) {
            if (treatmentid == id[i]) {
                hisName[hisIndex] = name[i];
                hisDisease[hisIndex] = disease[i];
                hisID[hisIndex] = id[i];
                found = 1;
                if (status[i].equals("Admitted") || status[i].equals("admitted")) {
                    status[i] = "under treatment";
                    hisStatus[hisIndex]=status[i];
                    break;
                }
                System.out.println("Invalid entry status should be admitted not " + status[i]);
                break;
            }
        }
        if (found == 0) {
            System.out.println("Patient not found");
        }
    }

    void dischargePatient() {
        int found = 0;
        int dischargeId = 0;
        try {
            System.out.println("Enter the id:");
            dischargeId = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid entry");
        }
        for (int i = 0; i < max; i++) {
            if (dischargeId == id[i]) {
                found = 1;
                if (status[i].equals("under treatment")) {
                    
                    hisStatus[hisIndex]=status[i];
                    TreatmentThread t=new TreatmentThread();
                    t.start();
                  
                
                    BillingThread b=new BillingThread();
                    
   if(hisBill[i]==0)
                    {
                        try
                        {
                          t.join();
                        }catch(Exception e)
                        {}
                        System.out.println("first complete billing");
                        
                        try
                        {
                        b.wait();
                    }catch(Exception e){}
                    break;
                
                }
                b.start();
                    
                       try 
                    {
                    
                    t.join();
                    b.join();
                    }catch(Exception e)
                    {
                        System.out.println("exception");
                    }
                  
                    System.out.println("-----Bill-----");
                    System.out.println("Name: "+name[i]);
                    System.out.println("Bill: "+hisBill[i]);
                    status[i] = "dicharge";
                    NotificationThread n=new NotificationThread();
                    n.start();
                             try 
                    {
                    
                    n.join();
                
                    }catch(Exception e)
                    {
                        System.out.println("exception");
                    }
                    break;
                }
                System.out.println("Invalid entry status should be under treatment not " + status[i]);
                break;
            }
        }
        if (found == 0) {
            System.out.println("Patient not found");
        }

    }

    void payBill() {
        int found = 0;
        double amount;
        int billId = 0;
        try {
            System.out.println("Enter the id:");
            billId = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid entry");
        }
        for (int i = 0; i < max; i++) {
            if (billId == id[i]) {
                found = 1;
                System.out.println("Enter the amount:");
                amount = sc.nextDouble();
                hisBill[hisIndex] = amount;
                if (amount < 0) {
                    System.out.println("Amount can't be negative");
                    break;
                }
                System.out.println("bill paid successfully");
                break;
            }
        }
        if (found == 0) {
            System.out.println("Patient not found");
        }
    }
    void viewHistory()
    {
       for(int i=0;i<max;i++)
       {
        if(hisID[i]==0)
            break;
    System.out.println("Patient name: "+hisName[i]+"\nPatient disease: "+hisDisease[i]+"\n Patient id: "+hisID[i]+"\nPatient bill: "+hisBill[i]+"\nPatient Status: "+hisStatus[i]);
       }
      
        
    }

    void menu() {
        int ch = 0;
        while (true) 
            
        {
        try {
            System.out.println("1.Add Patient\n2.View Patient\n3.Search Patient\n4.Delete Patient\n5.Start Treatment\n6.Discharge Patient\n7.Pay bill\n8.View History\n9.Exit");
            System.out.println("Enter the choice:");
            ch = sc.nextInt();
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("invalid entry");
        }
        switch (ch) {
            case 1:
                addPatient();
                break;
            case 2:
                viewPatient();
                break;
            case 3:
                searchPatient();
                break;
            case 4:
                deletePatient();
                break;
            case 5:
                startTreatment();
                break;
            case 6:
                dischargePatient();
                break;
            case 7:
                payBill();
                break;
            case 8:
                viewHistory();
                break;
            case 9:
                return;
            default:
                System.out.println("Invalid entry");
                break;
        }
    }
}
}
class Mp19
{
    public static void main(String[] args) {
        Hospital h=new Hospital();
        h.menu();
    }
}