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

public class PaymentService {
	Payment payment = new Payment();
	
	@GET
	@Path("/{NIC}")
	@Produces(MediaType.TEXT_HTML)
	public String readPayments(@PathParam("NIC") String NIC) {
		return payment.readPayments(NIC);
	}
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
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String itemData)
	{
	//Convert the input string to a JSON object
	JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
	//Read the values from the JSON object
	String itemID = itemObject.get("paymentID").getAsString();
	String itemCode = itemObject.get("NIC").getAsString();
	String itemName = itemObject.get("creditNumber").getAsString();
	String itemPrice = itemObject.get("cvv").getAsString();
	String itemDesc = itemObject.get("expireDate").getAsString();
	String itemDesc1 = itemObject.get("date").getAsString();
	String itemDesc2 = itemObject.get("amount").getAsString();
	String output = payment.updatePayment(itemID, itemCode, itemName, itemPrice, itemDesc,itemDesc1,itemDesc2);
	return output;
	}
	
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
	
}
