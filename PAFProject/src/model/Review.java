package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Review {
	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.cj.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/review", "root", "");
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
}
	
	public String insertReview(String reviewType,String reviewDesc, String reviewValue) {
		String output = "";
		try
		 {
			//check connection
		 Connection con = connect();
		 
		 
		 
		 if (con == null)
		 {return "Error while connecting to the database for inserting."; }
		 else {
			 
				 
				 //check values are empty or not
				if(reviewType.equals("")|| reviewDesc.equals("") || reviewValue.equals("")){
					 return "Please fill all the details";
				}
				
				//check review description limit
				else if ( reviewDesc.length() > 200 )
				{
					return "Words exceeded. Maximum words for a description is 200";
				}
				
				//check ratings between 1-5
				else if ( Integer.parseInt(reviewValue) > 5 || Integer.parseInt(reviewValue) < 5)
				{
					return "Rating value must be between 1-5";
				}

				 else  {
					 // create a prepared statement
					 String query = " insert into reviews(reviewID,reviewType,reviewDesc,reviewValue)"
					 + " values (?,?, ?, ?)";
					 PreparedStatement preparedStmt = con.prepareStatement(query);
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, reviewType);
					 preparedStmt.setString(3, reviewDesc);
					 preparedStmt.setInt(4, Integer.parseInt(reviewValue));
					 	 
					// execute the statement
			
					 preparedStmt.execute();
					 con.close();
					 output = "Inserted successfully";
				 }
		  		 		 
		}
		 			 
		
	}catch(Exception e) {
		
	}
		return output;
 }
	
	public String readReview()
	 {
	 String output = "";
	 try
	 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for reading."; }
		 
		 // Prepare the html table to be displayed
		 output = "<table border='1'><tr><th>Review ID</th><th>Review Type</th><th>Review Description</th>" +
		 "<th>Review Value</th>" +
		 "<th>Update</th><th>Remove</th></tr>";
	
		 String query = "select * from reviews ";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
		 String reviewID = Integer.toString(rs.getInt("reviewID"));
		 String reviewType = rs.getString("reviewType");
		 String reviewDesc = rs.getString("reviewDesc");
		 String reviewValue = Integer.toString(rs.getInt("reviewValue"));
		 
		 
		 // Add into the html table
		 output += "<tr><td>" + reviewID + "</td>";
		 output += "<td>" + reviewType + "</td>";
		 output += "<td>" + reviewDesc + "</td>";
		 output += "<td>" + reviewValue + "</td>";
		 
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
		 + "<td><form method='post' action='payments.jsp'>"
		 + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
		 + "<input name='itemID' type='hidden' value='" + reviewID
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
	
	public String updateReview(String reviewID, String reviewType,String reviewDesc, String reviewValue)

	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 
	 
	 if (con == null)
	 {return "Error while connecting to the database for updating."; }
	 // create a prepared statement
	 
	 //check validations
	 //check empty fields
	 else if(reviewType.equals("") ||reviewDesc.equals("")|| reviewValue.equals("")) {
		 return "please fill all the fields";
		 
		 //check credit card number is 12 digits
	 }
	 else {
	 String query = "UPDATE reviews SET reviewType=?,reviewDesc=?,reviewValue=? WHERE reviewID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setString(1, reviewType);
	 preparedStmt.setString(2, reviewDesc);
	 preparedStmt.setInt(3, Integer.parseInt(reviewValue));
	 
	 
	 preparedStmt.setInt(4, Integer.parseInt(reviewID));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Updated successfully";
	 }
	 }
	 catch (Exception e)
	 {
	 output = "Error while updating the item.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
		
	
	public String deleteReview(String reviewID)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; }
	 // create a prepared statement
	 String query = "delete from reviews where reviewID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(reviewID));
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
	
	
	
	public static boolean validateNIC(String NIC) {
		//check if length is 10
		int length = NIC.length();
		if(length != 10) {
			System.out.println("Invalid NIC. NIC length should be 10.");
			return false;
		}
		
		//check last character for v
		char lastChar = NIC.charAt(length-1);
		if(lastChar != 'V' || lastChar != 'v') {
			System.out.println("Invalid NIC.");
			return false;
		}
		//check first 9 characters are digits
		for(int i=0;i<length-2;i++) {
			char currentChar = NIC.charAt(i);
			if(currentChar<'0' || '9' <currentChar) {
				System.out.println("Invalid NIC format. First 9 digits only contain numbers");
				return false;
			}
		}
		return true;
	}
	
	public String insertUserReview(String NIC,String reviewID) {
		String output = "";
		try
		 {
			//check connection
		 Connection con = connect();
		 
		 
		 
		 if (con == null)
		 {return "Error while connecting to the database for inserting."; }
		 else {
			 
				 
				 //check values are empyt or not
				if(NIC.equals("")|| reviewID.equals("") ){
					 return "Please fill all the details";
				 
				//check expire date format
				 }
				 else if (validateNIC(NIC)) {
					 // create a prepared statement
					 String query = " insert into userreviews(NIC,reviewID)"
					 + " values (?,?)";
					 PreparedStatement preparedStmt = con.prepareStatement(query);
					 // binding values
					 preparedStmt.setString(1, NIC);
					 preparedStmt.setInt(2, Integer.parseInt(reviewID));
					 
					 	 
					// execute the statement
			
					 preparedStmt.execute();
					 con.close();
					 output = "Inserted successfully";
				 }
		  
		 
		 
		}
		 
		
	}catch(Exception e) {
		
	}
		return output;
 }
	
	public String insertProductReview(String productID,String reviewID) {
		String output = "";
		try
		 {
			//check connection
		 Connection con = connect();
		 
		 
		 
		 if (con == null)
		 {return "Error while connecting to the database for inserting."; }
		 else {
			 
				 
				 //check values are empyt or not
				if(productID.equals("")|| reviewID.equals("") ){
					 return "Please fill all the details";
				 
				//check expire date format
				 }
				 else  {
					 // create a prepared statement
					 String query = " insert into productreviews(productID,reviewID)"
					 + " values (?,?)";
					 PreparedStatement preparedStmt = con.prepareStatement(query);
					 // binding values
					 preparedStmt.setInt(1, Integer.parseInt(productID));
					 preparedStmt.setInt(2, Integer.parseInt(reviewID));
					 
					 	 
					// execute the statement
			
					 preparedStmt.execute();
					 con.close();
					 output = "Inserted successfully";
				 }
		  	 
		}
		 	 
		
	}catch(Exception e) {
		
	}
		return output;
 }
	
	public String readUserReview(String NIC)
	 {
	 String output = "";
	 try
	 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for reading."; }
		 
		 // Prepare the html table to be displayed
		 output = "<table border='1'><tr><th>User NIC</th><th>Review ID</th><th>Review Type</th><th>Review Description</th>" +
		 "<th>Review Value</th>" +
		 "<th>Update</th><th>Remove</th></tr>";
	
		 String query = "select r.reviewID,r.reviewType,r.reviewDesc,r.reviewValue from reviews r where r.reviewID = (select u.reviewID from userreviews u where u.NIC = '"+NIC+"' )";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
		 
		 String reviewID = Integer.toString(rs.getInt("reviewID"));
		 String reviewType = rs.getString("reviewType");
		 String reviewDesc = rs.getString("reviewDesc");
		 String reviewValue = Integer.toString(rs.getInt("reviewValue"));
		 
		 
		 // Add into the html table
		 
		 output += "<td>" + NIC + "</td>";
		 output += "<td>" + reviewID + "</td>";
		 output += "<td>" + reviewType + "</td>";
		 output += "<td>" + reviewDesc + "</td>";
		 output += "<td>" + reviewValue + "</td>";
		 
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
		 + "<td><form method='post' action='payments.jsp'>"
		 + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
		 + "<input name='itemID' type='hidden' value='" + reviewID
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
	

	public String readProudctReview(String productID)
	 {
	 String output = "";
	 try
	 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for reading."; }
		 
		 // Prepare the html table to be displayed
		 output = "<table border='1'><tr><th>Product ID<th>Review ID</th><th>Review Type</th><th>Review Description</th>" +
		 "<th>Review Value</th>" +
		 "<th>Update</th><th>Remove</th></tr>";
	
		 String query = "select r.reviewID,r.reviewType,r.reviewDesc,r.reviewValue from reviews r where r.reviewID = (select p.reviewID from productreviews p where p.productID = '"+productID+"' )";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
		 
		 String reviewID = Integer.toString(rs.getInt("reviewID"));
		 String reviewType = rs.getString("reviewType");
		 String reviewDesc = rs.getString("reviewDesc");
		 String reviewValue = Integer.toString(rs.getInt("reviewValue"));
		 
		 
		 // Add into the html table
		 
		 output += "<td>" + productID + "</td>";
		 output += "<td>" + reviewID + "</td>";
		 output += "<td>" + reviewType + "</td>";
		 output += "<td>" + reviewDesc + "</td>";
		 output += "<td>" + reviewValue + "</td>";
		 
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
		 + "<td><form method='post' action='payments.jsp'>"
		 + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
		 + "<input name='itemID' type='hidden' value='" + reviewID
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
	
	

}
