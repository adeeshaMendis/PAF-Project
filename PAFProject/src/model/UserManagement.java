package model; 

import java.sql.*; 

public class UserManagement 
{ 
	
	private Connection connect() 
	{ 
		 Connection con = null; 
		 
		 try
		 { 
			 Class.forName("com.mysql.cj.jdbc.Driver"); 
		 
			 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user_management?useServerPrepStmts=true&tinyInt1isBit=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC", "root", ""); 
		 } 
		 catch (Exception e) 
		 {
			 e.printStackTrace();
		 } 
		 return con; 
	 } 
	public String insertUser(String Unic, String Ufirstname, String Ulastname, String UType,String  Uemail,String UphoneNumber ,String Upassword ) 
	{ 
		 String output = ""; 
		 
		 try
		 { 
			 Connection con = connect(); 
			 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for inserting."; } 
			 
			 String query = " insert into user  (nic,firstName,lastName,userType,email,phoneNumber,password)"
			 + " values (?, ?, ?, ?, ?)"; 
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, Unic); 
			 preparedStmt.setString(3, Ufirstname); 
			 preparedStmt.setString(4, Ulastname); 
			 preparedStmt.setString(5, UType); 
			 preparedStmt.setString(6, Uemail); 
			 preparedStmt.setString(7, UphoneNumber); 
			 preparedStmt.setString(8, Upassword); 
			 
			 
//			 if(Unic.equals("")||Ufirstname.equals("")||Ulastname.equals("")||UType.equals("")||email.equals("")||UphoneNumber.equals("")||Upassword.equals("")) {
//					output="Please Fill the required feilds.";
//				}
//				else if(!validateNIC(Unic)) {
//					output = "Please check the NIC again.";
//				}
//				//else if((Unic.length() >=9)&&(Unic.length() <=12 )) {
//				//	output = "Please check the NIC again.";
//				//}
//				else if(!isValidEmailAddress(email)) {
//					output = "Please check the the Email.";
//				}
//				else if(UphoneNumber.length() !=10) {
//					 return "phoneNumber should be 10 digits";
//				
//				}
//				else {
				
					// execute the statement
					preparedStmt.execute();
					con.close();
					
				    output = "Inserted successfully";
		}
		
		 catch (Exception e) 
		 { 
			 output = "Error while inserting the user."; 
			 System.err.println(e.getMessage()); 
		 } 
		 
		 return output; 
	 } 
		 
		 
//	 public static boolean validateNIC(String Unic) {
//	 // Check if length is 10
//			    
//		 int length = Unic.length();
//			    if (length != 10) {
//			        return false;
//			    }
//			   
//			    // Check last character for V
//			    char lastChar = Unic.charAt(length - 1);
//			    if (lastChar != 'V') {
//			        return false;
//			    }
//
//			    // Check first 9 characters are digits
//			    for (int i = 0; i < length - 2; i++) {
//			        char currentChar = Unic.charAt(i);
//			        if (currentChar < '0' || '9' < currentChar) {
//			            return false;
//			        }
//			    }
//
//			    return true;
//	}
	 
	 
//	 public static boolean isValidEmailAddress(String email) {
//		   
//		 boolean result = true;
//		   
//		 try {
//		      InternetAddress emailAddr = new InternetAddress(email);
//		      emailAddr.validate();
//		 } 
//		 catch (AddressException ex) {
//		      result = false;
//		 }
//		 
//		 return result;
//	 }
	 
	public String readUserDetails(String nic) 
	{ 
		String output = ""; 
		
		try
		{ 
			Connection con = connect(); 
			
			 if (con == null) 
			 {
				 return "Error while connecting to the database for reading."; } 
			 
			 // Prepare the html table to be displayed
			 output = "<table border='1'>"
			 		    + "<tr>"
			 		    + "<th>First Name</th>"
			 		    + "<th>Last Name</th>" 
			            +"<th>NIC</th>"
			            + "<th>User Type</th>"
			            +"<th>Eamil</th>"
			            +"<th> Phone Number</th></tr>";
			             
			 String query = "SELECT * FROM user where nic='"+nic+"'";
			 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
				 String userID   = Integer.toString(rs.getInt("userID")); 
				 String firstName= rs.getString("firstName"); 
				 String lastName = rs.getString("lastName"); 
				 String nic1= rs.getString("nic"); 
				 String userType = rs.getString("userType"); 
				 String email    = rs.getString("email"); 
				 String phoneNumber = rs.getString("phoneNumber"); 
				 
				 
			 // Add into the html table
			 output += "<tr><td>" + firstName + "</td>"; 
			 output += "<td>" + lastName + "</td>"; 
			 output += "<td>" + nic1 + "</td>"; 
			 output += "<td>" + userType + "</td>"; 
			 output += "<td>" + email + "</td>"; 
			 output += "<td>" + phoneNumber + "</td>"; 
			 
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
	
	 public String UpdateUserDetails(String userID , String Ufirstname, String Ulastname,String Unic, String UType,String  Uemail,String UphoneNumber ,String Upassword)
	 { 
		 String output="";
		 try
		 { 
		
			 Connection con = connect(); 
			 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for updating."; } 
			 
		 
			 // create a prepared statement
		 	 String query = "UPDATE user SET firstName=?,lastName=?,userType=?,email=?,userType=?,email=?phoneNumber=?,password=? WHERE userID='"+userID+"'"; 
		 	 
			 	PreparedStatement prepStatement = con.prepareStatement(query);
				prepStatement.setString(1, Ufirstname);
				prepStatement.setString(2, Ulastname);
				prepStatement.setString(3, Unic);
				prepStatement.setString(4, UType);
				prepStatement.setString(5, Uemail);
				prepStatement.setString(6, UphoneNumber);
				prepStatement.setString(7, Upassword);
			
				prepStatement.execute();
				
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
	
	
	public String deleteUser(String nic)
	 {
	 String output = "";
	 try
	 {
		 Connection con = connect();
		 
		 if (con == null)
		 {
			 return "Error while connecting to the database for deleting."; 
		 }
		 // create a prepared statement
		 String query = "delete from user where nic=?";
		 
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setString(1, nic);
		 // execute the statement
		 preparedStmt.execute();
		 
		 con.close();
		 output = "Deleted successfully";
	 }
	 catch (Exception e)
	 {
		 output = "Error while deleting the user.";
		 System.err.println(e.getMessage());
	 }
	 return output;
	 }
} 


