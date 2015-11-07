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
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="sellerServlet?action=logout">Log Out</a></p>

<center><b></>Select from list below:</b></center><br>
<form action="sellerServlet?action=addNewItem" method="post">
<table width="700"><tr><td><b>Product Name</td><td><b>Features</td><td><b>Give Price</b></td><td><b>Add Quantity</td></tr>

<%
ArrayList<ArrayList<String>> prods= seller.getOthersProducts(username);

for(ArrayList<String> prod : prods){
	
%><tr><td><%=prod.get(1)%></td> <td><%=prod.get(2)%></td> <td><input type="number" min="0" step="any" name="price=<%=prod.get(0)%>" ></td><td><input type="number" min="0" max="1000" name="quantity=<%=prod.get(0)%>"  ></td><td><button type="submit" name="approvalPendingOld" value="<%=prod.get(0)%>">Submit for Approval</button></td></tr><%
		
}



%>
</table><br><br>

<center><b>To Add new item other than the above list, please fill the form below.</b></center><br>
Product name:
  <input type="text" name="pName" value="Product Name">
  <br><br>
Features:
  <input type="text" name="features" value="Features">
  <br><br>
 Price:
  <input type="numeric"  step="any" name="price" value="price">
  <br><br>
  Quantity:
  <input type="numeric" min="1" name="quantity" value="quantity">
  <br><br>
  <button type="submit" name="approvalPendingNew" value="Submit">Submit for Approval</button>

</form>
<br><br>
<center><a href='seller.jsp'>Go to item page</a></center>

</body>
</html>