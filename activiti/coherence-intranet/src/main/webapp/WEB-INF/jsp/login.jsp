<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="webjars/bootstrap/4.2.1/css/bootstrap.min.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">
</head>
<body style="padding-top:20px;">
	<h1 align="center">Coherence Application Management</h1>

	<div class="loginform">  
	<form action="/login" method="post">
	  <div class="imgcontainer">
	    <img src="img/backoffice.png" alt="Avatar" class="avatar">
	  </div>
	
	  <div class="form-group">
	    <label for="userId"><b>Username</b></label>
	    <input type="text" placeholder="Enter Username" name="userId" required value="po1">
	  </div>
	
	  <div class="form-group">
	    <label for="password"><b>Password</b></label>
	    <input type="password" placeholder="Enter Password" name="password" required value="123">
	  </div>
	        
	    <button type="submit">Login</button>
	  </div>
	</form>  
	</div>
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/4.2.1/js/bootstrap.min.js"></script>
</body>
</html>