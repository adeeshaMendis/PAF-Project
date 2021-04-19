package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Product {

	private Connection connect() {

		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pafproject?useServerPrepStmts=true&tinyInt1isBit=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;

	}

	public String insertProduct(String pcode, String pname, String pversion, String desc, String price) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			String query = "INSERT INTO products(productId,productCode,productName,version,description,amount) VALUES(?,?,?,?,?,?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, pcode);
			preparedStmt.setString(3, pname);
			preparedStmt.setString(4, pversion);
			preparedStmt.setString(5, desc);
			preparedStmt.setDouble(6, Double.parseDouble(price));

			preparedStmt.execute();
			con.close();
		} catch (Exception e) {

			output = "Error while inserting the product.";
			System.out.println(e.getMessage());
		}

		return output;
	}

	public String readItems() {
		
		String output = ""; 
		
		try{
			
		 Connection con = connect(); 
		 
		 if (con == null) 
		 {
			 return "Error while connecting to the database for reading.";
			 
		 } 
		 // Prepare the html table to be displayed
		 output = "<table border='1'>"
		 		   + "<tr>"
		 		    + "<th>Product Code</th>"
		 		    + "<th>Product Name</th>" 
		            +"<th>Product Version</th>"
		            + "<th>Description</th>"
		            + "<th>Product Price</th>"+
		 "<th>Update</th><th>Remove</th></tr>"; 
		 
		 String query = "select * from products"; 
		 Statement stmt = con.createStatement(); 
		 ResultSet rs = stmt.executeQuery(query); 
		 // iterate through the rows in the result set
		 while (rs.next()) 
		 { 
			 String productID = Integer.toString(rs.getInt("productId")); 
			 String productCode = rs.getString("productCode"); 
			 String productName = rs.getString("productName"); 
			 String version =rs.getString("version"); 
			 String description = rs.getString("description"); 
			 String price =  Double.toString(rs.getDouble("amount"));
			 
			 output += "<tr><td>" + productCode + "</td>"; 
			 output += "<td>" + productName + "</td>"; 
			 output += "<td>" +version + "</td>"; 
			 output += "<td>" + description + "</td>"; 
			 output += "<td>" + price + "</td>"; 
			 
			 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
			 + "<td><form method='post' action='Product.jsp'>"
			 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
			 + "<input name='productID' type='hidden' value='"+productID+"'>"
			 + "</form></td></tr>"; 
		 } 
		 con.close(); 
		
		 output += "</table>"; 
	  } 
	  catch (Exception e) 
	  { 
		 output = "Error while reading the items."; 
		 System.err.println(e.getMessage()); 
	  } 
	  return output; 
	}

}
