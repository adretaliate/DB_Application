<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="database.transporter" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
<h1>Welcome to eMarket</h1><p align="right">Hello, <%=username %>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="prodservelet?action=logout">Log Out</a></p>
<center><h2>Your cart</h2></center>


<%
	ArrayList<ArrayList<String>> packs = transporter.getShippedProducts(username);
	%>
	<table width="700"><tr><td><b>Product Name</b></td><td><b>Price</b></td><td><b>Quantity</b></td></tr>
	
	<%
	for(ArrayList<String> pack : packs){
	%><tr><td><%=pack.get(0)%></td><td><%=pack.get(1)%></td><td><%=pack.get(2)%></td> <%
			
	}

%>
</table>
<br>
<br>
<br>
</center>
</body>
</html>