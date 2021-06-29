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
	        <a class="nav-link" href="#">My Inbox</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="/groupinbox">Group Inbox</a>
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
		<h4>My Inbox</h4>
		<div class="table-responsive">
			<table id="mytable" class="table table-bordred table-striped">
				<thead>
					<th>Application<br/>Number</th>
					<th>Applicant<br/>First Name</th>
					<th>Applicant<br/>Last Name</th>
					<th>Application Data</th>
					<th>Submission Date</th>
					<th>Status</th>
					<th>Process</th>
					<th>Return</th>
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
									    onclick="openWorkSpace(${application.taskId}, ${application.id});">
										<i class="fas fa-edit"></i>
								</button>
								</td>
							<td><button class="btn btn-primary btn-xs" 
									    onclick="unclaim(${application.taskId});">
										<i class="fas fa-external-link-alt"></i>
								</button>
								</td>
			            </tr>
			        </c:forEach>
				</tbody>
			</table>
		</div>
	</div>
    </main><!-- /.container -->


	<div class="modal fade" id="edit" tabindex="-1" role="dialog"
		aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button>
					<h4 class="modal-title custom_align" id="Heading">Edit Your
						Detail</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<input class="form-control " type="text" placeholder="Mohsin">
					</div>
					<div class="form-group">

						<input class="form-control " type="text" placeholder="Irshad">
					</div>
					<div class="form-group">
						<textarea rows="2" class="form-control"
							placeholder="CB 106/107 Street # 11 Wah Cantt Islamabad Pakistan"></textarea>


					</div>
				</div>
				<div class="modal-footer ">
					<button type="button" class="btn btn-warning btn-lg"
						style="width: 100%;">
						<span class="glyphicon glyphicon-ok-sign"></span> Update
					</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>

	<script>
	function unclaim(taskId) {
		var r = confirm("Do you want to return this task to group inbox?");
		if (r == true) {
			$.post("/inbox/unclaim/"+taskId, function( data ) {
				location.reload();
			});		
		}
	}
	function openWorkSpace(taskId, applicationId) {
		window.location.href = "/inbox/open/"+taskId+"/"+applicationId+"/${action}";
	}
	</script>

    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/4.2.1/js/bootstrap.min.js"></script>
</body>
</html>