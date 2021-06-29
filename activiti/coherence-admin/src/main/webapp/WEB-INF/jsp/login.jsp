<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">
</head>
<body>
	<h1 align="center">Login to Admin Portal</h1>

	<div class="loginform">  
	<form action="/login" method="post">
	  <div class="imgcontainer">
	    <img src="img/img_avatar2.png" alt="Avatar" class="avatar">
	  </div>
	
	  <div class="form-group">
	    <label for="userId"><b>Username</b></label>
	    <input type="text" placeholder="Enter Username" name="userId" required value="admin">
	  </div>
	
	  <div class="form-group">
	    <label for="password"><b>Password</b></label>
	    <input type="password" placeholder="Enter Password" name="password" value="123">
	  </div>
	        
	    <button type="submit">Login</button>
	  </div>
	</form>  
	</div>
    <script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>