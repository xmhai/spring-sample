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
	    Admin Portal
	  </a>
    </div>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="#">Welcome <b>${userId}</b></a></li>
      <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
    </ul>
  </div>
</nav>

	<div class="container">
			<div class="col-md-12">
				<h4>Processes</h4>
<form action="/deploy" method="post" enctype="multipart/form-data">
<label class="btn btn-default">
    <input type="file" id="file" name="file">
</label>
<button type="submit">Deploy</button>
</form>				
				<div class="table-responsive">
					<table id="mytable" class="table table-bordred table-striped">
						<thead>
							<th>Id</th>
							<th>Name</th>
							<th>Description</th>
							<th>Version</th>
							<th>Deployment Time</th>
							<th>View</th>
						</thead>
						<tbody>
					       <c:forEach items="${processes}" var="process">
					            <tr>
									<td><c:out value="${process.id}"/></td>
									<td><c:out value="${process.name}"/></td>
									<td><c:out value="${process.description}"/></td>
									<td><c:out value="${process.version}"/></td>
									<td><fmt:formatDate value="${process.deploymentTime}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
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

    <script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>