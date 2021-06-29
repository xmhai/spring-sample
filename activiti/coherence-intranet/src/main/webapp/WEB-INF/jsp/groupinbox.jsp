<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="webjars/bootstrap/4.2.1/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
</head>
<body>
	<nav class="navbar class="navbar navbar-expand-lg navbar-light">
		<a class="navbar-brand">
			<span><img src="img/coherence.png" width="30" height="30" class="d-inline-block align-top" alt=""></span>
			Application Management
		</a>
		
	    <ul class="nav mr-auto">
	      <li class="nav-item">
	        <a class="nav-link" href="/myinbox">My Inbox</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="#">Group Inbox</a>
	      </li>
	    </ul>    
	    
	    <ul class="nav justify-content-end">
	      <li class="nav-item">
	      	<a class="nav-link" href="#">Welcome <b>${userId}</b></a>
	      </li>
	      <li class="nav-item">
	      	<a class="nav-link" href="/logout"><i class="fas fa-sign-out-alt"></i> Logout</a>
	      </li>
	    </ul>
	</nav>

    <main role="main" class="container">
	<div style="padding-top: 20px;">
			<h4>Group Inbox</h4>
			<div class="table-responsive">
				<table id="mytable" class="table table-bordred table-striped">
					<thead>
						<th>Application<br/>Number</th>
						<th>Applicant<br/>First Name</th>
						<th>Applicant<br/>Last Name</th>
						<th>Application Data</th>
						<th>Submission Date</th>
						<th>Status</th>
						<th>Acquire</th>
					</thead>
					<tbody>
				       <c:forEach items="${applications}" var="application">
				            <tr>
								<td><c:out value="${application.applicationNumber}"/></td>
								<td><c:out value="${application.applicantFirstName}"/></td>
								<td><c:out value="${application.applicantLastName}"/></td>
								<td><c:out value="${application.applicationData}"/></td>
								<td><fmt:formatDate value="${application.createdDate}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
								<td><c:out value="${application.status}"/></td>
								<td><button class="btn btn-primary btn-xs" 
										    onclick="acquire(${application.taskId});">
											<i class="fas fa-inbox"></i>
									</button>
									</td>
				            </tr>
				        </c:forEach>
					</tbody>
				</table>
			</div>

		</div>
    </main><!-- /.container -->

	<script>
	function acquire(taskId) {
		var r = confirm("Do you want to acquire this task?");
		if (r == true) {
			$.post("/inbox/acquire/"+taskId, function( data ) {
				location.reload();
			});		
		}
	}
	</script>

    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/4.2.1/js/bootstrap.min.js"></script>
</body>
</html>