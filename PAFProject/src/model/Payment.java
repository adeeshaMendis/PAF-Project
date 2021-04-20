package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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

public String insertPayment(String NIC, String creditNumber, String cvv, String expireDate,String date,String amount)
{
	String output = "";
	try
	{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for inserting."; }
		// create a prepared statement
		String query = " insert into payments(paymentID,NIC,creditNumber,cvv,expireDate,date,amount)"
		+ " values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setInt(1, 0);
		preparedStmt.setString(2,NIC);
		preparedStmt.setString(3, creditNumber);
		preparedStmt.setString(4, cvv);
		preparedStmt.setString(5, expireDate);
		preparedStmt.setString(6, date);
		preparedStmt.setDouble(7, Double.parseDouble(amount));
		
		//execute the statement
		
		preparedStmt.execute();
		con.close();
		output = "Inserted successfully";
	}
	catch (Exception e)
	{
	output = "Error while inserting the item.";
	System.err.println(e.getMessage());
	}
	return output;
}



}
