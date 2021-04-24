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
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/productservice?useServerPrepStmts=true&tinyInt1isBit=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;

	}

	public String insertProduct(String nic,String pcode, String pname, String pversion, String desc, String price) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			
			String query = "INSERT INTO products(productId,NIC,productCode,productName,version,description,amount) VALUES(?,?,?,?,?,?,?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, nic);
			preparedStmt.setString(3, pcode);
			preparedStmt.setString(4, pname);
			preparedStmt.setString(5, pversion);
			preparedStmt.setString(6, desc);
			preparedStmt.setDouble(7, Double.parseDouble(price));
			
			if(nic.equals("")||pcode.equals("")||pname.equals("")||pversion.equals("")||desc.equals("")||price.equals("")) {
				output="Please Fill the required feilds.";
			}
			else if(!validateNIC(nic)) {
				output = "Please check the NIC again.Follow the format.Ex:123456789V";
			}
			else if(!validateProductCode(pcode)) {
				output = "Please check the product code.";
			}
			else if(!validateversion(pversion)) {
				output = "Please check the format. Ex:'2020-01'";
			}
			else {
			
				preparedStmt.execute();
				con.close();
				
			    output = "Inserted successfully"; 
			}
		} catch (Exception e) {

			output = "Error while inserting the product.";
			System.out.println(e.getMessage());
		}

		return output;
	}
	
	//validations
	
	public static boolean validateProductCode(String pcode) {
		int length = pcode.length();
		if(length!=3) {
			return false;
		}
		if(pcode.charAt(length-3)!= 'p') {
			return false;
		}
		// Check last 2 characters are digits
	    for (int i = 2; i > 0; i--) {
	        char currentChar = pcode.charAt(i);
	        if (currentChar < '0' || '9' < currentChar) {
	            return false;
	        }
	    }
		return true;
	}
	public static boolean validateNIC(String nic) {
	    // Check if length is 10
	    int length = nic.length();
	    if (length != 10) {
	        return false;
	    }
	   
	    // Check last character for V
	    char lastChar = nic.charAt(length - 1);
	    if (lastChar != 'V') {
	        return false;
	    }

	    // Check first 9 characters are digits
	    for (int i = 0; i < length - 2; i++) {
	        char currentChar = nic.charAt(i);
	        if (currentChar < '0' || '9' < currentChar) {
	            return false;
	        }
	    }

	    return true;
	}
	
	public static boolean validateversion(String version) {

		if(!version.matches("\\d{4}-\\d{2}")) {
			return false;
		}
		
		return true;
	}


	public String readProducts(String nic) {
		
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
		            +"<th>Amount</th>"
		            + "<th>Update</th><th>Remove</th></tr>";
		             
		 
		 String query = "SELECT * FROM products WHERE NIC= '"+nic+"'"; 
		 Statement stmt = con.createStatement(); 
		 ResultSet rs = stmt.executeQuery(query); 
		 // iterate through the rows in the result set
		 while (rs.next()) 
		 { 
			 String productID = Integer.toString(rs.getInt("productId")); 
			 String usernic = rs.getString("NIC");
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
			 output += "<td><input name='btnUpdate' type='button' value='Update'  class='btn btn-secondary'></td>"
					 + "<td><form method='post' action='items.jsp'>"
					 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
					 + "<input name='itemID' type='hidden' value='" + productID 
					 + "'>" + "</form></td></tr>"; 
			
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
	
	public String readAllProducts() {
		
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
		            +"<th>Amount</th>"
		            + "<th>Pay</th></tr>";
		             
		 
		 String query = "SELECT * FROM products"; 
		 Statement stmt = con.createStatement(); 
		 ResultSet rs = stmt.executeQuery(query); 
		 // iterate through the rows in the result set
		 while (rs.next()) 
		 { 
			 String productID = Integer.toString(rs.getInt("productId")); 
			 String usernic = rs.getString("NIC");
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
			 output += "<td><input name='btnUpdate' type='button' value='PAY' class='btn btn-secondary'></td></tr>";
			
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
	
	public String updateProduct(String productId,String pcode,String pname,String pversion,String desc,String price) {
		
		String output="";
		
		try {
			
			Connection con = connect();
			
			if(con==null) {
				
				return "Error while connecting to the database for updating.";
			}
			
			String query = "UPDATE products SET productCode=?,productName = ?,version=?,description=?,amount=? WHERE productId='"+productId+"'";
			
			PreparedStatement prepStatement = con.prepareStatement(query);
			prepStatement.setString(1, pcode);
			prepStatement.setString(2, pname);
			prepStatement.setString(3, pversion);
			prepStatement.setString(4, desc);
			prepStatement.setDouble(5, Double.parseDouble(price));
			
			if(pcode.equals("")||pname.equals("")||pversion.equals("")||desc.equals("")||price.equals("")) {
				output="Please Fill the required feilds.";
			}
			
			else if(!validateProductCode(pcode)) {
				output = "Please check the product code.";
			}
			else if(!validateversion(pversion)) {
				output = "Please check the format. Ex:'2020-01'";
			}
			else {
				prepStatement.execute();
				con.close();
				output="Updated Successfully.";
			}
		}catch(Exception e) {
			output="Error while updating the item.";
			System.out.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
	public String deleteProduct(String pID) {
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con==null) {
				return "Error while connecting to the database for deleting.";
			}
			
			String query = "DELETE FROM products WHERE productId = ?";
			
			PreparedStatement preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setInt(1, Integer.parseInt(pID));
			
			preparedStatement.execute();
			con.close();
			
			output="Deleted Successfully";
			
		}catch(Exception e ) {
			
			output = "Error while deleting the item.";
			System.out.println(e.getMessage());
		}
		
		return output;
	}

}
