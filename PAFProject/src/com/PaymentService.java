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

}	 