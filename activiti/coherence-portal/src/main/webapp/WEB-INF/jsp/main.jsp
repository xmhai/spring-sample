<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
	  <a class="navbar-brand">
	    <span><img src="img/coherence.png" width="30" height="30" class="d-inline-block align-top" alt=""></span>
	    Coherence
	  </a>
    </div>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="#">Welcome <b>${userId}</b></a></li>
      <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
    </ul>
  </div>
</nav>

	<div class="container">
		<div class="row">


			<div class="col-md-12">
				<h4>My Applications</h4>
				<a class="btn btn-success" role="button" href="/apply">New Application</a>
				<div class="table-responsive">
					<table id="mytable" class="table table-bordred table-striped">
						<thead>
							<th>Application<br/>Number</th>
							<th>Applicant<br/>First Name</th>
							<th>Applicant<br/>Last Name</th>
							<th>Application Data</th>
							<th>Submission Date</th>
							<th>Status</th>
							<th>View</th>
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
									<td><p data-placement="top" data-toggle="tooltip"
											title="View">
											<button class="btn btn-primary btn-xs" data-title="View"
												data-toggle="modal" data-target="#view">
												<span class="glyphicon glyphicon-eye-open"></span>
											</button>
										</p></td>
					            </tr>
					        </c:forEach>
						</tbody>
					</table>
				</div>

			</div>
		</div>
	</div>


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

    <script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>