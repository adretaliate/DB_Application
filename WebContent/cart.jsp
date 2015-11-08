<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="database.product" %>
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
if(username==null){
	%><jsp:forward page = "loginpage.jsp" /><%
}
%>
<h1>Welcome to eMarket</h1><p align="right">Hello, <%=username %>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="order.jsp" >Your Orders</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="prodservelet?action=logout">Log Out</a></p>
<center><h2>Your cart</h2></center>

<form action="prodservelet?action=edit_or_delete" method="post">

<%
	ArrayList<ArrayList<String>> prods = product.getCart(username);
	%>
	<table width="700"><tr><td><b>Product Name</td><td><b>Price</td><td><b>Quantity</td></tr>
	
	<%
	for(ArrayList<String> prod : prods){
	%><tr><td><%=prod.get(0)%></td><td><%=prod.get(1)%></td><td><%=prod.get(2)%></td> <td><button type="submit" name="delete" value="<%=prod.get(3)%> <%=prod.get(4)%>">Delete item</button></td><td><input type="number" min="0" max="100" name="<%= prod.get(3)%> <%= prod.get(4)%>" maxlength="3" value="<%=prod.get(2)%> "></td><td><button type="submit" name="edit" value="<%=prod.get(3)%> <%=prod.get(4)%>">Edit item</button></td></tr><%
			
	}

%>
</table>
</form>
<br>
<br>
<br>

<form action="prodservelet?action=place_order" method="post">
	<button type="submit" name="place_order">Place Order</button>
</form>

</body>
</html>