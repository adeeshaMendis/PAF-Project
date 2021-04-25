package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Review;

@Path("/")
public class ReviewService {
	Review review = new Review();
 	//insert part
 	 @POST
 	 @Path("reviews")
 	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
 	 @Produces(MediaType.TEXT_PLAIN)
 	 public String insertReview(@FormParam("reviewType") String reviewType,
 			@FormParam("reviewDesc") String reviewDesc, 
 		  @FormParam("reviewValue") String reviewValue)
 		  
 		 {
 		  String output = review.insertReview(reviewType,reviewDesc,reviewValue);
 		 return output;
 	 }
 	 
 	@POST
	 @Path("userreviews")
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 @Produces(MediaType.TEXT_PLAIN)
	 public String insertItem1(@FormParam("NIC") String NIC,
			@FormParam("reviewID") String reviewID) 
		  		  
		 {
		  String output = review.insertUserReview(NIC,reviewID);
		 return output;
	 }
 	 
 	@POST
	 @Path("productreviews")
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 @Produces(MediaType.TEXT_PLAIN)
	 public String insertItem2(@FormParam("productID") String productID,
			@FormParam("reviewID") String reviewID) 
		  		  
		 {
		  String output = review.insertProductReview(productID,reviewID);
		 return output;
	 }
 	 
 	@GET
	 @Path("reviews")
	 @Produces(MediaType.TEXT_HTML)
	 public String readItems()
	  {
	  return review.readReview();
	  }
 	
 	@PUT
	 @Path("reviews")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.TEXT_PLAIN)
	 public String updateItem(String reviewData)
	 {
		 //Convert the input string to a JSON object
		  JsonObject reviewObject = new JsonParser().parse(reviewData).getAsJsonObject();
		 //Read the values from the JSON object
		  String reviewID = reviewObject.get("reviewID").getAsString();
		  String reviewType = reviewObject.get("reviewType").getAsString();
		 String reviewDesc = reviewObject.get("reviewDesc").getAsString();
		  String reviewValue = reviewObject.get("reviewValue").getAsString();
		  
		  String output = review.updateReview(reviewID,reviewType,reviewDesc,reviewValue);
		 return output;
	 }
 	
 	@DELETE
	 @Path("reviews")
	 @Consumes(MediaType.APPLICATION_XML)
	 @Produces(MediaType.TEXT_PLAIN)
	 public String deleteItem(String reviewData)
	 {
		 //Convert the input string to an XML document
		  Document doc = Jsoup.parse(reviewData, "", Parser.xmlParser());
		
		 //Read the value from the element <reviewID>
		  String reviewID = doc.select("reviewID").text();
		  String output = review.deleteReview(reviewID);
		 return output;
	 }
 	
 	@GET
	 @Path("reviews/{NIC}")
	 @Produces(MediaType.TEXT_HTML)
	 public String readItems1(@PathParam("NIC") String NIC)
	  {
	  return review.readUserReview(NIC);
	  }
	/*
 	@GET
	 @Path("reviews/{productID}")
	 @Produces(MediaType.TEXT_HTML)
	 public String readItems2(@PathParam("productID") String productID)
	  {
	  return review.readProudctReview(productID);
	  }
	
 	*/
}
