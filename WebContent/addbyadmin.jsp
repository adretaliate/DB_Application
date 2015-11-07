<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add New Admin/transporter</title>
</head>
<body>
<center>
<%
String username=null;
Integer id=0;
Cookie[] cookies=request.getCookies();
if(cookies!=null){
	for(Cookie cookie: cookies){
		if(cookie.getName().equals("username")){
			username=cookie.getValue();
		}
	}
}
%>
<h1>Welcome to eMarket<br></h1><h2>Add new Admin/transporter</h2>
<p align="right">Hello, <%=username %>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="prodservelet?action=logout">Log Out</a></p>

<form action="adminservelet?action=addbyadmin" method="post">
Your Name:<input type="text" name="name" value=""><br>
UserName:<input type="text" name="username" value=""><br>
Email:<input type="text" name="email" value=""><br>
Address:<input type="text" name="address" value=""><br>
Contact:<input type="text" name="contact" value=""><br>
Designation:<input type="text" name="designation" value="Leave blank for adding a transporter"><br>
Password:<input type="password" name="password" value=""><br>
Re-Password:<input type="password" name="repassword" value=""><br>

Register as: 
<input type="radio" name="option" value="transporter">transporter &nbsp &nbsp
<input type="radio" name="option" value="admin">admin  &nbsp &nbsp
<br>
<input type="submit"></input><br>
</form>
<a href="admin.jsp">Main Page</a>
</center>
</body>
</html>