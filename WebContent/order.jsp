<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="database.product" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Your orders</title>
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
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="prodservelet?action=logout">Log Out</a></p>
<%
HashMap<Integer, String> date= product.getOrderDate(username);
HashMap<Integer, ArrayList<List<String>> > orders = product.getOrders(username); 
HashMap<Integer, Boolean> review = product.checkReview(username);
System.out.println("functions are correct");
%>
<form action="prodservelet?action=review" method="post">
<table width="700">
<tr><td align="center"><b>OrderID</b></td><td align="center"><b>Order Date</b></td><td align="center"><b>Product Name</b>
</td><td align="center"><b>Quantity</b><td align="center"><b>Price</b></td><td align="center"><b>Current Location</b></td><td align="center"><b>Give Rating</b></td></td>
<td align="center"><b>Write Review</b></td></tr>
<% 
for(Integer orderID: orders.keySet()){
	System.out.println(orderID);
	%><tr><td align="center"><%=orderID %></td><td align="center"><%=date.get(orderID)%></td><%
	for(List<String> pack: orders.get(orderID)){
		System.out.println(orderID);
		System.out.println(pack.get(4));
		System.out.println(review.get(1));
		if(!review.get(Integer.parseInt(pack.get(4))) && pack.get(3).equals("Delivered")){
					System.out.println(orderID);
					 %><td align="center"><%=pack.get(0)%></td><td align="center"><%=pack.get(1)%></td><td align="center"><%=pack.get(2)%></td><td align="center"><%=pack.get(3)%></td><td><input type="number" name="rating<%= pack.get(4)%>" min="1" max="5" value="Write a review"></td><td><input type="text" name="<%= pack.get(4)%>" maxlength="100" value="Write a review"></td><td><button type="submit" name="review" value="<%=pack.get(4)%>">Edit item</button></td></tr><%
			}
		else{
			%><td align="center"><%=pack.get(0)%></td><td align="center"><%=pack.get(1)%></td><td align="center"><%=pack.get(2)%></td><td align="center"><%=pack.get(3)%></td></tr><%
		}
	}
}
%>
</table>
</form>
<a href="loginsuccess.jsp">Go to Item page</a>
</center>
</body>
</html>