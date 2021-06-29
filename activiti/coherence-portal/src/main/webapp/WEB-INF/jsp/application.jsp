<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
	<form action="/submit" method="post">
	<h3 style="color:orange;">Applicant Information</h3>
	  <div class="form-group">
	    <label for="applicantIdNumber">ID Number</label>
	    <input type="text" class="form-control" id="applicantIdNumber" name="applicantIdNumber">
	  </div>
	  <div class="form-group">
	    <label for="applicantFirstName">First Name</label>
	    <input type="text" class="form-control" id="applicantFirstName" name="applicantFirstName">
	  </div>
	  <div class="form-group">
	    <label for="applicantLastName">Last Name</label>
	    <input type="text" class="form-control" id="applicantLastName" name="applicantLastName">
	  </div>
	  <div class="form-group">
	    <label for="applicantEmail">Email address</label>
	    <input type="email" class="form-control" id="applicantEmail" name="applicantEmail">
	  </div>
	<h3 style="color:orange;">Application Details</h3>
	  <div class="form-group">
	    <label for="applicationData">Application Data</label>
	    <textarea class="form-control" id="applicationData" name="applicationData" rows="3"></textarea>
	  </div>
	<h3 style="color:orange;">Declaration</h3>
  	  <div class="form-check">
	    <input type="checkbox" class="form-check-input" id="declaration" name="declaration">
	    <label class="form-check-label" for="declaration">I agree with the terms and conditions</label>
	  </div>
	  <button type="submit" class="btn btn-primary">Submit</button>
	</form>
</div>

    <script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>