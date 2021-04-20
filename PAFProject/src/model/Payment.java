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
				 }else if(creditNumber.length()!=12) {
					 return "credit card number should be 12 digits";
				 }else if(cvv.length() !=4) {
					 return "cvv should be 4 digits";
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
		 output = "error while inserting data";
		 System.err.println(e.getMessage());
		 }
		return output;
	
		
	}
	public String readPayment(String NIC)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for reading."; }
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Payment ID</th><th>NIC</th>" +
	 "<th>Credit Number</th>" +
	 "<th>Cvv</th>" +
	 "<th>Expire Date</th>" +
	 "<th>Date</th>" +
	 "<th>Amount</th>" +
	 "<th>Update</th><th>Remove</th></tr>";

	 String query = "select * from payments where NIC = '"+NIC+"'";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
	 String paymentID = Integer.toString(rs.getInt("paymentID"));
	 String UserNIC = rs.getString("NIC");
	 String creditNumber = rs.getString("creditNumber");
	 String cvv = rs.getString("cvv");
	 String expireDate = rs.getString("expireDate");
	 String date = rs.getString("date");
	 String amount = Double.toString(rs.getDouble("amount"));
	 
	 // Add into the html table
	 output += "<tr><td>" + paymentID + "</td>";
	 output += "<td>" + UserNIC + "</td>";
	 output += "<td>" + creditNumber + "</td>";
	 output += "<td>" + cvv + "</td>";
	 output += "<td>" + expireDate + "</td>";
	 output += "<td>" + date + "</td>";
	 output += "<td>" + amount + "</td>";
	 // buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
	 + "<td><form method='post' action='payments.jsp'>"
	 + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
	 + "<input name='itemID' type='hidden' value='" + paymentID
	 + "'>" + "</form></td></tr>";
	 }
	 con.close();
	 // Complete the html table
	 output += "</table>";
	 }
	 catch (Exception e)
	 {
	 output = "Error while reading the items.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	
	public String updatePayment(String paymentID, String NIC, String creditNumber, String cvv, String expireDate,String date,String amount)

	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for updating."; }
	 // create a prepared statement
	 else if(NIC.equals("") || creditNumber.equals("") || cvv.equals("") || expireDate.equals("") || date.equals("") || amount.equals("")) {
		 return "please fill all the fields";
	 }else if(valDate(expireDate) == false) {
		 return "expire Date should be 'dd-MM-YYYY'";
	 }else if(valDate(date) == false) {
		 return "date should be 'dd-MM-yyyy'";
	 }
	 String query = "UPDATE payments SET creditNumber=?,cvv=?,expireDate=?,date=?,amount=? WHERE NIC=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setString(1, creditNumber);
	 preparedStmt.setString(2, cvv);
	 preparedStmt.setString(3, expireDate);
	 preparedStmt.setString(4, date);
	 preparedStmt.setDouble(5, Double.parseDouble(amount));
	 
	 preparedStmt.setString(6, NIC);
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Updated successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while updating the item.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	
	
}
	
	 

