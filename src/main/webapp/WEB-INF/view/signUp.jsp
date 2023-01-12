<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign Up form</title>
</head>
<body>

	<div align="center">
		
		<h3>SignUp</h3>
		
		<form:form action="add-User" modelAttribute="user" method="POST">
			
			<label>Name :  </label>
			<form:input path="name" placeholder="Enter your Name "/><br>
			
			<label>Email :  </label>
			<form:input path="email" placeholder="Enter your Email "/><br>
			
			<label>Phone :  </label>
			<form:input path="phone" placeholder="Enter your Phone "/><br>
			
			<label>Password : </label>
			<form:input path="password" placeholder="Enter your password "/><br>
			
			<input type="submit" value="submit">
		</form:form>
		
	</div>

</body>
</html>