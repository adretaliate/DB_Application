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
if(username==null){
	%><jsp:forward page = "loginpage.jsp" /><%
}
%>
<h1>Welcome to eMarket</h1><p align="right">Hello, <%=username %>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="cart.jsp" >Cart</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href = "order.jsp">Order</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="prodservelet?action=logout">Log Out</a></p>
<form action="prodservelet?action=place_order" method="post">
<%if(id!=0){
	ArrayList<ArrayList<String>> prods = product.products(id);
	%>
	<table width="700"><tr><td><b>Product Name</td><td><b>Price</td></td></tr>
	
	<%
	for(ArrayList<String> prod : prods){
	%><tr><td><a href="product.jsp?productID=<%=prod.get(0) %>" ><%=prod.get(1)%><br></td><td><%=prod.get(2)%></td></tr><%
	}
}
%>
</table>
<br>
<br>
<br>
<a href="prodservelet?action=prev" >Prev.</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="prodservelet?action=next" >Next</a>
<br>
</form>
</body>
</html>