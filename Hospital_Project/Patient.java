package Management_System;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.Scanner;

	public class Patient{
	    private Connection connection;
	    private Scanner scanner;
	    public Patient(Connection connection, Scanner scanner) {
	        
	        this.connection = connection;
	        this.scanner = scanner;
	    }
	    
	    public void addPatient() {
	        System.out.println("Enter Patient name :");
	        String name =scanner.next();
	        System.out.println("Enter Patient age :");
	        int age =scanner.nextInt();
	        System.out.println("Enter Patient gender :");
	        String gender = scanner.next();
	        
	        try {
	             String quary ="INSERT INTO patients(name , age , gender) VALUES(?, ?, ?)";
	             PreparedStatement preparedStatement = connection.prepareStatement(quary);
	             preparedStatement.setString(1, name);
	             preparedStatement.setInt(2, age);
	             preparedStatement.setString(3, gender);
	              int affectedRows = preparedStatement.executeUpdate();
	              if(affectedRows > 0) {
	                  System.out.println("Patient Added Successfully...");
	              }else {
	                System.out.println("Failed to Add Patient...");
	            }
	             
	        }catch (SQLException e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }
	        
	    }
	    public void viewPatients() {
	        String quary = "select * from patients";
	        
	        try {
	            PreparedStatement preparedStatement = connection.prepareStatement(quary);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            System.out.println("Patients :");
	            System.out.println("+-----------+---------------------+-----------+------------+");
	            System.out.println("|patient Id | Name                | Age       | Gender     |");
	            System.out.println("+-----------+---------------------+-----------+------------+");

	            while(resultSet.next()) {
	                int id= resultSet.getInt("id");
	                String name = resultSet.getString("name");
	                int age = resultSet.getInt("age");
	                String gender = resultSet.getString("gender");
	                System.out.printf("|%-12s|%-21s|%-11s|%-12s|\n", id, name, age, gender);
	                System.out.println("+-----------+---------------------+-----------+-----------+");
	           }
	        }catch(Exception e){
	            e.printStackTrace();
	            
	                    }
	    }
	      
	     public boolean getPatientByID(int id) {
	          String quary ="SELECT * FROM patients WHERE id = ?";
	          
	          try {
	            PreparedStatement preparedStatement = connection.prepareStatement(quary);
	              preparedStatement.setInt(1,id);
	              ResultSet resultSet = preparedStatement.executeQuery();
	              if(resultSet.next()) {
	                  return true;
	              }else {
	                  return false;
	              }

	          }catch(SQLException e) {
	              e.printStackTrace();
	              
	          }
	          return false;
	     }
	}


