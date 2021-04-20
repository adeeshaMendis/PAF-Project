package com;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;

import com.google.gson.*;

import model.Product;
 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

@Path("/products")
public class ProductService {

	Product pobj = new Product();

	@GET
	@Path("/{nic}")
	@Produces(MediaType.TEXT_HTML)
	public String readProducts(@PathParam("nic") String nic) {
		return pobj.readProducts(nic);
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertProduct(@FormParam("NIC") String nic,@FormParam("productCode") String pCode, @FormParam("productName") String pName,
			@FormParam("version") String pversion, @FormParam("description") String pDesc,@FormParam("amount") String pPrice) {
		String output = pobj.insertProduct(nic,pCode,pName,pversion,pDesc,pPrice);
		return output;
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateProduct(String productData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject(); 
	//Read the values from the JSON object
	 String pID =  productObject.get("productId").getAsString(); 
	 String pCode = productObject.get("productCode").getAsString();
	 String pName = productObject.get("productName").getAsString();
	 String pVersion = productObject.get("version").getAsString();
	 String pDesc = productObject.get("description").getAsString();
	 String pPrice = productObject.get("amount").getAsString(); 
	
	 String output = pobj.updateProduct(pID,pCode,pName,pVersion,pDesc,pPrice);
	return output; 
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteProduct(String productData) 
	{ 
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(productData, "", Parser.xmlParser()); 
	 
	 String pID = doc.select("productId").text(); 
	 String output = pobj.deleteProduct(pID); 
	return output; 
	}


}
