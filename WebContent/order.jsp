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
%>
<h1>Welcome to eMarket</h1><p align="right">Hello, <%=username %>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="prodservelet?action=logout">Log Out</a></p>
<%
HashMap<Integer, String> date= product.getOrderDate(username);
HashMap<Integer, List<List<String>> > orders = product.getOrders(username); 
HashMap<Integer, Boolean> review = product.checkReview(username);
%>
<form action="prodservelet?action=review" method="post">
<%

for(Integer orderID: orders.keySet()){
	%><table width="700"><tr><td><b>OrderID: <%=orderID %></td><td><b>OrderDate: <%=date.get(orderID) %></td></td></tr><%
	for(List<String> pack: orders.get(orderID)){
		%><tr><td><%=pack.get(0)%></td><td><%=pack.get(1)%></td><td><%=pack.get(2)%></td><td><%=pack.get(3)%></td><%
				if(review.get(pack.get(4))){
					%><td><input type="text" name="<%= pack.get(4)%>" maxlength="100" value="Write a review"></td><td><button type="submit" name="review" value="<%=pack.get(4)%>">Edit item</button></td><%
				}
	}
	%></table><%
}
%>
</form>
<a href="loginsuccess.jsp">Go to Item page</a>
</center>
</body>
</html>