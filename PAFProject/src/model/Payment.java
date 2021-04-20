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
	
	
	
	public static boolean valDate(String date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		
		
		df.setLenient(false);
		
		try {
			
			df.parse(date.trim());
			return true;
			
			
		}catch(Exception e) {
			return false;
			
		}
		
		
	}
	
   
	
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
	
	public String insertPayment(String NIC, String creditNumber, String cvv, String expireDate,String date,String amount)
	{
	String output = "";
	try
	{
	Connection con = connect();
	
	Date eDate = Date.valueOf(expireDate);
	Date sDate = Date.valueOf(date);
	if (con == null)
	{return "Error while connecting to the database for inserting."; }
	// create a prepared statement
	else  {
		try {
			
		Statement stmt = con.createStatement();
		String selectQuery = "SELECT * FROM payments where creditNumber = '"+creditNumber+"'";
		
		ResultSet rs = stmt.executeQuery(selectQuery);
		
		if(rs.next()) {
			return "Already inserted same credi car number";
		}else if(NIC.equals("") || creditNumber.equals("") ||cvv.equals("") || expireDate.equals("") || date.equals("") || amount.equals("")) {
			return "Please fill all the fileds";
		}else if(NIC.length() !=10) {
			return "NIC should be 9 digits and last one should be v";
		}
		else if(creditNumber.length() !=12){
			return "Please credit card should be 12 digits";
		}else if(cvv.length() !=4) {
			return "Please cvv should be 4 digits";
		}else if(valDate(expireDate) == false) {
			return "expire date should be yyyy-MM-dd format";
		
		
		}else if(eDate.compareTo(sDate)<0) {
			return " expire date should be greater than date";
		}
		else{
		String query = " insert into payments(paymentID,NIC,creditNumber,cvv,expireDate,date,amount)"
		+ " values (?, ?, ?, ?, ?,?,?)";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setInt(1, 0);
		preparedStmt.setString(2, NIC);
		preparedStmt.setString(3, creditNumber);
		preparedStmt.setString(4, cvv);
		preparedStmt.setDate(5, eDate);
		preparedStmt.setDate(6, sDate);
		preparedStmt.setDouble(7, Double.parseDouble(amount));
		
		// execute the statement
		
		preparedStmt.execute();
		con.close();
		output = "Inserted successfully";
		}
		}catch (Exception e)
		{
		output = "NIC should be 9 digits and last one should be v";
		System.err.println(e.getMessage());
		}
	}

	}catch(Exception e) {
		output = "date and expire date should be 'yyyy-MM-dd' this format ";
		System.err.println(e.getMessage());
	}
	return output;
		
	
		
		
	
	
	}
	public String readPayments(String NIC) {
		String output = "";
		
		try {
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database for reading!";
			}
			output = "<table border='1'><tr><th>PaymentID</th><th>NIC</th><th>creditNumber</th>" +
					"<th>cvv</th>"+
					"<th>expireDate</th>"+
					"<th>Date</th>"+
					"<th>amount</th>"+
					"<th>Update</th><th>Delete</th></tr>";
			
			String query = "select * from payments where NIC= '"+NIC+"'"; 
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				String paymentID = Integer.toString(rs.getInt("paymentID"));
				String BuyerNIC = rs.getString("NIC");
				String creditNumber = rs.getString("creditNumber");
				String cvv = rs.getString("cvv");
				String expireDate = rs.getString("expireDate");
				String date = rs.getString("date");
				String amount = Double.toString(rs.getDouble("amount"));
				
				output += "<tr><td>"+paymentID+"</td>";
				output += "<td>"+BuyerNIC+"</td>";
				output += "<td>"+creditNumber+"</td>";
				output += "<td>"+cvv+"</td>";
				output += "<td>"+expireDate+"</td>";
				output += "<td>"+date+"</td>";
				output += "<td>"+amount+"</td>";
				
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						+ "<td><form method='POST' action='payment.jsp'>"
						+"<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						+"<input name='paymentID' type='hidden' value="+paymentID+">"+"</form></td></tr>";
						
				
				
				
			}
			con.close();
			
			output += "</table>";
			
		}catch(Exception e) {
			
			
		}
		return output;
		
		
	}
	public String updatePayment(String paymentID, String NIC, String creditNumber, String cvv, String expireDate,String date,String amount)
	
	{
	String output = "";
	try
	{
	Connection con = connect();
	
	Date eDate = Date.valueOf(expireDate);
	Date sDate = Date.valueOf(date);
	if (con == null)
	{return "Error while connecting to the database for updating."; }
	// create a prepared statement
	else if(NIC.length() !=10) {
		return "NIC should be 9 digits and last one should be v";
	}
	else if(creditNumber.length() !=12){
				return "Please credit card should be 12 digits";
			}else if(cvv.length() !=4) {
				return "Please cvv should be 4 digits";
			}else if(valDate(expireDate) == false) {
				return "expire date should be yyyy-MM-dd format";
			
			
			}else if(eDate.compareTo(sDate)<0) {
				return " expire date should be greater than date";
			}
			else {
			
				String query = "UPDATE payments SET creditNumber=?,cvv=?,expireDate=?,date=?,amount=? WHERE NIC=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setString(1, creditNumber);
				preparedStmt.setString(2, cvv);
				preparedStmt.setString(3, expireDate);
				preparedStmt.setString(4,date);
				preparedStmt.setDouble(5, Double.parseDouble(amount));
				
				
				preparedStmt.setString(6, NIC);
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Updated successfully";
				}
		}catch(Exception e) {
			return "Date and expire date should be 'yyyy-MM-dd' this format";
					}
				
				
			return output;
			}
	
	public String deletePayment(String NIC)
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for deleting."; }
	// create a prepared statement
	String query = "delete from payments where NIC=?";
	PreparedStatement preparedStmt = con.prepareStatement(query);
	// binding values
	preparedStmt.setString(1, NIC);
	// execute the statement
	preparedStmt.execute();
	con.close();
	output = "Deleted successfully";
	}
	catch (Exception e)
	{
	output = "Error while deleting the item.";
	System.err.println(e.getMessage());
	}
	return output;
	}
	}
	
	
	
	


	
