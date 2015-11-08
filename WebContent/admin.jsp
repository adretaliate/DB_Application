<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="database.admin" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Administrator</title>
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
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="adminservelet?action=add">Add new Admin/transporter</a>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="prodservelet?action=logout">Log Out</a></p>

<form action="adminservelet?action=approve_or_reject" method="post">

<%
ArrayList<ArrayList<String>> approve= admin.ApproveProducts();
%>
	<table width="700"><tr><td><b>Product Name</td><td><b>Price</td><td><b>Quantity</td><td><b>Seller name</td><td><b>Seller Username</td><td><b>Features</td><td><b>Contact</td></tr>
	
	<%
	for(ArrayList<String> prod : approve){
	%><tr><td><%=prod.get(0)%></td><td><%=prod.get(2)%></td><td><%=prod.get(3)%></td><td><%=prod.get(4)%></td><td><%=prod.get(5)%></td><td><%=prod.get(1)%></td><td><%=prod.get(6)%></td><td><input type="number" min="0" max="100" name="<%= prod.get(7)%> <%= prod.get(5)%> <%= prod.get(8)%> <%= prod.get(9)%>" maxlength="3" value="discount"></td> <td><button type="submit" name="approve" value="<%=prod.get(7)%> <%=prod.get(5)%> <%= prod.get(8)%> <%= prod.get(9)%>">Approve</button></td><td><button type="submit" name="reject" value="<%=prod.get(9)%>">Reject</button></td><%
			
	}

%>
</table>
</form>
</center>
</body>
</html>