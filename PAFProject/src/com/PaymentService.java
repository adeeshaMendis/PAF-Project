package com;
import model.Payment;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;
@Path("/payments")
public class PaymentService
{
 	Payment payment = new Payment();
 	//insert part
 	 @POST
 	 @Path("/")
 	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
 	 @Produces(MediaType.TEXT_PLAIN)
 	 public String insertItem(@FormParam("NIC") String NIC,
 		  @FormParam("creditNumber") String creditNumber,
 		  @FormParam("cvv") String cvv,
 		  @FormParam("expireDate") String expireDate,
 		 @FormParam("date") String date,
 		 @FormParam("amount") String amount)
 		 {
 		  String output = payment.insertPayment(NIC,creditNumber,cvv,expireDate,date,amount);
 		 return output;
 	 }
 	 //read part
 	@GET
 	 @Path("/{NIC}")
 	 @Produces(MediaType.TEXT_HTML)
 	 public String readItems(@PathParam("NIC") String NIC)
 	  {
 	  return payment.readPayment(NIC);
 	  }
 	
 	
 	 //update part
 	@PUT
 	 @Path("/")
 	 @Consumes(MediaType.APPLICATION_JSON)
 	 @Produces(MediaType.TEXT_PLAIN)
 	 public String updateItem(String itemData)
 	 {
 		 //Convert the input string to a JSON object
 		  JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
 		 //Read the values from the JSON object
 		  String paymentID = itemObject.get("paymentID").getAsString();
 		  String NIC = itemObject.get("NIC").getAsString();
 		  String creditNumber = itemObject.get("creditNumber").getAsString();
 		  String cvv = itemObject.get("cvv").getAsString();
 		  String expireDate = itemObject.get("expireDate").getAsString();
 		  String date = itemObject.get("date").getAsString();
 		  String amount = itemObject.get("amount").getAsString();
 		  String output = payment.updatePayment(paymentID, NIC, creditNumber, cvv, expireDate,date,amount);
 		 return output;
 	 }
 	//delete part
 	@DELETE
 	 @Path("/")
 	 @Consumes(MediaType.APPLICATION_XML)
 	 @Produces(MediaType.TEXT_PLAIN)
 	 public String deleteItem(String itemData)
 	 {
 		 //Convert the input string to an XML document
 		  Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());
 		
 		 //Read the value from the element <itemID>
 		  String NIC = doc.select("NIC").text();
 		  String output = payment.deletePayment(NIC);
 		 return output;
 	 }
 	
 	/*@POST
	 @Path("/")
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 @Produces(MediaType.TEXT_PLAIN)
	 public String insertTotal(@FormParam("NIC") String NIC,
		  @FormParam("FromDate") String FromDate,
		  @FormParam("ToDate") String ToDate)
		  
		 {
		  String output = payment.insertTotalPayment(NIC,FromDate,ToDate);
		 return output;
	 }*/


}	 