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
 			@FormParam("productID") String productID, 
 		  @FormParam("creditNumber") String creditNumber,
 		  @FormParam("cvv") String cvv,
 		  @FormParam("expireDate") String expireDate,
 		 @FormParam("date") String date,
 		 @FormParam("amount") String amount)
 		 {
 		  String output = payment.insertPayment(NIC,productID,creditNumber,cvv,expireDate,date,amount);
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
 	
 	@GET
	 @Path("/total/{NIC}")
	 @Produces(MediaType.TEXT_HTML)
	 public String readItems1(@PathParam("NIC") String NIC)
	  {
	  return payment.readTotalPayment(NIC);
	  }
 	
 	@GET
	 @Path("/total/product/{productID}")
	 @Produces(MediaType.TEXT_HTML)
	 public String readItems2(@PathParam("productID") String productID)
	  {
	  return payment.readTotalPaymentForProduct(productID);
	  }
 	
 	
 	 //update part
 	@PUT
 	 @Path("/")
 	 @Consumes(MediaType.APPLICATION_JSON)
 	 @Produces(MediaType.TEXT_PLAIN)
 	 public String updateItem(String paymentData)
 	 {
 		 //Convert the input string to a JSON object
 		  JsonObject paymentObject = new JsonParser().parse(paymentData).getAsJsonObject();
 		 //Read the values from the JSON object
 		  String paymentID = paymentObject.get("paymentID").getAsString();
 		  String NIC = paymentObject.get("NIC").getAsString();
 		 String productID = paymentObject.get("productID").getAsString();
 		  String creditNumber = paymentObject.get("creditNumber").getAsString();
 		  String cvv = paymentObject.get("cvv").getAsString();
 		  String expireDate = paymentObject.get("expireDate").getAsString();
 		  String date = paymentObject.get("date").getAsString();
 		  String amount = paymentObject.get("amount").getAsString();
 		  String output = payment.updatePayment(paymentID, NIC,productID, creditNumber, cvv, expireDate,date,amount);
 		 return output;
 	 }
 	//delete part
 	@DELETE
 	 @Path("/")
 	 @Consumes(MediaType.APPLICATION_XML)
 	 @Produces(MediaType.TEXT_PLAIN)
 	 public String deleteItem(String paymentData)
 	 {
 		 //Convert the input string to an XML document
 		  Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());
 		
 		 //Read the value from the element <paymentID>
 		  String paymentID = doc.select("paymentID").text();
 		  String output = payment.deletePayment(paymentID);
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