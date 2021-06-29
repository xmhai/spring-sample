<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="/webjars/bootstrap/4.2.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body style="padding-top:20px;">
<div class="container">
	<h3>Application Workspace</h3>
	<form action="/workspace/evaluate" method="post">
	<fieldset class="border p-2">
	  <input type="hidden" readonly id="applicationId" name="applicationId" value="${application.id}">
	  <input type="hidden" readonly id="taskId" name="taskId" value="${application.taskId}">
	  
	  <legend  class="w-auto">Applicant Information</legend>
	  <div class="form-group">
	    <label for="applicantIdNumber"><b>ID Number</b></label>
	    <input type="text" readonly class="form-control-plaintext" id="applicantIdNumber" name="applicantIdNumber" value="${application.applicantIdNumber}">
	  </div>
	  <div class="form-group">
	    <label for="applicantFirstName"><b>First Name</b></label>
	    <input type="text" readonly class="form-control-plaintext" id="applicantFirstName" name="applicantFirstName" value="${application.applicantFirstName}">
	  </div>
	  <div class="form-group">
	    <label for="applicantLastName"><b>Last Name</b></label>
	    <input type="text" readonly class="form-control-plaintext" id="applicantLastName" name="applicantLastName" value="${application.applicantLastName}">
	  </div>
	  <div class="form-group">
	    <label for="applicantEmail"><b>Email address</b></label>
	    <input type="text" readonly class="form-control-plaintext" id="applicantEmail" name="applicantEmail" value="${application.applicantEmail}">
	  </div>
	</fieldset>
	<fieldset class="border p-2">
	  <legend  class="w-auto">Application Details</legend>
	  <div class="form-group">
	    <label for="applicationData"><b>Application Data</b></label>
	    <textarea readonly class="form-control-plaintext" id="applicationData" name="applicationData" rows="1">${application.applicationData}</textarea>
	  </div>
	  <div class="form-group">
	    <label for="submissionDate"><b>Submission Date</b></label>
	    <input type="text" readonly class="form-control-plaintext" id="createdDate" name="createdDate" value="${application.createdDate}">
	  </div>
	</fieldset>
	<h4 style="color:orange;">Recommendation & Routing Details</h4>
	  <div class="form-group">
		<div class="form-check">
		  <input class="form-check-input" type="radio" name="recommendation" id="recommendation1" value="approval" checked>
		  <label class="form-check-label" for="recommendation1">
		    Recommend to approve
		  </label>
		</div>
		<div class="form-check">
		  <input class="form-check-input" type="radio" name="recommendation" id="recommendation2" value="rejected">
		  <label class="form-check-label" for="recommendation2">
		    Recommend to reject
		  </label>
		</div>	  
		<div class="form-row">
		  <div class="col-auto">
			  <div class="form-check">
				  <input class="form-check-input" type="radio" name="recommendation" id="recommendation3" value="route">
				  <label class="form-check-label" for="recommendation3">
				    Route to Officer
				  </label>
			  </div>	  
		  </div>	  
		  <div class="col-auto">
		      <input class="form-control col-lg-6" type="text" id="routeToUserId" name="routeToUserId">
		  </div>	  
		</div>	  
	  </div>
	  <a class="btn btn-dark" href="/myinbox" role="button">&lt;&nbsp;Cancel</a>
	  <button type="submit" class="btn btn-primary">Submit</button>
	</form>
</div>

    <script src="/webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.2.1/js/bootstrap.min.js"></script>
</body>
</html>