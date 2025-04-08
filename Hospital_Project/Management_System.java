package Management_System;
	
//import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ManagementSystem {
      private static final String url = "jdbc:mysql://localhost:3306/Hospital";
      private static final String username = "root";
      private static final String password = "javadeveloper@12345";
      
    public static void main(String[] args) {
        // TODO Auto-generated method stub
  try {
      Class.forName("com.mysql.cj.jdbc.Driver");
     
}catch(ClassNotFoundException e) {
    e.printStackTrace();
}
  Scanner scanner= new Scanner(System.in); 
   try {
       Connection connection = DriverManager.getConnection(url,username, password);
       Patient patient = new Patient(connection, scanner);
       Doctor doctor = new Doctor(connection);
       while(true) {
           System.out.println("HOSPITAL MANAGEMENT SYSTEM :-");
           System.out.println("1. Add Patient");
           System.out.println("2. View Patient");
           System.out.println("3. View Doctor");
           System.out.println("4. Book Appointment");
           System.out.println("5. Exit....");
           System.out.println("Enter your choice... ");
           
           int choice = scanner.nextInt();
           switch (choice) {
        case 1:
            patient.addPatient();
            System.out.println();
            break;
        case 2: 
            patient.viewPatients();
            System.out.println();
            break;
        case 3: 
            doctor.viewDoctor();
            System.out.println();
            break;
        case 4:
            bookAppointment(patient, doctor, connection, scanner);
            System.out.println();
            break;
        case 5: 
            System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM....");
            return;
            default:
                System.out.println("Enter valid choice....");
                break;
        }
           
       }
   }catch(SQLException e) {
       e.printStackTrace();
   }
    }
    
     public static void bookAppointment(Patient patient,Doctor doctor,Connection connection, Scanner scanner) {
            System.out.println("Enter Patient Id:");
            int patientId = scanner.nextInt();
            System.out.println("Enter Doctor Id:");
            int doctorId = scanner.nextInt();
            System.out.println("Enter Appointment date (YYYY-MM-DD): ");
            String appointmentDate= scanner.next();
            
            if(patient.getPatientByID(patientId) && doctor.getDoctorByID(doctorId)) {
                if (checkDoctorAvailability(doctorId, appointmentDate,connection)) {
                    String appointmentQuary = "INSERT INTO appointments (patient_id,doctor_id,appointment_date) VALUES(?,?,?)";
                    
                     try {
                        PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuary);
                         preparedStatement.setInt(1,patientId);
                         preparedStatement.setInt(2,doctorId);
                         preparedStatement.setString(3,appointmentDate);
                          
                         int rowsAffacted = preparedStatement.executeUpdate();
                         if(rowsAffacted > 0) {
                              System.out.println("Appointment Booked");
                         } 
                         else {
                              System.out.println(" Failed to Appointment Booked");
 
                         }
                }catch(SQLException e) {
                    e.printStackTrace();
                }
                
            } else {
                System.out.println(" Doctor not available on this date ");
            }
            }
         else {
            System.out.println("Either doctor or patient does Appointment date (YYYY-MM-DD)... ");
     
     }
}
        public static boolean checkDoctorAvailability(int doctor_id , String appointmentDate , Connection connection) {
            String quary = "INSERT COUNT (*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
            
         try {
             PreparedStatement preparedStatement = connection.prepareStatement(quary);
             preparedStatement.setInt(1,doctor_id);
             preparedStatement.setString(2,appointmentDate);
             
                  ResultSet resultSet = preparedStatement.executeQuery();
             if (resultSet.next()) {
         int count = resultSet.getInt(1);	
         if( count == 0) {
             return true;
         }else {
             return false;
         }
             }
     }catch(SQLException e) {
          e.printStackTrace();  
     }
        
         return false;
        }
}
