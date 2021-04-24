package com;
import model.UserManagement;

import java.sql.SQLException;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

	
	
@Path("/users") 
public class UserManagementService 
{ 
	UserManagement  user = new UserManagement(); 
		
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readItems() 
	{ 
	 return user.readAllUsers(); 
	}
		
		
	 @POST
	 @Path("/add")
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 @Produces(MediaType.TEXT_PLAIN)
	 public String insertUser(@FormParam("firstName") String firstName,
	 		@FormParam("lastName") String lastName,
	 		@FormParam("nic") String nic,
	 		@FormParam("userType") String userType,
	 		@FormParam("email") String email,
	 		@FormParam("phoneNumber") String phoneNumber,
	 	 	 @FormParam("password") String password){
			 
	 		String output = user.insertUser(firstName, lastName, nic, userType, email, phoneNumber, password);
	 		return output;
	 }
	 	 //get details according to the NIC
		 @GET
	 	 @Path("/{nic}")
	 	 @Produces(MediaType.TEXT_HTML)
	 	
		 public String readUserDetails(@PathParam("nic") String Unic)
	 	 {
			 return user.readUserDetails(Unic);
	 	 }
		 
		 @GET
	 	 @Path("/test")
	 	 @Produces(MediaType.TEXT_HTML)
	 	
		 public String test()
	 	 {
			 return "test";
	 	 }
		 
		 
		 @PUT
	 	 @Path("/update")
	 	 @Consumes(MediaType.APPLICATION_JSON)
	 	 @Produces(MediaType.TEXT_PLAIN)
		 
	 	 public String UpdateUserDetails(String itemData)
	 	 {
	 		 //Convert the input string to a JSON object
	 		  JsonObject userObject = new JsonParser().parse(itemData).getAsJsonObject();
	 		  
	 		 //Read the values from the JSON object
	 		  String userID      = userObject.get("userID").getAsString();
	 		  String firstName   = userObject.get("firstName").getAsString();
	 		  String lastName    = userObject.get("lastName").getAsString();
	 		  String nic         = userObject.get("nic").getAsString();
	 		  String utype         = userObject.get("userType").getAsString();
	 		  String email       = userObject.get("email").getAsString();
	 		  String phoneNumber = userObject.get("phoneNumber").getAsString();
	 		  String password    = userObject.get("password").getAsString();
	 		  
	 		  String output = user.UpdateUserDetails(userID,firstName,lastName,nic,utype,email,phoneNumber,password);
	 		  return output;
	 	 }
	 	
		@POST
		@Path("/Delete")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_HTML)
		public String Delete(@FormParam("userID") String userID) throws SQLException
		{
				String output =user.deleteUser(userID);
				return output;

		}
		
		
		@DELETE
		@Path("/postDelete") 
		@Consumes(MediaType.APPLICATION_XML) 
		@Produces(MediaType.TEXT_PLAIN) 
		public String deleteItem(String userData) 
		{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(userData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <itemID>
		 String userID = doc.select("userID").text(); 
		 String output = user.deleteUser(userID); 
		return output; 
		}


		
	
}
