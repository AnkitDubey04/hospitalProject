
	import java.util.Scanner;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;

	public class Doctor {
	     
		   private Connection connection;
		   		public Doctor(Connection connection) {
				
				this.connection = connection;
			}
			
			public void viewDoctor() {
				String quary = "select * from doctors";
				
				try {
					PreparedStatement PreparedStatement = connection.prepareStatement(quary);
					ResultSet resultSet = PreparedStatement.executeQuery();
					System.out.println("Doctor :");
					System.out.println("+-----------+---------------------+-------------------+");
					System.out.println("|Doctor Id | Name                 | specialization    |");
					System.out.println("+-----------+---------------------+-------------------+");

					while(resultSet.next()) {
						int id= resultSet.getInt("id");
						String name = resultSet.getString("name");
						String specialization = resultSet.getString("specialization");
						
						System.out.printf("|%-12s|%-21s|%-20s|\n", id, name, specialization);
						System.out.println("+-----------+---------------------+-------------------+");

					}
				}catch(Exception e){
					e.printStackTrace();
					
							}
			}
				 public boolean getDoctorByID(int id) {
				  String quary ="SELECT * FROM doctors WHERE id = ?";
				  
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


