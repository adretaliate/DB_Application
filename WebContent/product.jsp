<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="database.product" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LogIn</title>
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
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="cart.jsp" >Cart</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="prodservelet?action=logout">Log Out</a></p>
<%
String productID=request.getParameter("productID");
ArrayList<ArrayList<String> > sellers = product.getSellers(productID);
%>
<form action="prodservelet?productID=<%=productID%>" method="post">
<table width="700"><tr><td><b>SellerName</td><td><b>Price</td><td><b>Discount</td><td><b>Discounted Price</td></tr>

<%
for(ArrayList<String> seller : sellers){
	%><tr><td><%=seller.get(0)%></td><td><%=seller.get(1)%></td><td><%=seller.get(2)%></td><td><%=seller.get(3)%></td><td><button type="submit" name="addtocart" value="<%=seller.get(4)%>">AddToCart</button></td></tr><%
}

%>
</table>
</form>

</center>
</body>
</html>