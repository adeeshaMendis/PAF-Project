<%@ page import="model.Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	<% 
 //Insert item---------------------------------- 
if (request.getParameter("productCode") != null) 
 { 
 Product pObj = new Product(); 
 String stsMsg = pObj.insertProduct(request.getParameter("productCode"), 
 request.getParameter("productName"), 
 request.getParameter("version"), 
 request.getParameter("productdescription"),
 request.getParameter("productPrice")); 
 session.setAttribute("statusMsg", stsMsg); 
 } 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Product Management</title>
<link rel="stylesheet" type="text/css" href="Styles/style.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>

<body>

	<header >
		<nav class="navbar navbar-style">
			<div class="container">

				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#micon">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a href=""><h3>Gadget Badget Softwares</h3></a>
				</div>
				<div class="collapse navbar-collapse" id="micon">

					<ul class="nav navbar-nav navbar-right">
						<li><a href="">Review</a></li>
						<li><a href="">Register</a></li>
						<li><a href="">Logout</a></li>
					</ul>
				</div>
			</div>
		</nav>
	</header>
<div class="container">
 <div class="row">
 <div class="col">
 
 <h1 style="color:orange">Sell Your Product Now!!!!!!!</h1>

	<form class="needs-validation" novalidate>
	
 
   <div class="col-md-4 mb-3">
      <label for="validationCustom01">Product Code</label>
      <input type="text" class="form-control" id="validationCustom01"required>
  
      <label for="validationCustom01">Product Name</label>
      <input type="text" class="form-control" id="validationCustom01"required>
  
 	  <label for="validationCustom01">Product Version</label>
      <input type="text" class="form-control" id="validationCustom01"required>
  
      <label for="validationCustom01">Description</label>
      <input type="text" class="form-control" id="validationCustom01"required>
   
      <label for="validationCustom01">Product Amount</label>
      <input type="text" class="form-control" id="validationCustom01"required><br><br>
      
      <button class="btn btn-primary" type="submit">ADD PRODUCT</button>
  
    </div>
   
   
  
   </form>
     </div>
 </div>
</div>

<br/><br/>
    	<footer>
		<div class="footer-top">
			<div class="container">
				<div class="row">
					<div
						class="col-md-3 col-sm-6 col-xs-12 segment-one md-mb-30 sm-mb-30">
						<h2 class="footerh2">About Us</h2>
						<p class="fpara">Gadget Badget softwares is providing online
							platform to sell innovative software products. We are here to
							give you the best opportunity to sell your software products.</p>
					</div>
					<div
						class="col-md-3 col-sm-6 col-xs-12 segment-two md-mb-30 sm-mb-30">
						<h2 class="footerh2">Contact Us</h2>
						<ul>
							<li>+9489623322</li>
							<li>+1148962332</li>
							<li><a href="#"></a>gbsoftware@yahoo.com</li>
						</ul>
					</div>
					<div class="col-md-3 col-sm-6 col-xs-12 segment-three sm-mb-30">
						<h2 class="footerh2">Follow Us</h2>
						<p class="fpara">Please follow us on our Social Media Profile
							in order to keep updated.</p>
						<a href="#"><i class="fa fa-facebook"></i></a> <a href="#"><i
							class="fa fa-twitter"></i></a> <a href="#"><i
							class="fa fa-pinterest"></i></a>
					</div>
					<div class="col-md-3 col-sm-6 col-xs-12 segment-four sm-mb-30">
						<h2 class="footerh2">Reviews</h2>
						<p class="fpara">Do not forget to give your comments on our
							service</p>

					</div>
				</div>
			</div>
		</div>
		<p class="footer-bottom-text">All Right Reserved by Gadget Badget</p>
	</footer>
	
	<%
 Product pObj = new Product(); 
 out.print(pObj.readItems()); 
%>
</body>

</html>