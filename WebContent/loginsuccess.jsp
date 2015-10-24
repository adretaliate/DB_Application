<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="database.product" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Logged IN</title>
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
		if(cookie.getName().equals("id")){
			id=Integer.parseInt(cookie.getValue());
		}
	}
}
%>
<h1>Welcome to eMarket</h1><p align="right">Hello, <%=username %>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="www.google.com" >Your Orders</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="prodservelet?action=logout">Log Out</a></p>
<%if(id!=0){
	HashMap<String, String> prods = product.products(id);
	%>
	<table width="700"><tr><td><b>Product Name</td><td><b>Price</td></td></tr>
	<form action="">
	<%
	for(String prod : prods.keySet()){
	%><tr><td><input type="checkbox" name="product" value="Bike"><%=prod%><br></td><td><%=prods[prod]%></td></tr><%
	}
}
%>
<br>
<br>
<br>
<a href="prodservelet?action=prev" >Prev.</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="prodservelet?action=next" >Next</a>
</body>
</html>