<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration</title>
</head>
<body>
<center>
<h1>Welcome to eMarket<br>Register Here</h1>
<form action="loginservelet?action=register" method="post">
Your Name:<input type="text" name="name" value=""><br>
UserName:<input type="text" name="username" value=""><br>
Email:<input type="text" name="email" value=""><br>
Address:<input type="text" name="address" value=""><br>
Contact:<input type="text" name="contact" value=""><br>
Password:<input type="password" name="password" value=""><br>
Re-Password:<input type="password" name="repassword" value=""><br>

Register as: 
<input type="radio" name="option" value="customer">customer &nbsp &nbsp
<input type="radio" name="option" value="seller">seller  &nbsp &nbsp
<br>
<input type="submit"></input><br>
</form>
<a href="loginpage.jsp">Log In Page</a>
</center>
</body>
</html>