package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Payment {
	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", "");
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
}
	public static boolean valDate(String date) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		
		
		df.setLenient(false);
		
		try {
			
			df.parse(date.trim());
			
			
		}catch(Exception e) {
			return false;
		}
		return true;
		
		
	}


	public String insertPayment(String NIC, String creditNumber, String cvv, String expireDate,String date,String amount) {
		String output = "";
		try
		 {
		 Connection con = connect();
		
		 
		 if (con == null)
		 {return "Error while connecting to the database for inserting."; }
		 else {
			 try {
				 
				 Statement stmt = con.createStatement();
				 String selectQuery = "SELECT * FROM payments WHERE creditNumber = '"+creditNumber+"'";
				 
				 ResultSet rs = stmt.executeQuery(selectQuery);
				 
				 if(rs.next()) {
					 return "Already registerd same credit card number";
				 }else if(NIC.equals("") || creditNumber.equals("") || cvv.equals("") || expireDate.equals("") || date.equals("") || amount.equals("")){
					 return "Please fill all the details";
				 }else if(valDate(expireDate) ==false){
					 return "Expire Date should be 'dd-MM-yyyy' in format";
				 }else if(valDate(date)== false){
					 return "date should be 'dd-MM-yyyy' in format";
				 }
				 else {
					 // create a prepared statement
					 String query = " insert into payments(paymentID,NIC,creditNumber,cvv,expireDate,date,amount)"
					 + " values (?, ?, ?, ?, ?, ?, ?)";
					 PreparedStatement preparedStmt = con.prepareStatement(query);
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, NIC);
					 preparedStmt.setString(3, creditNumber);
					 preparedStmt.setString(4, cvv);
					 preparedStmt.setString(5, expireDate);
					 preparedStmt.setString(6, date);
					 preparedStmt.setDouble(7, Double.parseDouble(amount));		 
					// execute the statement
			
					 preparedStmt.execute();
					 con.close();
					 output = "Inserted successfully";
				 }
		 }catch(Exception e) {
			 
		 }
		 }
		 }
		 catch (Exception e)
		 {
		 output = "expire date should be greater than today date";
		 System.err.println(e.getMessage());
		 }
		return output;
	
		
	}
}
	
	 

