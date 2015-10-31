<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="database.seller" %>
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
		if(cookie.getName().equals("id")){
			id=Integer.parseInt(cookie.getValue());
		}
	}
}
%>
<h1>Welcome to eMarket</h1><p align="right">Hello, <%=username %>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="addNewItem.jsp" >Add New Item</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="sellerServlet?action=logout">Log Out</a></p>
<center><h2>Your Current Products</h2></center>
<%
ArrayList<ArrayList<String>> prods= seller.getProducts(username);
%>
<form action="sellerServlet?action=editStock" method="post">
<table width="700"><tr><td><b>Product Name</td><td><b>Price</td><td><b>Quantity</td><td><b>Change Price</b></td><td><b>Edit stock</td></tr>

<%
for(ArrayList<String> prod : prods){
	System.out.println(seller.moneyToDouble(prod.get(2)).toString());
%><tr><td><a href="sellerProduct.jsp?prodid='<%=prod.get(0)%>'"><%=prod.get(1)%></a></td><td><%=prod.get(2)%></td> <td><%=prod.get(3)%></td> <td><input type="number" min="0" name="price=<%=prod.get(0)%>" value="<%=seller.moneyToDouble(prod.get(2)).toString()%>"></td><td><input type="number" min="0" max="100" name="quantity=<%=prod.get(0)%>" value="<%=prod.get(3)%>" maxlength="3"></td><td><button type="submit" name="edit" value="<%=prod.get(0)%>">Edit stock</button></td></tr><%
		
}



%>
</form>

</body>
</html>