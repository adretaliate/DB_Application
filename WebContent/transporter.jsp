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
if(username==null){
	%><jsp:forward page = "loginsuccess.jsp" /><%
}
%>
<h1>Welcome to eMarket</h1><p align="right">Hello, <%=username %>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="shipped.jsp" >Shipped packages</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="prodservelet?action=logout">Log Out</a></p>
<center><h2>Your cart</h2></center>

<form action="transporterservelet?action=editlocation" method="post">

<%
	ArrayList<ArrayList<String>> packs = transporter.getPendingProducts(username);
	%>
	<table width="700"><tr><td><b>OrderID</td><td><b>Product Name</td><td><b>Quantity</td><td><b>Address</td><td><b>CurrentLocation</td><td><b>CustomerName</td><td><b>CustomerContact</td></tr>
	
	<%
	for(ArrayList<String> pack : packs){
	%><tr><td><%=pack.get(7)%></td><td><%=pack.get(0)%></td><td><%=pack.get(1)%></td><td><%=pack.get(2)%></td> <td><%=pack.get(3)%></td><td><%=pack.get(5)%></td><td><%=pack.get(6)%></td> <td><button type="submit" name="deliver" value="<%=pack.get(4)%>">Delivered</button></td><td><input type="text" name="<%= pack.get(4)%>" value=""></td><td><button type="submit" name="changecurrentlocation" value="<%=pack.get(4)%>">Update location</button></td></tr><%															
	}

%>
</table>
</form>

</body>
</html>