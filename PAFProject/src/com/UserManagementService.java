package com;
import model.UserManagement;
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
		
		 @POST
	 	 @Path("/add")
	 	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 	 @Produces(MediaType.TEXT_PLAIN)
		 
	 	 public String insertUser(@FormParam("nic") String Unic,
	 		  @FormParam("firstName") String Ufirstname,
	 		  @FormParam("lastName") String Ulastname,
	 		  @FormParam("userType") String UType,
	 		  @FormParam("email") String Uemail,
	 		  @FormParam("phoneNumber") String UphoneNumber,
	 	 	  @FormParam("password") String Upassword)

		 	{
			 
	 		 String output = user.insertUser(Unic,Ufirstname,Ulastname,UType,Uemail,UphoneNumber,Upassword);
	 		 return output;
	 	 }
	 	 
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
	 	
		 @DELETE
	 	 @Path("/delete")
	 	 @Consumes(MediaType.APPLICATION_XML)
		 @Produces(MediaType.TEXT_PLAIN)
			
		 public String deleteUser(String itemData)
	 	 {
			 //Convert the input string to an XML document
		 	  Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());
		 		
			 //Read the value from the element <itemID>
	 		  String nic = doc.select("nic").text();
	 		  String output = user.deleteUser(nic);
	  		 return output;
		 }

		
	
}
